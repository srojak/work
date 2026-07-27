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
package srojak.cdo.swing.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;

import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.GridBagConstraintsTool;
import srojak.cdo.swing.impl.NamedChoicePanelConnector;
import srojak.cdo.swing.models.NamedChoiceModel;
import srojak.core.NameToken;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class NamedChoiceGroupSubPanel
		extends NameTokenTagPanel
		implements NamedChoicePanel {
	private final CommonEventListenerStore _listeners;
	private final NamedChoiceGroupCommonStore _common;
	private final GridBagConstraintsTool _toolGBC;
	
	@SuppressWarnings("unused")
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = NamedChoiceGroupSubPanel.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));		
	}

	/**
	 * @param tokenName
	 */
	public NamedChoiceGroupSubPanel(NameToken tokenName, int itemsAcross) {
		super(tokenName, new GridBagLayout());
		if (itemsAcross <= 0) {
			throw new IllegalArgumentException("itemsAcross must be positive");
		}
		_listeners = new CommonEventListenerList();
		_common = new NamedChoiceGroupCommonStore(new PanelConnector(), itemsAcross);
		_toolGBC = new GridBagConstraintsTool();
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public NamedChoiceGroupSubPanel(NameToken tokenName, boolean isDoubleBuffered, int itemsAcross) {
		super(tokenName, new GridBagLayout(), isDoubleBuffered);
		if (itemsAcross <= 0) {
			throw new IllegalArgumentException("itemsAcross must be positive");
		}
		_listeners = new CommonEventListenerList();
		_common = new NamedChoiceGroupCommonStore(new PanelConnector(), itemsAcross);
		_toolGBC = new GridBagConstraintsTool();
		postConstruct();
	}

	private void postConstruct() {
		_toolGBC.setFill(GridBagConstraints.NONE);
		_toolGBC.setGridSize(1,  1);
		_toolGBC.setAnchor(GridBagConstraints.WEST);
	}
	
	@Override
	public NamedChoiceModel getModel() {
		return _common.getModel();
	}

	@Override
	public void setModel(NamedChoiceModel model) {
		_common.setModel(model);
	}

	private class PanelConnector
			implements NamedChoicePanelConnector {
		
		public NameTokenTagPanel getPanel() {
			return NamedChoiceGroupSubPanel.this;
		}

		@Override
		public NameToken getPanelNameTag() {
			return getNameTag();
		}

		@Override
		public void addRadioButtonToPanel(Point ptLocation, JRadioButton button) {
			_toolGBC.setGridPosition(ptLocation.x, ptLocation.y);
			add(button, _toolGBC.snap());
		}

		@Override
		public void sendChangeEvent() {
			_listeners.sendToAll(ChangeListener.class, 
					() -> new ChangeEvent(NamedChoiceGroupSubPanel.this),
					(ls, ev) -> ls.stateChanged(ev));
		}
		
	}
}
