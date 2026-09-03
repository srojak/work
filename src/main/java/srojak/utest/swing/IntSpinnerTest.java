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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import srojak.cdo.AWTFormatters;
import srojak.cdo.ContainerMethods;
import srojak.cdo.GridBagConstraintsTool;
import srojak.cdo.swing.panels.IntegerSpinnerPanel;
import srojak.cdo.swing.panels.NameTokenTagPanel;
import srojak.core.NameToken;
import srojak.core.TextMessageRelay;
import srojak.core.observe.ObsLevel;
import srojak.core.result.XResult;
import srojak.core.tools.StringMethods;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigMethods;
import srojak.debug.config.DebugConfigNames;
import srojak.events.IntValueChangeEvent;
import srojak.events.IntValueChangeListener;
import srojak.numerics.IntervalType;
import srojak.numerics.intervals.IntervalInt;

/**
 * @author Stephen
 *
 */
public class IntSpinnerTest
		extends CommonTestAppFrame {
	private final JPanel _panelCenter;
	private IntegerSpinnerPanel _spLogText;
	private IntegerSpinnerPanel _spSingleRt;
	private IntegerSpinnerPanel _spPairedRts;
	private IntegerSpinnerPanel _spTown;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus(DebugNexus.CONS_NONE);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(IntSpinnerTest.class));
	}

	/**
	 * @param strAppName
	 */
	public IntSpinnerTest() {
		super("Spinner Test");
		
		TextMessageRelay relay = getMessageOut();
		
		_panelCenter = new JPanel(new GridBagLayout());
		_panelCenter.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (SwingUtilities.isRightMouseButton(e)) {
					Component component = SwingUtilities.getDeepestComponentAt(e.getComponent(),
							e.getX(), e.getY());
					Dimension dmSize = component.getSize();
					relay.writeln("component " + component.getClass().getSimpleName()
							+ " size=" + AWTFormatters.formatDimension(dmSize));
				}
			}
			
		});
		GridBagConstraintsTool builderGBC = new GridBagConstraintsTool();
		builderGBC.setFill(0);
		builderGBC.setGridSize(1,  1);
		builderGBC.setAnchor(GridBagConstraints.WEST);
		builderGBC.setGridPosition(0, 0);
		_panelCenter.add(new JLabel("Log Text"), builderGBC.snap());
		
		builderGBC.setGridPosition(1, 0);
    	SpinnerNumberModel modelSpin = defineModel(new IntervalInt(IntervalType.CLOSED, 8, 12), 10);
    	_spLogText = new IntegerSpinnerPanel(NameToken.factory("LogText"), modelSpin);
    	_spLogText.addIntValueChangeListener(new IntValueChangeListener() {

			@Override
			public void update(IntValueChangeEvent event) {
				relay.writeln("Log text spinner changed to " + event.getValue());
			}
    		
    	});
    	/*
    	_spLogText.setPreferredSize(
    			new Dimension(200, SpinnerPromptInteger.STD_SPINNER_HEIGHT));
    	*/
    	_panelCenter.add(_spLogText, builderGBC.snap());
     	
		builderGBC.setGridPosition(0, 1);
		_panelCenter.add(new JLabel("Single Route"), builderGBC.snap());
		
		builderGBC.setGridPosition(1, 1);
    	modelSpin = defineModel(new IntervalInt(IntervalType.CLOSED, 10, 16), 12);
    	_spSingleRt = new IntegerSpinnerPanel(NameToken.factory("SingleRoute"), modelSpin);
    	_spSingleRt.addIntValueChangeListener(new IntValueChangeListener() {

			@Override
			public void update(IntValueChangeEvent event) {
				relay.writeln("Single route spinner changed to " + event.getValue());
			}
    		
    	});
    	_panelCenter.add(_spSingleRt, builderGBC.snap());
    	
		builderGBC.setGridPosition(0, 2);
		_panelCenter.add(new JLabel("Paired Routes"), builderGBC.snap());
		
		builderGBC.setGridPosition(1, 2);
    	modelSpin = defineModel(new IntervalInt(IntervalType.CLOSED, 8, 12), 10);
    	_spPairedRts = new IntegerSpinnerPanel(NameToken.factory("PairRoutes"), modelSpin);
    	_spPairedRts.addIntValueChangeListener(new IntValueChangeListener() {

			@Override
			public void update(IntValueChangeEvent event) {
				relay.writeln("Paired route spinner changed to " + event.getValue());
			}
    		
    	});
    	_panelCenter.add(_spPairedRts, builderGBC.snap());
    	
		builderGBC.setGridPosition(0, 3);
		_panelCenter.add(new JLabel("Town Names"), builderGBC.snap());
		
		builderGBC.setGridPosition(1, 3);
    	modelSpin = defineModel(new IntervalInt(IntervalType.CLOSED, 8, 10), 8);
    	_spTown = new IntegerSpinnerPanel(NameToken.factory("Towns"), modelSpin);
    	_spTown.addIntValueChangeListener(new IntValueChangeListener() {

			@Override
			public void update(IntValueChangeEvent event) {
				relay.writeln("Town name spinner changed to " + event.getValue());
			}
    		
    	});
     	_panelCenter.add(_spTown, builderGBC.snap());
      	
    	addComponentToCenter(_panelCenter);
	}
	
	private static SpinnerNumberModel defineModel(IntervalInt range, int valueStart) {
		return new SpinnerNumberModel(valueStart, range.getMinimumValue(),
				range.getMaximumValue(), 1);
	}

	@Override
	protected void doOnceRunning() {
		super.doOnceRunning();
		TextMessageRelay relay = getMessageOut();
		ContainerMethods.walkComponentTree(_panelCenter,
				(d, n) -> {
    				String strIndent = StringMethods.makeIndent(d << 1);
    				relay.writeln(strIndent
    					+ "component " + n.getClass().getSimpleName());			
		});
		StringBuilder sb = new StringBuilder("application panels:");
    	ContainerMethods.walkComponentTree(_panelCenter, sb, JPanel.class,
    			(d, n, b) -> {
    				String strIndent = StringMethods.makeIndent(d << 1);
    					b.append("\n");
    					b.append(strIndent);
    					b.append("panel ");
    					b.append(n.getClass().getSimpleName());
    					if (n instanceof NameTokenTagPanel ntp) {
    						b.append(" nameTag=");
    						b.append(ntp.getNameTag());
    					}
    					b.append(" size (");
    					b.append(AWTFormatters.formatDimension(n.getSize()));
    					b.append(')');
    	});
		_swDebugClass.write(ObsLevel.DETAIL, sb.toString());
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
		AppDebugMethods.tryCreateLogFile(StringListMoverTest.class);
		AppDebugMethods.setAutoFlush(true);
		result = DebugConfigMethods.readConfigFileTwoPass(DebugConfigNames.FILE_SWITCHES, false);
		if (!result.isValid()) {
			System.exit(2);
		}
		
		IntSpinnerTest app = new IntSpinnerTest();
		app.buildMenus();
		javax.swing.SwingUtilities.invokeLater(app);
	}

}
