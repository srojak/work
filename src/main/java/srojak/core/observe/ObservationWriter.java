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

import java.io.IOException;

import srojak.core.events.LifeCycleListener;
import srojak.core.io.IONodeIdentifier;

/**
 * @author Stephen
 *
 * <h2>Responsibilities</h2>
 * <ol>
 * <li>Answer ability to present observation at its {@code ObsLevel}.</li>
 * <li>Apply options to the observation.</li>
 * <li>Write the observation to the output destination.</li>
 * <li>Raise a {@code LifeCycleEvent} if the output cannot be written.</li>
 * </ol>
 * 
 */
public interface ObservationWriter
		extends HasObsLevel, ObservationCommonWriter {
	
	/**
	 * Is this writer filtering by {@code ObsLevel} ?
	 * @return {@code true} if the writer is filtering.
	 */
	boolean isLevelFiltering();
	
	/**
	 * Is this writer set to flush after every write?
	 * @return {@code true} if autoflush is enabled.
	 */
	boolean isAutoFlush();
	
	/**
	 * Set the autoflush state for the writer.
	 * @param bState The value to which the state should be set.
	 */
	void setAutoFlush(boolean bState);
	
	/**
	 * Can this writer show code locations?
	 * @return {@code true} if the writer can show code locations.
	 */
	boolean canShowLocations();
	
	/**
	 * Set whether to show code locations.
	 * @param bState The value to which the state should be set.
	 */
	void setShowLocations(boolean bState);
	
	boolean isShowExceptionStackEnabled();
	
	void setShowExceptionStackEnabled(boolean bState);
	
	void flush();
	
	void close() throws IOException;
	
	void orderlyClose();
	
	void addLifeCycleListener(LifeCycleListener listener);
	
	void removeLifeCycleListener(LifeCycleListener listener);
}
