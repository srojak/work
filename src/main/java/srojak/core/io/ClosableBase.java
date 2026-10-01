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
package srojak.core.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleEventOriginating;
import srojak.core.events.LifeCycleListener;
import srojak.core.logic.FlagsShort;
import srojak.core.logic.FlagsShortTest;

/**
 * @author Stephen
 *
 */
public abstract class ClosableBase
		implements Closeable, LifeCycleEventOriginating {
	protected final CommonEventListenerStore _listeners;
	protected final FlagsShort _flags;
	
	public static final short FLAGS_CLOSED = 0x1;
	public static final short FLAGS_READY = 0x2;
	public static final short FLAGS_FAULTED = 0x4;

	protected ClosableBase() {
		_listeners = new CommonEventListenerList();
		_flags = new FlagsShort();
	}
	
	public final boolean isClosed() {
		return _flags.test(FLAGS_CLOSED);
	}
	
	public final boolean isReady() {
		return _flags.test(FLAGS_READY);
	}
	
	protected void setReady() {
		_flags.set(FLAGS_READY);
	}
	
	public final FlagsShortTest getFlags() {
		return _flags;
	}
	
	protected abstract void innerClose()
			throws IOException;
	
	private void raiseClosedEvent(List<LifeCycleListener> listeners) {
		LifeCycleEvent event = null;
		for (LifeCycleListener listener : listeners) {
			if (event == null) {
				event = new LifeCycleEvent(this, LifeCycleEvent.ID_CLOSED);
			}
			listener.receive(event);
		}
	}

	@Override
	public final void close()
			throws IOException {
		_flags.set(FLAGS_CLOSED);
		List<LifeCycleListener> listL = _listeners.getListeners(LifeCycleListener.class);
		IOException excIO = null;
		try {
			innerClose();
		} catch (IOException exc) {
			excIO = exc;
		}
		raiseClosedEvent(listL);
		if (excIO != null) {
			throw excIO;
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
