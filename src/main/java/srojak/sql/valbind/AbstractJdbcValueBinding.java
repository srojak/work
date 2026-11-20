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
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class AbstractJdbcValueBinding
		implements JdbcValueBinding {
	protected final String _name;
	protected final Object _objValue;

	/**
	 * 
	 */
	public AbstractJdbcValueBinding(String strName, Object value) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		Objects.requireNonNull(value, "value");
		_name = strName;
		_objValue = value;
	}

	@Override
	public String getName() {
		return _name;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getValueAs() {
		return (T) _objValue;
	}
	
	protected abstract void assignTo(PreparedStatement stmt, int ordinal) 
			throws SQLException;

	@Override
	public void setData(PreparedStatement stmt, int ordinal) 
			throws SQLException {
		Objects.requireNonNull(stmt, "stmt");
		assignTo(stmt, ordinal);
	}
}
