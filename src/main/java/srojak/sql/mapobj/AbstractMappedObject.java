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
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import srojak.collate.Collation;
import srojak.core.logic.FlagsShort;
import srojak.core.logic.FlagsShortTest;
import srojak.sql.DatabaseAction;
import srojak.sql.MappedObjectException;
import srojak.sql.ValueBindingException;
import srojak.sql.valbind.JdbcValueBinding;

/**
 * @author Stephen
 *
 */
public abstract class AbstractMappedObject
		implements MappedObjectFlags {
	private final FlagsShort _flagsMap;
	private ObjectMapping _mapping;
	
	protected AbstractMappedObject() {
		_flagsMap = new FlagsShort();
		_mapping = null;
	}
	
	protected AbstractMappedObject(ObjectMapping mapping) 
			throws MappedObjectException {
		Objects.requireNonNull(mapping, "mapping");
		_flagsMap = new FlagsShort();
		_mapping = mapping;
		if (!_mapping.isValid()) {
			throw new MappedObjectException(_mapping.mappedObjectClass(), "class mapping is invalid");
		}
	}
	
	protected void validateMapping()
			throws MappedObjectException {
		if (_mapping == null) {
			throw new IllegalStateException("no mapping defined");
		}
		if (!_mapping.isValid()) {
			throw new MappedObjectException(_mapping.mappedObjectClass(), "class mapping is invalid");
		}
	}
	
	protected void setFieldsToDefault()
				throws MappedObjectException {
		validateMapping();
		_mapping.populateDefaults(this);
	}
	
	void setObjectMapping(ObjectMapping mapping) {
		_mapping = mapping;
	}
	
	public FlagsShortTest getMappingFlags() {
		return _flagsMap;
	}
	
	public boolean isDirty() {
		return _flagsMap.test(FLAGS_MO_MODIFIED);
	}
	
	public boolean isSourcedFromDatabase() {
		return _flagsMap.test(FLAGS_MO_FROM_DB);
	}
	
	public boolean shouldDelete() {
		return _flagsMap.test(FLAGS_MO_SHOULD_DELETE);
	}
	
	protected void markDirty() {
		_flagsMap.set(FLAGS_MO_MODIFIED);
	}
	
	protected void markForDeletion() {
		_flagsMap.set(FLAGS_MO_SHOULD_DELETE);
	}
	
	void afterDatabaseAction(DatabaseAction action) {
		Objects.requireNonNull(action, "action");
		switch (action) {
		case INSERT:
		case UPDATE:
			_flagsMap.clear(FLAGS_MO_MODIFIED);
			_flagsMap.set(FLAGS_MO_FROM_DB); // if it wasn't before, it is now
			break;
			
		case QUERY:
			_flagsMap.clear(FLAGS_MO_MODIFIED);
			_flagsMap.set(FLAGS_MO_FROM_DB);
			break;
			
		case DELETE:
			_flagsMap.reset();
			break;
		}
	}
	
	protected Collation getCollationForField(String strField) 
			throws MappedObjectException {
		Objects.requireNonNull(strField, "strField");
		validateMapping();
		MappedObjectProperty property = _mapping.getPropertyForFieldNamed(strField);
		if (property instanceof MappedObjectTextProperty pText) {
			return pText.collation();
		} else {
			throw new IllegalArgumentException("field is not a text field");
		}
	}
	
	public void fillFromResultSet(ResultSet rset) throws SQLException, MappedObjectException {
		Objects.requireNonNull(rset, "rset");
		_mapping.populateFrom(rset, this);
		afterDatabaseAction(DatabaseAction.QUERY);
	}
	
	public List<JdbcValueBinding> bindPropertyValues(DatabaseAction action) 
			throws MappedObjectException, ValueBindingException {
		Objects.requireNonNull(action, "action");
		validateMapping();
		return _mapping.bindPropertyValues(this, action);
	}
	
	public List<JdbcValueBinding> bindPropertyValues(Collection<String> columnNames) 
			throws MappedObjectException, ValueBindingException {
		Objects.requireNonNull(columnNames, "columnNames");
		validateMapping();
		return _mapping.bindPropertyValues(this, columnNames);
	}
}
