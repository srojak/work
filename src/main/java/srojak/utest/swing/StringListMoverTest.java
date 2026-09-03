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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import srojak.cdo.AWTFormatters;
import srojak.cdo.ContainerMethods;
import srojak.cdo.GridBagConstraintsTool;
import srojak.cdo.swing.FileChooserAction;
import srojak.cdo.swing.SwingUIMethods;
import srojak.cdo.swing.components.FileChooserConfirming;
import srojak.cdo.swing.models.ListSelectChoiceModel;
import srojak.cdo.swing.models.NamedChoiceModel;
import srojak.cdo.swing.panels.ListSelectChoiceGroupBoxPanel;
import srojak.cdo.swing.panels.NameTokenTagPanel;
import srojak.cdo.uilib.string.StringEntryPanel;
import srojak.cdo.uilib.string.StringSelectListPanel;
import srojak.cdo.uilib.string.StringSelectedListPanel;
import srojak.core.NameIdentifiedAndLabeled;
import srojak.core.NameToken;
import srojak.core.TextMessageRelay;
import srojak.core.containers.NamedAndLabeledInt;
import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.core.specialized.IntegerCounter;
import srojak.core.tools.StringMethods;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigFileReader;
import srojak.debug.config.DebugConfigNames;
import srojak.events.ObjectValueChangeEvent;
import srojak.events.ObjectValueChangeListener;
import srojak.mantle.io.TextReaderMethods;

/**
 * @author Stephen
 *
 */
