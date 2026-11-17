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
package srojak.sql.impl;

import java.sql.SQLException;

import srojak.core.containers.ClassAndContentCarrier;
import srojak.core.faults.ExceptionFormatterBase;

/**
 * @author Stephen
 *
 */
public class SqlExceptionFormatter 
		extends ExceptionFormatterBase {

	/**
	 * @param classException
	 */
	public SqlExceptionFormatter() {
		super(SQLException.class);
	}

	@Override
	public void format(Exception exc, ClassAndContentCarrier carrier) {
		SQLException excSql = (SQLException) exc;
		carrier.addContent("SQL state = " + excSql.getSQLState());
		carrier.addContent("error code = " + excSql.getErrorCode());
		// TODO: use iterator
		useStandardMessageFormat(exc, carrier);
	}

}
