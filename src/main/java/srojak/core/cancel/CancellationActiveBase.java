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
package srojak.core.cancel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleEventOriginating;
import srojak.core.events.LifeCycleListener;
import srojak.core.excepts.OperationCancelledException;

/**
 * @author Stephen
 *
 */
public abstract sealed class CancellationActiveBase 
		implements Cancellation, LifeCycleEventOriginating
		permits CancellationToken, CancellationNotifyingToken {
	protected final CommonEventListenerStore _listeners;
	private final List<Thread> _listThreads;
	private final AtomicBoolean _bCancelled;
	private boolean _bDisposed;

	/**
	 * @param bCancelled
	 */
	public CancellationActiveBase(AtomicBoolean bCancelled) {
		_listeners = new CommonEventListenerList();
		_listThreads = new LinkedList<Thread>();
		_bCancelled = bCancelled;
		_bDisposed = false;
	}

	@Override
	public synchronized void registerOwnThread() {
		if (!_bDisposed) {
			Thread thread = Thread.currentThread();
			if (!_listThreads.contains(thread)) {
				_listThreads.add(thread);
			}
		}		
	}

	@Override
	public synchronized void unregisterOwnThread() {
		Thread thread = Thread.currentThread();
		_listThreads.remove(thread);
	}

	@Override
	public boolean isDisposed() {
		return _bDisposed;
	}

	@Override
	public boolean isCancelRequested() {
		return _bCancelled.get();
	}

	@Override
	public void throwIfCancelled() {
		if (_bCancelled.get()) {
			throw new OperationCancelledException();
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
	
	private synchronized void notifyThreads() {
		_listThreads.forEach(t -> t.interrupt());
	}
	
	void cancelNotify() {
		notifyThreads();
	}

	@Override
	public void dispose() {
		_bDisposed = true;
		List<LifeCycleListener> list = _listeners.getListeners(LifeCycleListener.class);
		_listeners.clear();
		_listThreads.clear();
		if (!list.isEmpty()) {
			LifeCycleEvent event = new LifeCycleEvent(this, LifeCycleEvent.ID_DISPOSED);
			list.forEach(ls -> ls.receive(event));
		}
	}
}
