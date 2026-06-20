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
package srojak.cdo.swing.functional;

import java.util.Objects;

import srojak.cdo.swing.CDOControlModel;
import srojak.core.events.ObjectOwnershipEvent;
import srojak.core.events.ObjectOwnershipListener;
import srojak.core.events.ObjectOwnershipOriginator;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;

/**
 * @author Stephen
 *
 * The purpose of this object is to organize the life cycle of a model as held by a control.
 */
public class ControlModelManager<M extends CDOControlModel> 
		implements ObjectOwnershipOriginator {
	private final SingleEventListenerStore<ObjectOwnershipListener> _listeners;
	private M _model;

	/**
	 * 
	 */
	public ControlModelManager() {
		_listeners = new SingleEventListenerList<ObjectOwnershipListener>();
		_model = null;
	}
	
	public M getModel() {
		return _model;
	}
	
	public void setModel(M model) {
		Objects.requireNonNull(model, "model");
		if (_listeners.getListenerCount() == 0) {
			throw new IllegalStateException("no listeners present");
		}
		if (_model != null) {
			ObjectOwnershipEvent eventPrior = new ObjectOwnershipEvent(this, _model);
			_listeners.forEach(ls -> ls.release(eventPrior));
		}
		_model = model;
		ObjectOwnershipEvent eventNew = new ObjectOwnershipEvent(this, _model);
		_listeners.forEach(ls -> ls.acquire(eventNew));
	}

	@Override
	public void addObjectOwnershipListener(ObjectOwnershipListener listener) {
		_listeners.add(listener);
	}

	@Override
	public void removedObjectOwnershipListener(ObjectOwnershipListener listener) {
		_listeners.remove(listener);
	}

}
