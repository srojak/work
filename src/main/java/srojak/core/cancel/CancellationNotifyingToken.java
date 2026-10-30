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

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleEventOriginating;
import srojak.core.events.LifeCycleListener;
import srojak.core.events.NotificationEvent;
import srojak.core.events.NotificationListener;

/**
 * @author Stephen
 *
 */
public final class CancellationNotifyingToken 
		extends CancellationActiveBase 
		implements CancellationNotifying {

	/**
	 * @param source
	 */
	public CancellationNotifyingToken(AtomicBoolean bCancelled) {
		super(bCancelled);
	}

	@Override
	public void addNotificationListener(NotificationListener listener) {
		_listeners.add(NotificationListener.class, listener);
	}

	@Override
	public void removeNotificationListener(NotificationListener listener) {
		_listeners.remove(NotificationListener.class, listener);
	}

	@Override
	void cancelNotify() {
		super.cancelNotify();
		List<NotificationListener> list = _listeners.getListeners(NotificationListener.class);
		if (!list.isEmpty()) {
			NotificationEvent event = new NotificationEvent(this);
			list.forEach(ls -> ls.receive(event));
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		List<LifeCycleListener> list = _listeners.getListeners(LifeCycleListener.class);
		_listeners.clear();
		if (!list.isEmpty()) {
			LifeCycleEvent event = new LifeCycleEvent(this, LifeCycleEvent.ID_DISPOSED);
			list.forEach(ls -> ls.receive(event));
		}
	}
}
