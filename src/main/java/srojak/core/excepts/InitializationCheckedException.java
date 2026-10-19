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
public class InitializationCheckedException 
		extends ClassReferencingCheckedException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5087461299530809593L;

	/**
	 * @param classReferenced
	 */
	public InitializationCheckedException(Class<?> classReferenced) {
		super(classReferenced);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param classReferenced
	 * @param message
	 */
	public InitializationCheckedException(Class<?> classReferenced, String message) {
		super(classReferenced, message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param classReferenced
	 * @param cause
	 */
	public InitializationCheckedException(Class<?> classReferenced, Throwable cause) {
		super(classReferenced, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param classReferenced
	 * @param message
	 * @param cause
	 */
	public InitializationCheckedException(Class<?> classReferenced, String message, Throwable cause) {
		super(classReferenced, message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param classReferenced
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InitializationCheckedException(Class<?> classReferenced, String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(classReferenced, message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
