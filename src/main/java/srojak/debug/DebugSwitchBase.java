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

import srojak.core.InstanceNumberCommon;
import srojak.core.io.IONodeIdentifier;
import srojak.core.io.IONodeName;
import srojak.core.io.IONodeType;
import srojak.core.observe.ObsLevel;
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 */
public interface DebugSwitchBase {

	public static final IONodeIdentifier DEBUG_SWITCH = new IONodeName(IONodeType.ABSTRACT, "debug switch");
	
	/**
	 * Get the key for the debug switch.
	 * @return The key for this debug switch.
	 */
	DebugSwitchKey getKey();
	
	/**
	 * Get the class locator for the debug switch.
	 * @return The class locator from the key for this debug switch.
	 */
	PackageClassLocator getClassLocator();
	
	/**
	 * Get the observation level assigned to the debug switch.
	 * @return The current observation level assigned to the debug switch.
	 */
	ObsLevel getObsLevel();
	
	/**
	 * Get the observation level assigned to the debug switch for a specific instance.
	 * @param instance The instance number of the current instance.
	 * @return The current observation level assigned to the debug switch for the given instance.
	 */
	ObsLevel getObsLevelForInstance(InstanceNumberCommon instance);
	
	/**
	 * Get the state of the show source locations flag for the debug switch.
	 * @return The state of the show source locations flag for the debug switch.
	 */
	boolean showSourceLocations();
	
	/**
	 * Get the name of the control set that defined this switch, if it was defined
	 * by a control set.
	 * @return The name of the defining control set, or an empty string.
	 */
	String getDefiningControlSet();
	
	/**
	 * Test if the observation level assigned to the debug switch is at or above
	 *   a given value.
	 * @param level The test level to compare to the level assigned to the debug switch.
	 * @return {@code true} if the debug switch level is at least the given level.
	 */
	boolean isLevelAtLeast(ObsLevel level);
}
