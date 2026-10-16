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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

import srojak.core.ClassMismatchException;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.reflect.ParameterTypeSet;
import srojak.core.reflect.ReflectionProxy;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultClass;
import srojak.core.result.XResultClassCarrier;
import srojak.core.result.XResultOf;
import srojak.kernel.ServiceNames;
import srojak.kernel.services.AppKernelService;
import srojak.kernel.services.KernelProxyReflectionService;
import srojak.kernel.services.KernelReflectionServiceProviderBase;

/**
 * @author Stephen
 *
 */
@AppKernelService(name = ServiceNames.PROXY_REFLECTION)
public class ProxyReflectionServiceProvider 
		extends KernelReflectionServiceProviderBase
		implements KernelProxyReflectionService {

	/**
	 * 
	 */
	public ProxyReflectionServiceProvider() {
		super();
	}

	@Override
	public XResultClass findClass(ReflectionProxy proxy, PackageClassLocator locator) {
		Objects.requireNonNull(proxy, "proxy");
		Objects.requireNonNull(locator, "locator");
		XResultClassCarrier result = new XResultClassCarrier(ACTIVITY_FIND_CLASS);
		try {
			Class<?> classObj = proxy.findClass(locator);
			result.setResult(classObj);
		} catch (ClassNotFoundException exc) {
			result.caughtThrowable(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
			return result;
		} catch (IllegalAccessException exc) {
			result.caughtThrowable(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
			return result;
		}
		return result;
	}

	@Override
	public <T> XResultOf<T> createInstanceOf(Class<T> classTarget, PackageClassLocator locator) {
		ReflectionProxy proxy = new ReflectionProxy(MethodHandles.lookup());
		return createInstanceOf(proxy, classTarget, locator);
	}

	@Override
	public <T> XResultOf<T> createInstanceOf(ReflectionProxy proxy, Class<T> classTarget, PackageClassLocator locator) {
		Objects.requireNonNull(proxy, "proxy");
		Objects.requireNonNull(classTarget, "classTarget");
		Objects.requireNonNull(locator, "locator");
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_FIND_CLASS);
		XResultClass resultClass = findClass(proxy, locator);
		if (!resultClass.isValid()) {
			result.copyFrom(resultClass);
			return result;
		}
		Class<?> classObj = resultClass.getResult();
		if (!classTarget.isAssignableFrom(classObj)) {
			ClassMismatchException exc = new ClassMismatchException(classObj, "does not implement interface");
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
			return result;
		}
		if (classObj == null) {
			result.caughtException(new ClassNotFoundException(locator.getClassName()));
			result.setModifierFlag(MOD_FROM_SURROUND);
			return result;
		}
		MethodHandle constr = null;
		result.setActivity(ACTIVITY_CREATE_INST);
		try {
			constr = proxy.getDefaultConstructor(classObj);
		} catch (NoSuchMethodException exc) {
			result.caughtThrowable(exc);
			return result;
		} catch (IllegalAccessException exc) {
			result.caughtThrowable(exc);
			return result;
		}
		try {
			T objIntf = (T) constr.invoke();
			result.setResult(objIntf);
		} catch (Throwable t) {
			result.caughtThrowable(t);
		}
		return result;
	}

	@Override
	public <T> XResultOf<T> createInstanceOf(ReflectionProxy proxy, Class<T> classTarget, PackageClassLocator locator,
			ParameterTypeSet typeParams, Object... params) {
		Objects.requireNonNull(proxy, "proxy");
		Objects.requireNonNull(classTarget, "classTarget");
		Objects.requireNonNull(locator, "locator");
		Objects.requireNonNull(typeParams, "typeParams");
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_FIND_CLASS);
		XResultClass resultClass = findClass(proxy, locator);
		if (!resultClass.isValid()) {
			result.copyFrom(resultClass);
			return result;
		}
		Class<?> classObj = resultClass.getResult();
		if (!classTarget.isAssignableFrom(classObj)) {
			ClassMismatchException exc = new ClassMismatchException(classObj, "does not implement interface");
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
			return result;
		}
		if (classObj == null) {
			result.caughtException(new ClassNotFoundException(locator.getClassName()));
			result.setModifierFlag(MOD_FROM_SURROUND);
			return result;
		}
		MethodHandle constr = null;
		result.setActivity(ACTIVITY_CREATE_INST);
		try {
			constr = proxy.getConstructor(classObj, typeParams);
		} catch (NoSuchMethodException exc) {
			result.caughtThrowable(exc);
			return result;
		} catch (IllegalAccessException exc) {
			result.caughtThrowable(exc);
			return result;
		}
		try {
			@SuppressWarnings("unchecked")
			T objIntf = (T) constr.invokeWithArguments(params);
			result.setResult(objIntf);
		} catch (Throwable t) {
			result.caughtThrowable(t);
		}
		return result;
	}

}
