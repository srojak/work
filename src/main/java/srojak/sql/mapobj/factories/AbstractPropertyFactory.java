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
package srojak.sql.mapobj.factories;

import java.lang.reflect.Field;
import java.util.Objects;

import srojak.core.keys.ClassKey;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.logic.FlagsShort;
import srojak.core.logic.FlagsShortTest;
import srojak.sql.MappedObjectException;
import srojak.sql.mapobj.ColumnSource;
import srojak.sql.mapobj.ColumnTimeZone;
import srojak.sql.mapobj.KeyColumn;
import srojak.sql.mapobj.MappedObjectProperty;
import srojak.sql.mapobj.TextColumnSource;
import srojak.sql.mapobj.props.AbstractMappedObjectProperty;

/**
 * @author Stephen
 *
 */
public abstract class AbstractPropertyFactory 
		implements MappedObjectPropertyFactory {
	private final ClassKey _keyClass;
	private final FlagsShort _flagsDefine;
	protected final FlagsInt _flags;
	protected Field _field;
	protected String _nameColumn;
	private int _keyOrdinal;
	
	protected AbstractPropertyFactory(Class<?> classToMatch) {
		Objects.requireNonNull(classToMatch, "classToMatch");
		// interfaces are allowed here
		_keyClass = new ClassKey(classToMatch, true);
		_flagsDefine = new FlagsShort();
		_flags = new FlagsInt();
		_field = null;
		_nameColumn = null;
	}

	@Override
	public ClassKey forClass() {
		return _keyClass;
	}

	@Override
	public boolean isTextField() {
		return _flagsDefine.test(FLAGS_DEFINE_IS_TEXT);
	}

	@Override
	public boolean isTimeZonedField() {
		return _flagsDefine.test(FLAGS_DEFINE_IS_TIME_ZONED);
	}

	@Override
	public FlagsShortTest flagsDefining() {
		return _flagsDefine;
	}

	@Override
	public boolean isInKey() {
		return _flags.test(FLAGS_IS_IN_KEY);
	}

	@Override
	public FlagsIntTest flagsWorking() {
		return _flags;
	}

	@Override
	public void reset() {
		_field = null;
		_nameColumn = null;
		_flags.reset();
		_keyOrdinal = 0;
	}

	@Override
	public void setField(Field field) {
		Objects.requireNonNull(field, "field");
		_field = field;
	}
	
	protected void applyKeyTo(AbstractMappedObjectProperty property) {
		if (_flags.test(FLAGS_IS_IN_KEY)) {
			property.setKey(_keyOrdinal);
		}
	}

	@Override
	public void apply(ClassKey keyForObject, ColumnSource annotColumn) throws MappedObjectException {
		StringBuilder sb = new StringBuilder("@ColumnSource is not applicable to column of type ");
		sb.append(_keyClass.toShortString());
		throw new MappedObjectException(keyForObject, sb.toString());
	}

	@Override
	public void apply(ClassKey keyForObject, KeyColumn annotKey) throws MappedObjectException {
		_flags.set(FLAGS_IS_IN_KEY);
		_keyOrdinal = annotKey.ordinal();
	}

	@Override
	public void apply(ClassKey keyForObject, ColumnTimeZone annotZone) throws MappedObjectException {
		StringBuilder sb = new StringBuilder("@ColumnTimeZone is not applicable to column of type ");
		sb.append(_keyClass.toShortString());
		throw new MappedObjectException(keyForObject, sb.toString());
	}

	@Override
	public abstract MappedObjectProperty create(ClassKey keyForObject) throws MappedObjectException;
}
