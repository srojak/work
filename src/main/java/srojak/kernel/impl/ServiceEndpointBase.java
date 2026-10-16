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
package srojak.kernel.impl;

import java.util.List;
import java.util.Objects;

import srojak.core.events.CommonEventListenerList;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.kernel.KernelServiceEndpointControl;

/**
 * @author Stephen
 *
 */
public abstract class ServiceEndpointBase 
		implements KernelServiceEndpointControl {
	protected final CommonEventListenerList _listeners;
	protected final FlagsInt _flags;
	private final KernelEndpointHome _home;
	
	public static final int FLAGS_ACTIVE = 0x1;
	
	protected ServiceEndpointBase(KernelEndpointHome home) {
		Objects.requireNonNull(home, "home");
		_listeners = new CommonEventListenerList();
		_flags = new FlagsInt();
		_home = home;
		_home.attach(this);
	}

	@Override
	public FlagsIntTest flags() {
		return _flags;
	}

	@Override
	public boolean isActive() {
		return _flags.test(FLAGS_ACTIVE);
	}

	@Override
	public void activate() {
		_flags.set(FLAGS_ACTIVE);
	}

	@Override
	public void addLifeCycleListener(LifeCycleListener listener) {
		_listeners.add(LifeCycleListener.class, listener);
	}

	@Override
	public void removeLifeCycleListener(LifeCycleListener listener) {
		_listeners.remove(LifeCycleListener.class, listener);
	}
	
	protected abstract void closeSelf();

	@Override
	public void close() {
		_flags.clear(FLAGS_ACTIVE);
		List<LifeCycleListener> list = _listeners.getListeners(LifeCycleListener.class);
		_listeners.clear();
		closeSelf();
		if (!list.isEmpty()) {
			LifeCycleEvent event = new LifeCycleEvent(this, LifeCycleEvent.ID_CLOSED);
			list.forEach(ls -> ls.receive(event));
		}
		_home.detach(this);
	}

	@Override
	public void orderlyClose() {
		close();
	}

}
