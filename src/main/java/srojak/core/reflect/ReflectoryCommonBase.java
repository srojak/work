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
package srojak.core.reflect;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.Objects;

import srojak.core.observe.ObservationCollector;
import srojak.core.observe.TraceLevel;
import srojak.core.observe.code.CodeValueMethods;
import srojak.core.observe.sources.ObservationCollectorCommonErrorSource;

/**
 * @author Stephen
 *
 */
public abstract class ReflectoryCommonBase 
		implements Reflectory {
	private ObservationCollector _collector;
	
	public ReflectoryCommonBase() {
		//_collector = KernelLog.OBS_LOG;
		ObservationCollectorCommonErrorSource source = new ObservationCollectorCommonErrorSource();
		_collector = source.getCollector();
	}

	@Override
	public ObservationCollector getObservationCollector() {
		return _collector;
	}
	
	@Override
	public void setObservationCollector(ObservationCollector collector) {
		Objects.requireNonNull(collector, "collector");
		_collector = collector;
	}

	protected abstract Class<?> getClassFrom(ObservationCollector collObs, String strFullClassName)
				throws ClassNotFoundException;
	
	protected abstract Class<?> getClassFrom(ObservationCollector collObs, String strFullClassName,
			boolean bInitialize, ClassLoader loader)
				throws ClassNotFoundException;
	
	protected abstract Class<?> getClassFrom(ObservationCollector collObs, Module module, String strFullClassName)
				throws ClassNotFoundException;
	
	protected abstract void setAccessible(ObservationCollector collObs, String strIdentifier, 
			AccessibleObject obj, boolean bState) 
					throws SecurityException;
	
	protected abstract Constructor<?> getDefaultConstructor(ObservationCollector collObs, Class<?> classImpl)
				throws NoSuchMethodException, SecurityException;
	
	protected abstract Constructor<?> getConstructor(ObservationCollector collObs, Class<?> classImpl, Class<?>[] parameterTypes)
				throws NoSuchMethodException, SecurityException;
	
	protected abstract Object makeInstance(ObservationCollector collObs, Constructor<?> constructor, Object[] args) 
				throws Throwable;

	@Override
	public Class<?> getClassFrom(PackageClassLocator locator) throws ClassNotFoundException {
		Objects.requireNonNull(locator, "locator");
		_collector.traceCodeEnterObject(TraceLevel.MEDIUM, this, cb -> {
			cb.addNamedValue(CodeValueMethods.captureObject("locator", locator));
		});
		return getClassFrom(_collector, locator.getFullName());
	}

	@Override
	public Class<?> getClassFrom(PackageClassLocator locator, boolean bInitialize, ClassLoader loader)
			throws ClassNotFoundException {
		Objects.requireNonNull(locator, "locator");
		Objects.requireNonNull(loader, "loader");
		_collector.traceCodeEnterObject(TraceLevel.MEDIUM, this, cb -> {
			cb.addNamedValue(CodeValueMethods.captureObject("locator", locator));
			cb.addNamedValue(CodeValueMethods.captureObject("loader", loader));
		});
		return getClassFrom(_collector, locator.getFullName(), bInitialize, loader);
	}

	@Override
	public Class<?> getClassFrom(Module module, PackageClassLocator locator) throws ClassNotFoundException {
		Objects.requireNonNull(module, "module");
		Objects.requireNonNull(locator, "locator");
		_collector.traceCodeEnterObject(TraceLevel.MEDIUM, this, cb -> {
			cb.addNamedValue(CodeValueMethods.capture("module", module.getName()));
			cb.addNamedValue(CodeValueMethods.captureObject("locator", locator));
		});
		return getClassFrom(_collector, module, locator.getFullName());
	}

	@Override
	public void setAccessible(String strIdentifier, AccessibleObject obj, boolean bState) 
			throws SecurityException {
		Objects.requireNonNull(strIdentifier, "strIdentifier");
		Objects.requireNonNull(obj, "obj");
		_collector.traceCodeEnterObject(TraceLevel.MEDIUM, this, cb -> {
			cb.addNamedValue(CodeValueMethods.captureObject(strIdentifier, obj));
			cb.addNamedValue(CodeValueMethods.capture("bState", bState));
		});
		setAccessible(_collector, strIdentifier, obj, bState);
	}

	@Override
	public Constructor<?> getDefaultConstructor(Class<?> classImpl) throws NoSuchMethodException, SecurityException {
		Objects.requireNonNull(classImpl, "classImpl");
		_collector.traceCodeEnterObject(TraceLevel.MEDIUM, this, cb -> {
			cb.addNamedValue(CodeValueMethods.capture("class", classImpl.getName()));
		});
		return getDefaultConstructor(_collector, classImpl);
	}

	@Override
	public Constructor<?> getConstructor(Class<?> classImpl, ParameterTypeSet setParams)
			throws NoSuchMethodException, SecurityException {
		_collector.traceCodeEnterObject(TraceLevel.MEDIUM, this, cb -> {
			cb.addNamedValue(CodeValueMethods.capture("class", classImpl.getName()));
		});
		Objects.requireNonNull(classImpl, "classImpl");
		Objects.requireNonNull(setParams, "setParams");
		return getConstructor(_collector, classImpl, setParams.parameterTypes());
	}

	@Override
	public Object makeInstance(Constructor<?> constructor, Object... args) throws Throwable {
		Objects.requireNonNull(constructor, "constructor");
		_collector.traceCodeEnterObject(TraceLevel.MEDIUM, this, cb -> {
			cb.addNamedValue(CodeValueMethods.capture("cons for class", constructor.getDeclaringClass().getName()));
		});
		return makeInstance(_collector, constructor, args);
	}
}
