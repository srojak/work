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
package srojak.sql;

import srojak.core.excepts.NameBearingRuntimeException;

/**
 * @author Stephen
 *
 */
public class ValueBindingException 
		extends NameBearingRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2237586883278614637L;

	/**
	 * @param strName
	 */
	public ValueBindingException(String strName) {
		super(strName);
	}

	/**
	 * @param strName
	 * @param message
	 */
	public ValueBindingException(String strName, String message) {
		super(strName, message);
	}

	/**
	 * @param strName
	 * @param cause
	 */
	public ValueBindingException(String strName, Throwable cause) {
		super(strName, cause);
	}

	/**
	 * @param strName
	 * @param message
	 * @param cause
	 */
	public ValueBindingException(String strName, String message, Throwable cause) {
		super(strName, message, cause);
	}

	/**
	 * @param strName
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ValueBindingException(String strName, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(strName, message, cause, enableSuppression, writableStackTrace);
	}

}
