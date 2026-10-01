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

import java.nio.file.Path;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.props.PropertiesReadOnly;

/**
 * @author Stephen
 *
 */
public interface CommonDebugAccess {
	
	/**
	 * Get the current properties.
	 * @return The current properties collection.
	 */
	PropertiesReadOnly getProperties();
	
	/**
	 * Get the writer for the debug switches.
	 * @return The current writer.
	 */
	ObservationWriter getWriter();
	
	/**
	 * The writer for the debug switches is in the kernel.
	 */
	
	/**
	 * Get the {@code ObsLevel} at which the debug facility will write an announcement.
	 * @return The current {@code ObsLevel} threshold.
	 */
	ObsLevel getAnnounceLevel();
	
	/**
	 * Set the {@code ObsLevel} at which the debug facility will write an announcement.
	 * @param level The new {@code ObsLevel} threshold.
	 */
	void setAnnounceLevel(ObsLevel level);
	
	/**
	 * Get a specialized {@Code ObservationCollector} that forwards all observations to the current
	 * 	debug writer.
	 * @return The debug observation collector.
	 */
	ObservationCollector getDebugObservationCollector();
	
	/**
	 * Get the log directory, if defined.
	 * @return A {@code Path} object identifying the log directory, or {@code null} if none is defined.
	 */
	Path getLogDirectory();
	
	/**
	 * Get the debug level for a specific debug switch.
	 * The switch will be created if it does not already exist.
	 * @param key The key identifying the debug switch.
	 * @return The observation level defined by the debug switch.
	 */
	ObsLevel getDebugLevel(DebugSwitchKey key);
	
	/**
	 * Get a specific debug switch.
	 * The switch will be created if it does not already exist.
	 * @param key The key identifying the debug switch.
	 * @return The debug switch.
	 */
	DebugSwitch getSwitch(DebugSwitchKey key);
}