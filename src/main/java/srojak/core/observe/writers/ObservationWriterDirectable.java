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
public abstract class ObservationWriterDirectable 
		extends ObservationWriterBase 
		implements ObservationWriter {
	private final ObservationWriter _writerDefault;
	private ObservationWriter _writer;

	/**
	 * @param writesTo
	 */
	public ObservationWriterDirectable(IONodeIdentifier writesTo, ObservationWriter writerDefault) {
		super(writesTo);
		Objects.requireNonNull(writerDefault, "writerDefault");
		if (!writerDefault.canWrite()) {
			throw new IllegalArgumentException("writerDefault is not usable");
		}
		_writerDefault = writerDefault;
		_writer = _writerDefault;
		setCanWrite(_writer.canWrite());
	}
	
	public void redirectWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		if (!writer.canWrite()) {
			throw new IllegalArgumentException("writer is not usable");
		}
		_writer = writer;
	}
	
	public void restoreDefaultWriter(boolean bClose) {
		if (_writer != _writerDefault) {
			ObservationWriter writerPrior = _writer;
			_writer = _writerDefault;
			if (bClose) {
				writerPrior.orderlyClose();
			}
		}
	}
	
	public boolean isCurrentWriter(ObservationWriter writerCompare) {
		return _writer == writerCompare;
	}
	
	protected void checkActiveWriter() {
		if (!_writer.canWrite()) {
			if (_writer != _writerDefault) {
				_writer = _writerDefault;
			}
		}
	}

	@Override
	public void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText) {
		checkActiveWriter();
		_writer.write(collector, locOrigin, strText);
	}

	@Override
	public void write(ObsLevel level, String strText) {
		checkActiveWriter();
		_writer.write(level, strText);
	}

	@Override
	public void writeObserved(CodeObservation observation) {
		checkActiveWriter();
		_writer.writeObserved(observation);
	}

	@Override
	public void write(ObsLevel level, SourceLocation locOrigin, String strText) {
		checkActiveWriter();
		_writer.write(level, locOrigin, strText);
	}

	@Override
	public void writeObservedTrace(CodeObservation observation) {
		checkActiveWriter();
		_writer.writeObservedTrace(observation);
	}

	@Override
	public void writeTrace(ObsLevel level, SourceLocation locOrigin, String strText) {
		checkActiveWriter();
		_writer.writeTrace(level, locOrigin, strText);
	}

	@Override
	public void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack) {
		checkActiveWriter();
		_writer.writeException(level, locOrigin, activity, exc, bShowStack);
	}

	@Override
	public void writeObservedFault(CodeFaultObservation observation) {
		checkActiveWriter();
		_writer.writeObservedFault(observation);
	}

	@Override
	public void writeFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, StructuredFault fault) {
		checkActiveWriter();
		_writer.writeFault(level, locOrigin, activity, formats, fault);
	}

	@Override
	public void writeTimeStamp(ObsLevel level, SourceLocation locOrigin, LocalDateTime dtObs) {
		checkActiveWriter();
		_writer.writeTimeStamp(level, locOrigin, dtObs);
	}

	@Override
	public void writeDiagnostic(String strText) {
		checkActiveWriter();
		_writer.writeDiagnostic(strText);
	}

	@Override
	public void writeDiagnostic(SourceLocation locOrigin, String strText) {
		checkActiveWriter();
		_writer.writeDiagnostic(locOrigin, strText);
	}

}
