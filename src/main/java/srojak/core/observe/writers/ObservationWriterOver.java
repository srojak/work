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
public abstract class ObservationWriterOver 
		extends ObservationWriterBase 
		implements ObservationWriter {
	private ObservationWriter _writer;
	
	public ObservationWriterOver(IONodeIdentifier writesTo) {
		super(writesTo);
		_writer = null;
	}
	
	protected ObservationWriter assignedWriter() {
		return _writer;
	}
	
	protected abstract ObservationWriter currentWriter();
	
	protected void initializeOver() {
		_writer = currentWriter();
		if (_writer != null && _writer.canWrite()) {
			setCanWrite(true);
		}
	}

	@Override
	public boolean canWriteAt(ObsLevel level) {
		_writer = currentWriter();
		return _writer.canWriteAt(level);
	}

	@Override
	public void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText) {
		_writer.write(collector, locOrigin, strText);
	}

	@Override
	public void write(ObsLevel level, String strText) {
		_writer.write(level, strText);
	}

	@Override
	public void writeObserved(String strLead, CodeObservation observation) {
		_writer.writeObserved(strLead, observation);
	}

	@Override
	public void write(ObsLevel level, SourceLocation locOrigin, String strText) {
		_writer.write(level, locOrigin, strText);
	}

	@Override
	public void writeObservedTrace(String strLead, CodeObservation observation) {
		_writer.writeObservedTrace(strLead, observation);
	}

	@Override
	public void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack) {
		_writer.writeException(level, locOrigin, activity, exc, bShowStack);
	}

	@Override
	public void writeObservedFault(String strLead, CodeFaultObservation observation) {
		_writer.writeObservedFault(strLead, observation);
	}

	@Override
	public void writeFault(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity,
			EnumSet<FaultFormat> formats, String strLead, StructuredFault fault) {
		_writer.writeFault(level, locOrigin, activity, formats, strLead, fault);
	}

	@Override
	public void writeTimeStamp(ObsLevel level, SourceLocation locOrigin, String strLead, LocalDateTime dtObs) {
		_writer.writeTimeStamp(level, locOrigin, strLead, dtObs);
	}

	@Override
	public void writeDiagnostic(String strText) {
		_writer = currentWriter();
		_writer.writeDiagnostic(strText);
	}

	@Override
	public void writeDiagnostic(SourceLocation locOrigin, String strText) {
		_writer = currentWriter();
		_writer.writeDiagnostic(locOrigin, strText);
	}
}
