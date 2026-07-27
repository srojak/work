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

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;

import srojak.cdo.events.AWTEventMethods;
import srojak.cdo.swing.functional.ControlModelManager;
import srojak.cdo.swing.impl.NamedChoicePanelConnector;
import srojak.cdo.swing.models.DefaultNamedChoiceModel;
import srojak.cdo.swing.models.NameIdentifiedButtonModel;
import srojak.cdo.swing.models.NamedChoiceModel;
import srojak.core.NameIdentifiedAndLabeled;
import srojak.core.NameToken;
import srojak.core.events.ObjectOwnershipEvent;
import srojak.core.events.ObjectOwnershipListener;
import srojak.core.observe.ObsLevel;
import srojak.core.tools.ListMethods;
import srojak.core.tools.StringMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class NamedChoiceGroupCommonStore {
	private final NamedChoicePanelConnector _connectorPanel;
	private final LinkedList<NameIdentifiedButtonModel> _listButtons;
	private final ControlModelManager<NamedChoiceModel> _model;
	private final ButtonGroup _group;
	private final Point _ptNextLoc;
	private final int _nAcross;
	private NamedChoiceModelMultiListener _listenerModel;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = NamedChoiceGroupCommonStore.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	NamedChoiceGroupCommonStore(NamedChoicePanelConnector connector, int itemsAcross) {
		if (itemsAcross <= 0) {
			throw new IllegalArgumentException("itemsAcross must be positive");
		}
		_connectorPanel = connector;
		_listButtons = new LinkedList<NameIdentifiedButtonModel>();
		_model = new ControlModelManager<NamedChoiceModel>();
		_group = new ButtonGroup();
		_ptNextLoc = new Point(0, 0);
		_nAcross = itemsAcross;
		_listenerModel = null;
		
		_model.addObjectOwnershipListener(new ObjectOwnershipListener() {

			@Override
			public void acquire(ObjectOwnershipEvent event) {
				NamedChoiceModel model = event.getValueAs();
				_listenerModel = new NamedChoiceModelMultiListener(NamedChoiceGroupCommonStore.this);
				model.addChangeListener(_listenerModel);
				model.addItemListener(_listenerModel);
				model.addCollectionChangeListener(_listenerModel);
				model.addObjectValueChangeListener(_listenerModel);
				model.addNameAndStateChangeListener(_listenerModel);
				
				mergeRadioButtons(model.getChoices());
			}

			@Override
			public void release(ObjectOwnershipEvent event) {
				NamedChoiceModel model = event.getValueAs();
				model.removeChangeListener(_listenerModel);
				model.removeItemListener(_listenerModel);
				model.removeCollectionChangeListener(_listenerModel);
				model.removeObjectValueChangeListener(_listenerModel);	
				model.removeNameAndStateChangeListener(_listenerModel);
			}
			
		});
		_model.setModel(new DefaultNamedChoiceModel());
	}

	NamedChoiceModel getModel() {
		return _model.getModel();
	}

	void setModel(NamedChoiceModel model) {
		Objects.requireNonNull(model, "model");
		_model.setModel(model);
	}
	
	NameIdentifiedButtonModel findButtonModelByName(String strName) {
		NameIdentifiedButtonModel nib 
			= ListMethods.findInList(_listButtons, i -> i.isNameEqual(strName));
		if (nib == null) {
			_swDebugClass.write(ObsLevel.ERROR, 
					() -> "no button model for name=" + StringMethods.encloseInQuotes(strName));
		}
		return nib;
	}

	void addRadioButtonToPanel(NameIdentifiedAndLabeled item) {
		JRadioButton button = new JRadioButton(item.getLabel());
		ButtonModel model = button.getModel();
		model.setActionCommand(item.getName());
		_group.add(button);
		_connectorPanel.addRadioButtonToPanel(_ptNextLoc, button);
		_ptNextLoc.x++;
		if (_ptNextLoc.x == _nAcross) {
			_ptNextLoc.x = 0;
			_ptNextLoc.y++;
		}
		ButtonActionListener listener = new ButtonActionListener();
		model.addActionListener(listener);
		model.addItemListener(listener);
		NameIdentifiedButtonModel nib = new NameIdentifiedButtonModel(item.getName(), model);
		_listButtons.add(nib);
	}
	
	
	private void mergeRadioButtons(Collection<NameIdentifiedAndLabeled> items) {	
		List<NameIdentifiedAndLabeled> listNew = new LinkedList<NameIdentifiedAndLabeled>();
		for (NameIdentifiedAndLabeled item : items) {
			if (!ListMethods.isTrueForAny(_listButtons, i -> i.isNameEqual(item.getName()))) {
				listNew.add(item);
			}
		}
		for (NameIdentifiedAndLabeled item : listNew) {
			addRadioButtonToPanel(item);
		}
	}
	
	NameTokenTagPanel getPanel() {
		return _connectorPanel.getPanel();
	}
	
	void setSelectedButton(ButtonModel model, boolean bState) {
		_group.setSelected(model, bState);
	}
	
	void changeNamedButonState(NameIdentifiedButtonModel niButton, boolean bState) {
		if (bState) {
			niButton.setEnabled(true);
		} else {
			niButton.setEnabled(false);
			ButtonModel modelButton = niButton.getModel();
			_swDebugClass.write(ObsLevel.DEBUG2, 
					() -> "button selected state is " + modelButton.isSelected());
			if (modelButton.isSelected()) {
				modelButton.setSelected(false);
				_group.clearSelection();
			}
		}
	}
	
	public void respondToStateChanged(ChangeEvent event) {
		// the model communicates enabled state change
		NameTokenTagPanel panel = _connectorPanel.getPanel();
		NamedChoiceModel model = _model.getModel();
		if (panel.isEnabled() != model.isEnabled()) {
			panel.setEnabled(model.isEnabled());
			_connectorPanel.sendChangeEvent();
			panel.repaint();
		}		
	}
	
	private class ButtonActionListener
			implements ActionListener, ItemListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ButtonModel modelButton = (ButtonModel) e.getSource();
			String strName = modelButton.getActionCommand();
			NameToken tokenPanel = _connectorPanel.getPanelNameTag();
			_swDebugClass.write(ObsLevel.DEBUG, 
					() -> "panel " + tokenPanel + " button " + strName + " clicked");
			NamedChoiceModel model = _model.getModel();
			model.setSelectionByName(strName);
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			ButtonModel modelButton = (ButtonModel) e.getSource();
			NameToken tokenPanel = _connectorPanel.getPanelNameTag();
			String strName = modelButton.getActionCommand();
			_swDebugClass.buildAndWrite(ObsLevel.DEBUG, sb -> {
				sb.append("panel ");
				sb.append(tokenPanel);
				sb.append(" button ");
				sb.append(strName);
				sb.append(" ");
				AWTEventMethods.formatItemEvent(sb, e);
			});
		}
	}
}
