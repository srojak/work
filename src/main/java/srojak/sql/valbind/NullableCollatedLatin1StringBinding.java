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

import srojak.sql.NullableCollatedString;

/**
 * @author Stephen
 *
 */
public final class NullableCollatedLatin1StringBinding 
		extends AbstractJdbcMultiTypeValueBinding {

	/**
	 * 
	 * @param sqlType
	 * @param value
	 * @param maxLength
	 */
	public NullableCollatedLatin1StringBinding(String strName, int sqlType, Object value, int maxLength) {
		super(strName, sqlType, value, maxLength);
	}

	@Override
	protected void assignTo(PreparedStatement stmt, int ordinal) throws SQLException {
		NullableCollatedString nble = getValueAs();
		if (nble.isNull()) {
			stmt.setNull(ordinal, _sqlType);
		} else {
			String strContent = nble.value();
			checkStringLength(strContent);
			if (isUnicodeColumn()) {
				stmt.setNString(ordinal, strContent);
			} else {
				stmt.setString(ordinal, strContent);
			}
		}
	}

}
