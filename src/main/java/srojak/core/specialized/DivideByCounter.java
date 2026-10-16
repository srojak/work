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

import srojak.core.events.ActionCompletedListener;
import srojak.core.events.NotificationEvent;
import srojak.core.events.NotificationListener;
import srojak.core.events.NotificationOriginator;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;

/**
 * @author Stephen
 *
 */
public class DivideByCounter 
		implements NotificationOriginator {
	private final SingleEventListenerStore<NotificationListener> _listeners;
	private int _start;
	private int _counter;
	
	public DivideByCounter(int nStart) {
		if (nStart <= 1) {
			throw new IllegalArgumentException("nStart must be > 1");
		}
		_listeners = new SingleEventListenerList<NotificationListener>();
		_counter = _start = nStart;
	}
	
	public int startValue() {
		return _start;
	}
	
	public void setStartValue(int nStart) {
		if (nStart <= 1) {
			throw new IllegalArgumentException("nStart must be > 1");
		}
	}
	
	public synchronized void pulse() {
		if (--_counter <= 0) {
			_counter = _start;
			NotificationEvent event = new NotificationEvent(this);
			_listeners.forEach(ls -> ls.receive(event));
		}
	}

	@Override
	public void addNotificationListener(NotificationListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removeNotificationListener(NotificationListener listener) {
		_listeners.remove(listener);
	}

}
