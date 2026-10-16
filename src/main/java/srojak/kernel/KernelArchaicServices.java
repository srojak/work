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
package srojak.kernel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import srojak.core.ClassMismatchException;
import srojak.core.modules.ModuleLocator;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.reflect.ParameterTypeSet;
import srojak.core.reflect.ReflectionActivityNames;
import srojak.core.reflect.ReflectionProxy;
import srojak.core.reflect.Reflectory;
import srojak.core.result.XResult;
import srojak.core.result.XResultClass;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultModifierFlags;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public class KernelArchaicServices 
		implements XResultModifierFlags, ReflectionActivityNames, CoreObservers {
	
	@Deprecated(forRemoval = true)
	public static <T> XResultOf<T> createInstanceOf(Class<T> classTarget, PackageClassLocator locator) {
		throw new UnsupportedOperationException("functionality moved");
	}
	
	@Deprecated(forRemoval = true)
	public static <T> XResultOf<T> createInstanceOf(Class<T> classTarget, Class<?> classImplementation) {
		throw new UnsupportedOperationException("functionality moved");
	}
	
	@Deprecated(forRemoval = true)
	public static <T> XResultOf<T> createInstanceOf(ReflectionProxy proxy, Class<T> classTarget, PackageClassLocator locator,
			ParameterTypeSet typeParams, Object ... params) {
		throw new UnsupportedOperationException("functionality moved");
	}
	
	@Deprecated(forRemoval = true)
	public static XResultClass findClass(Reflectory reflectory, PackageClassLocator locator) {
		throw new UnsupportedOperationException("functionality moved");
	}
	
	@Deprecated(forRemoval = true)
	public static XResultClass findClass(Reflectory reflectory, Module module, PackageClassLocator locator) {
		throw new UnsupportedOperationException("functionality moved");
	}
	
	@Deprecated(forRemoval = true)
	public static <T> XResultOf<T> createInstanceOf(Reflectory reflectory, Class<T> classTarget, Class<?> classImplementation) {
		throw new UnsupportedOperationException("functionality moved");
	}
	
	public static XResult runLoaderFrom(Class<?> classLoader) {
		XResultStatusCarrier result = new XResultStatusCarrier(new SingleActivity("run loader"));
		if (!Loader.class.isAssignableFrom(classLoader)) {
			ClassMismatchException exc = new ClassMismatchException(classLoader, "does not implement Loader");
			result.caughtException(exc);
			return result;
		}
		try {
			Constructor<?> cons = classLoader.getDeclaredConstructor();
			Object objInstance = cons.newInstance();
			Loader loader = (Loader) objInstance;
			XResult resultLoad = loader.load();
			result.copyFrom(resultLoad);
		} catch (NoSuchMethodException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		} catch (SecurityException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		} catch (InstantiationException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		} catch (InvocationTargetException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		}
		return result;
	}
	
	@Deprecated(forRemoval = true)
	public static XResultInt loadModuleEagerly(ModuleLocator locModule) {
		throw new UnsupportedOperationException("functionality moved");
	}
}
