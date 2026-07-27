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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import srojak.cdo.swing.models.NameIdentifiedButtonModel;
import srojak.cdo.swing.models.NamedChoiceModel;
import srojak.core.NameIdentifiedAndLabeled;
import srojak.core.events.NameAndStateChangeEvent;
import srojak.core.events.NameAndStateChangeListener;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
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
public class NamedChoiceModelMultiListener 
		implements ChangeListener, ItemListener, CollectionChangeListener,
			ObjectValueChangeListener, NameAndStateChangeListener {
	private final NamedChoiceGroupCommonStore _common;
	private final NameTokenTagPanel _panel;

	private static final DebugSwitch _swDebugClass;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = NamedChoiceModelMultiListener.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	NamedChoiceModelMultiListener(NamedChoiceGroupCommonStore storeCommon) {
		Objects.requireNonNull(storeCommon, "storeCommon");
		_common = storeCommon;
		_panel = _common.getPanel();
	}

	@Override
	public void stateChanged(NameAndStateChangeEvent event) {
		NameIdentifiedButtonModel nib 
			= _common.findButtonModelByName(event.getName());
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, 
				() -> event.toDataString());
		if (nib == null) {
			_swDebugClass.write(ObsLevel.WARN, () -> "button name " + event.getName() + " not found");
			return;
		}
		_common.changeNamedButonState(nib, event.getState());
	}

	@Override
	public void update(ObjectValueChangeEvent event) {
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, () -> event.toDataString());
	}

	@Override
	public void collectionChanged(CollectionChangeEvent event) {
		// the model communicates changes to the set of choices
		NamedChoiceModel model = _common.getModel();
		switch (event.getVerb()) {
		case CollectionChangeEvent.VERB_CLEAR:
			_swDebugClass.write(ObsLevel.WARN, () -> "no provision for removing items");
			break;
			
		case CollectionChangeEvent.VERB_ADD_MULT:
			for (NameIdentifiedAndLabeled ni : model.getChoices()) {
				_common.addRadioButtonToPanel(ni);
			}
			_panel.repaint();
			break;
			
		case CollectionChangeEvent.VERB_ADD:
			_common.addRadioButtonToPanel(event.<NameIdentifiedAndLabeled>getChangeObjectAs());
			_panel.repaint();
			break;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// the model communicates item selection/deselection
		NameIdentifiedAndLabeled ni = (NameIdentifiedAndLabeled) e.getItem();
		NameIdentifiedButtonModel nib 
			= _common.findButtonModelByName(ni.getName());
		if (nib == null) {
			return;
		}
		switch (e.getStateChange()) {
		case ItemEvent.DESELECTED:
			_common.setSelectedButton(nib.getModel(), false);
			break;
			
		case ItemEvent.SELECTED:
			_common.setSelectedButton(nib.getModel(), true);
			break;
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// the model communicates enabled state change
		_common.respondToStateChanged(e);
	}

}
