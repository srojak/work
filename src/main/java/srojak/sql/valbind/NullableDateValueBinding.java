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
package srojak.sql.valbind;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import srojak.numerics.nullable.NullableDate;

/**
 * @author Stephen
 *
 */
public final class NullableDateValueBinding 
		extends AbstractJdbcValueBinding {

	/**
	 * @param strName
	 * @param value
	 */
	public NullableDateValueBinding(String strName, Object value) {
		super(strName, value);
	}

	@Override
	protected void assignTo(PreparedStatement stmt, int ordinal) throws SQLException {
		NullableDate nble = getValueAs();
		if (nble.isNull()) {
			stmt.setNull(ordinal, Types.DATE);
		} else {
			LocalDate ldtValue = nble.value();
			stmt.setObject(ordinal, ldtValue, Types.DATE);
		}
	}
}
