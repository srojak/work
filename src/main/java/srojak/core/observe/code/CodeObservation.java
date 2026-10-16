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
package srojak.core.observe.code;

import java.util.EnumSet;

import srojak.core.logic.FlagsIntTest;
import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 * A structured interface between an {@code ObservationCollector} and an
 * {@code ObservationWriter} permitting the writer to control formatting.
 */
public interface CodeObservation {

	/**
	 * The observation level at which the observation was recorded.
	 * @return The {@code ObsLevel} at which the observation was recorded.
	 */
	ObsLevel level();
	
	/**
	 * The source location at which the observation was initiated.
	 * @return The {@code SourceLocation} at which the observation was initiated.
	 */
	SourceLocation origin();
	
	/**
	 * Read-only access to the observation flags.
	 * @return Testable flag values.
	 */
	FlagsIntTest flags();
	
	/**
	 * Does this observation have the originator class?
	 * @return {@code true} if the observation contains the originator class.
	 */
	boolean hasOriginClass();
	
	/**
	 * Should full class names be written?
	 * @return {@code true} if the writer should write full class names.
	 */
	boolean writesFullClassNames();
	
	/**
	 * Get the originator class name, if recorded.
	 * @return A string containing the originator class name, 
	 * as formatted according to settings,
	 * or {@code null} if not recorded.
	 */
	String getOriginClassName();
	
	/**
	 * Get text content associated with the observation.
	 * @return A string containing text content.
	 */
	String content();
	
	/**
	 * Write the class origin, if any.
	 * @param sb The {@code StringBuilder} to which text is to be written.
	 * @param optionsFormat Formatting options for writing.
	 */
	void writeOriginTo(StringBuilder sb, EnumSet<ObsFormatOption> optionsFormat);
	
	/**
	 * Write the entire content of the observation.
	 * @param sb The {@code StringBuilder} to which text is to be written.
	 * @param optionsFormat Formatting options for writing.
	 */
	void writeSelfTo(StringBuilder sb, EnumSet<ObsFormatOption> optionsFormat);
}
