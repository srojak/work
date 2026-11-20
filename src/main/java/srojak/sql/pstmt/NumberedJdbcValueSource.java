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
package srojak.sql.pstmt;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import srojak.sql.JdbcValueSource;
import srojak.sql.ValueBindingException;

/**
 * @author Stephen
 *
 */
public class NumberedJdbcValueSource {
	private final JdbcValueSource _source;
	private final int _ordinal;
	
	/**
	 * 
	 */
	public NumberedJdbcValueSource(JdbcValueSource source, int ordinal) {
		Objects.requireNonNull(source, "source");
		if (ordinal <= 0) {
			throw new IllegalArgumentException("ordinal must be positive");
		}
		_source = source;
		_ordinal = ordinal;
	}

	public int ordinal() {
		return _ordinal;
	}
	
	public void applyTo(PreparedStatement stmt) 
			throws SQLException, ValueBindingException {
		Objects.requireNonNull(stmt, "stmt");
		_source.setData(stmt, _ordinal);
	}
}
