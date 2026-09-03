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

import javax.swing.JComponent;

import srojak.cdo.AWTFormatters;
import srojak.cdo.ContainerMethods;
import srojak.cdo.swing.frames.AppFrameContainer;
import srojak.cdo.swing.frames.AppFrameTestAccessor;
import srojak.cdo.swing.panels.NameTokenTagPanel;
import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.core.tools.StringMethods;
import srojak.debug.AppDebugMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.config.DebugConfigFileReader;
import srojak.debug.config.DebugConfigNames;

/**
 * @author Stephen
 *
 */
public class ComponentNameTest 
		extends CommonTestAppFrame {
	
	private static final DebugSwitch _swDebugClass;
	private static final String PREFIX_LOG_FILE = "CNmTest";
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = ComponentNameTest.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * @param strAppName
	 */
	public ComponentNameTest() {
		super("ComponentName Test");
		
		useCommonAppIcon();
	}

	@Override
	protected void doOnceRunning() {
		super.doOnceRunning();
		StringBuilder sb = new StringBuilder("center region component tree:");
		AppFrameContainer frame = getAppFrameContainer();
		AppFrameTestAccessor accessor = new AppFrameTestAccessor(frame);
		JComponent root = accessor.getRootPane();
    	ContainerMethods.walkComponentTree(root, sb,
    			(d, n, b) -> {
    				String strIndent = StringMethods.makeIndent(d << 1);
    					b.append("\n  ");
    					b.append(strIndent);
    					b.append("component ");
    					b.append(n.getName());
    					if (n instanceof NameTokenTagPanel ntp) {
    						b.append(" nameTag=");
    						b.append(ntp.getNameTag());
    					}
    					b.append(" size (");
    					b.append(AWTFormatters.formatDimension(n.getSize()));
    					b.append(')');
    	});
    	sb.append("\nEnd");
		_swDebugClass.write(ObsLevel.INFO, sb.toString());
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
		AppDebugMethods.tryCreateLogFile(ComponentNameTest.class, PREFIX_LOG_FILE);
		AppDebugMethods.setAutoFlush(true);
		
		DebugConfigFileReader readerDebug = new DebugConfigFileReader();
		XResultInt resultRead = readerDebug.readConfigFile(DebugConfigNames.FILE_SWITCHES, FileExistence.Any);
		if (!resultRead.isValid()) {
			System.exit(2);
		}
		
		ComponentNameTest app = new ComponentNameTest();
		app.buildMenus();
		javax.swing.SwingUtilities.invokeLater(app);
	}
}