public class StringListMoverTest
		extends CommonTestAppFrame {
	private final StringListMoverOrchestrator _orchestrator;
	private final StringSelectListPanel _panelSelect;
	private final StringSelectedListPanel _panelSelected;
	private final StringEntryPanel _panelEntry;
	private final ListSelectChoiceGroupBoxPanel _gbListSelect;
	private final JCheckBox _ckAllowSingleInterval;
	private final JCheckBox _ckAllowMultiInterval;
	private final FileChooserConfirming _dlgTextFile;
	private JComponent _componentCenter;
	
	public static final NameToken ClassToken;
	private static final DebugSwitch _swDebugClass;
	private static final String PREFIX_LOG_FILE = "SLMTest";
	
	static {
		DebugNexus debug = new DebugNexus(DebugNexus.CONS_NONE);
		Class<?> classThis = StringListMoverTest.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * 
	 */
	public StringListMoverTest() {
		super("StringList Mover Test");
		_orchestrator = new StringListMoverOrchestrator();
		_orchestrator.setTextRelay(getMessageOut());
		_panelSelect = new StringSelectListPanel(StringSelectListPanel.ClassToken);
		_panelSelected = new StringSelectedListPanel(StringSelectedListPanel.ClassToken);
		_panelEntry = new StringEntryPanel(StringEntryPanel.ClassToken);
		_gbListSelect = new ListSelectChoiceGroupBoxPanel(ListSelectChoiceGroupBoxPanel.PANEL_NAME, 3);
		_ckAllowSingleInterval = new JCheckBox("Allow single interval");
		_ckAllowMultiInterval = new JCheckBox("Allow multi interval");
		
		_dlgTextFile = new FileChooserConfirming();
		FileFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		_dlgTextFile.setCurrentDirectory(new File("."));
		_dlgTextFile.setFileFilter(filter);
		_dlgTextFile.setFileExistenceBehavior(FileChooserAction.Open, FileExistence.MustExist);
		
		_gbListSelect.setGroupBoxTitle("Primary List Selection Mode");
		NamedChoiceModel modelSelector = _gbListSelect.getModel();
		modelSelector.setSelectionByName(ListSelectChoiceModel.NAME_SINGLE);
		modelSelector.addObjectValueChangeListener(new ObjectValueChangeListener() {

			@Override
			public void update(ObjectValueChangeEvent event) {
				TextMessageRelay relay = getMessageOut();
				ListSelectionModel modelSelect = _panelSelect.getListSelectionModel();
				NamedAndLabeledInt choice = event.getValueAs();
				relay.writeln("Selection mode set to " + choice.getLabel());
				modelSelect.setSelectionMode(choice.getValue());
			}
			
		});
		modelSelector.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				TextMessageRelay relay = getMessageOut();
				NameIdentifiedAndLabeled item = (NameIdentifiedAndLabeled) e.getItem();
				switch (e.getStateChange()) {
				case ItemEvent.SELECTED:
					relay.writeln("Item " + item.getName() + " selected");
					break;
					
				case ItemEvent.DESELECTED:
					relay.writeln("Item " + item.getName() + " deselected");
					break;
				}
			}
			
		});
		/*
		_gbListSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TextMessageRelay relay = getMessageOut();
				ListSelectionModel modelSelect = _panelSelect.getListSelectionModel();
				int modeSelect = _gbListSelect.getSelectionMode();
				switch (modeSelect) {
				case ListSelectionModel.SINGLE_SELECTION:
					relay.writeln("Selection mode set to single");
					modelSelect.setSelectionMode(modeSelect);
					break;
					
				case ListSelectionModel.SINGLE_INTERVAL_SELECTION:
					relay.writeln("Selection mode set to single-interval");
					modelSelect.setSelectionMode(modeSelect);
					break;
					
				case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION:
					relay.writeln("Selection mode set to multi-interval");
					modelSelect.setSelectionMode(modeSelect);
					break;
				}
			}
			
		});
		*/
		
		addComponentToLeft(_panelSelect);
		
		NameTokenTagPanel panelCenter 
			= new NameTokenTagPanel(NameToken.factory(ClassToken, "Center"), new GridBagLayout());
		GridBagConstraintsTool toolGBC = new GridBagConstraintsTool();
		toolGBC.setFill(0);
		toolGBC.setGridSize(1,  1);
		toolGBC.setAnchor(GridBagConstraints.WEST);
		toolGBC.setInsets(5);
		toolGBC.setGridSize(2, 1);
		toolGBC.setGridPosition(0, 0);	
		panelCenter.add(_panelSelected, toolGBC.snap());
		
		toolGBC.setGridPosition(0, 1);
		panelCenter.add(_gbListSelect, toolGBC.snap());
		
		toolGBC.setGridPosition(0, 4);
		panelCenter.add(_panelEntry, toolGBC.snap());
		
		toolGBC.setGridSize(1, 1);
		toolGBC.setGridPosition(1, 2);
		_ckAllowSingleInterval.setSelected(true);
		_ckAllowSingleInterval.addActionListener(
				new AllowCheckBoxActionListener(ListSelectChoiceModel.NAME_SINGLE_INTV));
		panelCenter.add(_ckAllowSingleInterval, toolGBC.snap());
		toolGBC.setGridPosition(1, 3);
		_ckAllowMultiInterval.setSelected(true);
		_ckAllowMultiInterval.addActionListener(
				new AllowCheckBoxActionListener(ListSelectChoiceModel.NAME_MULTI_INTV));
		panelCenter.add(_ckAllowMultiInterval, toolGBC.snap());
		
		addComponentToCenter(panelCenter);
		_componentCenter = panelCenter;
		
		_panelSelect.setTitle("Available Strings");
		
		_orchestrator.receiverSelectListPanel().receive(_panelSelect);
		_orchestrator.receiverSelectedListPanel().receive(_panelSelected);
		_orchestrator.receiverEntryPanel().receive(_panelEntry);
		
		_orchestrator.initialize();
	}

	@Override
	protected void addItemsToTestMenu(JMenu menuTest) {
		JMenuItem itemMenu = new JMenuItem("Read File");
		menuTest.add(itemMenu);
		itemMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int nResult = showOpenFileDialog(_dlgTextFile);
				switch (nResult) {
				case JFileChooser.APPROVE_OPTION:
					break;
					
				case JFileChooser.CANCEL_OPTION:
					relayText("Cancelled");
					return;
					
				case JFileChooser.ERROR_OPTION:
					relayText("Error");
					return;
				}
				File fileSelected = _dlgTextFile.getSelectedFile();
				TextMessageRelay relay = getMessageOut();
				IntegerCounter counter = new IntegerCounter();
				XResult result = TextReaderMethods.forEachLine(fileSelected,
						s -> {
							if (!s.isBlank() && s.charAt(0) != '#') {
								_panelSelect.addEntry(s);
								counter.increment();
							}
							
						});
				if (result.isValid()) {
					String strMessage = "read " + counter.getValue() 
						+ " lines from " + fileSelected.getName();
					_swDebugClass.write(ObsLevel.NOTICE, strMessage);
					relay.writeln(strMessage);
				} else {
					String strMessage = formatException(result.getException(), true);
					_swDebugClass.write(ObsLevel.ERROR, strMessage);
					relay.writeln(strMessage);
				}
				
			}
			
		});
	}

	@Override
	protected void doOnceRunning() {
		super.doOnceRunning();
		StringBuilder sb = new StringBuilder("center region component tree:");
    	ContainerMethods.walkComponentTree(_componentCenter, sb,
    			(d, n, b) -> {
    				String strIndent = StringMethods.makeIndent(d << 1);
    					b.append("\n  ");
    					b.append(strIndent);
    					b.append("component ");
    					b.append(n.getClass().getSimpleName());
    					if (n instanceof NameTokenTagPanel ntp) {
    						b.append(" nameTag=");
    						b.append(ntp.getNameTag());
    					}
    					b.append(" size (");
    					b.append(AWTFormatters.formatDimension(n.getSize()));
    					b.append(')');
    	});
    	sb.append("\nEnd");
		_swDebugClass.write(ObsLevel.DETAIL, sb.toString());
	}
	
	public class AllowCheckBoxActionListener
			implements ActionListener {
		private final String _strName;
		
		public AllowCheckBoxActionListener(String strControlName) {
			_strName = strControlName;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JCheckBox ckBox = (JCheckBox) e.getSource();
			NamedChoiceModel model = _gbListSelect.getModel();
			_swDebugClass.write(ObsLevel.DETAIL, () -> "check box for " + _strName
					+ ", selected=" + ckBox.isSelected());
			model.setChoiceEnabled(_strName, ckBox.isSelected());
		}
	}

	public static void main(String[] args) {
		XResult result = AppDebugMethods.readDebugPropertiesFromCurrentDir();
		if (!result.isValid()) {
			System.err.println("cannot load properties: " + result.getException().getMessage());
			System.exit(2);
		}
		AppDebugMethods.tryCreateLogFile(StringListMoverTest.class, PREFIX_LOG_FILE);
		AppDebugMethods.setAutoFlush(true);
		
		DebugConfigFileReader readerDebug = new DebugConfigFileReader();
		XResultInt resultRead = readerDebug.readConfigFile(DebugConfigNames.FILE_SWITCHES, FileExistence.Any);
		if (!resultRead.isValid()) {
			System.exit(2);
		}
		
		String strLaF = UIManager.getSystemLookAndFeelClassName();
		_swDebugClass.write(ObsLevel.INFO, "setting look&feel to " + strLaF);
		result = SwingUIMethods.setLookAndFeel(strLaF);
		if (!result.isValid()) {
			_swDebugClass.write(ObsLevel.ERROR, "cannot set look&feel: "
					+ result.getException().getMessage());
		}
		
		StringListMoverTest app = new StringListMoverTest();
		app.buildMenus();
		javax.swing.SwingUtilities.invokeLater(app);
	}
}
