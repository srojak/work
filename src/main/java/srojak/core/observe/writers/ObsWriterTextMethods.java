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
package srojak.core.observe.writers;

/**
 * @author Stephen
 *
 */
public class ObsWriterTextMethods {

	public static final String LEADER_DIAG = "*DIAG: ";
	public static final String STR_NULL = "(null)";
	
	public static void formatException(StringBuilder sb, Exception exc, boolean bShowStack) {
		if (exc != null) {
			sb.append(" caught ");
			sb.append(exc.getClass().getSimpleName());
			sb.append("\n  ");
			sb.append(exc.getMessage());
			if (bShowStack) {
				StackTraceElement[] frames = exc.getStackTrace();
				sb.append("\nstack trace:");
				for (StackTraceElement frame : frames) {
					sb.append("\n  ");
					sb.append(frame);
				}
			}
		} else {
			sb.append(" **null exception");
		}
	}
}
