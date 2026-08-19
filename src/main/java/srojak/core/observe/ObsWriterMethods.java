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

import java.util.IllegalFormatException;

/**
 * @author Stephen
 *
 */
public class ObsWriterMethods {

	/**
	 * 
	 * @param loc
	 * @param format
	 * @param args
	 * @return
	 */
	public static String formatSafely(SourceLocation loc, String format, Object... args) {
		// eats IllegalFormatException
		try {
			return String.format(format, args);
		} catch (IllegalFormatException exc) {
			return "Illegal format at "
					+ loc.toString(SourceDetail.CLASS_METHOD_LINE)
					+ ": " + exc.getMessage();
		}
	}
	
	/**
	 * 
	 * @param format
	 * @param args
	 * @return
	 */
	public static String formatSafely(String format, Object... args) {
		SourceLocation loc = SourceLocation.caller();
		return formatSafely(loc, format, args);
	}
}
