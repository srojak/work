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

import java.time.LocalDateTime;
import java.util.EnumSet;

import srojak.core.faults.FaultFormat;
import srojak.core.faults.StructuredFault;
import srojak.core.io.IONodeIdentifier;
import srojak.core.observe.code.CodeFaultObservation;
import srojak.core.observe.code.CodeObservation;

/**
 * @author Stephen
 *
 * The minimum interface every write-to destination must offer
 */
public interface ObservationCommonWriter {
	
	IONodeIdentifier writesTo();
	
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
	
	/**
	 * Write the contents of a single observation collector.
	 * @param collector The {@code SingleObservationCollector} bearing the content.
	 * @param locOrigin The location at which the collector was created.
	 * @param strText The text content from the collector.
	 */
	void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText);
	
	void write(ObsLevel level, String strText);
	
	void writeObserved(CodeObservation observation);
	
	void write(ObsLevel level, SourceLocation locOrigin, String strText);
	
	void writeObservedTrace(CodeObservation observation);
	
	@Deprecated(forRemoval = true)
	void writeTrace(ObsLevel level, SourceLocation locOrigin, String strText);
	
	@Deprecated(forRemoval = true)
	void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc, boolean bShowStack);
	
	void writeObservedFault(CodeFaultObservation observation);
	
	void writeFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, 
			EnumSet<FaultFormat> formats, StructuredFault fault);
	
	void writeTimeStamp(ObsLevel level, SourceLocation locOrigin, LocalDateTime dtObs);
	
	void writeDiagnostic(String strText);
	
	/**
	 * Write a diagnostic message.
	 * @param locOrigin The location at which the collector was created.
	 * @param strText The text of the message.
	 */
	void writeDiagnostic(SourceLocation locOrigin, String strText);
}
