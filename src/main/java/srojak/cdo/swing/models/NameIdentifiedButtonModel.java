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
package srojak.cdo.swing.models;

import java.util.Objects;

import javax.swing.ButtonModel;

import srojak.cdo.CanBeEnabled;
import srojak.cdo.swing.NameIdentifiedCDOControlModel;
import srojak.core.containers.NameIdentifiedBase;

/**
 * @author Stephen
 *
 */
public class NameIdentifiedButtonModel
		extends NameIdentifiedBase
		implements NameIdentifiedCDOControlModel, CanBeEnabled {
	private final ButtonModel _model;

	/**
	 * @param strName
	 */
	public NameIdentifiedButtonModel(String strName, ButtonModel model) {
		super(strName);
		Objects.requireNonNull(model, "model");
		_model = model;
	}
	
	public ButtonModel getModel() {
		return _model;
	}

	@Override
	public boolean isEnabled() {
		return _model.isEnabled();
	}

	@Override
	public void setEnabled(boolean bState) {
		_model.setEnabled(bState);
	}

	@Override
	protected boolean canBeComparedTo(NameIdentifiedBase other) {
		return other instanceof NameIdentifiedButtonModel;
	}

	@Override
	public String toString() {
		return makeTaggedName("NIButtonModel");
	}

}
