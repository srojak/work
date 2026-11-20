/**
 * Copyright © 2026 Stephen Rojak.
 * 
 * This file is part of the srojak Java portfolio.
 * 
 * The srojak Java portfolio is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 3 of the License.
 * 
 * The srojak Java portfolio is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this portfolio.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package srojak.sql.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import srojak.collate.Collation;
import srojak.collate.CollationCatalog;
import srojak.collate.CollationComparisonRule;
import srojak.collate.CollationDefinition;
import srojak.collate.DecoratedCollation;
import srojak.core.data.DataErrorSeverity;
import srojak.core.excepts.KeyNotFoundException;
import srojak.core.keys.ClassKey;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.specialized.IntegerCounter;
import srojak.mantle.decorated.DecoratorIntValue;
import srojak.sql.DatabaseAction;
import srojak.sql.MappedObjectException;
import srojak.sql.SQLCollation;
import srojak.sql.ValueBindingException;
import srojak.sql.collate.SQLCollationCatalog;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.mapobj.ColumnSource;
import srojak.sql.mapobj.ColumnTimeZone;
import srojak.sql.mapobj.KeyColumn;
import srojak.sql.mapobj.MappedObjectProperty;
import srojak.sql.mapobj.MappedObjectSource;
import srojak.sql.mapobj.ObjectMapping;
import srojak.sql.mapobj.ObjectMappingIssue;
import srojak.sql.mapobj.SingleTableSource;
import srojak.sql.mapobj.TextColumnSource;
import srojak.sql.mapobj.UseCollation;
import srojak.sql.mapobj.UseSQLCollation;
import srojak.sql.mapobj.factories.MapObjPropertyFactoryStore;
import srojak.sql.mapobj.factories.MappedObjectPropertyFactory;
import srojak.sql.mapobj.factories.MappedTextPropertyFactory;
import srojak.sql.valbind.JdbcValueBinding;

/**
 * @author Stephen
 *
 */
