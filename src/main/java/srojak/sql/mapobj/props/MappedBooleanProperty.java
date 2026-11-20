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
import java.util.Comparator;

import srojak.core.logic.FlagsIntTest;
import srojak.numerics.NullableValue;
import srojak.numerics.nullable.NullableBoolean;
import srojak.sql.ValueBindingException;
import srojak.sql.mapobj.AbstractMappedObject;
import srojak.sql.valbind.JdbcValueBinding;
import srojak.sql.valbind.NullableBooleanValueBinding;

/**
 * @author Stephen
 *
 */
public final class MappedBooleanProperty
		extends AbstractMappedObjectProperty {

	/**
	 * @param field
	 * @param strColumnName
	 * @param flagsSource
	 */
	public MappedBooleanProperty(Field field, String strColumnName, FlagsIntTest flagsSource) {
		super(field, strColumnName, flagsSource, Types.BIT);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected NullableValue createTypedDefault() {
		return NullableBoolean.fromNull();
	}

	@Override
	protected NullableValue readFrom(ResultSet rset) throws SQLException {
		boolean bValue = rset.getBoolean(_nameColumn);
		if (rset.wasNull()) {
			return NullableBoolean.fromNull();
		} else {
			return NullableBoolean.from(bValue);
		}
	}

	@Override
	public JdbcValueBinding bind(AbstractMappedObject instance)
			throws IllegalArgumentException, IllegalAccessException, ValueBindingException {
		NullableBoolean nble = getFieldValueAs(instance);
		checkNullState(nble);
		return new NullableBooleanValueBinding(_nameColumn, nble);
	}
}
