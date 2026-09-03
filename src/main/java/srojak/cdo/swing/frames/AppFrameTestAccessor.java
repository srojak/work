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
package srojak.cdo.swing.frames;

import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JRootPane;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.SourceLocation;
import srojak.core.reflect.PackageClassLocator;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchKey;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class AppFrameTestAccessor {
	private final JFrame _frame;

	private static final DebugSwitch _swDebugClass;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = AppFrameTestAccessor.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public AppFrameTestAccessor(AppFrameContainer ctnrFrame) {
		Objects.requireNonNull(ctnrFrame, "ctnrFrame");
		SourceLocation caller = SourceLocation.caller();
		PackageClassLocator locSelf = _swDebugClass.getClassLocator();
		_swDebugClass.write(ObsLevel.NOTICE, locSelf.getFullName() + " called from " + caller);
		_frame = ctnrFrame.getFrame();
	}
	
	public JFrame getFrame() {
		return _frame;
	}
	
	public JRootPane getRootPane() {
		return _frame.getRootPane();
	}
}
