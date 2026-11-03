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
package srojak.core.excepts;

/**
 * @author Stephen
 *
 */
public class KeyNotFoundException 
		extends KeyRelatedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4719547112487587721L;

	/**
	 * @param objKey
	 */
	public KeyNotFoundException(Object objKey) {
		super(objKey);
	}

	/**
	 * @param objKey
	 * @param message
	 */
	public KeyNotFoundException(Object objKey, String message) {
		super(objKey, message);
	}

	/**
	 * @param objKey
	 * @param cause
	 */
	public KeyNotFoundException(Object objKey, Throwable cause) {
		super(objKey, cause);
	}

	/**
	 * @param objKey
	 * @param message
	 * @param cause
	 */
	public KeyNotFoundException(Object objKey, String message, Throwable cause) {
		super(objKey, message, cause);
	}

	/**
	 * @param objKey
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public KeyNotFoundException(Object objKey, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(objKey, message, cause, enableSuppression, writableStackTrace);
	}

}
