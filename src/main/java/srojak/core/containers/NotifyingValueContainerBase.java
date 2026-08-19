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
package srojak.core.containers;

import srojak.core.NotifyingValueCommon;
import srojak.core.events.SequentialEvent;
import srojak.core.events.SequentialListener;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;
import srojak.core.specialized.LongCounter;

/**
 * @author Stephen
 *
 */
public abstract class NotifyingValueContainerBase
		implements NotifyingValueCommon {
	private final SingleEventListenerStore<SequentialListener> _listeners;
	private final LongCounter _seq;
	
	protected NotifyingValueContainerBase() {
		_listeners = new SingleEventListenerList<SequentialListener>();
		_seq = new LongCounter();
	}

	@Override
	public void addSequentialListener(SequentialListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void addSequentialListenerAndSync(SequentialListener listener) {
		if (listener != null) {
			_listeners.add(listener);
			listener.occurrence(new SequentialEvent(this, SequentialEvent.ID_SYNC, _seq.getValue()));
		}		
	}

	@Override
	public void removeSequentialListener(SequentialListener listener) {
		_listeners.remove(listener);
	}
	
	protected void announceChange() {
		_seq.increment();
		SequentialEvent event = new SequentialEvent(this, SequentialEvent.ID_CHANGE, _seq.getValue());
		_listeners.forEach(ls -> ls.occurrence(event));
	}
}
