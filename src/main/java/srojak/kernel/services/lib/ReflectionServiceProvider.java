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
package srojak.kernel.services.lib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import srojak.core.observe.activity.SingleActivity;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.reflect.Reflectory;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultClass;
import srojak.core.result.XResultClassCarrier;
import srojak.core.result.XResultOf;
import srojak.kernel.ServiceNames;
import srojak.kernel.services.AppKernelService;
import srojak.kernel.services.KernelReflectionService;
import srojak.kernel.services.KernelReflectionServiceProviderBase;

/**
 * @author Stephen
 *
 */
@AppKernelService(name = ServiceNames.REFLECTION)
public class ReflectionServiceProvider 
		extends KernelReflectionServiceProviderBase 
		implements KernelReflectionService {

	/**
	 * 
	 */
	public ReflectionServiceProvider() {
		super();
	}

	@Override
	public XResultClass findClass(Reflectory reflectory, PackageClassLocator locator) {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(locator, "locator");
		XResultClassCarrier result = new XResultClassCarrier(new SingleActivity("reflectory load"));
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
	public XResultClass findClass(Reflectory reflectory, Module module, PackageClassLocator locator) {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(module, "module");
		Objects.requireNonNull(locator, "locator");
		XResultClassCarrier result = new XResultClassCarrier(new SingleActivity("reflectory load"));
		try {
			Class<?> classTarget = reflectory.getClassFrom(module, locator);
			result.setResult(classTarget);
		} catch (ClassNotFoundException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		}
		return result;
	}

	@Override
	public <T> XResultOf<T> createInstanceOf(Class<T> classTarget, Class<?> classImplementation) {
		Objects.requireNonNull(classTarget, "classTarget");
		Objects.requireNonNull(classImplementation, "classImplementation");
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_CREATE_INST);
		try {
			Constructor<?> constructor = classImplementation.getDeclaredConstructor();
			Object objInstance = constructor.newInstance();
			@SuppressWarnings("unchecked")
			T instance = (T) objInstance;
			result.setResult(instance);
		} catch (IllegalAccessException exc) {
			result.caughtThrowable(exc);
		} catch (InstantiationException exc) {
			result.caughtThrowable(exc);
		} catch (IllegalArgumentException exc) {
			result.caughtThrowable(exc);
		} catch (InvocationTargetException exc) {
			result.caughtThrowable(exc);
		} catch (NoSuchMethodException exc) {
			result.caughtThrowable(exc);
		} catch (SecurityException exc) {
			result.caughtThrowable(exc);
		}
		return result;
	}

	@Override
	public <T> XResultOf<T> createInstanceOf(Reflectory reflectory, Class<T> classTarget,
			Class<?> classImplementation) {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(classTarget, "classTarget");
		Objects.requireNonNull(classImplementation, "classImplementation");
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

}
