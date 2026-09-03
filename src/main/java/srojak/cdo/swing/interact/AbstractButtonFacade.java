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

import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;

/**
 * @author Stephen
 *
 * AbstractButton really does implement ItemSelectable
 */
public class AbstractButtonFacade 
		extends ComponentDetailFacadeBase {
	private final ButtonModelAdapter _adapterModel;

	/**
	 * @param component
	 */
	public AbstractButtonFacade(AbstractButton component) {
		super(component);
		_adapterModel = new ButtonModelAdapter();
		ButtonModel modelOrig = component.getModel();
		component.setModel(_adapterModel);
		_adapterModel.setModel(modelOrig);
		component.addItemListener(new ItemEventRelay());
	}
	
	public ButtonModelAdapter getModelAdapter() {
		return _adapterModel;
	}

	@Override
	public Object[] getSelectedObjects() {
		AbstractButton button = getComponentAs();
		return button.getSelectedObjects();
	}

	@Override
	public void addItemListener(ItemListener l) {
		_listeners.add(ItemListener.class, l);
	}

	@Override
	public void removeItemListener(ItemListener l) {
		_listeners.remove(ItemListener.class, l);
	}
}
