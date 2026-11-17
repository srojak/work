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

import srojak.core.keys.ClassKey;
import srojak.sql.MappedObjectException;
import srojak.sql.mapobj.ColumnSource;
import srojak.sql.mapobj.props.AbstractMappedObjectProperty;

/**
 * @author Stephen
 *
 */
public abstract class AbstractPrimitivePropertyFactory 
		extends AbstractPropertyFactory {

	/**
	 * @param classToMatch
	 */
	public AbstractPrimitivePropertyFactory(Class<?> classToMatch) {
		super(classToMatch);
	}

	@Override
	public void apply(ClassKey keyForObject, ColumnSource annotColumn) throws MappedObjectException {
		_nameColumn = annotColumn.name();
		if (_nameColumn.isBlank()) {
			throw new MappedObjectException(keyForObject, "column name is blank");
		}
		if (annotColumn.isNullable()) {
			_flags.set(FLAGS_NULLABLE);
		}
		if (annotColumn.isReadOnly()) {
			_flags.set(FLAGS_READONLY);
		}
		if (annotColumn.isIdentity()) {
			_flags.set(FLAGS_IDENTITY);
		}
	}
}
