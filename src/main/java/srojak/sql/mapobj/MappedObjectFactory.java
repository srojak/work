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
package srojak.sql.mapobj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.Objects;

import srojak.collate.Collation;
import srojak.core.excepts.InitializationRuntimeException;
import srojak.core.field.SetOnce;
import srojak.core.keys.ClassKey;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.VerbObjectActivity;
import srojak.numerics.NullableComparable;
import srojak.sql.DatabaseAction;
import srojak.sql.MappedObjectException;
import srojak.sql.SQLCommon;
import srojak.sql.impl.MappedObjectStore;
import srojak.sql.impl.ObjectMappingCarrier;

/**
 * @author Stephen
 *
 */
public final class MappedObjectFactory<T extends AbstractMappedObject> {
	private final ClassKey _keyClass;
	private final ObservedActivity _activityCreate;
	private final SetOnce<ObjectMapping> _mapObj;

	public MappedObjectFactory(Class<T> classObj) {
		Objects.requireNonNull(classObj, "classObj");
		_keyClass = new ClassKey(classObj, false);
		_activityCreate = new VerbObjectActivity("create mapped object", _keyClass.toShortString());
		_mapObj = new SetOnce<ObjectMapping>(SetOnce.DEFAULT);
	}
	
	private void initializeDependencies() {
		// we don't know whether or not these have been initialized
		SQLCommon.makeReady();
	}

	/**
	 * @throws MappedObjectException 
	 * 
	 */
	public void initialize() throws MappedObjectException {
		initializeDependencies();
		
		ObjectMapping mapping = MappedObjectStore.getObjectMappingFor(_keyClass);
		if (mapping == null) {
			mapping = new ObjectMappingCarrier(_keyClass.getReferencedClass());
			mapping.process();
			MappedObjectStore.register(_keyClass, mapping);
		}
		if (!mapping.isValid()) {
			throw new MappedObjectException(_keyClass, "mapping is not valid");
		}
		_mapObj.set(mapping);
	}
	
	private void verifyState() {
		if (_mapObj.isEmpty()) {
			throw new InitializationRuntimeException(_keyClass.getReferencedClass(), "factory not initialized");
		}
		
	}
	
	public ObservedActivity getFactoryActivity() {
		return _activityCreate;
	}
	
	public Collation getCollationForColumn(String strColumn) {
		Objects.requireNonNull(strColumn, "strColumn");
		verifyState();
		ObjectMapping mapping = _mapObj.get();
		MappedObjectProperty property = mapping.getPropertyForColumnNamed(strColumn);
		if (property instanceof MappedObjectTextProperty pText) {
			return pText.collation();
		} else {
			throw new IllegalArgumentException("column is not a character column");
		}
	}
	
	public <T extends NullableComparable<T>> Comparator<T> getComparatorForColumnAs(String strColumn) {
		Objects.requireNonNull(strColumn, "strColumn");
		verifyState();
		ObjectMapping mapping = _mapObj.get();
		MappedObjectProperty property = mapping.getPropertyForColumnNamed(strColumn);
		@SuppressWarnings("unchecked")
		Comparator<T> comparator = (Comparator<T>) property.getComparator();
		return comparator;
	}
	
	public ResultSet executeReadQuery(Statement stmt) 
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		verifyState();
		ObjectMapping mapping = _mapObj.get();
		return mapping.query(stmt);
	}
	
	public T createInstance() throws MappedObjectException {
		verifyState();
		ObjectMapping mapping = _mapObj.get();
		T obj = mapping.createInstance();
		return obj;
	}

	public T createInstanceAndPopulateFrom(ResultSet rset) 
			throws MappedObjectException, SQLException {
		Objects.requireNonNull(rset, "rset");
		verifyState();
		ObjectMapping mapping = _mapObj.get();
		T obj = mapping.createInstance();
		mapping.populateFrom(rset, obj);
		obj.afterDatabaseAction(DatabaseAction.QUERY);
		return obj;
	}
}
