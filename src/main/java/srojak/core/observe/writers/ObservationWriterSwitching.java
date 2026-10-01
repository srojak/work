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

import java.util.Objects;

import srojak.core.observe.HasObsLevel;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public class ObservationWriterSwitching
		extends ObservationWriterBase
		implements HasObsLevel {
	private final ObservationWriter _writerHigh;
	private final ObservationWriter _writerLow;
	private ObsLevel _levelWriteHigh;

	/**
	 * 
	 */
	public ObservationWriterSwitching(ObservationWriter writerHigh, ObservationWriter writerLow) {
		Objects.requireNonNull(writerHigh, "writerHigh");
		Objects.requireNonNull(writerLow, "writerLow");
		_writerHigh = writerHigh;
		_writerLow = writerLow;
		_levelWriteHigh = ObsLevel.WARN;
	}

	@Override
	public boolean canWrite() {
		return _writerHigh.canWrite() && _writerLow.canWrite();
	}
	
	private boolean useHighWriter(ObsLevel level) {
		return _levelWriteHigh.compareTo(level) > 0;
	}

	@Override
	public void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText) {
		if (useHighWriter(collector.getLevel())) {
			_writerHigh.write(collector, locOrigin, strText);
		} else {
			_writerLow.write(collector, locOrigin, strText);
		}
	}

	@Override
	public ObsLevel getObsLevel() {
		return _levelWriteHigh;
	}

	@Override
	protected boolean trySetObsLevel(ObsLevel level) {
		_levelWriteHigh = level;
		return true;
	}

	@Override
	public void write(ObsLevel level, String strText) {
		Objects.requireNonNull(level, "level");
		ObservationWriter writer = useHighWriter(level) ? _writerHigh : _writerLow;
		writer.write(level, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			writer.flush();
		}
	}

	@Override
	public void write(ObsLevel level, SourceLocation locOrigin, String strText) {
		Objects.requireNonNull(level, "level");
		ObservationWriter writer = useHighWriter(level) ? _writerHigh : _writerLow;
		writer.write(level, locOrigin, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			writer.flush();
		}
	}

	@Override
	public void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack) {
		Objects.requireNonNull(level, "level");
		ObservationWriter writer = useHighWriter(level) ? _writerHigh : _writerLow;
		writer.writeException(level, locOrigin, activity, exc, bShowStack);
		if (isAutoFlush()) {
			writer.flush();
		}
	}

	@Override
	public void writeDiagnostic(String strText) {
		_writerHigh.writeDiagnostic(strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			_writerHigh.flush();
		}
	}

	@Override
	public void writeDiagnostic(SourceLocation locOrigin, String strText) {
		_writerHigh.writeDiagnostic(locOrigin, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (isAutoFlush()) {
			_writerHigh.flush();
		}
	}

	@Override
	public void flush() {
		_writerHigh.flush();
		_writerLow.flush();
	}
}
