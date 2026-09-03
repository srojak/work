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

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

import srojak.cdo.swing.VisualPropertyNames;
import srojak.core.NameToken;
import srojak.core.concurrent.StopBarrier;
import srojak.core.concurrent.StopGate;
import srojak.core.observe.TraceLevel;
import srojak.core.tools.ListMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class ComponentEnabledStateManager
		implements VisualPropertyNames {
	private final JComponent _parent;
	private final LinkedList<ComponentEnablingFacade> _listChildren;
	private final StopGate _gateStopHierChange;
	
	public static final NameToken ClassToken;
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = ComponentEnabledStateManager.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * 
	 */
	public ComponentEnabledStateManager(JComponent parent) {
		Objects.requireNonNull(parent, "parent");
		final Class<?> classParent = parent.getClass();
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "parent(class=" + classParent.getName() + ")");
		_parent = parent;
		_listChildren = new LinkedList<ComponentEnablingFacade>();
		_gateStopHierChange = new StopGate(ClassToken);
		_parent.addContainerListener(new EnabledStateContainerListener());
		_parent.addPropertyChangeListener(ENABLED, new EnabledStateRelay());
	}
	
	public ComponentEnablingFacade findFacadeForComponent(JComponent component) {
		return ListMethods.findInList(_listChildren, f -> f.getComponent().equals(component));
	}
	
	public ComponentEnablingFacade createFacadeFor(JComponent component) {
		ComponentEnablingFacade facade = new ComponentEnablingFacade(component);
		_listChildren.add(facade);
		return facade;
	}
	
	public List<ComponentEnablingFacade> getAllFacades() {
		return List.copyOf(_listChildren);
	}
	
	public void addChild(ComponentEnablingFacade facade) {
		Objects.requireNonNull(facade, "facade");
		StopBarrier barrier = _gateStopHierChange.addStop(facade);
		_parent.add(facade.getComponent());
		if (!_listChildren.contains(facade)) {
			_listChildren.add(facade);
		}
		barrier.dispose();
	}
	
	public void addChild(ComponentEnablingFacade facade, Object constraints) {
		Objects.requireNonNull(facade, "facade");
		StopBarrier barrier = _gateStopHierChange.addStop(facade);
		_parent.add(facade.getComponent(), constraints);
		if (!_listChildren.contains(facade)) {
			_listChildren.add(facade);
		}
		barrier.dispose();
	}
	
	public void removeChild(ComponentEnablingFacade facade) {
		Objects.requireNonNull(facade, "facade");
		StopBarrier barrier = _gateStopHierChange.addStop(facade);
		_parent.remove(facade.getComponent());
		_listChildren.remove(facade);
		barrier.dispose();
	}

	private class EnabledStateContainerListener
			implements ContainerListener {

		@Override
		public void componentAdded(ContainerEvent event) {
			if (_gateStopHierChange.isClear()) {
				JComponent component = (JComponent) event.getChild();
				ComponentEnablingFacade facade = new ComponentEnablingFacade(component);
				facade.setParentEnabled(_parent.isEnabled());
				_listChildren.add(facade);
			}			
		}

		@Override
		public void componentRemoved(ContainerEvent event) {
			if (_gateStopHierChange.isClear()) {
				JComponent component = (JComponent) event.getChild();
				ComponentEnablingFacade facade = findFacadeForComponent(component);
				if (facade != null) {
					_listChildren.remove(facade);
				}
			}
		}	
	}
	
	private class EnabledStateRelay
			implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			final Boolean bNewValue = (Boolean) evt.getNewValue();
			final Boolean bOldValue = (Boolean) evt.getOldValue();
			if (bNewValue.booleanValue() != bOldValue.booleanValue()) {
				_listChildren.forEach(f -> f.setParentEnabled(bNewValue.booleanValue()));
			}
		}
		
	}
}
