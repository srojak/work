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
public class DuplicateKeyException 
		extends KeyRelatedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5458744547333925198L;

	/**
	 * @param objKey
	 */
	public DuplicateKeyException(Object objKey) {
		super(objKey);
	}

	/**
	 * @param objKey
	 * @param message
	 */
	public DuplicateKeyException(Object objKey, String message) {
		super(objKey, message);
	}

	/**
	 * @param objKey
	 * @param cause
	 */
	public DuplicateKeyException(Object objKey, Throwable cause) {
		super(objKey, cause);
	}

	/**
	 * @param objKey
	 * @param message
	 * @param cause
	 */
	public DuplicateKeyException(Object objKey, String message, Throwable cause) {
		super(objKey, message, cause);
	}

	/**
	 * @param objKey
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DuplicateKeyException(Object objKey, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(objKey, message, cause, enableSuppression, writableStackTrace);
	}
}
