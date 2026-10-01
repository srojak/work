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
public class IncommensurableValuesException 
		extends ClassReferencingRuntimeException {
	private final Class<?> _classCompared;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7719522077376743071L;

	/**
	 * @param classReferenced
	 */
	public IncommensurableValuesException(Class<?> classReferenced, Class<?> classCompared) {
		super(classReferenced);
		_classCompared = validateClass(classCompared);
	}

	/**
	 * @param classReferenced
	 * @param message
	 */
	public IncommensurableValuesException(Class<?> classReferenced, Class<?> classCompared, String message) {
		super(classReferenced, message);
		_classCompared = validateClass(classCompared);
	}

	/**
	 * @param classReferenced
	 * @param cause
	 */
	public IncommensurableValuesException(Class<?> classReferenced, Class<?> classCompared, Throwable cause) {
		super(classReferenced, cause);
		_classCompared = validateClass(classCompared);
	}

	/**
	 * @param classReferenced
	 * @param message
	 * @param cause
	 */
	public IncommensurableValuesException(Class<?> classReferenced, Class<?> classCompared, String message, Throwable cause) {
		super(classReferenced, message, cause);
		_classCompared = validateClass(classCompared);
	}

	/**
	 * @param classReferenced
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public IncommensurableValuesException(Class<?> classReferenced, Class<?> classCompared, String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(classReferenced, message, cause, enableSuppression, writableStackTrace);
		_classCompared = validateClass(classCompared);
	}

	public Class<?> getComparedClass() {
		return _classCompared;
	}
}
