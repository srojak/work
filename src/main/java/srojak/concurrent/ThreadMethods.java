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
package srojak.concurrent;

import java.util.Objects;

import srojak.core.tools.StringMethods;

/**
 * @author Stephen
 *
 */
public class ThreadMethods {
	
	private static String identifyIt(Thread thread) {
		String strName = thread.getName();
		if (strName == null || strName.isBlank()) {
			strName = "0x" + Long.toString(thread.getId(), 16);
		} else {
			strName = StringMethods.encloseInQuotes(strName);
		}
		return strName;
	}

	public static String identifyThread(Thread thread) {
		Objects.requireNonNull(thread, "thread");
		return identifyIt(thread);
	}
	
	public static String identifyCurrentThread() {
		return identifyIt(Thread.currentThread());
	}
}
