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

import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;

/**
 * @author Stephen
 *
 */
public abstract class ObservationWriterBase
		implements ObservationWriter {
	protected final CommonEventListenerStore _listeners;
	private final FlagsInt _flags;

	protected static final ObsLevel DEFAULT_FILTERED_LEVEL = ObsLevel.INFO;
	protected static final ObsLevel UNFILTERED_LEVEL = ObsLevel.FINEST;
	protected static final int FLAGS_AUTO_FLUSH = 0x1;
	protected static final int FLAGS_CAN_WRITE = 0x2;
	protected static final int FLAGS_SHOW_LOCATIONS = 0x4;
	protected static final int FLAGS_CAN_SHOW_EXC_STACK = 0x8;
	protected static final int FLAGS_NO_FILTER = 0x10;
	protected static final int FLAGS_CLOSABLE = 0x20;
	protected static final int FLAGS_SENT_CLOSED = 0x40;
	
	/**
	 * 
	 */
	public ObservationWriterBase() {
		_listeners = new CommonEventListenerList();
		_flags = new FlagsInt();
	}
	
	public final FlagsIntTest getFlags() {
		return _flags;
	}

	@Override
	public boolean isLevelFiltering() {
		return false;
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
	
	protected void setNoFilter() {
		_flags.set(FLAGS_NO_FILTER);
	}

	@Override
	public boolean canWriteAt(ObsLevel level) {
		return canWrite();
	}

	@Override
	public ObsLevel getObsLevel() {
		return UNFILTERED_LEVEL;
	}
	
	protected boolean trySetObsLevel(ObsLevel level) {
		return false;
	}

	@Override
	public final void setObsLevel(ObsLevel level) {
		Objects.requireNonNull(level, "level");
		if (!trySetObsLevel(level)) {
			throw new UnsupportedOperationException("writer is not level filtering");
		}
	}
		
	/**
	 * Flush the writer, if the underlying mechanism supports it.
	 */
	public void flush() {
		// does nothing
	}
	
	protected void closeOutput() throws IOException {
		// the default behavior
		flush();
	}

	@Override
	public final void close() throws IOException {
		try {
			if (_flags.test(FLAGS_CLOSABLE)) {
				_flags.clear(FLAGS_CAN_WRITE);
				closeOutput();
			} else {
				flush();
			}
		} finally {
			raiseClosedEvent();
		}
	}
	
	protected void raiseClosedEvent() {
		if (!_flags.test(FLAGS_SENT_CLOSED)) {
			LifeCycleEvent event = new LifeCycleEvent(this, LifeCycleEvent.ID_CLOSED);
			_listeners.forEach(LifeCycleListener.class, ls -> ls.receive(event));
			_flags.set(FLAGS_SENT_CLOSED);
		}
	}

	@Override
	public void addLifeCycleListener(LifeCycleListener listener) {
		_listeners.add(LifeCycleListener.class, listener);		
	}

	@Override
	public void removeLifeCycleListener(LifeCycleListener listener) {
		_listeners.remove(LifeCycleListener.class, listener);		
	}
}
