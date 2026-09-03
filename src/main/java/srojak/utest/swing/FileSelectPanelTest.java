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
import java.nio.file.Paths;

import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import srojak.cdo.GridBagConstraintsTool;
import srojak.cdo.events.TextContentEvent;
import srojak.cdo.events.TextContentListener;
import srojak.cdo.events.TextReferents;
import srojak.cdo.swing.FileChooserSelectionMode;
import srojak.cdo.swing.SwingUIMethods;
import srojak.cdo.swing.models.SingleFileSelectModel;
import srojak.cdo.swing.panels.FileSelectPanel;
import srojak.cdo.swing.panels.NameTokenTagPanel;
import srojak.core.NameToken;
import srojak.core.TextMessageRelay;
import srojak.core.events.ActionStatusCodes;
import srojak.core.events.ActionStatusEvent;
import srojak.core.events.ActionStatusListener;
import srojak.core.events.StateChangeCodes;
import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigFileReader;
import srojak.debug.config.DebugConfigNames;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;

/**
 * @author Stephen
 *
 */
public class FileSelectPanelTest 
		extends CommonTestAppFrame
		implements ActionStatusCodes, TextReferents {
	private final NameTokenTagPanel _panelCenter;
	private final ButtonModel _modelIncDirs;
	private final FileSelectPanel _panelSelect;
	private final SingleFileSelectModel _modelSelect;
	private ButtonModel _modelItemGetFileName;
	
	public static final NameToken ClassToken;
	private static final String PREFIX_LOG_FILE = "FSelect";
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = FileSelectPanelTest.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * @param strAppName
	 */
	public FileSelectPanelTest() {
		super("FileSelectPanel Test");
		
		_panelCenter = new NameTokenTagPanel(NameToken.factory(ClassToken, "Center"),
				new GridBagLayout());
		GridBagConstraintsTool toolGBC = new GridBagConstraintsTool();
		toolGBC.setFill(0);
		toolGBC.setGridSize(1,  1);
		toolGBC.setAnchor(GridBagConstraints.WEST);
		toolGBC.setInsets(5);
		toolGBC.setGridPosition(0, 0);
		JCheckBox ckIncDirs = new JCheckBox("Allow selection of directories");
		_modelIncDirs = ckIncDirs.getModel();
		_panelCenter.add(ckIncDirs, toolGBC.snap());
		
		toolGBC.setGridPosition(0, 1);
		_panelSelect = new FileSelectPanel(NameToken.factory(ClassToken, "FileSelect"));
		_panelSelect.setTextColumns(50);
		_modelSelect = _panelSelect.getModel();
		_modelSelect.setStartingDirectory(Paths.get("."));
		_modelSelect.addFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
		_modelSelect.addFileFilter(new FileNameExtensionFilter("XML Schemata", "xsd"));
		_modelSelect.addFileFilter(new FileNameExtensionFilter("Text files", "txt", "log"));
		_panelCenter.add(_panelSelect, toolGBC.snap());
		
		addComponentToCenter(_panelCenter);
		
		_modelSelect.addActionStatusListener(new ActionStatusListener() {

			@Override
			public void statusChanged(ActionStatusEvent event) {
				if (event.getReferent() == StateChangeCodes.SC_OPERATION) {
					if (event.getStatus() == ASTATUS_COMPLETED) {
						setStatusText("Done");
					} else if (event.getStatus() == ASTATUS_CANCELLED) {
						setStatusText("Cancelled");
					}
				}
			}
			
		});
		
		_modelIncDirs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_modelIncDirs.isSelected()) {
					_modelSelect.setFileSelectionMode(FileChooserSelectionMode.FILES_AND_DIRECTORIES);					
				} else {
					_modelSelect.setFileSelectionMode(FileChooserSelectionMode.FILES_ONLY);
				}
			}
			
		});
		
		useCommonAppIcon();
	}

	@Override
	protected void addItemsToTestMenu(JMenu menuTest) {
		super.addItemsToTestMenu(menuTest);
		
		JMenuItem itemMenu = new JMenuItem("Get File Name");
		_modelItemGetFileName = itemMenu.getModel();
		menuTest.add(itemMenu);
		_modelItemGetFileName.setEnabled(false);
		_modelItemGetFileName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TextMessageRelay relay = getMessageOut();
				String strFileName = _modelSelect.getFileName();
				relay.writeln("file name: " + strFileName);
			}
			
		});
		
		_modelSelect.addTextContentListener(new TextContentListener() {

			@Override
			public void textChanged(TextContentEvent event) {
				if (event.getReferent() == TXR_MODEL) {
					_modelItemGetFileName.setEnabled(!event.isTextNullOrEmpty());
				}
			}
			
		});
		
		itemMenu = new JMenuItem("Get File Name Size");
		menuTest.add(itemMenu);
		itemMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TextMessageRelay relay = getMessageOut();
				String strFileName = _modelSelect.getFileName();
				if (strFileName == null) {
					relay.writeln("file name is null");
				} else {
					relay.writeln("file name length=" + strFileName.length());
				}
			}
			
		});
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XResult result = AppDebugMethods.readDebugPropertiesFromCurrentDir();
		if (!result.isValid()) {
			System.err.println("cannot load properties: " + result.getException().getMessage());
			System.exit(2);
		}
		AppDebugMethods.tryCreateLogFile(FileSelectPanelTest.class, PREFIX_LOG_FILE);
		AppDebugMethods.setAutoFlush(true);
		DebugConfigFileReader readerDebug = new DebugConfigFileReader();
		XResultInt resultRead = readerDebug.readConfigFile(DebugConfigNames.FILE_SWITCHES, FileExistence.Any);
		if (!resultRead.isValid()) {
			_swDebugClass.writeException(ObsLevel.ERROR, resultRead.getException(), true);
		} else if (readerDebug.hasParseErrors()) {
			var listErrors = readerDebug.getParseErrors();
			ObservationCollector collector = _swDebugClass.createCollector(ObsLevel.NOTICE);
			collector.append("has ");
			collector.append(listErrors.size());
			collector.append(" parse errors");
			for(XmlStreamParseErrorDescr error : listErrors) {
				collector.append("\n  ");
				collector.append(error);
			}
			collector.alsoWriteTo(System.err);
			collector.commit();
		}
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

		FileSelectPanelTest app = new FileSelectPanelTest();
		start(app);
	}
}
