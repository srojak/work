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
import srojak.numerics.nullable.NullableDateTime;
import srojak.sql.MappedObjectException;
import srojak.sql.mapobj.MappedObjectProperty;
import srojak.sql.mapobj.props.MappedDateTimeProperty;

/**
 * @author Stephen
 *
 */
public class NullableDateTimePropertyFactory
		extends AbstractTimeZonedPropertyFactory {

	public NullableDateTimePropertyFactory() {
		super(NullableDateTime.class);
	}

	@Override
	public MappedObjectProperty create(ClassKey keyForObject) throws MappedObjectException {
		MappedDateTimeProperty property = new MappedDateTimeProperty(_field, _nameColumn, _flags);
		applyKeyTo(property);
		applyTimeZoneTo(property);
		return property;
	}

}
