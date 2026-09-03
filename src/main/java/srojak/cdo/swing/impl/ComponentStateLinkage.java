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
package srojak.cdo.swing.impl;

import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.Objects;

import javax.swing.JComponent;

import srojak.cdo.swing.VisualPropertyNames;
import srojak.core.NameToken;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 * The prototype that led to ComponentEnabledStateManager.
 */
public class ComponentStateLinkage
		implements VisualPropertyNames {
	private final JComponent _parent;
	private final LinkedList<JComponent> _listChildren;
	
	public static final NameToken ClassToken;
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = ComponentStateLinkage.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	public ComponentStateLinkage(JComponent parent) {
		Objects.requireNonNull(parent, "parent");
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "parent " + parent.getName());
		_parent = parent;
		_listChildren = new LinkedList<JComponent>();
		_parent.addContainerListener(new LinkageContainerListener());
		_parent.addPropertyChangeListener(ENABLED, new LinkageEnabledPropertyListener());
	}
	
	private class LinkageContainerListener
			implements ContainerListener {

		@Override
		public void componentAdded(ContainerEvent e) {
			JComponent child = (JComponent) e.getChild();
			_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, () -> "adding child " + child.getName() + " to parent " + _parent.getName());
			_listChildren.add(child);
		}

		@Override
		public void componentRemoved(ContainerEvent e) {
			JComponent child = (JComponent) e.getChild();
			_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, () -> "removing child " + child.getName() + " from parent " + _parent.getName());
			_listChildren.remove(child);
		}
		
	}
	
	private class LinkageEnabledPropertyListener
			implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			final Boolean bValue = (Boolean) evt.getNewValue();
			_listChildren.forEach(c -> c.setEnabled(bValue.booleanValue()));
			
		}
		
	}
}
