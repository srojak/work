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
import java.util.Objects;

import srojak.core.excepts.InitializationRuntimeException;
import srojak.core.field.SetOnce;
import srojak.core.keys.ClassKey;
import srojak.sql.MappedObjectException;
import srojak.sql.impl.MappedObjectCatalog;
import srojak.sql.impl.ObjectMappingCarrier;

/**
 * @author Stephen
 *
 */
public class MappedObjectFactory<T extends AbstractMappedObject> {
	private final ClassKey _keyClass;
	private final SetOnce<ObjectMapping> _mapObj;

	public MappedObjectFactory(Class<T> classObj) {
		Objects.requireNonNull(classObj, "classObj");
		_keyClass = new ClassKey(classObj, false);
		_mapObj = new SetOnce<ObjectMapping>(SetOnce.DEFAULT);
	}

	/**
	 * @throws MappedObjectException 
	 * 
	 */
	public void initialize() throws MappedObjectException {
		ObjectMapping mapping = MappedObjectCatalog.getObjectMappingFor(_keyClass);
		if (mapping == null) {
			mapping = new ObjectMappingCarrier(_keyClass.getReferencedClass());
			mapping.process();
			MappedObjectCatalog.register(_keyClass, mapping);
		}
		if (!mapping.isValid()) {
			throw new MappedObjectException(_keyClass, "mapping is not valid");
		}
		_mapObj.set(mapping);
	}
	
	public ResultSet executeReadQuery(Statement stmt) 
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		if (_mapObj.isEmpty()) {
			throw new InitializationRuntimeException(_keyClass.getReferencedClass(), "factory not initialized");
		}
		ObjectMapping mapping = _mapObj.get();
		return mapping.query(stmt);
	}
	
	public T createInstance() throws MappedObjectException {
		if (_mapObj.isEmpty()) {
			throw new InitializationRuntimeException(_keyClass.getReferencedClass(), "factory not initialized");
		}
		ObjectMapping mapping = _mapObj.get();
		T obj = mapping.createInstance();
		obj.setObjectMapping(mapping);
		return obj;
	}

	public T createInstanceAndPopulateFrom(ResultSet rset) 
			throws MappedObjectException, SQLException {
		Objects.requireNonNull(rset, "rset");
	if (_mapObj.isEmpty()) {
			throw new InitializationRuntimeException(_keyClass.getReferencedClass(), "factory not initialized");
		}
		ObjectMapping mapping = _mapObj.get();
		T obj = mapping.createInstance();
		obj.setObjectMapping(mapping);
		mapping.populateFrom(rset, obj);
		obj.friendSetDirty(false);
		return obj;
	}
}
