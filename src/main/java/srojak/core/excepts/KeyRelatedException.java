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
@SuppressWarnings("serial")
public abstract class KeyRelatedException 
		extends RuntimeException {
	private final Object _key;

	/**
	 * 
	 */
	public KeyRelatedException(Object objKey) {
		super();
		_key = objKey == null ? new UnspecifiedClass() : objKey;
	}

	/**
	 * @param message
	 */
	public KeyRelatedException(Object objKey, String message) {
		super(message);
		_key = objKey == null ? new UnspecifiedClass() : objKey;
	}

	/**
	 * @param cause
	 */
	public KeyRelatedException(Object objKey, Throwable cause) {
		super(cause);
		_key = objKey == null ? new UnspecifiedClass() : objKey;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public KeyRelatedException(Object objKey, String message, Throwable cause) {
		super(message, cause);
		_key = objKey == null ? new UnspecifiedClass() : objKey;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public KeyRelatedException(Object objKey, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		_key = objKey == null ? new UnspecifiedClass() : objKey;
	}

	public Object getKey() {
		return _key;
	}
}
