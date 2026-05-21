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
package srojak.core;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class InvalidOperationException 
		extends RuntimeException {
	private final String _strSource;
	
	/**
	 * 
	 * @param source The name of the source
	 */
	public InvalidOperationException(String strSource) {
		super();
		_strSource = (strSource == null || strSource.isEmpty()) ? "?" : strSource;
	}

	/**
	 * @param message
	 */
	public InvalidOperationException(String strSource, String message) {
		super(message);
		_strSource = (strSource == null || strSource.isEmpty()) ? "?" : strSource;
	}

	/**
	 * @param cause
	 */
	public InvalidOperationException(String strSource, Throwable cause) {
		super(cause);
		_strSource = (strSource == null || strSource.isEmpty()) ? "?" : strSource;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidOperationException(String strSource, String message, Throwable cause) {
		super(message, cause);
		_strSource = (strSource == null || strSource.isEmpty()) ? "?" : strSource;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidOperationException(String strSource, String message, Throwable cause, 
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		_strSource = (strSource == null || strSource.isEmpty()) ? "?" : strSource;
	}

	public String getSource() {
		return _strSource;
	}
}
