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
package srojak.sql.mapobj.props;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Objects;

import srojak.core.keys.InterfaceKey;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.numerics.NullableValue;
import srojak.sql.ValueBindingException;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.mapobj.MappedObjectProperty;

/**
 * @author Stephen
 *
 */
public abstract class AbstractMappedObjectProperty 
		implements MappedObjectProperty {
	protected final FlagsInt _flags;
	protected final Field _field;
	protected final InterfaceKey _intfField;
	protected final String _nameColumn;
	protected final int _sqlType;
	protected int _keyOrdinal;
	
	protected AbstractMappedObjectProperty(Field field, String strColumnName, FlagsIntTest flagsSource, int sqlType) {
		Objects.requireNonNull(field, "field");
		Objects.requireNonNull(strColumnName, "strColumnName");
		Objects.requireNonNull(flagsSource, "flagsSource");
		_flags = new FlagsInt();
		_flags.copyFrom(flagsSource);
		_field = field;
		_intfField = new InterfaceKey(field.getType());
		_nameColumn = strColumnName;
		_sqlType = sqlType;
		_keyOrdinal = 0;
	}
	
	public void setKey(int ordinal) {
		_flags.set(FLAGS_IS_IN_KEY);
		_keyOrdinal = ordinal;
	}

	@Override
	public InterfaceKey fieldType() {
		return _intfField;
	}

	@Override
	public String fieldName() {
		return _field.getName();
	}

	@Override
	public String columnName() {
		return _nameColumn;
	}

	@Override
	public boolean columnNameEquals(String strName) {
		return _nameColumn.equalsIgnoreCase(strName);
	}

	@Override
	public FlagsIntTest getFlags() {
		return _flags;
	}

	@Override
	public boolean isKeyComponent() {
		return _flags.test(FLAGS_IS_IN_KEY);
	}

	@Override
	public int keyOrdinal() {
		return _keyOrdinal;
	}

	@Override
	public boolean isNullable() {
		return _flags.test(FLAGS_NULLABLE);
	}

	@Override
	public boolean isReadOnly() {
		return _flags.test(FLAGS_READONLY);
	}

	@Override
	public boolean isIdentity() {
		return _flags.test(FLAGS_IDENTITY);
	}

	@Override
	public int sqlType() {
		return _sqlType;
	}
	
	protected abstract NullableValue createTypedDefault(); 
	
	@Override
	public void setToDefault(AbstractMappedObject instance) 
			throws IllegalAccessException {
		NullableValue nble = createTypedDefault();
		_field.set(instance, nble);
	}

	@Override
	public Comparator<?> getComparator() {
		throw new UnsupportedOperationException("objects of this type do not have comparators");
	}

	protected abstract NullableValue readFrom(ResultSet rset)
			throws SQLException;

	@Override
	public final void readIntoProperty(AbstractMappedObject instance, ResultSet rset)
			throws IllegalArgumentException, IllegalAccessException, SQLException {
		Objects.requireNonNull(instance, "instance");
		Objects.requireNonNull(rset, "rset");
		NullableValue nble = readFrom(rset);
		_field.set(instance, nble);
	}
	
	protected Object getFieldValue(AbstractMappedObject instance) 
			throws IllegalArgumentException, IllegalAccessException {
		return _field.get(instance);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends NullableValue> T getFieldValueAs(AbstractMappedObject instance)
			throws IllegalArgumentException, IllegalAccessException {
		Object objValue = _field.get(instance);
		return (T) objValue;
	}
	
	protected void checkNullState(NullableValue nble) {
		if (!isNullable()) {
			if (nble.isNull()) {
				throw new ValueBindingException(fieldName(), "value is null for non-null column");
			}
		}
	}
}
