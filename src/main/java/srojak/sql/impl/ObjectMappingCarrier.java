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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import srojak.collate.Collation;
import srojak.collate.CollationCatalog;
import srojak.collate.CollationComparisonRule;
import srojak.collate.CollationDefinition;
import srojak.collate.DecoratedCollation;
import srojak.core.keys.ClassKey;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.mantle.decorated.DecoratorIntValue;
import srojak.sql.MappedObjectException;
import srojak.sql.SQLCollation;
import srojak.sql.collate.SQLCollationCatalog;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.mapobj.ColumnSource;
import srojak.sql.mapobj.ColumnTimeZone;
import srojak.sql.mapobj.KeyColumn;
import srojak.sql.mapobj.MappedObjectProperty;
import srojak.sql.mapobj.ObjectMapping;
import srojak.sql.mapobj.SingleTableSource;
import srojak.sql.mapobj.TextColumnSource;
import srojak.sql.mapobj.UseCollation;
import srojak.sql.mapobj.UseSQLCollation;
import srojak.sql.mapobj.factories.MappedObjectPropertyFactory;
import srojak.sql.mapobj.factories.MappedTextPropertyFactory;
import srojak.sql.mapobj.factories.NullableCollatedStringPropertyFactory;
import srojak.sql.mapobj.factories.NullableDateTimePropertyFactory;
import srojak.sql.mapobj.factories.NullableIntPropertyFactory;

/**
 * @author Stephen
 *
 */
public class ObjectMappingCarrier 
		implements ObjectMapping {
	private final ClassKey _classMapped;
	private final FlagsInt _flags;
	private AbstractMappedObjectSource _objSource;
	private String _queryRead;
	private Constructor<?> _constr;
	
	private static final Map<Class<?>, MappedObjectPropertyFactory> _mapFactories;
	
	static {
		_mapFactories = new HashMap<Class<?>, MappedObjectPropertyFactory>();
		
		MappedObjectPropertyFactory[] factories = {
				new NullableIntPropertyFactory(),
				new NullableDateTimePropertyFactory(),
				new NullableCollatedStringPropertyFactory(),
		};
		
		for (MappedObjectPropertyFactory pf : factories) {
			_mapFactories.put(pf.forClass().getReferencedClass(), pf);
		}
	}
	
	public ObjectMappingCarrier(Class<?> classMappedObject) {
		Objects.requireNonNull(classMappedObject, "classMappedObject");
		_classMapped = new ClassKey(classMappedObject);
		_flags = new FlagsInt();
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
	public void process() throws MappedObjectException {
		if (!_flags.test(FLAGS_PROCESSED)) {
			AbstractMappedObjectSource sourceMapped = null;
			Annotation[] annots = _classMapped.getAnnotations();
			for (Annotation annot : annots) {
				if (annot instanceof SingleTableSource a) {
					if (sourceMapped != null) {
						throw new MappedObjectException(_classMapped,
								"multiple definining annotations");
					} else {
						sourceMapped = new MappedObjectSingleTableSource(a);
					}
				}
			}
			if (sourceMapped == null) {
				throw new MappedObjectException(_classMapped, "no source defined");
			}
			
			Set<String> setColumns = new HashSet<String>();
			Field[] fields = _classMapped.getDeclaredFields();
			for (Field field : fields) {
				KeyColumn kc = field.getAnnotation(KeyColumn.class);
				ColumnSource cs = field.getAnnotation(ColumnSource.class);
				if (cs != null) {
					if (setColumns.contains(cs.name())) {
						throw new MappedObjectException(_classMapped, "duplicate column name \"" + cs.name() + "\"");
					}
					MappedObjectPropertyFactory factory = _mapFactories.get(field.getType());
					if (factory == null) {
						throw new MappedObjectException(_classMapped, "field " + field.getName() + " has unrecognized data type");
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
						throw new MappedObjectException(_classMapped, "duplicate column name \"" + tcs.name() + "\"");
					}
					MappedObjectPropertyFactory factory = _mapFactories.get(field.getType());
					if (factory == null) {
						throw new MappedObjectException(_classMapped, "field " + field.getName() + " has unrecognized data type");
					}
					UseCollation annotCollation = field.getAnnotation(UseCollation.class);
					UseSQLCollation annotSQLColl = field.getAnnotation(UseSQLCollation.class);
					if (annotCollation == null && annotSQLColl == null) {
						throw new MappedObjectException(_classMapped, "field " + field.getName() + " does not define its collation");
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
								throw new MappedObjectException(_classMapped, "undefined collation " + definition);
							}
						} else {					
							SQLCollation collSql = SQLCollation.name(annotSQLColl.value());
							DecoratedCollation dcoll = SQLCollationCatalog.get(collSql);
							if (dcoll == null) {
								throw new MappedObjectException(_classMapped, "unrecognized collation " + collation);
							}
							int nDelta = 0;
							DecoratorIntValue decorDelta = dcoll.getDecoratorAs(SQLCollationCatalog.DELTA);
							if (decorDelta != null) {
								nDelta = decorDelta.getValue();
							}
							if (nDelta > annotSQLColl.delta()) {
								throw new MappedObjectException(_classMapped, "collation " + collation + " not suitable");
							}
							collation = dcoll.getValue();
						}
						factoryText.setCollation(collation);
						MappedObjectProperty property = factoryText.create(_classMapped);
						sourceMapped.add(property);
						setColumns.add(property.columnName());
					} else {
						throw new MappedObjectException(_classMapped, "field" + field.getName() + " is not a text field");
					}
				}
			}
			
			if (sourceMapped._listColumns.isEmpty()) {
				throw new MappedObjectException(_classMapped, "no mapped columns defined");
			}
			
			try {
				_constr = _classMapped.getReferencedClass().getDeclaredConstructor();
			} catch (NoSuchMethodException e) {
				throw new MappedObjectException(_classMapped, "no default constructor", e);
			} catch (SecurityException e) {
				throw new MappedObjectException(_classMapped, "default constructor is inaccessible", e);
			}
			
			_queryRead = sourceMapped.stmtRead();
			
			// do final checks
			_objSource = sourceMapped;
			_flags.set(FLAGS_PROCESSED, FLAGS_VALID);
		}
	}

	@Override
	public ResultSet query(Statement stmt) throws SQLException {
		return stmt.executeQuery(_queryRead);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AbstractMappedObject> T createInstance() 
			throws MappedObjectException {
		try {
			return (T) _constr.newInstance();
		} catch (InstantiationException e) {
			throw new MappedObjectException(_classMapped, "default constructor instantiation", e);
		} catch (IllegalAccessException e) {
			throw new MappedObjectException(_classMapped, "default constructor access", e);
		} catch (IllegalArgumentException e) {
			throw new MappedObjectException(_classMapped, "default constructor argument", e);
		} catch (InvocationTargetException e) {
			throw new MappedObjectException(_classMapped, "default constructor invocation", e);
		}
	}

	@Override
	public <T extends AbstractMappedObject> void populateFrom(ResultSet rset, T instance) 
			throws SQLException, MappedObjectException {
		Objects.requireNonNull(rset, "rset");
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
}
