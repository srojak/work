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

import java.sql.Types;
import java.util.Objects;

import srojak.collate.Collation;
import srojak.core.keys.ClassKey;
import srojak.sql.MappedObjectException;
import srojak.sql.mapobj.TextColumnSource;

/**
 * @author Stephen
 *
 */
public abstract class AbstractTextPropertyFactory 
		extends AbstractPropertyFactory 
		implements MappedTextPropertyFactory {
	protected short _maxLength;
	protected Collation _collation;

	/**
	 * @param classToMatch
	 */
	public AbstractTextPropertyFactory(Class<?> classToMatch) {
		super(classToMatch);
		_maxLength = 0;
		_collation = null;
	}

	@Override
	public void reset() {
		super.reset();
		_maxLength = 0;
		_collation = null;
	}

	@Override
	public void apply(ClassKey keyForObject, TextColumnSource annotColumn) 
			throws MappedObjectException {
		_nameColumn = annotColumn.name();
		if (annotColumn.isLarge()) {
			_flags.set(FLAGS_LARGE_DATA);
			_maxLength = -1;
		} else {
			_maxLength = annotColumn.maxLength();
			if (annotColumn.isFixedLength()) {
				_flags.set(FLAGS_FIXED_LENGTH);
			} 
			if (annotColumn.trimAfterRead()) {
				_flags.set(FLAGS_TRIM_AFTER_READ);
			}
		}
		if (annotColumn.isNullable()) {
			_flags.set(FLAGS_NULLABLE);
		}
		if (annotColumn.isReadOnly()) {
			_flags.set(FLAGS_READONLY);
		}
		if (annotColumn.isUnicode()) {
			_flags.set(FLAGS_UNICODE);
		}
	}

	@Override
	public void setCollation(Collation collation) {
		Objects.requireNonNull(collation, "collation");
		_collation = collation;
	}
	
	protected int deriveSqlTypeFromFlags() {
		if (_flags.test(FLAGS_FIXED_LENGTH)) {
			if (_flags.test(FLAGS_UNICODE)) {
				return Types.NCHAR;
			} else {
				return Types.CHAR;
			}
		} else {
			if (_flags.test(FLAGS_UNICODE)) {
				return Types.NVARCHAR;
			} else {
				return Types.VARCHAR;
			}
		}
	}
}
