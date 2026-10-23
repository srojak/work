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
package srojak.core.main;

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import srojak.core.modules.ModuleLocator;
import srojak.core.modules.ModuleName;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.TraceLevel;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.reflect.ParameterTypeSet;
import srojak.core.reflect.ReflectionActivityNames;
import srojak.core.reflect.Reflectory;
import srojak.core.reflect.lib.ReflectionModuleHelper;
import srojak.core.result.XResult;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultClass;
import srojak.core.result.XResultClassCarrier;
import srojak.core.result.XResultModifierFlags;
import srojak.core.result.XResultOf;
import srojak.core.specialized.StatusAccumulator;

/**
 * @author Stephen
 *
 */
public class CoreReflectionServiceProvider 
		implements CoreReflectionService, ReflectionActivityNames, XResultModifierFlags {
	private ObservationCollector _obsCollect;
	private boolean _bVerbose;
	
	CoreReflectionServiceProvider() {
		_obsCollect = CoreObservers.OBS_LOG;
		_bVerbose = false;
	}

	@Override
	public ObservationCollector getObservationCollector() {
		return _obsCollect;
	}

	@Override
	public void setObservationCollector(ObservationCollector collector) {
		Objects.requireNonNull(collector, "collector");
		_obsCollect = collector;
	}

	@Override
	public boolean isVerbose() {
		return _bVerbose;
	}

	@Override
	public void setVerbose(boolean bState) {
		_bVerbose = bState;
	}

	@Override
	public XResultClass findClass(Reflectory reflectory, PackageClassLocator locator) {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(locator, "locator");
		_obsCollect.traceEnter(TraceLevel.HIGH, () -> "looking for " + locator);
		XResultClassCarrier result = new XResultClassCarrier(ACTIVITY_FIND_CLASS);
		try {
			Class<?> classTarget = reflectory.getClassFrom(locator);
			result.setResult(classTarget);
		} catch (ClassNotFoundException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		}
		return result;
	}

	@Override
	public <T> XResultOf<T> createInstanceOf(Reflectory reflectory, Class<T> classTarget,
			Class<?> classImplementation) {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(classTarget, "classTarget");
		Objects.requireNonNull(classImplementation, "classImplementation");
		_obsCollect.traceEnter(TraceLevel.HIGH, () -> "create instance of " + classTarget.getSimpleName()
				+ " from " + classImplementation.getSimpleName());
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_CREATE_INST);
		try {
			Constructor<?> constructor = reflectory.getDefaultConstructor(classImplementation);
			reflectory.setAccessible("constructor", constructor, true);
			Object objInstance = reflectory.makeInstance(constructor);
			@SuppressWarnings("unchecked")
			T instance = (T) objInstance;
			result.setResult(instance);
		} catch (NoSuchMethodException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		} catch (SecurityException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		} catch (Throwable t) {
			result.caughtThrowable(t);
		}
		return result;
	}
	
	@Override
	public <T> XResultOf<T> createInstanceOf(Reflectory reflectory, Class<T> classTarget, Class<?> classImplementation,
			ParameterTypeSet typeParams, Object... params) {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(classTarget, "classTarget");
		Objects.requireNonNull(classImplementation, "classImplementation");
		Objects.requireNonNull(typeParams, "typeParams");
		Objects.requireNonNull(params, "params");
		_obsCollect.traceEnter(TraceLevel.HIGH, () -> "create instance of " + classTarget.getSimpleName()
				+ " from " + classImplementation.getSimpleName() + " with " + params.length + " params");
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_CREATE_INST);
		try {
			Constructor<?> constructor = reflectory.getConstructor(classImplementation, typeParams);
			reflectory.setAccessible("constructor", constructor, true);
			Object objInstance = reflectory.makeInstance(constructor, params);
			@SuppressWarnings("unchecked")
			T instance = (T) objInstance;
			result.setResult(instance);
		} catch (NoSuchMethodException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		} catch (SecurityException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		} catch (Throwable t) {
			result.caughtThrowable(t);
		}
		return result;
	}

	public void findModule(List<Module> list, ModuleLayer layer, ModuleName nameModule) {
		Iterator<Module> iterator = layer.modules().iterator();
		while (iterator.hasNext()) {
			Module module = iterator.next();
			if (module.isNamed() && nameModule.isNameEqual(module.getName())) {
				list.add(module);
			}
		}
		for (ModuleLayer parent : layer.parents()) {
			findModule(list, parent, nameModule);
		}
	}

	@Override
	public List<Module> locateModule(ModuleName nameModule) {
		Objects.requireNonNull(nameModule, "nameModule");
		List<Module> list = new LinkedList<Module>();
		findModule(list, ModuleLayer.boot(), nameModule);
		return list;
	}

	@Override
	public XResultOf<ModuleLayer> loadModuleEagerly(ModuleLayer layerBase, ModuleLocator locModule,
			StatusAccumulator status) {
		Objects.requireNonNull(layerBase, "layerBase");
		Objects.requireNonNull(locModule, "locModule");
		Objects.requireNonNull(status, "status");
		XResultCarrierOf<ModuleLayer> result = new XResultCarrierOf<ModuleLayer>(new SingleActivity("load module eagerly"));
		ReflectionModuleHelper helper = new ReflectionModuleHelper(isVerbose());
		ModuleFinder finder = helper.createModuleFinder(locModule);
		Set<ModuleReference> references = finder.findAll();
		ModuleLayer layer = helper.createNewModuleLayer(layerBase, finder, references);
		XResult resultClassLoad = helper.loadLayerClasses(layer, references, status);
		result.copyFrom(resultClassLoad);
		if (resultClassLoad.isValid()) {
			result.setResult(layer);
			if (isVerbose()) {
				_obsCollect.record(ObsLevel.INFO, "loaded " + status.successes() + " classes");
			}
		}
		return result;
	}
}
