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
public class ClassMismatchException
		extends ClassReferencingRuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8060189997795946304L;
	
	/**
	 * @param message
	 */
	public ClassMismatchException(Class<?> classObserved, String message) {
		super(classObserved, message);
	}

	/**
	 * @param cause
	 */
	public ClassMismatchException(Class<?> classObserved, Throwable cause) {
		super(classObserved, cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ClassMismatchException(Class<?> classObserved, String message, Throwable cause) {
		super(classObserved, message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ClassMismatchException(Class<?> classObserved, String message, Throwable cause, 
			boolean enableSuppression, boolean writableStackTrace) {
		super(classObserved, message, cause, enableSuppression, writableStackTrace);
	}
}
