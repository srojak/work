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
package srojak.debug.impl;

import java.time.LocalDateTime;
import java.util.EnumSet;

import srojak.core.faults.FaultFormat;
import srojak.core.faults.StructuredFault;
import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.code.CodeFaultObservation;
import srojak.core.observe.code.CodeObservation;
import srojak.core.observe.writers.ObservationWriterStandardBase;

/**
 * @author Stephen
 *
 */
public class DebugWriterForwarder
		extends ObservationWriterStandardBase {

	/**
	 * 
	 */
	public DebugWriterForwarder() {
		// pass all exception details to the debug writer
		setShowExceptionStackEnabled(true);
		setCanWrite(true);
	}

	@Override
	public boolean canWriteAt(ObsLevel level) {
		// use the value from the debug writer
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		return writerDebug.canWriteAt(level);
	}

	@Override
	protected void innerWrite(ObsLevel level, String strText) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.write(level, strText);
	}

	@Override
	protected void innerWrite(ObsLevel level, SourceLocation locOrigin, boolean bIsTrace, String strText) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		if (bIsTrace) {
			writerDebug.writeTrace(level, locOrigin, strText);
		} else {
			writerDebug.write(level, locOrigin, strText);
		}
	}

	@Override
	protected void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			Exception exc, boolean bShowStack) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.writeException(level, locOrigin, activity, exc, bShowStack);
	}

	@Override
	protected void innerWriteFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, String strLead, StructuredFault fault) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.writeFault(level, locOrigin, activity, formats, strLead, fault);
	}

	@Override
	protected void innerWriteObserved(String strLead, CodeObservation observation, EnumSet<ObsFormatOption> optionsFormat) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		// TODO: what happens to the options?
		writerDebug.writeObserved(strLead, observation);
	}

	@Override
	protected void innerWriteObservedFault(String strLead, CodeFaultObservation observation, EnumSet<ObsFormatOption> optionsFormat) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		// TODO: what happens to the options?
		writerDebug.writeObservedFault(strLead, observation);
	}

	@Override
	protected void innerWriteTimeStamp(ObsLevel level, SourceLocation locOrigin, String strLead, LocalDateTime dtObs) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.writeTimeStamp(level, locOrigin, strLead, dtObs);
	}

	@Override
	protected void innerWriteDiagnostic(String strText) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.writeDiagnostic(strText);
	}

	@Override
	protected void innerWriteDiagnostic(SourceLocation locOrigin, String strText) {
		ObservationWriter writerDebug = DebugNexusCore.getWriter();
		writerDebug.writeDiagnostic(strText);
	}

}
