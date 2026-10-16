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
package srojak.core.observe.writers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.EnumSet;

import srojak.core.faults.FaultFormat;
import srojak.core.faults.FaultMethods;
import srojak.core.faults.StructuredFault;
import srojak.core.io.IONodeIdentifier;
import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.code.CodeFaultObservation;
import srojak.core.observe.code.CodeObservation;

/**
 * @author Stephen
 *
 */
public abstract class ObservationWriterCommonTextBase 
		extends ObservationWriterStandardBase
		implements ObservationCommon {

	/**
	 * 
	 */
	public ObservationWriterCommonTextBase() {
		super();
	}
	
	public ObservationWriterCommonTextBase(IONodeIdentifier writesTo) {
		super(writesTo);
	}
	
	protected abstract void writeln(String strText)
		throws IOException;
	
	protected abstract void flushOutput()
		throws IOException;
	
	private void writeOutput(StringBuilder sb) {
		try {
			writeln(sb.toString());
		} catch (IOException exc) {
			setCanWrite(false);
			raiseClosedEvent();
		}
	}
	
	private boolean checkCanShowLocations(ObsLevel level) {
		if (canShowLocations()) {
			return true;
		} else {
			return level.compareTo(ObsLevel.TRACE) <= 0;
		}
	}

	@Override
	protected void innerWrite(ObsLevel level, String strText) {
		StringBuilder sb = new StringBuilder(level.getName());
		sb.append(": ");
		sb.append(strText);
		writeOutput(sb);
	}

	@Override
	protected void innerWriteObserved(CodeObservation observation, EnumSet<ObsFormatOption> optionsFormat) {
		StringBuilder sb = new StringBuilder();
		observation.writeSelfTo(sb, optionsFormat);
		writeOutput(sb);
	}

	@Override
	protected void innerWriteObservedFault(CodeFaultObservation observation, EnumSet<ObsFormatOption> optionsFormat) {
		StringBuilder sb = new StringBuilder();
		observation.writeSelfTo(sb, optionsFormat);
		writeOutput(sb);
	}

	@Override
	protected void innerWrite(ObsLevel level, SourceLocation locOrigin, boolean bIsTrace, String strText) {
		StringBuilder sb = new StringBuilder(level.getName());
		if (bIsTrace || checkCanShowLocations(level)) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": ");
		sb.append(strText);
		writeOutput(sb);
	}

	@Override
	protected void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			Exception exc, boolean bShowStack) {
		StringBuilder sb = new StringBuilder(level.getName());
		if (checkCanShowLocations(level)) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": when ");
		sb.append(activity.describe());
		ObsWriterTextMethods.formatException(sb, exc, bShowStack);
		writeOutput(sb);
	}

	@Override
	protected void innerWriteFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, StructuredFault fault) {
		StringBuilder sb = new StringBuilder(level.getName());
		if (checkCanShowLocations(level)) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": when ");
		sb.append(activity.describe());
		if (formats.contains(FaultFormat.Originating)) {
			sb.append(" throwing");
		}
		sb.append(' ');
		FaultMethods.formatFaultTo(sb, formats, fault);
		writeOutput(sb);
	}

	@Override
	protected void innerWriteTimeStamp(ObsLevel level, SourceLocation locOrigin, LocalDateTime dtObs) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(level.getName());
		if (checkCanShowLocations(level)) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(" time: ");
		sb.append(FORMAT_TIME_STAMP.format(dtObs));
		writeOutput(sb);
	}

	@Override
	protected void innerWriteDiagnostic(String strText) {
		StringBuilder sb = new StringBuilder(ObsWriterTextMethods.LEADER_DIAG);
		sb.append(": ");
		sb.append(strText);
		writeOutput(sb);
	}

	@Override
	protected void innerWriteDiagnostic(SourceLocation locOrigin, String strText) {
		StringBuilder sb = new StringBuilder(ObsWriterTextMethods.LEADER_DIAG);
		if (canShowLocations()) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": ");
		sb.append(strText);
		writeOutput(sb);
	}

	@Override
	public void flush() {
		try {
			flushOutput();
		} catch (IOException exc) {
			setCanWrite(false);
			raiseClosedEvent();
		}
	}
}
