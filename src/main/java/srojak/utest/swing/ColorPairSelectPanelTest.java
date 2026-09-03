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

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;

import srojak.cdo.AWTFormatters;
import srojak.cdo.ColorPair;
import srojak.cdo.swing.models.ColorPairBoxSelectModel;
import srojak.cdo.swing.panels.ColorPairBoxSelectPanel;
import srojak.cdo.swing.panels.NameTokenTagPanel;
import srojak.core.NameToken;
import srojak.core.TextMessageRelay;
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
public class ColorPairSelectPanelTest 
		extends CommonTestAppFrame {
	private final ColorPairBoxSelectPanel _panelColorSelect;
	private final ColorPairBoxSelectModel _modelColorSelect;
	private final NameTokenTagPanel _panelChosen;
	private final NameTokenTagPanel _panelCenter;
	private final JLabel _labelText;
		
	public static final NameToken ClassToken;
	private static final DebugSwitch _swDebugClass;
	private static final String PREFIX_LOG_FILE = "ColorPS";
	
	static {
		Class<?> classThis = ColorPairSelectPanelTest.class;
		ClassToken = NameToken.classNameFactory(classThis);
		DebugNexus debug = new DebugNexus(DebugNexus.CONS_NONE);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public ColorPairSelectPanelTest() {
		super("ColorPairSelectPanel Test");
		
		_panelCenter = new NameTokenTagPanel(NameToken.factory(ClassToken, "center"));
		_panelCenter.add(Box.createHorizontalStrut(10));
		_panelCenter.add(new JLabel("Choose color"));
		_panelCenter.add(Box.createHorizontalStrut(20));
		
		_panelColorSelect = new ColorPairBoxSelectPanel(NameToken.factory("ColorSelect"));
		_modelColorSelect = _panelColorSelect.getModelAs(); 
		_panelColorSelect.setRectangleSize(new Dimension(40, 40));
		_modelColorSelect.setChoices(List.of(
				new ColorPair(Color.WHITE, Color.BLACK),
				new ColorPair(Color.BLACK, Color.WHITE),
				new ColorPair(Color.YELLOW, Color.BLACK),
				new ColorPair(Color.BLUE, Color.WHITE),
				new ColorPair(Color.GREEN, Color.BLACK),
				new ColorPair(Color.GREEN.darker(), Color.WHITE),
				new ColorPair(Color.RED, Color.WHITE),
				new ColorPair(Color.CYAN, Color.WHITE)));
		_panelCenter.add(_panelColorSelect);
		_panelCenter.add(Box.createHorizontalStrut(30));
		ColorPair pairChosen = _modelColorSelect.getSelection();
		
		_panelChosen = new NameTokenTagPanel(NameToken.factory(ClassToken, "Choice"));
		_panelChosen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		Dimension dmSize = new Dimension(60, 60);
		_panelChosen.setMinimumSize(dmSize);
		_panelChosen.setPreferredSize(dmSize);
		_panelChosen.setBackground(pairChosen.getBackgroundColor());
		_panelCenter.add(_panelChosen);
		
		_labelText = new JLabel("40");
		_labelText.setMinimumSize(_labelText.getPreferredSize());
		_labelText.setBackground(pairChosen.getBackgroundColor());
		_labelText.setForeground(pairChosen.getForegroundColor());
		_panelChosen.add(_labelText);
		
		_panelCenter.add(Box.createHorizontalStrut(100));
		
		addComponentToCenter(_panelCenter);
		
		_modelColorSelect.addObjectValueChangeListener(new ObjectValueChangeListener() {

			@Override
			public void update(ObjectValueChangeEvent event) {
				TextMessageRelay relay = getMessageOut();
				ColorPair pairChosen = _modelColorSelect.getSelection();
				_panelChosen.setBackground(pairChosen.getBackgroundColor());
				_labelText.setBackground(pairChosen.getBackgroundColor());
				_labelText.setForeground(pairChosen.getForegroundColor());
				relay.writeln("background color changed to " 
						+ AWTFormatters.formatColor(pairChosen.getBackgroundColor()));
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
		AppDebugMethods.tryCreateLogFile(ColorPairSelectPanelTest.class, PREFIX_LOG_FILE);
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
		
		ColorPairSelectPanelTest app = new ColorPairSelectPanelTest();
		app.buildMenus();
		javax.swing.SwingUtilities.invokeLater(app);
	}

}
