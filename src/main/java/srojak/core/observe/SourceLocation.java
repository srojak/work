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

import java.util.Objects;

import srojak.core.impl.SourceLocationPseudo;
import srojak.core.impl.SourceLocationSpecific;

/**
 * @author Stephen
 *
 */
public sealed interface SourceLocation
		permits SourceLocationSpecific, SourceLocationPseudo {
	
	boolean isReal();
	
	String getPackageName();
	
	public String getClassName();
	
	public String getMethodName();
	
	public int getLineNumber();
	
	public String toString(SourceDetail detail);
	
	public String toString();
	
	/**
	 * Get the source location from the immediate caller.
	 * @return A source location object.
	 */
	public static SourceLocation here() {
		return new SourceLocationSpecific(Thread.currentThread().getStackTrace()[2]);
	}
	
	/**
	 * Get the source location from the caller's caller.
	 * @return A source location object.
	 */
	public static SourceLocation caller() {
		return new SourceLocationSpecific(Thread.currentThread().getStackTrace()[3]);
	}
	
	/**
	 * Get the source location from a specified level backward.
	 * @param offset The number of stack levels to go down.
	 * @return A source location object.
	 */
	public static SourceLocation caller(int offset) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		Objects.checkIndex(offset, stack.length);
		return new SourceLocationSpecific(stack[offset]);
	}
	
	public static SourceLocation redacted() {
		return new SourceLocationPseudo("redacted");
	}
	
	public static SourceLocation start() {
		return new SourceLocationPseudo("start");
	}
}
