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

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;

import srojak.cdo.swing.base.ButtonModelFacadeBase;

/**
 * @author Stephen
 *
 */
public class LocalEnabledButtonModelFacade
		extends ButtonModelFacadeBase {
	private boolean _bExternal;
	private boolean _bLocal;
	
	public static LocalEnabledButtonModelFacade makeFacadeFor(AbstractButton button) {
		Objects.requireNonNull(button, "button");
		return new LocalEnabledButtonModelFacade(button.getModel());
	}

	/**
	 * @param model
	 */
	public LocalEnabledButtonModelFacade(ButtonModel model) {
		super(model);
		_bExternal = model.isEnabled();
		_bLocal = true;
	}
	
	public boolean isLocalEnabled() {
		return _bLocal;
	}
	
	@Override
	public boolean isEnabled() {
		return _bLocal && _bExternal;
	}
	
	public void setLocalEnabled(boolean bState) {
		if (bState == _bLocal) {
			return;
		}
		_bLocal = bState;
		_model.setEnabled(_bLocal && _bExternal);
	}

	@Override
	public void setEnabled(boolean bState) {
		if (bState == _bExternal) {
			return;
		}
		_bExternal = bState;
		_model.setEnabled(_bLocal && _bExternal);
	}
}
