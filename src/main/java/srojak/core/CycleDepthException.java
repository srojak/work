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
public class CycleDepthException
		extends RuntimeException {
	private final String _strStopName;

	public static final String UNDEFINED = "?undef";
	/**
	 * 
	 */
	public CycleDepthException(String strStopName) {
		super();
		_strStopName = strStopName != null ? strStopName : UNDEFINED;
	}

	/**
	 * @param message
	 */
	public CycleDepthException(String strStopName, String message) {
		super(message);
		_strStopName = strStopName != null ? strStopName : UNDEFINED;
	}

	/**
	 * @param cause
	 */
	public CycleDepthException(String strStopName, Throwable cause) {
		super(cause);
		_strStopName = strStopName != null ? strStopName : UNDEFINED;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CycleDepthException(String strStopName, String message, Throwable cause) {
		super(message, cause);
		_strStopName = strStopName != null ? strStopName : UNDEFINED;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CycleDepthException(String strStopName, String message, Throwable cause, 
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		_strStopName = strStopName != null ? strStopName : UNDEFINED;
	}
	
	public String getStopName() {
		return _strStopName;
	}

}
