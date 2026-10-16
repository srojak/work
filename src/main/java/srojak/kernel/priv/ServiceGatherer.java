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
package srojak.kernel.priv;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.reflect.PackageClassLocator;
import srojak.kernel.KernelProperties;
import srojak.kernel.KernelServiceException;
import srojak.kernel.services.ServiceMetaProvider;

/**
 * @author Stephen
 *
 */
public class ServiceGatherer
		implements CoreObservers {
	private final KernelProperties _properties;
	private final boolean _bVerbose;

	private final String CLASS_NAME = "ServiceEntry";
	private final ObservedActivity ACTIVITY_DEFINE_CLASS = new SingleActivity("define class");
	private static final String KEY_VERBOSE = "gatherer.verbose";
	
	/**
	 * 
	 */
	public ServiceGatherer() {
		_properties = KernelStore._propsKernel;
		_bVerbose = _properties.evalBooleanProperty(KEY_VERBOSE, false);
	}
	
	public boolean isVerbose() {
		return _bVerbose;
	}

	public boolean gather(boolean bDeferResolve) {
		ModuleLayer layerBoot = ModuleLayer.boot();
		List<Module> listModules = layerBoot.modules().stream().filter(m -> m.getName().startsWith("srojak.kernel")).toList();
		Module moduleSelf = getClass().getModule();
		Iterator<Module> iterator = listModules.iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (isVerbose()) {
				OBS_LOG.record(ObsLevel.INFO, "evaluating module " + module.getName());
			}
			if (module == moduleSelf) {
				if (isVerbose()) {
					OBS_LOG.record(ObsLevel.INFO, "this is my module");
				}
				continue;
			}
			PackageClassLocator locator = new PackageClassLocator(module.getName(), CLASS_NAME);
			Class<?> classMeta = Class.forName(module, locator.getFullName());
			if (classMeta != null) {
				try {
					Object obj = classMeta.getDeclaredConstructor().newInstance();
					ServiceMetaProvider meta = (ServiceMetaProvider) obj;
					List<Class<?>> listServices = meta.services();
					listServices.stream().forEach(s -> addServiceClass(s));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException exc) {
					OBS_LOG.recordThrowable(ObsLevel.ERROR, ACTIVITY_DEFINE_CLASS, exc, false);
					return false;
				}
				
			}
		}
		
		if (!bDeferResolve) {
			try {
				KernelStore._services.resolvePending();
			} catch (KernelServiceException exc) {
				OBS_LOG.recordThrowable(ObsLevel.ERROR, ACTIVITY_DEFINE_CLASS, exc, true);
				OBS_LOG.record(ObsLevel.ERROR, "cannot resolve service " + exc.getServiceName());
				return false;
			}
		}
		
		return true;
		
	}
	
	private void addServiceClass(Class<?> classService) {
		if (isVerbose()) {
			OBS_LOG.record(ObsLevel.INFO, "adding class " + classService.getName());
		}
		KernelStore._services.define(classService);
	}
}
