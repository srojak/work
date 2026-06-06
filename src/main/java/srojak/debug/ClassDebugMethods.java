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
package srojak.debug;

import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.logic.BooleanLatch;
import srojak.debug.tools.DebuggingBooleanLatch;

/**
 * @author Stephen
 *
 */
public class ClassDebugMethods {
	public static final String OPT_DEBUG_LATCH = "OptDebugLatch";
	
	public static BooleanLatch makeBooleanLatch(ClassDebugOptions options, NameToken tokenName) {
		Objects.requireNonNull(options, "options");
		int nValue = options.getOptionValue(OPT_DEBUG_LATCH);
		if (nValue != 0) {
			return new DebuggingBooleanLatch(tokenName);
		} else {
			return new BooleanLatch();
		}
	}
}
