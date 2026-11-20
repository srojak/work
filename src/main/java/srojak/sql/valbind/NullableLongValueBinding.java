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

import srojak.numerics.nullable.NullableLong;

/**
 * @author Stephen
 *
 */
public final class NullableLongValueBinding
		extends AbstractJdbcValueBinding {

	/**
	 * @param strName
	 * @param value
	 */
	public NullableLongValueBinding(String strName, Object value) {
		super(strName, value);
	}

	@Override
	protected void assignTo(PreparedStatement stmt, int ordinal) throws SQLException {
		NullableLong nble = getValueAs();
		if (nble.isNull()) {
			stmt.setNull(ordinal, Types.BIGINT);
		} else {
			stmt.setLong(ordinal, nble.value());
		}
	}

}
