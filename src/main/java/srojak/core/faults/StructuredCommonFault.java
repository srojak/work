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
package srojak.core.faults;

import java.util.List;
import java.util.Objects;

import srojak.core.ClassAndContent;

/**
 * @author Stephen
 *
 */
public class StructuredCommonFault 
		implements StructuredFault {
	private final FaultType _type;
	private final ClassAndContent _content;
	private final StackTraceElement[] _stack;
	private StructuredFault _cause;

	private StructuredCommonFault(FaultType typeFault, Throwable thr, ClassAndContent contentThr) {
		Objects.requireNonNull(thr, "thr");
		Objects.requireNonNull(contentThr, "contentThr");
		_type = typeFault;
		_content = contentThr;
		_stack = thr.getStackTrace();
		_cause = null;
	}
	
	public StructuredCommonFault(Error error, ClassAndContent contentThr) {
		this(FaultType.ERROR, error, contentThr);
	}
	
	public StructuredCommonFault(Exception exc, ClassAndContent contentThr) {
		this(FaultType.EXCEPTION, exc, contentThr);
	}
	
	public void setCause(StructuredFault cause) {
		Objects.requireNonNull(cause, "cause");
		_cause = cause;
	}

	@Override
	public FaultType getFaultType() {
		return _type;
	}

	@Override
	public ClassAndContent getContent() {
		return _content;
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return _stack;
	}

	@Override
	public boolean hasCause() {
		return _cause != null;
	}

	@Override
	public StructuredFault getCause() {
		return _cause;
	}

}
