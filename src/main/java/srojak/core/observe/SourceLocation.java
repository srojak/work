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
package srojak.core.observe;

/**
 * @author Stephen
 *
 */
public final class SourceLocation {
	private final String _strClass;
	private final String _strMethod;
	private final int _nLine;
	
	public static SourceLocation here() {
		return new SourceLocation(Thread.currentThread().getStackTrace()[2]);
	}
	
	public static SourceLocation caller() {
		return new SourceLocation(Thread.currentThread().getStackTrace()[3]);
	}
	
	private SourceLocation(StackTraceElement element) {
		_strClass = element.getClassName();
		_strMethod = element.getMethodName();
		_nLine = element.getLineNumber();
	}
	
	public String getClassName() {
		return _strClass;
	}
	
	public String getMethodName() {
		return _strMethod;
	}
	
	public int getLineNumber() {
		return _nLine;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		sb.append("class=");
		sb.append(_strClass);
		sb.append(", method=");
		sb.append(_strMethod);
		sb.append(", line=");
		sb.append(_nLine);
		sb.append(']');
		return sb.toString();
	}
}
