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
package srojak.cdo.swing.interact;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.swing.ComponentFacadeFlags;
import srojak.cdo.swing.event.ChangeEventOriginator;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;

/**
 * @author Stephen
 *
 */
public abstract class ComponentModelAdapterBase
		implements ComponentFacadeFlags, ChangeEventOriginator {
	protected final CommonEventListenerStore _listeners;
	protected final FlagsInt _flags;

	public ComponentModelAdapterBase() {
		_listeners = new CommonEventListenerList();	
		_flags = new FlagsInt();
		_flags.set(CFF_PARENT_ENABLED, CFF_LOCAL_ENABLED, CFF_MODEL_ENABLED, CFF_SYNTH_ENABLED);
	}
	
	public FlagsIntTest getFlags() {
		return _flags;
	}

	public boolean isEnabled() {
		return _flags.test(CFF_SYNTH_ENABLED);
	}
	
	public boolean isModelEnabled() {
		return _flags.test(CFF_MODEL_ENABLED);
	}
	
	protected void checkEnabled() {
		boolean bSum = _flags.testAnd(CFF_LOCAL_ENABLED, CFF_PARENT_ENABLED, CFF_MODEL_ENABLED);
		if (_flags.apply(bSum, CFF_SYNTH_ENABLED)) {
			ChangeEvent event = new ChangeEvent(this);
			_listeners.forEach(ChangeListener.class, ls -> ls.stateChanged(event));
		}
	}

	public void setEnabled(boolean bState) {
		if (_flags.apply(bState, CFF_MODEL_ENABLED)) {
			checkEnabled();
		}		
	}
	
	public boolean isLocalEnabled() {
		return _flags.test(CFF_LOCAL_ENABLED);
	}
	
	public boolean isParentEnabled() {
		return _flags.test(CFF_PARENT_ENABLED);
	}
	
	public void setLocalEnabled(boolean bState) {
		if (_flags.apply(bState, CFF_LOCAL_ENABLED)) {
			checkEnabled();
		}
	}
	
	public void seParentEnabled(boolean bState) {
		if (_flags.apply(bState, CFF_PARENT_ENABLED)) {
			checkEnabled();
		}
	}
	
	@Override
	public void addChangeListener(ChangeListener listener) {
		_listeners.add(ChangeListener.class, listener);
	}

	@Override
	public void removeChangeListener(ChangeListener listener) {
		_listeners.remove(ChangeListener.class, listener);
	}
}
