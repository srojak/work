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
package srojak.cdo.swing.base;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Objects;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeListener;

/**
 * @author Stephen
 *
 */
public class ButtonModelFacadeBase
		implements ButtonModel {
	protected final ButtonModel _model;
	
	public ButtonModelFacadeBase(ButtonModel model) {
		Objects.requireNonNull(model, "model");
		_model = model;
	}

	@Override
	public Object[] getSelectedObjects() {
		return _model.getSelectedObjects();
	}

	@Override
	public boolean isArmed() {
		return _model.isArmed();
	}

	@Override
	public boolean isSelected() {
		return _model.isSelected();
	}

	@Override
	public boolean isEnabled() {
		return _model.isEnabled();
	}

	@Override
	public boolean isPressed() {
		return _model.isPressed();
	}

	@Override
	public boolean isRollover() {
		return _model.isRollover();
	}

	@Override
	public void setArmed(boolean b) {
		_model.setArmed(b);
	}

	@Override
	public void setSelected(boolean b) {
		_model.setSelected(b);
	}

	@Override
	public void setEnabled(boolean b) {
		_model.setEnabled(b);
	}

	@Override
	public void setPressed(boolean b) {
		_model.setPressed(b);
	}

	@Override
	public void setRollover(boolean b) {
		_model.setRollover(b);
	}

	@Override
	public void setMnemonic(int key) {
		_model.setMnemonic(key);
	}

	@Override
	public int getMnemonic() {
		return _model.getMnemonic();
	}

	@Override
	public void setActionCommand(String s) {
		 _model.setActionCommand(s);
	}

	@Override
	public String getActionCommand() {
		return _model.getActionCommand();
	}

	@Override
	public void setGroup(ButtonGroup group) {
		_model.setGroup(group);
	}

	@Override
	public void addActionListener(ActionListener l) {
		_model.addActionListener(l);
	}

	@Override
	public void removeActionListener(ActionListener l) {
		_model.removeActionListener(l);
	}

	@Override
	public void addItemListener(ItemListener l) {
		_model.addItemListener(l);
	}

	@Override
	public void removeItemListener(ItemListener l) {
		_model.removeItemListener(l);
	}

	@Override
	public void addChangeListener(ChangeListener l) {
		_model.addChangeListener(l);
	}

	@Override
	public void removeChangeListener(ChangeListener l) {
		_model.removeChangeListener(l);
	}

}
