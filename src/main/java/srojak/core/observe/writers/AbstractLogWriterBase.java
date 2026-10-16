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

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;

import srojak.core.faults.FaultFormat;
import srojak.core.faults.StructuredFault;
import srojak.core.io.IONodeIdentifier;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.code.CodeFaultObservation;
import srojak.core.observe.code.CodeObservation;

/**
 * @author Stephen
 *
 */
public abstract class AbstractLogWriterBase 
		extends ObservationWriterBase {

	/**
	 * @param writesTo
	 */
	public AbstractLogWriterBase(IONodeIdentifier writesTo) {
		super(writesTo);
		setCanWrite(true);
	}
	
	protected abstract ObservationWriter getCurrentLogWriter();

	@Override
	public void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText) {
		Objects.requireNonNull(collector, "collector");
		Objects.requireNonNull(locOrigin, "locOrigin");
		if (strText == null) {
			strText = ObsWriterTextMethods.STR_NULL;
		}
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.write(collector, locOrigin, strText);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void write(ObsLevel level, String strText) {	
		ObsLevel.validateEventLevel(level);
		if (strText == null) {
			strText = ObsWriterTextMethods.STR_NULL;
		}
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.write(level, strText);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void writeObserved(CodeObservation observation) {
		Objects.requireNonNull(observation, "observation");
		ObsLevel level = observation.level();
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.writeObserved(observation);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void writeObservedTrace(CodeObservation observation) {
		Objects.requireNonNull(observation, "observation");
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.writeObservedTrace(observation);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void write(ObsLevel level, SourceLocation locOrigin, String strText) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(locOrigin, "locOrigin");
		if (strText == null) {
			strText = ObsWriterTextMethods.STR_NULL;
		}
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.write(level, locOrigin, strText);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void writeTrace(ObsLevel level, SourceLocation locOrigin, String strText) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(locOrigin, "locOrigin");
		if (strText == null) {
			strText = ObsWriterTextMethods.STR_NULL;
		}
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.writeTrace(level, locOrigin, strText);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(locOrigin, "locOrigin");
		Objects.requireNonNull(activity, "activity");
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.writeException(level, locOrigin, activity, exc, bShowStack);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void writeObservedFault(CodeFaultObservation observation) {
		Objects.requireNonNull(observation, "observation");
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.writeObservedFault(observation);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void writeFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, StructuredFault fault) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(locOrigin, "locOrigin");
		Objects.requireNonNull(activity, "activity");
		Objects.requireNonNull(formats, "formats");
		Objects.requireNonNull(fault, "fault");
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.writeFault(level, locOrigin, activity, formats, fault);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void writeTimeStamp(ObsLevel level, SourceLocation locOrigin, LocalDateTime dtObs) {
		ObsLevel.validateEventLevel(level);
		Objects.requireNonNull(dtObs, "dtObs");
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.writeTimeStamp(level, locOrigin, dtObs);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void writeDiagnostic(String strText) {
		if (strText == null) {
			strText = ObsWriterTextMethods.STR_NULL;
		}
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.writeDiagnostic(strText);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

	@Override
	public void writeDiagnostic(SourceLocation locOrigin, String strText) {
		Objects.requireNonNull(locOrigin, "locOrigin");
		if (strText == null) {
			strText = ObsWriterTextMethods.STR_NULL;
		}
		ObservationWriter writerLog = getCurrentLogWriter();
		writerLog.writeDiagnostic(strText);
		if (isAutoFlush()) {
			writerLog.flush();
		}
	}

}
