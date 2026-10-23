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

import srojak.core.containers.ClassAndContentCarrier;
import srojak.core.faults.ExceptionFormatter;
import srojak.core.faults.ExceptionFormatterBase;
import srojak.core.faults.FaultMethods;

/**
 * @author Stephen
 *
 */
public class IncommensurableValuesException 
		extends ClassReferencingRuntimeException {
	private final Class<?> _classCompared;
	
	static {
		ExceptionFormatter formatter = new MyExceptionFormatter();
		FaultMethods.useFormatter(formatter);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7719522077376743071L;

	/**
	 * @param classReferenced
	 */
	public IncommensurableValuesException(Class<?> classReferenced, Class<?> classCompared) {
		super(classReferenced);
		_classCompared = ExceptionHelpers.validateClass(classCompared);
	}

	/**
	 * @param classReferenced
	 * @param message
	 */
	public IncommensurableValuesException(Class<?> classReferenced, Class<?> classCompared, String message) {
		super(classReferenced, message);
		_classCompared = ExceptionHelpers.validateClass(classCompared);
	}

	/**
	 * @param classReferenced
	 * @param cause
	 */
	public IncommensurableValuesException(Class<?> classReferenced, Class<?> classCompared, Throwable cause) {
		super(classReferenced, cause);
		_classCompared = ExceptionHelpers.validateClass(classCompared);
	}

	/**
	 * @param classReferenced
	 * @param message
	 * @param cause
	 */
	public IncommensurableValuesException(Class<?> classReferenced, Class<?> classCompared, String message, Throwable cause) {
		super(classReferenced, message, cause);
		_classCompared = ExceptionHelpers.validateClass(classCompared);
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
		_classCompared = ExceptionHelpers.validateClass(classCompared);
	}

	public Class<?> getComparedClass() {
		return _classCompared;
	}
	
	private static class MyExceptionFormatter
			extends ExceptionFormatterBase {

		/**
		 * 
		 */
		protected MyExceptionFormatter() {
			super(IncommensurableValuesException.class);
		}

		@Override
		public void format(Exception exc, ClassAndContentCarrier carrier) {
			IncommensurableValuesException e = (IncommensurableValuesException) exc;
			carrier.addContent("referenced class: " + e.getReferencedClass().getName());
			carrier.addContent("compared class: " + e.getComparedClass().getName());
			useStandardMessageFormat(e, carrier);
		}
		
	}
}
