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

import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public abstract class XResultCarrierBase
		implements XResult, XResultModifierFlags {
	private final FlagsInt _modifiers;
	private SourceLocation _origin;
	private ObservedActivity _activity;
	private boolean _bValid;
	private Throwable _throwable;

	protected XResultCarrierBase(SourceLocation source, ObservedActivity activity) {
		Objects.requireNonNull(source, "source");
		Objects.requireNonNull(activity, "activity");
		_origin = source;
		_activity = activity;
		_modifiers = new FlagsInt();
		_bValid = false;
		_throwable = null;
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
	public final void caughtException(Exception exc) {
		Objects.requireNonNull(exc, "exc");
		_throwable = exc;
	}
	
	public final void caughtThrowable(Throwable t) {
		Objects.requireNonNull(t, "t");
		_throwable = t;
	}
	
	@Override
	public final FlagsIntTest modifiers() {
		return _modifiers;
	}
	
	public final void setModifierFlag(int mask) {
		_modifiers.set(mask);
	}
	
	protected void markValid() {
		_bValid = true;
	}
	
	public <A extends ObservedActivity> A getActivityAs() {
		@SuppressWarnings("unchecked")
		A activity = (A) _activity;
		return activity;
	}
	
	public final void setActivity(ObservedActivity activity) {
		Objects.requireNonNull(activity, "activity");
		_activity = activity;
	}
	
	protected void coreCopyFrom(XResult result) {
		_origin = result.getOriginator();
		_modifiers.copyFrom(result.modifiers());
		_bValid = result.isValid();
		_throwable = result.getThrowable();
	}
	
	/**
	 * Copy another result into this result.
	 * 
	 * A method would use this to overlay another result from a method it called that failed
	 * 		so that the caller has the actual origin and exception from the source of the exception.
	 * @param result The result from the subordinate method.
	 */
	public final void copyFrom(XResult result) {
		Objects.requireNonNull(result, "result");
		coreCopyFrom(result);
		_activity = result.getActivity();
	}
	
	/**
	 * Copy another result into this result with defined handing for the activity.
	 * 
	 * A method would use this to overlay another result from a method it called that failed
	 * 		so that the caller has the actual origin and exception from the source of the exception.
	 * @param result The result from the subordinate method.
	 */
	public final void copyFrom(XResult result, BiFunction<XResultCarrierBase, ObservedActivity, ObservedActivity> transferActivity) {
		Objects.requireNonNull(result, "result");
		Objects.requireNonNull(transferActivity, "transferActivity");
		coreCopyFrom(result);
		_activity = transferActivity.apply(this, result.getActivity());
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

	@Override
	public Throwable getThrowable() {
		return _throwable;
	}

	@Override
	public boolean hasException() {
		return _throwable != null && _throwable instanceof Exception;
	}

	/**
	 * Get the exception, if any, that was thrown performing the requested operation.
	 * @return The captured exception, or {@code null} if there was none.
	 */
	@Override
	public Exception getException() {
		if (_throwable == null) {
			return null;
		} else if (_throwable instanceof Exception exc) {
			return exc;
		} else {
			return null;
		}
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
		if (_throwable == null) {
			return false;
		} else {
			return classException.isAssignableFrom(_throwable.getClass());
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
		if (_throwable != null) {
			sb.append(", exception=");
			sb.append(_throwable.getClass().getSimpleName());
		}
		sb.append(']');
		return sb.toString();
	}
}
