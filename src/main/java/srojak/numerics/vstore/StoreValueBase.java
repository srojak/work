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
package srojak.numerics.vstore;

import java.util.Objects;

import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;
import srojak.core.events.StateChangeCodes;
import srojak.core.events.StateChangeEvent;
import srojak.core.events.StateChangeListener;
import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public abstract class StoreValueBase
		implements StoreValue {
	protected final NamedKey _key;
	private final SingleEventListenerStore<StateChangeListener> _listeners;
	
	public StoreValueBase(NamedKey key) {
		Objects.requireNonNull(key, "key");
		_key = key;
		_listeners = new SingleEventListenerList<StateChangeListener>();
	}

	@Override
	public String getName() {
		return _key.getName();
	}

	@Override
	public NamedKey getKey() {
		return _key;
	}
	
	@Override
	public boolean canSet() {
		return true;
	}

	@Override
	public int hashCode() {
		return _key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return _key.equals(obj);
	}

	protected static void faultInvalid(NamedKey key, String strValueName) {
		throw new IllegalArgumentException("invalid " + strValueName + " for " + key);
	}
	
	protected void onValueChange(boolean bState) {
		_listeners.sendToAll(() -> new StateChangeEvent(this, StateChangeCodes.SC_SELF, bState),
				(ls, e) -> ls.stateChanged(e));
	}

	@Override
	public void addStateChangeListener(StateChangeListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removeStateChangeListener(StateChangeListener listener) {
		_listeners.remove(listener);
	}
}
