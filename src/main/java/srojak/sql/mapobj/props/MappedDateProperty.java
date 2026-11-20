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
import java.sql.Types;
import java.time.LocalDate;

import srojak.core.logic.FlagsIntTest;
import srojak.numerics.NullableComparable;
import srojak.numerics.NullableValue;
import srojak.numerics.nullable.NullableDate;
import srojak.sql.ValueBindingException;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.valbind.JdbcValueBinding;
import srojak.sql.valbind.NullableDateValueBinding;

/**
 * @author Stephen
 *
 */
public class MappedDateProperty 
		extends AbstractMappedComparableProperty {

	/**
	 * @param field
	 * @param strColumnName
	 * @param flagsSource
	 */
	public MappedDateProperty(Field field, String strColumnName, FlagsIntTest flagsSource) {
		super(field, strColumnName, flagsSource, Types.DATE);
	}

	@Override
	protected NullableComparable<?> createComparableDefault() {
		return NullableDate.fromNull();
	}

	@Override
	protected NullableValue readFrom(ResultSet rset) 
			throws SQLException {
		LocalDate dtValue = rset.getObject(_nameColumn, LocalDate.class);
		if (rset.wasNull()) {
			return NullableDate.fromNull();
		} else {
			return NullableDate.from(dtValue);
		}
	}

	@Override
	public JdbcValueBinding bind(AbstractMappedObject instance)
			throws IllegalArgumentException, IllegalAccessException, ValueBindingException {
		NullableDate nble = getFieldValueAs(instance);
		checkNullState(nble);
		return new NullableDateValueBinding(_nameColumn, nble);
	}
}
