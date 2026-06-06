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
package srojak.utest.debug;

import srojak.core.NameToken;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class TestSpecializedTarget
		extends TestSub1 {
	
	public static final NameToken ClassToken;
	private static final DebugSwitch _swDebugClass;
	
	static {
		Class<?> classThis = TestSpecializedTarget.class;
		ClassToken = NameToken.classNameFactory(classThis);
		DebugNexus debug = new DebugNexus(DebugNexus.CONS_NONE);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis),
				ObsLevel.DEBUG, true);
	}

	/**
	 * 
	 */
	public TestSpecializedTarget() {
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH);
	}

}
