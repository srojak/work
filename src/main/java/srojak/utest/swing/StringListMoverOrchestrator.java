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
package srojak.utest.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ListSelectionModel;

import srojak.cdo.swing.DataComponent;
import srojak.cdo.swing.base.GuiOrchestratorBase;
import srojak.cdo.swing.functional.OrchReceptor;
import srojak.cdo.swing.models.DxButtonModelFacade;
import srojak.cdo.swing.models.ModelDecoratorValues;
import srojak.cdo.uilib.string.StringEntryPanel;
import srojak.cdo.uilib.string.StringSelectListPanel;
import srojak.cdo.uilib.string.StringSelectedListPanel;
import srojak.core.NameToken;
import srojak.core.TextMessageRelay;
import srojak.core.field.SetOnce;
import srojak.core.field.SetOnceConditions;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.events.routed.RoutedStateChangeEvent;
import srojak.events.routed.RoutedStateChangeListener;
import srojak.mantle.ReceptorReceiver;

/**
 * @author Stephen
 *
 */
public class StringListMoverOrchestrator
		extends GuiOrchestratorBase 
		implements ModelDecoratorValues {
	private final SetOnce<TextMessageRelay> _relay;
	private final OrchReceptor<StringSelectListPanel> _receptorSelect;
	private final OrchReceptor<StringSelectedListPanel> _receptorSelected;
	private final OrchReceptor<StringEntryPanel> _receptorEntry;
	
	public static final NameToken ClassToken;
	private static final DebugSwitch _swDebugClass;
	private static final DebugSwitch _swDebugEventResponse;
	
	static {
		Class<?> classThis = StringListMoverOrchestrator.class;
		ClassToken = NameToken.classNameFactory(classThis);
		DebugNexus debug = new DebugNexus();
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
		_swDebugEventResponse 
				= debug.getSwitch(DebugSwitchTool.makeClassSubjectKey(classThis, "EventResponse"));		
	}
	
	/**
	 * 
	 */
	public StringListMoverOrchestrator() {
		super(_swDebugClass);
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH);
		_relay = new SetOnce<TextMessageRelay>(SetOnceConditions.DEFAULT);
		_receptorSelect = new OrchReceptor<StringSelectListPanel>(StringSelectListPanel.class, 
				_mapButtonModels);
		_receptorSelected = new OrchReceptor<StringSelectedListPanel>(StringSelectedListPanel.class, 
				_mapButtonModels);
		_receptorEntry = new OrchReceptor<StringEntryPanel>(StringEntryPanel.class,
				_mapButtonModels);
	}
	
	public void setTextRelay(TextMessageRelay relay) {
		_relay.set(relay);
	}
	
	@Override
	public void receiveDataComponent(DataComponent dc) {
		if (dc instanceof StringSelectListPanel panel) {
			_receptorSelect.receive(panel);
		} else if (dc instanceof StringSelectedListPanel panel) {
			_receptorSelected.receive(panel);
		} else if (dc instanceof StringEntryPanel panel) {
			_receptorEntry.receive(panel);
		}
	}

	public ReceptorReceiver<StringSelectListPanel> receiverSelectListPanel() {
		return _receptorSelect;
	}
	
	public ReceptorReceiver<StringSelectedListPanel> receiverSelectedListPanel() {
		return _receptorSelected;
	}
	
	public ReceptorReceiver<StringEntryPanel> receiverEntryPanel() {
		return _receptorEntry;
	}

	@SuppressWarnings("serial")
	@Override
	public void initialize() {
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH);
		
		_receptorSelect.require();
		_receptorSelected.require();
		_receptorEntry.require();
		
		DxButtonModelFacade facade = getButtonModel(StringEntryPanel.BN_ENTER);
		if (facade != null) {
			facade.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					String strText = _receptorEntry.get().getText();
					_swDebugEventResponse.buildAndWrite(ObsLevel.DEBUG, sb -> {
						sb.append("button ");
						sb.append(StringEntryPanel.BN_ENTER.getName());
						sb.append(" returned \"");
						sb.append(strText);
						sb.append("\"");
					});
					_receptorSelect.get().addEntry(strText);
					_receptorEntry.get().reset();
				}
				
			});
		}
		
		_receptorEntry.get().addRoutedStateChangeListener(new RoutedStateChangeListener() {

			@Override
			public void stateChanged(RoutedStateChangeEvent event) {
				_swDebugEventResponse.write(ObsLevel.DEBUG, () -> "received " + event.toDataString());
				Object objSource = event.getSource();
				if (objSource instanceof StringEntryPanel panel 
						&& event.getSubjectID() == SUBJECT_DATA_READY) {
					String strText = panel.getText();
					_swDebugEventResponse.buildAndWrite(ObsLevel.DEBUG, sb -> {
						sb.append("panel ");
						sb.append(StringEntryPanel.ClassToken.getName());
						sb.append(" returned \"");
						sb.append(strText);
						sb.append("\"");
					});
					_receptorSelect.get().addEntry(strText);
					panel.reset();
				}

				
			}
			
		});
		
		facade = getButtonModel(StringSelectListPanel.BN_SELECT);
		facade.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelectListPanel list = _receptorSelect.get();
				ListSelectionModel modelSelect = list.getListSelectionModel();
				_swDebugClass.write(ObsLevel.DEBUG, "selection mode is " + modelSelect.getSelectionMode());
				int[] indices = modelSelect.getSelectedIndices();
				String strIndices = "selected " + indices.length + " indices";
				_swDebugClass.write(ObsLevel.DEBUG, strIndices);
				_relay.get().writeln(strIndices);
				for (int index : indices) {
					String strText = list.getElementAt(index);
					if (!_receptorSelected.get().addToList(strText)) {
						_relay.get().writeln(strText + " is already in the list");
					}
				}
			}
			
		});

		
		_swDebugClass.writeTraceReturn(TraceLevel.HIGH);
	}

	@Override
	public void sync() {

	}

}
