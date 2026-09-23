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

/**
 * @author Stephen
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
	
	/**
	 * Can this writer write at all?
	 * @return {@code true} if the writer can write.
	 */
	boolean canWrite();

	/**
	 * Can this writer write output at the given level?
	 * @param level The {@code ObsLevel} threshold to test.
	 * @return {@code true} if the writer can write at the given level.
	 */
	boolean canWriteAt(ObsLevel level);
	
	void write(ObsLevel level, SourceLocation locOrigin, String strText);
	
	void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc, boolean bShowStack);
	
	void writeDiagnostic(String strText);
	
	void writeDiagnostic(SourceLocation locOrigin, String strText);
	
	void close() throws IOException;
}
