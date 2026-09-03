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

import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.filechooser.FileNameExtensionFilter;

import srojak.cdo.GridBagConstraintsTool;
import srojak.cdo.swing.interact.ComponentEnablingFacade;
import srojak.cdo.swing.models.SingleFileSelectModel;
import srojak.cdo.swing.panels.FileSelectPanel;
import srojak.cdo.swing.panels.GroupBoxPanel;
import srojak.cdo.swing.panels.NameTokenTagPanel;
import srojak.core.NameToken;
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
public class ButtonEnablingTest 
		extends CommonTestAppFrame {
	private final NameTokenTagPanel _panelCenter;
	private final GroupBoxPanel _panelSwitches;
	private final FileSelectPanel _panelFileSelect;
	private final ComponentEnablingFacade _facadeFileSelect;
	private final SingleFileSelectModel _modelFileSelect;
	private final ButtonModel _modelBnBrowse;
	private final ButtonModel _modelBnEnablePanel;
	private final ButtonModel _modelBnEnableLocal;
	private final ButtonModel _modelBnEnableBrowse;
	
	
	public static final NameToken ClassToken;
	private static final String PREFIX_LOG_FILE = "BnEnab";
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = ButtonEnablingTest.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * @param strAppName
	 */
	public ButtonEnablingTest() {
		super("ButtonEnablingTest");
		
		_panelCenter = new NameTokenTagPanel(NameToken.factory(ClassToken, "Center"),
				new GridBagLayout());
		_panelSwitches = new GroupBoxPanel(NameToken.factory(ClassToken, "Switches"));
		_panelSwitches.setGroupBoxTitle("Switches");
		_panelFileSelect = new FileSelectPanel(NameToken.factory(ClassToken, "FileSelect"));
		_panelFileSelect.setTextColumns(50);
		_facadeFileSelect = _panelCenter.createEnablingFacadeFor(_panelFileSelect);
		_modelFileSelect = _panelFileSelect.getModel();
		_modelFileSelect.setStartingDirectory(Paths.get("."));
		_modelFileSelect.addFileFilter(new FileNameExtensionFilter("Text files", "txt", "log"));
		_modelBnBrowse = _panelFileSelect.getBrowseButtonModel();
		JCheckBox ckEnablePanel = new JCheckBox("Enable FileSelect Panel");
		_modelBnEnablePanel = ckEnablePanel.getModel();
		JCheckBox ckEnableLocal = new JCheckBox("LocalEnable FileSelect");
		_modelBnEnableLocal = ckEnableLocal.getModel();
		JCheckBox ckEnableBrowse = new JCheckBox("Enable Browse button through model");
		_modelBnEnableBrowse = ckEnableBrowse.getModel();
		
		GridBagConstraintsTool toolGBC = new GridBagConstraintsTool();
		toolGBC.setFill(0);
		toolGBC.setGridSize(1, 1);
		toolGBC.setAnchor(GridBagConstraints.WEST);
		toolGBC.setInsets(5);
		toolGBC.setGridPosition(0, 0);
		_panelSwitches.setLayout(new BoxLayout(_panelSwitches, BoxLayout.Y_AXIS));
		_panelSwitches.add(ckEnablePanel);
		_panelSwitches.add(ckEnableLocal);
		_panelSwitches.add(ckEnableBrowse);
		_panelCenter.add(_panelSwitches, toolGBC.snap());
		
		toolGBC.setGridPosition(1, 0);
		_panelCenter.add(_panelFileSelect, toolGBC.snap());
		
		addComponentToCenter(_panelCenter);
		
		_modelBnEnablePanel.setSelected(_facadeFileSelect.isEnabled());
		_modelBnEnableLocal.setSelected(_facadeFileSelect.isLocalEnabled());
		_modelBnEnableBrowse.setSelected(_modelBnBrowse.isEnabled());
		
		_modelBnEnablePanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_facadeFileSelect.setEnabled(_modelBnEnablePanel.isSelected());
			}
			
		});
		
		_modelBnEnableLocal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_facadeFileSelect.setLocalEnabled(_modelBnEnableLocal.isSelected());
			}
			
		});
		
		_modelBnEnableBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_modelBnBrowse.setEnabled(_modelBnEnableBrowse.isSelected());
			}
			
		});
		
		useCommonAppIcon();
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
		AppDebugMethods.tryCreateLogFile(ButtonEnablingTest.class, PREFIX_LOG_FILE);
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
		
		ButtonEnablingTest app = new ButtonEnablingTest();
		app.buildMenus();
		javax.swing.SwingUtilities.invokeLater(app);
	}

}
