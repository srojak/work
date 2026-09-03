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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import srojak.cdo.swing.components.CompassRoseControl;
import srojak.cdo.swing.models.CompassControlModel;
import srojak.core.NameToken;
import srojak.core.TextMessageRelay;
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
import srojak.numerics.compass.CompassPoint;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;

/**
 * @author Stephen
 *
 */
public class CompassRoseControlTest 
		extends CommonTestAppFrame {
	private final CompassRoseControl _rose1;
	private final CompassControlModel _model1;
	private final CompassRoseControl _rose2;
	private final CompassControlModel _model2;
	private final CompassRoseControl _rose3;
	private final CompassControlModel _model3;
	private final CompassRoseControl _rose4;
	private final CompassControlModel _model4;
	private final CompassRoseControl _rose5;
	private final CompassControlModel _model5;

	private static final String PREFIX_LOG_FILE = "CRose";
	public static final NameToken ClassToken;
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = CompassRoseControlTest.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	/**
	 * @param strAppName
	 */
	public CompassRoseControlTest() {
		super("CompassRoseControl Test");
		
		_rose1 = new CompassRoseControl(NameToken.factory(ClassToken, "Rose1"));
		_rose1.setPreferredSize(new Dimension(80, 80));
		_model1 = _rose1.getModel();
		_model1.setActionCommand("Rose1");
		_model1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TextMessageRelay relayText = getMessageOut();
				CompassPoint cpoint = _model1.getCurrentValue();
				relayText.writeln("Rose 1 set to " + cpoint);
			}
			
		});
		
		_rose2 = new CompassRoseControl(NameToken.factory(ClassToken, "Rose2"));
		_rose2.setPreferredSize(new Dimension(80, 80));
		_rose2.setArrowColor(Color.GREEN.darker());
		_model2 = _rose2.getModel();
		_model1.setActionCommand("Rose2");
		_model2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TextMessageRelay relayText = getMessageOut();
				CompassPoint cpoint = _model2.getCurrentValue();
				relayText.writeln("Rose 2 set to " + cpoint);
			}
		
		});
		
		_rose3 = new CompassRoseControl(NameToken.factory(ClassToken, "Rose3"));
		_rose3.setPreferredSize(new Dimension(60, 60));
		_rose3.setLabelPoints(false);
		_model3 = _rose3.getModel();
		_model1.setActionCommand("Rose3");
		_model3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TextMessageRelay relayText = getMessageOut();
				CompassPoint cpoint = _model3.getCurrentValue();
				relayText.writeln("Rose 3 set to " + cpoint);
			}
		
		});
		
		_rose4 = new CompassRoseControl(NameToken.factory(ClassToken, "Rose4"));
		_rose4.setPreferredSize(new Dimension(120, 80));
		_rose4.setBorder(new LineBorder(Color.BLUE, 2));
		_rose4.setBackground(Color.WHITE);
		_model4 = _rose4.getModel();
		_model1.setActionCommand("Rose4");
		_model4.addActionListener(new ControlActionListener());
		
		_rose5 = new CompassRoseControl(NameToken.factory(ClassToken, "Rose5"));
		_rose5.setPreferredSize(new Dimension(80, 120));
		_rose5.setBorder(new LineBorder(Color.BLUE, 2));
		_rose5.setBackground(Color.YELLOW);
		_model5 = _rose5.getModel();
		_model1.setActionCommand("Rose5");
		_model5.addActionListener(new ControlActionListener());
		
		JPanel panelCenter = new JPanel();
		panelCenter.add(_rose1);
		panelCenter.add(_rose2);
		panelCenter.add(_rose3);
		panelCenter.add(_rose4);
		panelCenter.add(_rose5);
		
		addComponentToCenter(panelCenter);
		
		useCommonAppIcon();
	}

	@Override
	protected void doBeforeRendering() {
		super.doBeforeRendering();
		Font font = _rose2.getFont();
		TextMessageRelay relayText = getMessageOut();
		relayText.writeln("Font is " + font.getFontName() + ", size=" + font.getSize());
		_rose2.setFont(font.deriveFont(Font.BOLD));
	}

	@Override
	protected void doOnceRunning() {
		super.doOnceRunning();
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
		AppDebugMethods.tryCreateLogFile(CompassRoseControlTest.class, PREFIX_LOG_FILE);
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
		
		CompassRoseControlTest app = new CompassRoseControlTest();
		app.buildMenus();
		javax.swing.SwingUtilities.invokeLater(app);
	}

	private class ControlActionListener
			implements ActionListener {
		
		public ControlActionListener() {
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			TextMessageRelay relayText = getMessageOut();
			CompassControlModel model = (CompassControlModel) e.getSource();
			NameToken token = model.getNameTag();
			CompassPoint cpoint = model.getCurrentValue();
			relayText.writeln("Rose " + token.getName() + " set to " + cpoint);
		}
	}
}
