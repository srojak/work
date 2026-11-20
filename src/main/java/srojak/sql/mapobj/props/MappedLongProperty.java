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

import srojak.core.logic.FlagsIntTest;
import srojak.numerics.NullableComparable;
import srojak.numerics.NullableValue;
import srojak.numerics.nullable.NullableLong;
import srojak.sql.ValueBindingException;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.valbind.JdbcValueBinding;

/**
 * @author Stephen
 *
 */
public final class MappedLongProperty 
	extends AbstractMappedComparableProperty {

	/**
	 * @param field
	 * @param strColumnName
	 * @param flagsSource
	 */
	public MappedLongProperty(Field field, String strColumnName, FlagsIntTest flagsSource) {
		super(field, strColumnName, flagsSource, Types.BIGINT);
	}

	@Override
	public JdbcValueBinding bind(AbstractMappedObject instance)
			throws IllegalArgumentException, IllegalAccessException, ValueBindingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected NullableComparable<?> createComparableDefault() {
		return NullableLong.fromNull();
	}

	@Override
	protected NullableValue readFrom(ResultSet rset) throws SQLException {
		long lnValue = rset.getLong(_nameColumn);
		if (rset.wasNull()) {
			return NullableLong.fromNull();
		} else {
			return NullableLong.from(lnValue);
		}
	}

}
