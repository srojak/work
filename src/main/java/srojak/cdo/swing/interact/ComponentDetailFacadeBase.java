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

import java.awt.ItemSelectable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.swing.event.ChangeEventOriginator;
import srojak.core.InvalidOperationException;

/**
 * @author Stephen
 *
 */
public abstract class ComponentDetailFacadeBase 
		extends ComponentFacadeBase
		implements ChangeEventOriginator, ItemSelectable {

	/**
	 * @param component
	 */
	public ComponentDetailFacadeBase(JComponent component) {
		super(component);
	}
	
	@Override
	public void addChangeListener(ChangeListener listener) {
		_listeners.add(ChangeListener.class, listener);
	}

	@Override
	public void removeChangeListener(ChangeListener listener) {
		_listeners.remove(ChangeListener.class, listener);
	}
	
	public Object getModel() {
		return null;
	}
	
	public void setModel(Object model) {
		throw new InvalidOperationException(_reflector.getClass().getSimpleName(), "does not have a model");
	}
	
	@Override
	protected void changeComponentEnabled() {
		getComponent().setEnabled(isEnabled());
		ChangeEvent event = new ChangeEvent(this);
		_listeners.forEach(ChangeListener.class, ls -> ls.stateChanged(event));
	}

	public boolean isSelected() {
		return false;
	}
	
	public void setSelected(boolean bState) {
		throw new InvalidOperationException(_reflector.getClass().getSimpleName(), "does not implement setSelected");
	}

	@Override
	public Object[] getSelectedObjects() {
		throw new InvalidOperationException(_reflector.getClass().getSimpleName(), "does not implement ItemSelectable");
	}

	@Override
	public void addItemListener(ItemListener l) {
		throw new InvalidOperationException(_reflector.getClass().getSimpleName(), "does not implement ItemSelectable");
	}

	@Override
	public void removeItemListener(ItemListener l) {
		throw new InvalidOperationException(_reflector.getClass().getSimpleName(), "does not implement ItemSelectable");
	}

	public void addActionListener(ActionListener listener) {
		throw new InvalidOperationException(_reflector.getClass().getSimpleName(), "does not create action events");
	}
	
	public void removeActionListener(ActionListener listener) {
		throw new InvalidOperationException(_reflector.getClass().getSimpleName(), "does not create action events");
	}
	
	protected class ChangeEventRelay
			implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent event) {
			ChangeEvent eventRelay = new ChangeEvent(ComponentDetailFacadeBase.this);
			_listeners.forEach(ChangeListener.class, ls -> ls.stateChanged(eventRelay));
		}
		
	}
	
	protected class ItemEventRelay
			implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			ItemEvent eventRelay = new ItemEvent(ComponentDetailFacadeBase.this, event.getID(), event.getItem(), event.getStateChange());
			_listeners.forEach(ItemListener.class, ls -> ls.itemStateChanged(eventRelay));
		}
		
	}
}
