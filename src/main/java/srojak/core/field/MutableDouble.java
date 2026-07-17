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
package srojak.core.field;

import srojak.core.LifeCycleEventOriginating;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;

/**
 * @author Stephen
 *
 */
public class MutableDouble
		implements LifeCycleEventOriginating {
	private final SingleEventListenerStore<LifeCycleListener> _listeners;
	private double _value;
	
	public MutableDouble(double valueInitial) {
		_listeners = new SingleEventListenerList<LifeCycleListener>();
		_value = valueInitial;
	}
	
	public MutableDouble() {
		this(0.0d);
	}
	
	public double get() {
		return _value;
	}
	
	public void set(double value) {
		_value = value;
		LifeCycleEvent event = null;
		for (LifeCycleListener listener : _listeners) {
			if (event == null) {
				event = new LifeCycleEvent(this, LifeCycleEvent.ID_VALUE_CHANGED);
			}
			listener.receive(event);
		}
	}

	@Override
	public void addLifeCycleListener(LifeCycleListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removeLifeCycleListener(LifeCycleListener listener) {
		_listeners.remove(listener);
	}
}
