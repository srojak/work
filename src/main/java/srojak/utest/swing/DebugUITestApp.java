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
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import srojak.cdo.swing.SwingUIMethods;
import srojak.cdo.swing.event.WorkerCompletionListener;
import srojak.cdo.swing.workers.GetAvailableFontsWorker;
import srojak.core.TextMessageRelay;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.StackCapture;
import srojak.core.result.XResult;
import srojak.debug.AppDebugMethods;
import srojak.debug.ClassDebugOptions;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigMethods;
import srojak.debug.config.DebugConfigNames;

/**
 * @author Stephen
 *
 */
public class DebugUITestApp
		extends CommonTestAppFrame {
	
	
	private static final DebugSwitch _swDebugClass;
	private static final ClassDebugOptions _optsDebug;
	private static final String OPTIONS_USE_SYS_LF = "useSystemLF";
	private static final String PREFIX_LOG_FILE = "DebugUI";
	private static boolean SHOW_MODULE = false;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = DebugUITestApp.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
		_optsDebug = debug.getClassOptions(classThis);
	}
	
	/**
	 * @param strAppName
	 */
	public DebugUITestApp() {
		super("DebugUI Test");
		
		useCommonAppIcon();
	}

	@Override
	protected void addMenus() {
		super.addMenus();
		
		JMenu menu = new JMenu("Trace");
		JMenuItem itemMenu = new JMenuItem("Full Stack Trace");
		menu.add(itemMenu);
		itemMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TextMessageRelay relay = getMessageOut();
				StackCapture stk = new StackCapture(2);
				relay.writeln("Stack trace:");
				stk.walkNumbered((s, n) -> {
					relay.writeln("  " + String.format("%3d ", n) + s);
					if (SHOW_MODULE) {
						relay.writeln("    " + s.getModuleName());
					}
				});
			}
			
		});
		addMenu(menu);
		
		menu = new JMenu("Fonts");
		 itemMenu = new JMenuItem("List Available Fonts");
		menu.add(itemMenu);
		itemMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TextMessageRelay relay = getMessageOut();
				GetAvailableFontsWorker worker = new GetAvailableFontsWorker();
				worker.addPropertyChangeListener(new WorkerCompletionListener() {

					@Override
					protected void workerCompleted() {
						try {
							List<String> list = worker.get();
							relay.writeln("Available fonts:");
							for (String strFont : list) {
								relay.writeln(strFont);
							}
						} catch (Exception exc) {
							relay.writeln("exception processing: " + exc.getMessage());
						}
					}
					
				});
				relay.writeln("Processing in background");
				worker.execute();
			}
		});
		addMenu(menu);
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
		result = AppDebugMethods.tryCreateLogFile(DebugUITestApp.class, PREFIX_LOG_FILE);
		AppDebugMethods.setAutoFlush(true);
		
		result = DebugConfigMethods.readConfigFileTwoPass(DebugConfigNames.FILE_SWITCHES, false);
		if (!result.isValid()) {
			System.exit(2);
		}
		
		int nChangeSysLF = _optsDebug.getOptionValue(OPTIONS_USE_SYS_LF);
		if (nChangeSysLF != 0) {
			String strLaF = UIManager.getSystemLookAndFeelClassName();
			_swDebugClass.write(ObsLevel.INFO, "setting look&feel to " + strLaF);
			result = SwingUIMethods.setLookAndFeel(strLaF);
			if (!result.isValid()) {
				_swDebugClass.write(ObsLevel.ERROR, "cannot set look&feel: "
						+ result.getException().getMessage());
			}
		}
		
		DebugUITestApp app = new DebugUITestApp();
		app.buildMenus();
		javax.swing.SwingUtilities.invokeLater(app);
	}

}
