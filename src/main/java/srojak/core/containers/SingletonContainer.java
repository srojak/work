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

import java.util.LinkedList;
import java.util.Objects;

import srojak.core.EmptyCollectionException;
import srojak.core.ISingletonContainer;
import srojak.core.events.DataChangeEvent;
import srojak.core.events.DataChangeListener;
import srojak.core.events.StateChangeEvent;
import srojak.core.events.StateChangeListener;

/**
 * @author Stephen
 *
 */
public class SingletonContainer<T>
		implements ISingletonContainer {
	private final LinkedList<DataChangeListener> _listenersData;
	private final LinkedList<StateChangeListener> _listenersState;
	private T _obj;
	
	public SingletonContainer() {
		_listenersData = new LinkedList<DataChangeListener>();
		_listenersState = new LinkedList<StateChangeListener>();
		_obj = null;
	}
	
	@Override
	public boolean isEmpty() {
		return _obj == null;
	}
	
	@Override
	public void faultIfEmpty(String strMessage) {
		if (_obj == null) {
			if (strMessage == null || strMessage.isEmpty()) {
				strMessage = "container is empty";
			}
			throw new EmptyCollectionException(strMessage);
		}
		
	}

	public void clear() {
		if (_obj != null) {
			if (!_listenersState.isEmpty()) {
				StateChangeEvent event = new StateChangeEvent(this);
				_listenersState.forEach(ls -> ls.stateChanged(event));
			}
			if (!_listenersData.isEmpty()) {
				DataChangeEvent event = new DataChangeEvent(_obj);
				_listenersData.forEach(ls -> ls.dataRemoved(event));
			}
		}
		_obj = null;
	}
	
	public T get() {
		return _obj;
	}
	
	public void set(T objNew) {
		Objects.requireNonNull(objNew, "objNew");
		if (_obj != null) {
			if (!_listenersState.isEmpty()) {
				StateChangeEvent event = new StateChangeEvent(this);
				_listenersState.forEach(ls -> ls.stateChanged(event));
			}
			if (!_listenersData.isEmpty()) {
				DataChangeEvent event = new DataChangeEvent(_obj);
				_listenersData.forEach(ls -> ls.dataRemoved(event));
			}
		}
		_obj = objNew;
		if (!_listenersState.isEmpty()) {
			StateChangeEvent event = new StateChangeEvent(this);
			_listenersState.forEach(ls -> ls.stateChanged(event));
		}
		if (!_listenersData.isEmpty()) {
			DataChangeEvent event = new DataChangeEvent(_obj);
			_listenersData.forEach(ls -> ls.dataAdded(event));
		}
	}
	
	@Override
	public void addStateChangeListener(StateChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		_listenersState.add(listener);
	}

	@Override
	public void removeStateChangeListener(StateChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		_listenersState.remove(listener);
	}

	@Override
	public void addDataChangeListener(DataChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		_listenersData.add(listener);
	}
	
	@Override
	public void removeDataChangeListener(DataChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		_listenersData.remove(listener);
	}
}
