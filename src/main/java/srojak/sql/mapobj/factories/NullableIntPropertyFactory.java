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
import srojak.numerics.nullable.NullableInt;
import srojak.sql.MappedObjectException;
import srojak.sql.mapobj.MappedObjectProperty;
import srojak.sql.mapobj.props.MappedIntProperty;

/**
 * @author Stephen
 *
 */
public final class NullableIntPropertyFactory 
		extends AbstractScalarPropertyFactory {

	public NullableIntPropertyFactory() {
		super(NullableInt.class);
	}

	@Override
	public MappedObjectProperty create(ClassKey keyForObject) throws MappedObjectException {
		MappedIntProperty property = new MappedIntProperty(_field, _nameColumn, _flags);
		applyKeyTo(property);
		return property;
	}

}
