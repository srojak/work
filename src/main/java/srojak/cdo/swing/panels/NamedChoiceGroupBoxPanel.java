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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.GridBagConstraintsTool;
import srojak.cdo.swing.functional.ControlModelManager;
import srojak.cdo.swing.models.DefaultNamedChoiceModel;
import srojak.cdo.swing.models.NameIdentifiedButtonModel;
import srojak.cdo.swing.models.NamedChoiceModel;
import srojak.core.NameIdentifiedAndLabeled;
import srojak.core.NameToken;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.events.ObjectOwnershipEvent;
import srojak.core.events.ObjectOwnershipListener;
import srojak.core.observe.ObsLevel;
import srojak.core.tools.ListMethods;
import srojak.core.tools.StringMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.events.CollectionChangeEvent;
import srojak.events.CollectionChangeListener;
import srojak.events.ObjectValueChangeEvent;
import srojak.events.ObjectValueChangeListener;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class NamedChoiceGroupBoxPanel
		extends GroupBoxPanel {
	private final CommonEventListenerStore _listeners;
	private final LinkedList<NameIdentifiedButtonModel> _listButtons;
	private final ControlModelManager<NamedChoiceModel> _model;
	private final ButtonGroup _group;
	private final GridBagConstraintsTool _toolGBC;
	private final Point _ptNextLoc;
	private final int _nAcross;
	private ModelListener _listenerModel;
	
	private static final DebugSwitch _swDebugClass;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = NamedChoiceGroupBoxPanel.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	/**
	 * @param tokenName
	 */
	public NamedChoiceGroupBoxPanel(NameToken tokenName, int itemsAcross) {
		super(tokenName, new GridBagLayout());
		if (itemsAcross <= 0) {
			throw new IllegalArgumentException("itemsAcross must be positive");
		}
		_listeners = new CommonEventListenerList();
		_listButtons = new LinkedList<NameIdentifiedButtonModel>();
		_model = new ControlModelManager<NamedChoiceModel>();
		_group = new ButtonGroup();
		_toolGBC = new GridBagConstraintsTool();
		_ptNextLoc = new Point(0, 0);
		_nAcross = itemsAcross;
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public NamedChoiceGroupBoxPanel(NameToken tokenName, boolean isDoubleBuffered, int itemsAcross) {
		super(tokenName, new GridBagLayout(), isDoubleBuffered);
		if (itemsAcross <= 0) {
			throw new IllegalArgumentException("itemsAcross must be positive");
		}
		_listeners = new CommonEventListenerList();
		_listButtons = new LinkedList<NameIdentifiedButtonModel>();
		_model = new ControlModelManager<NamedChoiceModel>();
		_group = new ButtonGroup();
		_toolGBC = new GridBagConstraintsTool();
		_ptNextLoc = new Point(0, 0);
		_nAcross = itemsAcross;
		postConstruct();
	}

	private void postConstruct() {
		_model.addObjectOwnershipListener(new ObjectOwnershipListener() {

			@Override
			public void acquire(ObjectOwnershipEvent event) {
				NamedChoiceModel model = event.getValueAs();
				_listenerModel = new ModelListener();
				model.addChangeListener(_listenerModel);
				model.addItemListener(_listenerModel);
				model.addCollectionChangeListener(_listenerModel);
				model.addObjectValueChangeListener(_listenerModel);
				
			}

			@Override
			public void release(ObjectOwnershipEvent event) {
				NamedChoiceModel model = event.getValueAs();
				model.removeChangeListener(_listenerModel);
				model.removeItemListener(_listenerModel);
				model.removeCollectionChangeListener(_listenerModel);
				model.removeObjectValueChangeListener(_listenerModel);				
			}
			
		});
		_model.setModel(new DefaultNamedChoiceModel());
		
		_toolGBC.setFill(GridBagConstraints.NONE);
		_toolGBC.setGridSize(1,  1);
		_toolGBC.setAnchor(GridBagConstraints.WEST);
	}
	
	public NamedChoiceModel getModel() {
		return _model.getModel();
	}
	
	public void setModel(NamedChoiceModel model) {
		_model.setModel(model);
	}
	
	private void addRadioButton(NameIdentifiedAndLabeled item) {
		JRadioButton button = new JRadioButton(item.getLabel());
		ButtonModel model = button.getModel();
		model.setActionCommand(item.getName());
		_group.add(button);
		_toolGBC.setGridPosition(_ptNextLoc.x, _ptNextLoc.y);
		add(button, _toolGBC.snap());
		_ptNextLoc.x++;
		if (_ptNextLoc.x == _nAcross) {
			_ptNextLoc.x = 0;
			_ptNextLoc.y++;
		}
		model.addActionListener(new ButtonActionListener());
		NameIdentifiedButtonModel nib = new NameIdentifiedButtonModel(item.getName(), model);
		_listButtons.add(nib);
	}
	
	private NameIdentifiedButtonModel findButtonModelByName(String strName) {
		NameIdentifiedButtonModel nib 
			= ListMethods.findInList(_listButtons, i -> i.isNameEqual(strName));
		if (nib == null) {
			_swDebugClass.write(ObsLevel.ERROR, 
					() -> "no button model for name=" + StringMethods.encloseInQuotes(strName));
		}
		return nib;
	}
	
	private class ButtonActionListener
			implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ButtonModel modelButton = (ButtonModel) e.getSource();
			String strName = modelButton.getActionCommand();
			_swDebugClass.write(ObsLevel.DEBUG, 
					() -> "panel " + getNameTag() + " button " + strName + " clicked");
			NamedChoiceModel model = _model.getModel();
			model.setSelectionByName(strName);
		}
		
	}
	
	private class ModelListener
			implements ChangeListener, ItemListener, CollectionChangeListener,
				ObjectValueChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			// the model communicates enabled state change
			NamedChoiceModel model = _model.getModel();
			if (isEnabled() != model.isEnabled()) {
				setEnabled(model.isEnabled());
				_listeners.sendToAll(ChangeListener.class, 
						() -> new ChangeEvent(NamedChoiceGroupBoxPanel.this),
						(ls, ev) -> ls.stateChanged(ev));
				repaint();
			}
		}

		@Override
		public void collectionChanged(CollectionChangeEvent event) {
			// the model communicates changes to the set of choices
			NamedChoiceModel model = _model.getModel();
			switch (event.getVerb()) {
			case CollectionChangeEvent.VERB_CLEAR:
				_swDebugClass.write(ObsLevel.WARN, () -> "no provision for removing items");
				break;
				
			case CollectionChangeEvent.VERB_ADD_MULT:
				for (NameIdentifiedAndLabeled ni : model.getChoices()) {
					addRadioButton(ni);
				}
				repaint();
				break;
				
			case CollectionChangeEvent.VERB_ADD:
				addRadioButton(event.<NameIdentifiedAndLabeled>getChangeObjectAs());
				repaint();
				break;
			}
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			// the model communicates item selection/deselection
			NameIdentifiedAndLabeled ni = (NameIdentifiedAndLabeled) e.getItem();
			NameIdentifiedButtonModel nib 
				= findButtonModelByName(ni.getName());
			if (nib == null) {
				return;
			}
			switch (e.getStateChange()) {
			case ItemEvent.DESELECTED:
				_group.setSelected(nib.getModel(), false);
				break;
				
			case ItemEvent.SELECTED:
				_group.setSelected(nib.getModel(), true);
				break;
			}
		}

		@Override
		public void update(ObjectValueChangeEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
