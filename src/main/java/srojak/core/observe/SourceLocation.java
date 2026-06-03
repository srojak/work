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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Stephen
 *
 */
public final class SourceLocation
		implements SourceDetailFlags {
	private final String _strPackage;
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
		String strFull = element.getClassName();
		int index = strFull.lastIndexOf('.');
		_strClass = strFull.substring(index + 1);
		_strPackage = strFull.substring(0, index - 1);
		_strMethod = element.getMethodName();
		_nLine = element.getLineNumber();
	}
	
	public String getPackageName() {
		return _strPackage;
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
	
	public String toString(SourceDetail detail) {
		List<String> list = new LinkedList<String>();
		if (detail.isFlagSet(FLAG_CLASS)) {
			list.add("class=" + (detail.isFlagSet(FLAG_PACKAGE)
					? _strPackage + "." + _strClass : _strClass));
		}
		if (detail.isFlagSet(FLAG_METHOD)) {
			list.add("method=" + _strMethod);
		}
		if (detail.isFlagSet(FLAG_LINE)) {
			list.add("line=" + _nLine);
		}
		StringBuilder sb = new StringBuilder("@[");
		Iterator<String> iter = list.iterator();
		if (iter.hasNext()) {
			sb.append(iter.next());
		}
		while (iter.hasNext()) {
			sb.append(", ");
			sb.append(iter.next());
		}
		sb.append(']');
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("@[");
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
