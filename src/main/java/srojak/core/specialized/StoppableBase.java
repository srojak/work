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
package srojak.core.specialized;

import java.util.List;

import srojak.core.Stoppable;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleEventOriginating;
import srojak.core.events.LifeCycleListener;

/**
 * @author Stephen
 *
 */
public abstract class StoppableBase 
		implements Stoppable, LifeCycleEventOriginating {
	protected final CommonEventListenerStore _listeners;
	private boolean _bRunning;
	
	protected StoppableBase(boolean startRunning) {
		_listeners = new CommonEventListenerList();
		_bRunning = startRunning;
	}

	@Override
	public void addLifeCycleListener(LifeCycleListener listener) {
		_listeners.add(LifeCycleListener.class, listener);
	}

	@Override
	public void removeLifeCycleListener(LifeCycleListener listener) {
		_listeners.remove(LifeCycleListener.class, listener);
	}

	@Override
	public final boolean isRunning() {
		return _bRunning;
	}
	
	protected void startRunning() {
		_bRunning = true;
	}
	
	protected abstract void halt();

	@Override
	public final void stop() {
		if (_bRunning) {
			_bRunning = false;
			List<LifeCycleListener> listListeners = _listeners.getListeners(LifeCycleListener.class);
			// the derived class may clear out the list of listeners
			halt();
			if (!listListeners.isEmpty()) {
				LifeCycleEvent event = new LifeCycleEvent(this, LifeCycleEvent.ID_CLOSED);
				listListeners.forEach(ls -> ls.receive(event));
			}
		}
	}
}
