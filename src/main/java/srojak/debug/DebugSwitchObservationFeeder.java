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

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;

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
import srojak.debug.impl.DebugSwitchContent;

/**
 * @author Stephen
 *
 */
public final class DebugSwitchObservationFeeder
		extends ObservationWriterStandardBase {
	private final DebugSwitchContent _swDebug;

	/**
	 * @param bIsFiltered
	 */
	public DebugSwitchObservationFeeder(DebugSwitch swDebug) {
		super();
		Objects.requireNonNull(swDebug, "swDebug");
		_swDebug = (DebugSwitchContent) swDebug;
		setCanWrite(true);
	}

	@Override
	public boolean canWriteAt(ObsLevel level) {
		return _swDebug.isLevelAccepted(level);
	}

	@Override
	public ObsLevel getObsLevel() {
		return _swDebug.getObsLevel();
	}

	@Override
	protected void innerWrite(ObsLevel level, String strText) {
		_swDebug.record(level, strText);
	}

	@Override
	protected void innerWrite(ObsLevel level, SourceLocation locOrigin, boolean bIsTrace, String strText) {
		// TODO act on bIsTrace
		ObservationWriter writer = _swDebug.getWriterForSwitch();
		writer.write(level, locOrigin, strText);
	}

	@Override
	protected void innerWriteObserved(String strLead, CodeObservation observation, EnumSet<ObsFormatOption> optionsFormat) {
		// TODO requires improved interface
		ObservationWriter writer = _swDebug.getWriterForSwitch();
		writer.writeObserved(strLead, observation);
	}

	@Override
	protected void innerWriteObservedFault(String strLead, CodeFaultObservation observation, EnumSet<ObsFormatOption> optionsFormat) {
		// TODO requires improved interface
		ObservationWriter writer = _swDebug.getWriterForSwitch();
		writer.writeObservedFault(strLead, observation);
	}

	@Override
	protected void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			Exception exc, boolean bShowStack) {
		// TODO requires improved interface
		ObservationWriter writer = _swDebug.getWriterForSwitch();
		writer.writeException(level, locOrigin, activity, exc, bShowStack);
	}

	@Override
	protected void innerWriteFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, String strLead, StructuredFault fault) {
		ObservationWriter writer = _swDebug.getWriterForSwitch();
		writer.writeFault(level, locOrigin, activity, formats, strLead, fault);
	}

	@Override
	protected void innerWriteTimeStamp(ObsLevel level, SourceLocation locOrigin, String strLead, LocalDateTime dtObs) {
		ObservationWriter writer = _swDebug.getWriterForSwitch();
		writer.writeTimeStamp(level, locOrigin, strLead, dtObs);		
	}

	@Override
	protected void innerWriteDiagnostic(String strText) {
		ObservationWriter writer = _swDebug.getWriterForSwitch();
		writer.writeDiagnostic(SourceLocation.redacted(), strText);
	}

	@Override
	protected void innerWriteDiagnostic(SourceLocation locOrigin, String strText) {
		ObservationWriter writer = _swDebug.getWriterForSwitch();
		writer.writeDiagnostic(locOrigin, strText);
	}

}
