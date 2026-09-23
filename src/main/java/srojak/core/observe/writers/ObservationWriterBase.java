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
import java.util.Objects;

import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsLevelFilterDecorator;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.observe.SourceLocation;

/**
 * @author Stephen
 *
 */
public abstract class ObservationWriterBase
		implements ObservationWriter {
	private final FlagsInt _flags;
	private ObsLevelFilterDecorator _filterLevel;

	protected static final ObsLevel DEFAULT_FILTERED_LEVEL = ObsLevel.INFO;
	protected static final ObsLevel UNFILTERED_LEVEL = ObsLevel.FINEST;
	protected static final int FLAGS_AUTO_FLUSH = 0x1;
	protected static final int FLAGS_CAN_WRITE = 0x2;
	protected static final int FLAGS_SHOW_LOCATIONS = 0x4;
	protected static final int FLAGS_CAN_SHOW_EXC_STACK = 0x8;
	protected static final int FLAGS_NO_FILTER = 0x10;
	protected static final int FLAGS_CLOSABLE = 0x20;
	
	/**
	 * 
	 */
	public ObservationWriterBase() {
		_flags = new FlagsInt();
		_filterLevel = null;
	}
	
	public final FlagsIntTest getFlags() {
		return _flags;
	}
	
	public final void enableLevelFilter() {
		if (_filterLevel == null) {
			_filterLevel = new ObsLevelFilterDecorator(DEFAULT_FILTERED_LEVEL);
		}
	}

	@Override
	public final boolean isLevelFiltering() {
		return _filterLevel != null;
	}

	@Override
	public boolean isAutoFlush() {
		return _flags.test(FLAGS_AUTO_FLUSH);
	}

	@Override
	public void setAutoFlush(boolean bState) {
		_flags.apply(bState, FLAGS_AUTO_FLUSH);
	}

	@Override
	public boolean canShowLocations() {
		return _flags.test(FLAGS_SHOW_LOCATIONS);
	}

	@Override
	public void setShowLocations(boolean bState) {
		_flags.apply(bState, FLAGS_SHOW_LOCATIONS);
	}

	@Override
	public boolean isShowExceptionStackEnabled() {
		return _flags.test(FLAGS_CAN_SHOW_EXC_STACK);
	}

	@Override
	public void setShowExceptionStackEnabled(boolean bState) {
		_flags.apply(bState, FLAGS_CAN_SHOW_EXC_STACK);
	}

	@Override
	public boolean canWrite() {
		return _flags.test(FLAGS_CAN_WRITE);
	}
	
	protected void setCanWrite(boolean bState) {
		_flags.apply(bState, FLAGS_CAN_WRITE);
	}
	
	protected void setIsClosable() {
		_flags.set(FLAGS_CLOSABLE);
	}

	@Override
	public boolean canWriteAt(ObsLevel level) {
		if (!_flags.test(FLAGS_CAN_WRITE)) {
			return false;
		} else if (_filterLevel != null) {
			return _filterLevel.isLevelAtLeast(level);			
		} else {
			return true;
		}
	}

	@Override
	public ObsLevel getObsLevel() {
		if (_filterLevel != null) {
			return _filterLevel.getObsLevel();
		} else {
			return UNFILTERED_LEVEL;
		}
	}

	@Override
	public final void setObsLevel(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		if (_filterLevel != null) {
			_filterLevel.setObsLevel(level);
		} else {
			throw new UnsupportedOperationException("writer is not level filtering");
		}
	}
	
	protected abstract void innerWrite(ObsLevel level, SourceLocation locOrigin, String strText);
	
	protected abstract void innerWriteException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack);
	
	protected abstract void innerWriteDiagnostic(String strText);
	
	protected abstract void innerWriteDiagnostic(SourceLocation locOrigin, String strText);
		
	/**
	 * Flush the writer, if the underlying mechanism supports it.
	 */
	protected void flush() {
		// does nothing
	}

	@Override
	public void write(ObsLevel level, SourceLocation locOrigin, String strText) {
		Objects.requireNonNull(level, "level");
		innerWrite(level, locOrigin, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (_flags.test(FLAGS_AUTO_FLUSH)) {
			flush();
		}
	}

	@Override
	public void writeException(ObsLevel level, SourceLocation locOrigin, ObservedActivity activity, Exception exc,
			boolean bShowStack) {
		Objects.requireNonNull(level, "level");
		Objects.requireNonNull(activity, "activity");
		innerWriteException(level, locOrigin, activity, exc, bShowStack && _flags.test(FLAGS_CAN_SHOW_EXC_STACK));
		if (_flags.test(FLAGS_AUTO_FLUSH)) {
			flush();
		}
	}

	@Override
	public void writeDiagnostic(String strText) {
		innerWriteDiagnostic(strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (_flags.test(FLAGS_AUTO_FLUSH)) {
			flush();
		}
	}

	@Override
	public void writeDiagnostic(SourceLocation locOrigin, String strText) {
		innerWriteDiagnostic(locOrigin, strText == null ? ObsWriterTextMethods.STR_NULL : strText);
		if (_flags.test(FLAGS_AUTO_FLUSH)) {
			flush();
		}
	}

	@Override
	public void write(SingleObservationCollector collector, SourceLocation locOrigin, String strText) {
		innerWrite(collector.getLevel(), locOrigin, strText);
		if (_flags.test(FLAGS_AUTO_FLUSH)) {
			flush();
		}
	}
	
	protected void closeOutput() throws IOException {
		// the default behavior
		flush();
	}

	@Override
	public final void close() throws IOException {
		if (_flags.test(FLAGS_CLOSABLE)) {
			closeOutput();
			_flags.clear(FLAGS_CAN_WRITE);
		} else {
			flush();
		}
	}
}
