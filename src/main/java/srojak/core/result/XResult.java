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

import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 * A result from a method that returns no value if it succeeds.
 * Other result interfaces build on this.
 */
public interface XResult {
	
	/**
	 * Get the source location where the result object was created.
	 * This will usually be in the method where an exception could be thrown,
	 * 		but not at the line where the exception could occur.
	 * @return The source location where the result object was created.
	 */
	SourceLocation getOriginator();
	
	/**
	 * Did the requested operation succeed?
	 * @return {@code true} if the operation was successful.
	 */
	boolean isValid();

	/**
	 * Get the exception, if any, that was thrown performing the requested operation.
	 * @return The captured exception, or {@code null} if there was none.
	 */
	Exception getException();
	
	/**
	 * Was there an exception of the specified type thrown?
	 * @param classException The class of the exception of interest.
	 * @return {@code true} if there was an exception and it is of the given class or a supertype.
	 */
	boolean isExceptionOfType(Class<?> classException);
}
