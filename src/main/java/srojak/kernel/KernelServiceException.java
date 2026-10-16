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
package srojak.kernel;

/**
 * @author Stephen
 *
 */
public class KernelServiceException 
		extends Exception {
	private final String _name;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5934154815321689910L;
	private static final String UNKNOWN = "*unknown";

	/**
	 * 
	 */
	public KernelServiceException(String strName) {
		_name = strName != null ? strName : UNKNOWN;
	}

	/**
	 * @param message
	 */
	public KernelServiceException(String strName, String message) {
		super(message);
		_name = strName != null ? strName : UNKNOWN;
	}

	/**
	 * @param cause
	 */
	public KernelServiceException(String strName, Throwable cause) {
		super(cause);
		_name = strName != null ? strName : UNKNOWN;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public KernelServiceException(String strName, String message, Throwable cause) {
		super(message, cause);
		_name = strName != null ? strName : UNKNOWN;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public KernelServiceException(String strName, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		_name = strName != null ? strName : UNKNOWN;
	}

	public String getServiceName() {
		return _name;
	}
}