public class ObjectMappingCarrier 
		implements ObjectMapping {
	private final ClassKey _classMapped;
	private final FlagsInt _flags;
	private final List<ObjectMappingIssue> _listIssues;
	private AbstractMappedObjectSource _objSource;
	private String _queryRead;
	private Constructor<?> _constr;
	
	public ObjectMappingCarrier(Class<?> classMappedObject) {
		Objects.requireNonNull(classMappedObject, "classMappedObject");
		_classMapped = new ClassKey(classMappedObject);
		_flags = new FlagsInt();
		_listIssues = new LinkedList<ObjectMappingIssue>();
	}

	@Override
	public ClassKey mappedObjectClass() {
		return _classMapped;
	}


	@Override
	public FlagsIntTest getFlags() {
		return _flags;
	}


	@Override
	public boolean isValid() {
		return _flags.test(FLAGS_VALID);
	}

	@Override
	public boolean hasWarnings() {
		return _flags.test(FLAGS_HAS_WARNINGS);
	}

	@Override
	public Stream<ObjectMappingIssue> issues() {
		return _listIssues.stream();
	}
	
	@Override
	public MappedObjectSource objSource() {
		return _objSource;
	}

	@SuppressWarnings("unused")
	private void recordWarning(String strText) {
		ObjectMappingIssueBox issue = new ObjectMappingIssueBox(_classMapped, DataErrorSeverity.WARN, strText);
		_listIssues.add(issue);
		_flags.set(FLAGS_HAS_WARNINGS);
	}
	
	private void recordError(IntegerCounter countErrors, String strText) {
		ObjectMappingIssueBox issue = new ObjectMappingIssueBox(_classMapped, DataErrorSeverity.ERROR, strText);
		_listIssues.add(issue);
		countErrors.increment();
	}

	@Override
	public void process() throws MappedObjectException {
		if (!_flags.test(FLAGS_PROCESSED)) {
			IntegerCounter errors = new IntegerCounter();
			AbstractMappedObjectSource sourceMapped = null;
			Annotation[] annots = _classMapped.getAnnotations();
			for (Annotation annot : annots) {
				if (annot instanceof SingleTableSource a) {
					if (sourceMapped != null) {
						recordError(errors, "multiple definining annotations");
					} else {
						sourceMapped = new MappedObjectSingleTableSource(a);
					}
				}
			}
			if (sourceMapped == null) {
				recordError(errors, "no source defined");
			}
			if (errors.value() > 0) {
				throw new MappedObjectException(_classMapped, "improper source definition");
			}
			
			Set<String> setColumns = new HashSet<String>();
			Field[] fields = _classMapped.getDeclaredFields();
			loopFields:
			for (Field field : fields) {
				KeyColumn kc = field.getAnnotation(KeyColumn.class);
				ColumnSource cs = field.getAnnotation(ColumnSource.class);
				if (cs != null) {
					if (setColumns.contains(cs.name())) {
						recordError(errors, "duplicate column name \"" + cs.name() + "\"");
						continue loopFields;
					}
					MappedObjectPropertyFactory factory = MapObjPropertyFactoryStore.get(field.getType());
					if (factory == null) {
						recordError(errors, "field " + field.getName() + " has unrecognized data type");
						continue loopFields;
					}
					factory.reset();
					factory.setField(field);
					field.setAccessible(true);
					factory.apply(_classMapped, cs);
					if (kc != null) {
						factory.apply(_classMapped, kc);
					}
					if (factory.isTimeZonedField()) {
						ColumnTimeZone tzc = field.getAnnotation(ColumnTimeZone.class);
						if (tzc != null) {
							factory.apply(_classMapped, tzc);
						}
					}
					MappedObjectProperty property = factory.create(_classMapped);
					sourceMapped.add(property);
					setColumns.add(property.columnName());
					continue;
				}
				TextColumnSource tcs = field.getAnnotation(TextColumnSource.class);
				if (tcs != null) {
					if (setColumns.contains(tcs.name())) {
						recordError(errors, "duplicate column name \"" + tcs.name() + "\"");
						continue loopFields;
					}
					MappedObjectPropertyFactory factory = MapObjPropertyFactoryStore.get(field.getType());
					if (factory == null) {
						recordError(errors, "field " + field.getName() + " has unrecognized data type");
						continue loopFields;
					}
					UseCollation annotCollation = field.getAnnotation(UseCollation.class);
					UseSQLCollation annotSQLColl = field.getAnnotation(UseSQLCollation.class);
					if (annotCollation == null && annotSQLColl == null) {
						recordError(errors, "field " + field.getName() + " does not define its collation");
						continue loopFields;
					}
					if (factory instanceof MappedTextPropertyFactory factoryText) {
						factoryText.reset();
						factoryText.setField(field);
						field.setAccessible(true);
						factoryText.apply(_classMapped, tcs);
						if (kc != null) {
							factoryText.apply(_classMapped, kc);
						}
						Collation collation = null;
						if (annotCollation != null) {
							Locale lcl = Locale.forLanguageTag(annotCollation.locale());
							Charset charSet = Charset.forName(annotCollation.charSet());
							CollationComparisonRule ruleCompare = CollationComparisonRule.forName(annotCollation.comparisonRule());
							CollationDefinition definition = CollationDefinition.compose(lcl, charSet, ruleCompare);
							collation = CollationCatalog.get(definition);
							if (collation == null) {
								recordError(errors, "undefined collation " + definition);
								continue loopFields;
							}
						} else {					
							SQLCollation collSql = SQLCollation.name(annotSQLColl.value());
							DecoratedCollation dcoll = SQLCollationCatalog.get(collSql);
							if (dcoll == null) {
								recordError(errors, "unrecognized collation " + collation);
								continue loopFields;
							}
							int nDelta = 0;
							DecoratorIntValue decorDelta = dcoll.getDecoratorAs(SQLCollationCatalog.DELTA);
							if (decorDelta != null) {
								nDelta = decorDelta.getValue();
							}
							if (nDelta > annotSQLColl.delta()) {
								recordError(errors, "collation " + collation + " not suitable");
								continue loopFields;
							}
							collation = dcoll.getValue();
						}
						factoryText.setCollation(collation);
						MappedObjectProperty property = factoryText.create(_classMapped);
						sourceMapped.add(property);
						setColumns.add(property.columnName());
					} else {
						recordError(errors, "field" + field.getName() + " is not a text field");
					}
				}
			}
			
			if (sourceMapped._listColumns.isEmpty()) {
				recordError(errors, "no mapped columns defined");
			}
			
			if (errors.value() > 0) {
				throw new MappedObjectException(_classMapped, "one or more improper property definitions");
			}
			
			try {
				_constr = _classMapped.getReferencedClass().getDeclaredConstructor(ObjectMapping.class);
			} catch (NoSuchMethodException e) {
				throw new MappedObjectException(_classMapped, "no mapping constructor", e);
			} catch (SecurityException e) {
				throw new MappedObjectException(_classMapped, "mapping constructor is inaccessible", e);
			}
			
			_queryRead = sourceMapped.stmtRead();
			
			// do final checks
			_objSource = sourceMapped;
			_flags.set(FLAGS_PROCESSED, FLAGS_VALID);
		}
	}

	@Override
	public MappedObjectProperty getPropertyForFieldNamed(String strField) {
		Objects.requireNonNull(strField, "strField");
		MappedObjectProperty property = _objSource.propertyForColumn(strField);
		if (property == null) {
			throw new KeyNotFoundException(strField, "named field not found");
		}
		return property;
	}

	@Override
	public MappedObjectProperty getPropertyForColumnNamed(String strColumn) {
		Objects.requireNonNull(strColumn, "strColumn");
		MappedObjectProperty property = _objSource.propertyForColumn(strColumn);
		if (property == null) {
			throw new KeyNotFoundException(strColumn, "named column not found");
		}
		return property;
	}

	@Override
	public ResultSet query(Statement stmt) throws SQLException {
		return stmt.executeQuery(_queryRead);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <M extends AbstractMappedObject> M createInstance() 
			throws MappedObjectException {
		try {
			return (M) _constr.newInstance(this);
		} catch (InstantiationException e) {
			throw new MappedObjectException(_classMapped, "constructor instantiation", e);
		} catch (IllegalAccessException e) {
			throw new MappedObjectException(_classMapped, "constructor access", e);
		} catch (IllegalArgumentException e) {
			throw new MappedObjectException(_classMapped, "constructor argument", e);
		} catch (InvocationTargetException e) {
			throw new MappedObjectException(_classMapped, "constructor invocation", e);
		}
	}

	@Override
	public <M extends AbstractMappedObject> void populateDefaults(M instance)
			throws MappedObjectException {
		Objects.requireNonNull(instance, "instance");
		List<MappedObjectProperty> properties = _objSource.properties().toList();
		for (MappedObjectProperty p : properties) {
			try {
				p.setToDefault(instance);
			} catch (IllegalAccessException e) {
				throw new MappedObjectException(_classMapped, "illegal access", e);
			}
		}
	}

	@Override
	public <M extends AbstractMappedObject> void populateFrom(ResultSet rset, M instance) 
			throws SQLException, MappedObjectException {
		Objects.requireNonNull(rset, "rset");
		Objects.requireNonNull(instance, "instance");
		List<MappedObjectProperty> properties = _objSource.properties().toList();
		for (MappedObjectProperty p : properties) {
			try {
				p.readIntoProperty(instance, rset);
			} catch (IllegalArgumentException e) {
				throw new MappedObjectException(_classMapped, "illegal argument", e);
			} catch (IllegalAccessException e) {
				throw new MappedObjectException(_classMapped, "illegal access", e);
			}
		}
	}
	
	private <M extends AbstractMappedObject> JdbcValueBinding bindPropertyValue(M instance, MappedObjectProperty property)
				throws MappedObjectException, ValueBindingException {
		try {
			return property.bind(instance);
		} catch (IllegalArgumentException exc) {
			throw new MappedObjectException(_classMapped, "illegal argument during binding", exc);
		} catch (IllegalAccessException exc) {
			throw new MappedObjectException(_classMapped, 
					"no access to property " + property.columnName() + " during binding", exc);
		}
	}

	@Override
	public <M extends AbstractMappedObject> List<JdbcValueBinding> bindPropertyValues(M instance, DatabaseAction action)
			throws MappedObjectException, ValueBindingException {
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(action, "action");
		List<MappedObjectProperty> properties = _objSource.properties().toList();
		List<JdbcValueBinding> listBindings = new ArrayList<JdbcValueBinding>(properties.size());
		for (MappedObjectProperty p : properties) {
			if (action == DatabaseAction.INSERT) {
				if (p.isIdentity() || p.isReadOnly()) {
					continue;
				}
			}
			JdbcValueBinding binding = bindPropertyValue(instance, p);
			listBindings.add(binding);
		}
		return listBindings;
	}

	@Override
	public <M extends AbstractMappedObject> List<JdbcValueBinding> bindPropertyValues(M instance,
			Collection<String> columnNames) 
					throws MappedObjectException, ValueBindingException {
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(columnNames, "columnNames");
		int nColumns = columnNames.size();
		if (nColumns == 0) {
			throw new IllegalArgumentException("columnNames is empty");
		}
		List<JdbcValueBinding> listBindings = new ArrayList<JdbcValueBinding>(nColumns);
		List<String> listUnresolvedColumns = new LinkedList<String>();
		for (String nameColumn : columnNames) {
			MappedObjectProperty property = _objSource.propertyForColumn(nameColumn);
			if (property == null) {
				listUnresolvedColumns.add(nameColumn);
			} else {
				JdbcValueBinding binding = bindPropertyValue(instance, property);
				listBindings.add(binding);
			}
		}
		if (!listUnresolvedColumns.isEmpty()) {
			throw new ValueBindingException(_classMapped.toShortString(), 
					"unresolved columns: " + String.join(", ", listUnresolvedColumns));
		}
		return listBindings;
	}
}
