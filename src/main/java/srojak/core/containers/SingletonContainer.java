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
import srojak.core.LifeCycleEventOriginating;
import srojak.core.SingletonReadOnly;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.events.StateChangeCodes;
import srojak.core.events.StateChangeEvent;
import srojak.core.events.StateChangeListener;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterNull;

/**
 * @author Stephen
 *
 */
public class SingletonContainer<T>
		implements SingletonReadOnly<T> {
	private final LinkedList<StateChangeListener> _listenersState;
	private final ObjectLifeCycleListener _listenerLife;
	private T _obj;
	
	private static ObservationWriter _writer = new ObservationWriterNull();
	
	public static ObservationWriter getObservationWriter() {
		return _writer;
	}
	
	public static void setObservationWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writer = writer;
	}
	
	public SingletonContainer() {
		_listenersState = new LinkedList<StateChangeListener>();
		_listenerLife = new ObjectLifeCycleListener();
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
	
	private void releaseCurrent() {
		if (_obj != null) {
			if (!_listenersState.isEmpty()) {
				StateChangeEvent event = new StateChangeEvent(this, StateChangeCodes.ID_SELF, false);
				_listenersState.forEach(ls -> ls.stateChanged(event));
			}
			if (_obj instanceof AutoCloseable objClose) {
				try {
					objClose.close();
				} catch (Exception exc) {
					_writer.buildAndWrite(ObsLevel.ERROR, sb -> {
						sb.append("caught ");
						sb.append(exc.getClass().getSimpleName());
						sb.append("\n    ");
						sb.append(exc.getMessage());
					});
				}
			}
			if (_obj instanceof LifeCycleEventOriginating objLC) {
				objLC.removeLifeCycleListener(_listenerLife);
			}
		}
	}
	
	private synchronized void vacate(Object objClosed) {
		if (_obj == objClosed) {
			releaseCurrent();
			_obj = null;
		}
	}

	public synchronized void clear() {
		releaseCurrent();
		_obj = null;
	}
	
	@Override
	public T get() {
		return _obj;
	}
	
	public synchronized void set(T objNew) {
		Objects.requireNonNull(objNew, "objNew");
		releaseCurrent();
		_obj = objNew;
		if (_obj instanceof LifeCycleEventOriginating objLC) {
			objLC.addLifeCycleListener(_listenerLife);
		}
		if (!_listenersState.isEmpty()) {
			StateChangeEvent event = new StateChangeEvent(this, StateChangeCodes.ID_SELF, true);
			_listenersState.forEach(ls -> ls.stateChanged(event));
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
	
	private class ObjectLifeCycleListener
			implements LifeCycleListener {

		@Override
		public void receive(LifeCycleEvent event) {
			if (event.getID() == LifeCycleEvent.ID_CLOSED) {
				vacate(event.getSource());
			}
		}
		
	}
}
