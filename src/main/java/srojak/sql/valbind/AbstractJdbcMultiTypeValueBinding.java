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

import java.sql.Types;

/**
 * @author Stephen
 *
 */
public abstract class AbstractJdbcMultiTypeValueBinding 
		extends AbstractJdbcVarLengthValueBinding {
	/**
	 * for a value from java.sql.Types
	 * @see java.sql.Types;
	 */
	protected final int _sqlType;

	/**
	 * @param sqlType
	 * @param value
	 */
	public AbstractJdbcMultiTypeValueBinding(String strName, int sqlType, Object value, int maxLength) {
		super(strName, value, maxLength);
		_sqlType = sqlType;
	}
	
	protected boolean isUnicodeColumn() {
		switch (_sqlType) {
		case Types.NCHAR:
		case Types.NVARCHAR:
		case Types.LONGNVARCHAR:
			return true;
		}
		
		return false;
	}
}
