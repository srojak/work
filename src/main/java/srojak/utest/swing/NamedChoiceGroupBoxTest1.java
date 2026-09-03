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
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import srojak.cdo.GridBagConstraintsTool;
import srojak.cdo.swing.models.NamedChoiceModel;
import srojak.cdo.swing.panels.NameTokenTagPanel;
import srojak.cdo.swing.panels.NamedChoiceGroupBoxPanel;
import srojak.core.NameToken;
import srojak.core.containers.NamedAndLabeledInt;
import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigFileReader;
import srojak.debug.config.DebugConfigNames;
import srojak.events.ObjectValueChangeEvent;
import srojak.events.ObjectValueChangeListener;

/**
 * @author Stephen
 *
 */
public class NamedChoiceGroupBoxTest1
		extends CommonTestAppFrame {
	private final NameTokenTagPanel _panelCenter;
	private final JTextField _txCurrent;
	private final NamedChoiceGroupBoxPanel _panelChoice;
	
	public static final NameToken ClassToken;
	private static final DebugSwitch _swDebugClass;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = NamedChoiceGroupBoxTest1.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	/**
	 * 
	 */
	public NamedChoiceGroupBoxTest1() {
		super("NamedChoiceGroupBox Test 1");
		_panelCenter = new NameTokenTagPanel(NameToken.factory(ClassToken, "Center"),
				new GridBagLayout());
		GridBagConstraintsTool toolGBC = new GridBagConstraintsTool();
		toolGBC.setFill(0);
		toolGBC.setGridSize(1,  1);
		toolGBC.setAnchor(GridBagConstraints.WEST);
		toolGBC.setInsets(5);
		toolGBC.setGridPosition(0, 0);
		JLabel label = new JLabel("Current choice:");
		_panelCenter.add(label, toolGBC.snap());
		
		toolGBC.setGridPosition(1, 0);
		_txCurrent = new JTextField(5);
		_txCurrent.setText("0");
		_panelCenter.add(_txCurrent, toolGBC.snap());
		
		toolGBC.setGridSize(2, 1);
		toolGBC.setGridPosition(0, 1);
		_panelChoice = new NamedChoiceGroupBoxPanel(NameToken.factory(ClassToken, "Choices"), 3);
		_panelChoice.setGroupBoxTitle("Ordinals");
		NamedChoiceModel modelChoice = _panelChoice.getModel();
		modelChoice.setChoices(List.of(new NamedAndLabeledInt("#1", "First", 1),
				new NamedAndLabeledInt("Second", 2),
				new NamedAndLabeledInt("Third", 3),
				new NamedAndLabeledInt("Fourth", 4),
				new NamedAndLabeledInt("Fifth", 5),
				new NamedAndLabeledInt("Sixth", 6)));
		_panelCenter.add(_panelChoice, toolGBC.snap());
		modelChoice.addObjectValueChangeListener(new ObjectValueChangeListener () {

			@Override
			public void update(ObjectValueChangeEvent event) {
				NamedAndLabeledInt ni = event.getValueAs();
				_txCurrent.setText(String.valueOf(ni.getValue()));
			}
			
		});
		
		addComponentToCenter(_panelCenter);
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
		result = AppDebugMethods.tryCreateLogFile(NamedChoiceGroupBoxTest1.class);
		AppDebugMethods.setAutoFlush(true);
		
		DebugConfigFileReader readerDebug = new DebugConfigFileReader();
		XResultInt resultRead = readerDebug.readConfigFile(DebugConfigNames.FILE_SWITCHES, FileExistence.Any);
		if (!resultRead.isValid()) {
			_swDebugClass.writeException(ObsLevel.ERROR, resultRead.getException(), true);
		} else if (readerDebug.hasParseErrors()) {
			analyzeDebugConfigParse(readerDebug);
		}
		if (!resultRead.isValid()) {
			System.exit(2);
		}
		
		NamedChoiceGroupBoxTest1 app = new NamedChoiceGroupBoxTest1();
		app.buildMenus();
		javax.swing.SwingUtilities.invokeLater(app);
	}

}
