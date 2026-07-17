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
package srojak.core.logic;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

import srojak.core.events.StateChangeEvent;
import srojak.core.events.StateChangeListener;

/**
 * @author Stephen
 *
 */
public class BooleanLatch
	implements BooleanLatchReadOnly {
	private final LinkedList<StateChangeListener> _listeners;
	private boolean _bState;
	
	public BooleanLatch() {
		_bState = false;
		_listeners = new LinkedList<StateChangeListener>();
	}
	
	public BooleanLatch(boolean bStateInitial) {
		_bState = bStateInitial;
		_listeners = new LinkedList<StateChangeListener>();
	}
	
	@Override
	public boolean getState() {
		return _bState;
	}
	
	public void propagate() {
		ListIterator<StateChangeListener> iter = _listeners.listIterator();
		StateChangeEvent event = null;
		while (iter.hasNext()) {
			if (event == null) {
				event = new StateChangeEvent(this, _bState);
			}
			iter.next().stateChanged(event);
		}
	}
	
	/**
	 * Sets the state of the latch.
	 * @param bState
	 * @return
	 */
	public boolean setState(boolean bState) {
		if (_bState == bState) {
			return false;
		}
		_bState = bState;
		propagate();
		return true;
	}

	@Override
	public void addStateChangeListener(StateChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		_listeners.add(listener);
	}

	@Override
	public void removeStateChangeListener(StateChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		_listeners.remove(listener);
	}
}
