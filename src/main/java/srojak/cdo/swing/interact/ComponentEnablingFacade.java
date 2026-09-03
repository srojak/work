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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;

import srojak.cdo.swing.VisualPropertyNames;
import srojak.core.NameToken;
import srojak.core.concurrent.StopBarrier;
import srojak.core.concurrent.StopGate;
import srojak.core.events.StateChangeCodes;
import srojak.core.events.StateChangeEvent;
import srojak.core.events.StateChangeListener;
import srojak.core.events.StateChangeOriginator;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class ComponentEnablingFacade
		extends ComponentFacadeBase
		implements VisualPropertyNames, StateChangeCodes, StateChangeOriginator {
	private final StopGate _gateStopEnabled;
	private final boolean _bIsForButton;
	
	public static final NameToken ClassToken;
	@SuppressWarnings("unused")
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = ComponentEnablingFacade.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public ComponentEnablingFacade(JComponent component) {
		super(component);
		_gateStopEnabled = new StopGate(ClassToken);
		component.addPropertyChangeListener(ENABLED, new EnabledPropertyChangeListener());
		_bIsForButton = component instanceof AbstractButton;
	}

	/**
	 *
	 * 
	 * @see javax.swing.AbstractButton$Handler.stateChanged
	 */
	@Override
	public boolean isEnabled() {
		if (_bIsForButton) {
			if (_gateStopEnabled.isClear()) {
				return super.isEnabled();
			} else {
				// must present what the model expects
				// or an infinite regression occurs
				return isModelEnabled();
			}
		} else {
			return super.isEnabled();
		}
	}

	@Override
	public void addStateChangeListener(StateChangeListener listener) {
		_listeners.add(StateChangeListener.class, listener);
	}

	@Override
	public void removeStateChangeListener(StateChangeListener listener) {
		_listeners.remove(StateChangeListener.class, listener);
	}
	
	private void sendEnabledStateChangeEvent() {
		final boolean bEnabled = isEnabled();
		_listeners.sendToAll(StateChangeListener.class, () -> new StateChangeEvent(this, SC_ENABLED, bEnabled),
				(ls, ev) -> ls.stateChanged(ev));
	}

	@Override
	protected void changeComponentEnabled() {
		JComponent component = getComponent();
		final boolean bEnabled = isEnabled();
		StopBarrier barrierMyChange = _gateStopEnabled.addStop(component);
		component.setEnabled(bEnabled);
		barrierMyChange.dispose();
	}
	
	private class EnabledPropertyChangeListener
		implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (_gateStopEnabled.isClear()) {
				// the change went around us. reverse it if necessary.
				JComponent component = getComponent();
				// prevent infinite recursion.
				StopBarrier barrierMyChange = _gateStopEnabled.addStop(component);
				// apply the change to the internal flags.
				setEnabledImmediate(component.isEnabled());
				boolean bShouldBe = isEnabled();
				boolean bForward = false;
				if (component.isEnabled() != bShouldBe) {
					component.setEnabled(bShouldBe);
					bForward = true;
				}
				barrierMyChange.dispose();
				if (bForward) {
					sendEnabledStateChangeEvent();
				}
			}
		}
		
	}
}
