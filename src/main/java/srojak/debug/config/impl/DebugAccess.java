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
package srojak.debug.config.impl;

import srojak.core.main.CoreCentral;
import srojak.core.main.CoreReflectionService;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.reflect.Reflectory;
import srojak.core.result.XResultClass;
import srojak.core.result.XResultOf;
import srojak.debug.DebugAccessConsFlags;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.debug.control.DebugControl;

/**
 * @author Stephen
 *
 */
public class DebugAccess 
		implements CoreObservers {

	protected static final PackageClassLocator LOC_DEBUG_CONTROL;
	protected static final PackageClassLocator LOC_DEBUG_CONTROL_EXT;
	protected static CoreReflectionService serviceReflect = CoreCentral.getReflectionService();
	private static final DebugSwitch _swDebugClass;	
	
	static {
		LOC_DEBUG_CONTROL = new PackageClassLocator("srojak.debug.impl", "DebugControlInstance");
		LOC_DEBUG_CONTROL_EXT = new PackageClassLocator("srojak.debug.control", "DebugControlInstanceExt");
	
		DebugNexus debug = new DebugNexus(DebugAccessConsFlags.CONS_NONE);
		Class<?> classThis = DebugAccess.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
		
	}
	
	public static void start() {
		_swDebugClass.traceEnter(TraceLevel.HIGH);
	}

	/*
	public static DebugControl getDebugControllerOldWay() {
		ReflectionProxy proxy = new ReflectionProxy(MethodHandles.lookup());
		XResultOf<DebugControl> result = serviceProxy.createInstanceOf(proxy, DebugControl.class, LOC_DEBUG_CONTROL);
		if (result.isValid()) {
			DebugControl control = result.getResult();
			OBS_LOG.record(ObsLevel.INFO, "controller is " + control.getClass().getName());
			return control;
		} else {
			Throwable t = result.getThrowable();
			OBS_LOG.recordThrowable(ObsLevel.ERROR, result.getActivity(), t, true);
			OBS_LOG.recordNoLocation(ObsLevel.ALERT, "cannot get debug controller instance");
			return null;
		}
	}
	*/
	
	public static DebugControl getDebugController(boolean bUseExt) {
		Reflectory reflectory = new DebugConfigReflectory();
		DebugControl control = null;
		
		XResultClass resultClass = serviceReflect.findClass(reflectory, bUseExt ? LOC_DEBUG_CONTROL_EXT : LOC_DEBUG_CONTROL);
		if (resultClass.isValid()) {
			Class<?> classTarget = resultClass.getResult();
			OBS_LOG.record(ObsLevel.INFO, "controller implementation is " + classTarget.getName());
			XResultOf<DebugControl> result = serviceReflect.createInstanceOf(reflectory, DebugControl.class, classTarget);
			if (result.isValid()) {
				control = result.getResult();
			} else {
				OBS_LOG.recordThrowable(ObsLevel.ERROR, result.getActivity(), result.getThrowable(), true);
			}
		} else {
			OBS_LOG.recordThrowable(ObsLevel.ERROR, resultClass.getActivity(), resultClass.getThrowable(), true);
		}
		if (control == null) {
			OBS_LOG.recordNoLocation(ObsLevel.ALERT, "cannot get debug controller instance");
		}
		return control;
	}
}
