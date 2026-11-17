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
import java.util.Objects;

import srojak.collate.Collation;
import srojak.core.logic.FlagsIntTest;
import srojak.sql.mapobj.MappedObjectTextProperty;

/**
 * @author Stephen
 *
 */
public abstract class AbstractMappedTextProperty 
		extends AbstractMappedObjectProperty
		implements MappedObjectTextProperty {
	protected final short _maxLength;
	protected final Collation _collation;

	/**
	 * @param field
	 * @param strColumnName
	 * @param flagsSource
	 * @param sqlType
	 * @param maxLength
	 * @param collation
	 */
	public AbstractMappedTextProperty(Field field, String strColumnName, FlagsIntTest flagsSource, 
			int sqlType, short maxLength, Collation collation) {
		super(field, strColumnName, flagsSource, sqlType);
		Objects.requireNonNull(collation, "collation");
		_maxLength = maxLength;
		_collation = collation;
	}

	@Override
	public boolean isLarge() {
		return _flags.test(FLAGS_LARGE_DATA);
	}

	@Override
	public boolean isUnicode() {
		return _flags.test(FLAGS_UNICODE);
	}

	@Override
	public boolean trimAfterReading() {
		return _flags.test(FLAGS_TRIM_AFTER_READ);
	}

	@Override
	public short maxLength() {
		return _maxLength;
	}

	@Override
	public Collation collation() {
		return _collation;
	}
}
