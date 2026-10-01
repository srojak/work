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
public abstract class ClassReferencingRuntimeException 
		extends RuntimeException 
		implements ClassReferencing {
	private final Class<?> _classReferenced;
	
	protected static Class<?> validateClass(Class<?> classArg) {
		return classArg == null ? UnsuppliedClass.class : classArg;
	}

	/**
	 * 
	 */
	public ClassReferencingRuntimeException(Class<?> classReferenced) {
		super();
		_classReferenced = validateClass(classReferenced);
	}

	/**
	 * @param message
	 */
	public ClassReferencingRuntimeException(Class<?> classReferenced, String message) {
		super(message);
		_classReferenced = validateClass(classReferenced);
	}

	/**
	 * @param cause
	 */
	public ClassReferencingRuntimeException(Class<?> classReferenced, Throwable cause) {
		super(cause);
		_classReferenced = validateClass(classReferenced);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ClassReferencingRuntimeException(Class<?> classReferenced, String message, Throwable cause) {
		super(message, cause);
		_classReferenced = validateClass(classReferenced);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public ClassReferencingRuntimeException(Class<?> classReferenced, String message, Throwable cause, 
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		_classReferenced = validateClass(classReferenced);
	}

	@Override
	public Class<?> getReferencedClass() {
		return _classReferenced;
	}

	private class UnsuppliedClass {
		
	}
}
