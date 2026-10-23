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

import srojak.core.faults.ExceptionFormatter;
import srojak.core.faults.FaultMethods;

/**
 * @author Stephen
 *
 */
public class ObjectExpiredException 
		extends ClassReferencingRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -692203914675377823L;
	
	static {
		ExceptionFormatter formatter = new CommonExceptionFormatter(ObjectExpiredException.class);
		FaultMethods.useFormatter(formatter);
	}

	/**
	 * @param classReferenced
	 */
	public ObjectExpiredException(Class<?> classReferenced) {
		super(classReferenced);
	}

	/**
	 * @param classReferenced
	 * @param message
	 */
	public ObjectExpiredException(Class<?> classReferenced, String message) {
		super(classReferenced, message);
	}

	/**
	 * @param classReferenced
	 * @param cause
	 */
	public ObjectExpiredException(Class<?> classReferenced, Throwable cause) {
		super(classReferenced, cause);
	}

	/**
	 * @param classReferenced
	 * @param message
	 * @param cause
	 */
	public ObjectExpiredException(Class<?> classReferenced, String message, Throwable cause) {
		super(classReferenced, message, cause);
	}

	/**
	 * @param classReferenced
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ObjectExpiredException(Class<?> classReferenced, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(classReferenced, message, cause, enableSuppression, writableStackTrace);
	}

}
