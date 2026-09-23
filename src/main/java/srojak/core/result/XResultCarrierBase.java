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
package srojak.core.result;

import java.util.Objects;
import java.util.function.BiFunction;

import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public abstract class XResultCarrierBase
		implements XResult {
	private SourceLocation _origin;
	private ObservedActivity _activity;
	private boolean _bValid;
	private Exception _exception;

	protected XResultCarrierBase(SourceLocation source, ObservedActivity activity) {
		Objects.requireNonNull(source, "source");
		Objects.requireNonNull(activity, "activity");
		_origin = source;
		_activity = activity;
		_bValid = false;
		_exception = null;
	}
	
	/**
	 * Used by the originating method to capture an exception thrown when
	 * 		performing an operation and make it available within the result.
	 * 
	 * Generally, methods should not capture all possible exceptions and roll them into the result.
	 * A method that is performing an operation that can throw checked exceptions should catch and
	 * record only those exceptions, allowing unchecked exceptions to bubble up normally.
	 * 
	 * @param exc The exception that was captured.
	 */
	public void caughtException(Exception exc) {
		Objects.requireNonNull(exc, "exc");
		_exception = exc;
	}
	
	protected void markValid() {
		_bValid = true;
	}
	
	public <A extends ObservedActivity> A getActivityAs() {
		@SuppressWarnings("unchecked")
		A activity = (A) _activity;
		return activity;
	}
	
	public void setActivity(ObservedActivity activity) {
		Objects.requireNonNull(activity, "activity");
		_activity = activity;
	}
	
	/**
	 * Copy another result into this result.
	 * 
	 * A method would use this to overlay another result from a method it called that failed
	 * 		so that the caller has the actual origin and exception from the source of the exception.
	 * @param result The result from the subordinate method.
	 */
	public void copyFrom(XResult result) {
		Objects.requireNonNull(result, "result");
		_origin = result.getOriginator();
		_activity = result.getActivity();
		_bValid = result.isValid();
		_exception = result.getException();
	}
	
	/**
	 * Copy another result into this result with defined handing for the activity.
	 * 
	 * A method would use this to overlay another result from a method it called that failed
	 * 		so that the caller has the actual origin and exception from the source of the exception.
	 * @param result The result from the subordinate method.
	 */
	public void copyFrom(XResult result, BiFunction<XResultCarrierBase, ObservedActivity, ObservedActivity> transferActivity) {
		Objects.requireNonNull(result, "result");
		Objects.requireNonNull(transferActivity, "transferActivity");
		_origin = result.getOriginator();
		_activity = transferActivity.apply(this, result.getActivity());
		_bValid = result.isValid();
		_exception = result.getException();
	}

	/**
	 * Get the source location where the result object was created.
	 * This will usually be in the method where an exception could be thrown,
	 * 		but not at the line where the exception could occur.
	 * @return The source location where the result object was created.
	 */
	@Override
	public SourceLocation getOriginator() {
		return _origin;
	}

	/**
	 * Get text of the activity being performed.
	 * @return An object describing the activity.
	 */
	@Override
	public ObservedActivity getActivity() {
		return _activity;
	}

	/**
	 * Did the requested operation succeed?
	 * @return {@code true} if the operation was successful.
	 */
	@Override
	public boolean isValid() {
		return _bValid;
	}

	/**
	 * Get the exception, if any, that was thrown performing the requested operation.
	 * @return The captured exception, or {@code null} if there was none.
	 */
	@Override
	public Exception getException() {
		return _exception;
	}

	/**
	 * Was there an exception of the specified type thrown?
	 * @param classException The class of the exception of interest.
	 * @return {@code true} if there was an exception and it is of the given class or a supertype.
	 */
	@Override
	public boolean isExceptionOfType(Class<?> classException) {
		Objects.requireNonNull(classException, "classException");
		if (!Exception.class.isAssignableFrom(classException)) {
			throw new IllegalArgumentException("argument is not an exception class");
		}
		if (_exception == null) {
			return false;
		} else {
			return classException.isAssignableFrom(_exception.getClass());
		}
	}
	
	protected void buildValidString(StringBuilder sb) {
		// base class method does nothing
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("result [origin=");
		sb.append(_origin.toString(SourceDetail.PACKAGE_CLASS_METHOD));
		sb.append(", activity=");
		sb.append(_activity.describe());
		sb.append(", valid=");
		sb.append(_bValid);
		if (_bValid) {
			buildValidString(sb);
		}
		if (_exception != null) {
			sb.append(", exception=");
			sb.append(_exception.getClass().getSimpleName());
		}
		sb.append(']');
		return sb.toString();
	}
}
