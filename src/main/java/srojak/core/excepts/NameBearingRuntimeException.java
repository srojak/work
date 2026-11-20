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

import srojak.core.Named;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public abstract class NameBearingRuntimeException 
		extends RuntimeException
		implements Named {
	private final String _name;
	
	public static final String STR_UNSPEC = "?unspecified";
	
	protected static String validateName(String strName) {
		if (strName == null || strName.isBlank()) {
			return STR_UNSPEC;
		} else {
			return strName;
		}
	}
	
	/**
	 * 
	 */
	public NameBearingRuntimeException(String strName) {
		super();
		_name = validateName(strName);
	}

	/**
	 * @param message
	 */
	public NameBearingRuntimeException(String strName, String message) {
		super(message);
		_name = validateName(strName);
	}

	/**
	 * @param cause
	 */
	public NameBearingRuntimeException(String strName, Throwable cause) {
		super(cause);
		_name = validateName(strName);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NameBearingRuntimeException(String strName, String message, Throwable cause) {
		super(message, cause);
		_name = validateName(strName);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NameBearingRuntimeException(String strName, String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		_name = validateName(strName);
	}

	@Override
	public String getName() {
		return _name;
	}
}
