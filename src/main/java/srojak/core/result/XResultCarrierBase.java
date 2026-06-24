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

import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public abstract class XResultCarrierBase
		implements XResult {
	private SourceLocation _origin;
	private boolean _bValid;
	private Exception _exception;

	protected XResultCarrierBase(SourceLocation source) {
		_origin = source;
		_bValid = false;
		_exception = null;
	}
	
	public void caughtException(Exception exc) {
		Objects.requireNonNull(exc, "exc");
		_exception = exc;
	}
	
	protected void markValid() {
		_bValid = true;
	}
	
	public void copyFrom(XResult result) {
		Objects.requireNonNull(result, "result");
		_origin = result.getOriginator();
		_bValid = result.isValid();
		_exception = result.getException();
	}

	@Override
	public SourceLocation getOriginator() {
		return _origin;
	}

	@Override
	public boolean isValid() {
		return _bValid;
	}

	@Override
	public Exception getException() {
		return _exception;
	}

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Result [origin=");
		sb.append(_origin.toString(SourceDetail.PACKAGE_CLASS_METHOD));
		sb.append(", valid=");
		sb.append(_bValid);
		if (_exception != null) {
			sb.append(", exception=");
			sb.append(_exception.getClass().getSimpleName());
		}
		sb.append(']');
		return sb.toString();
	}
}
