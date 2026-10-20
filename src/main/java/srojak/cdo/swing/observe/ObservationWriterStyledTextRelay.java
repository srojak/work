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
package srojak.cdo.swing.observe;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;

import javax.swing.text.AttributeSet;

import srojak.cdo.swing.StyledTextMessageRelay;
import srojak.core.faults.FaultFormat;
import srojak.core.faults.FaultMethods;
import srojak.core.faults.StructuredFault;
import srojak.core.observe.ObsFormatOption;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCommon;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceDetail;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.code.CodeFaultObservation;
import srojak.core.observe.code.CodeObservation;
import srojak.core.observe.writers.ObsWriterTextMethods;
import srojak.core.observe.writers.ObservationWriterStandardBase;

/**
 * @author Stephen
 *
 */
public class ObservationWriterStyledTextRelay 
		extends ObservationWriterStandardBase {
	private final StyledTextMessageRelay _receiver;

	/**
	 * 
	 */
	public ObservationWriterStyledTextRelay(StyledTextMessageRelay receiver) {
		Objects.requireNonNull(receiver, "receiver");
		_receiver = receiver;
		setCanWrite(true);
	}

	@Override
	protected void innerWrite(ObsLevel level, String strText) {
		AttributeSet style = ObsLevelStyleMap.getStyle(level);
		StringBuilder sb = new StringBuilder(level.getName());
		sb.append(": ");
		sb.append(strText);
		_receiver.writeln(style, sb.toString());
	}

	@Override
	protected void innerWriteObserved(CodeObservation observation, EnumSet<ObsFormatOption> optionsFormat) {
		AttributeSet style = ObsLevelStyleMap.getStyle(observation.level());
		StringBuilder sb = new StringBuilder();
		observation.writeSelfTo(sb, optionsFormat);
		_receiver.writeln(style, sb.toString());
	}

	@Override
	protected void innerWriteObservedFault(CodeFaultObservation observation, EnumSet<ObsFormatOption> optionsFormat) {
		AttributeSet style = ObsLevelStyleMap.getStyle(observation.level());
		StringBuilder sb = new StringBuilder();
		observation.writeSelfTo(sb, optionsFormat);
		_receiver.writeln(style, sb.toString());
	}

	@Override
	protected void innerWrite(ObsLevel level, SourceLocation locOrigin, boolean bIsTrace, String strText) {
		AttributeSet style = ObsLevelStyleMap.getStyle(level);
		StringBuilder sb = new StringBuilder(level.getName());
		if (bIsTrace || canShowLocations()) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": ");
		sb.append(strText);
		_receiver.writeln(style, sb.toString());
	}

	@Override
	protected void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			Exception exc, boolean bShowStack) {
		AttributeSet style = ObsLevelStyleMap.getStyle(level);
		StringBuilder sb = new StringBuilder(level.getName());
		if (canShowLocations()) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": when ");
		sb.append(activity.describe());
		ObsWriterTextMethods.formatException(sb, exc, bShowStack);
		_receiver.writeln(style, sb.toString());
	}

	@Override
	protected void innerWriteFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, StructuredFault fault) {
		AttributeSet style = ObsLevelStyleMap.getStyle(level);
		StringBuilder sb = new StringBuilder(level.getName());
		if (canShowLocations()) {
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
		_receiver.writeln(style, sb.toString());
	}

	@Override
	protected void innerWriteTimeStamp(ObsLevel level, SourceLocation locOrigin, LocalDateTime dtObs) {
		AttributeSet style = ObsLevelStyleMap.getStyle(level);
		StringBuilder sb = new StringBuilder(level.getName());
		if (canShowLocations()) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(" time: ");
		sb.append(ObservationCommon.FORMAT_TIME_STAMP.format(dtObs));
		_receiver.writeln(style, sb.toString());
	}

	@Override
	protected void innerWriteDiagnostic(String strText) {
		AttributeSet style = ObsLevelStyleMap.getDiagnosticStyle();
		StringBuilder sb = new StringBuilder(ObsWriterTextMethods.LEADER_DIAG);
		sb.append(": ");
		sb.append(strText);
		_receiver.writeln(style, sb.toString());
	}

	@Override
	protected void innerWriteDiagnostic(SourceLocation locOrigin, String strText) {
		AttributeSet style = ObsLevelStyleMap.getDiagnosticStyle();
		StringBuilder sb = new StringBuilder(ObsWriterTextMethods.LEADER_DIAG);
		if (canShowLocations()) {
			sb.append(' ');
			sb.append(locOrigin.toString(SourceDetail.CLASS_METHOD));
		}
		sb.append(": ");
		sb.append(strText);
		_receiver.writeln(style, sb.toString());
	}
}
