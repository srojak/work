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
package srojak.core.tools;

import java.util.function.Consumer;
/**
 * @author Stephen
 *
 */
public class ExceptionMethods {
	
	public static void report(String strLeader, Exception exc, Consumer<String> writer) {
		writer.accept(strLeader);
		Class<?> classEx = exc.getClass();
		writer.accept(classEx.getName() + ":" + exc.getMessage());
	}
	
	public static void showStackTrace(Exception exc, Consumer<String> writer) {
		StackTraceElement[] frames = exc.getStackTrace();
		writer.accept("stack trace:");
		for (StackTraceElement frame : frames) {
			writer.accept("  " + frame);
		}
	}
	
	public static void reportWithStackTrace(String strLeader, 
			Exception exc, Consumer<String> writer) {
		report(strLeader, exc, writer);
		showStackTrace(exc, writer);
	}
}
