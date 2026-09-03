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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.ControlModelManager;
import srojak.core.events.ObjectOwnershipEvent;
import srojak.core.events.ObjectOwnershipListener;

/**
 * @author Stephen
 *
 */
public class ButtonModelAdapter 
		extends ComponentModelAdapterBase 
		implements ButtonModel {
	private final ControlModelManager<ButtonModel> _model;
	private ModelEventRelay _relayEvents;

	/**
	 * 
	 */
	public ButtonModelAdapter() {
		super();
		_model = new ControlModelManager<ButtonModel>();
		_model.addObjectOwnershipListener(new ObjectOwnershipListener() {

			@Override
			public void acquire(ObjectOwnershipEvent event) {
				_relayEvents = new ModelEventRelay();
				ButtonModel model = event.getValueAs();
				model.addActionListener(_relayEvents);
				model.addChangeListener(_relayEvents);
				model.addItemListener(_relayEvents);
			}

			@Override
			public void release(ObjectOwnershipEvent event) {
				ButtonModel model = event.getValueAs();
				model.removeItemListener(_relayEvents);
				model.removeChangeListener(_relayEvents);
				model.removeActionListener(_relayEvents);
			}
			
		});
	}
	
	public ButtonModel getModel() {
		return _model.getModel();
	}
	
	public void setModel(ButtonModel model) {
		_model.setModel(model);
	}

	@Override
	public Object[] getSelectedObjects() {
		if (_model.isEmpty()) {
			return null;
		} else {
			return _model.getModel().getSelectedObjects();
		}
	}

	@Override
	public boolean isArmed() {
		return _model.isEmpty() ? false : _model.getModel().isArmed();
	}

	@Override
	public boolean isPressed() {
		return _model.isEmpty() ? false : _model.getModel().isPressed();
	}

	@Override
	public boolean isSelected() {
		return _model.isEmpty() ? false : _model.getModel().isSelected();
	}

	@Override
	public boolean isRollover() {
		return _model.isEmpty() ? false : _model.getModel().isRollover();
	}

	@Override
	public void setArmed(boolean b) {
		if (!_model.isEmpty()) {
			_model.getModel().setArmed(b);
		}
	}

	@Override
	public void setSelected(boolean b) {
		if (!_model.isEmpty()) {
			_model.getModel().setSelected(b);
		}
	}

	@Override
	public void setPressed(boolean b) {
		if (!_model.isEmpty()) {
			_model.getModel().setPressed(b);
		}
	}

	@Override
	public void setRollover(boolean b) {
		if (!_model.isEmpty()) {
			_model.getModel().setRollover(b);
		}
	}

	@Override
	public void setMnemonic(int key) {
		if (!_model.isEmpty()) {
			_model.getModel().setMnemonic(key);
		}
	}

	@Override
	public int getMnemonic() {
		return _model.isEmpty() ? 0 : _model.getModel().getMnemonic();
	}

	@Override
	public void setActionCommand(String s) {
		if (!_model.isEmpty()) {
			_model.getModel().setActionCommand(s);
		}
	}

	@Override
	public String getActionCommand() {
		return _model.isEmpty() ? null : _model.getModel().getActionCommand();
	}

	@Override
	public void setGroup(ButtonGroup group) {
		if (!_model.isEmpty()) {
			_model.getModel().setGroup(group);
		}
	}
	
	protected void relayStateChanged(ChangeEvent eventSource) {
		ChangeEvent event = new ChangeEvent(this);
		_listeners.forEach(ChangeListener.class, ls -> ls.stateChanged(event));			
		
	}

	@Override
	public void addActionListener(ActionListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeActionListener(ActionListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addItemListener(ItemListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeItemListener(ItemListener l) {
		// TODO Auto-generated method stub

	}
	
	private class ModelEventRelay
		implements ActionListener, ChangeListener, ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// pass it straight through
			_listeners.forEach(ItemListener.class, ls -> ls.itemStateChanged(e));
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			_listeners.forEach(ChangeListener.class, ls -> ls.stateChanged(e));			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			_listeners.forEach(ActionListener.class, ls -> ls.actionPerformed(e));			
			
		}
	}
}
