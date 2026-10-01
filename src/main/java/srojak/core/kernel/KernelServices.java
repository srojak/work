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
package srojak.core.kernel;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import srojak.core.ClassMismatchException;
import srojak.core.kernel.priv.KernelStore;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.reflect.ParameterTypeSet;
import srojak.core.reflect.ReflectionActivityNames;
import srojak.core.reflect.ReflectionProxy;
import srojak.core.result.XResult;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.core.result.XResultModifierFlags;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;
import srojak.core.specialized.IntegerCounter;

/**
 * @author Stephen
 *
 */
public class KernelServices 
		implements XResultModifierFlags, ReflectionActivityNames {
	
	public static boolean _verbose = true;
	
	private static ClassLoader _loaderLocal = Thread.currentThread().getContextClassLoader();
	private static ObservationCollector OBSV_KERNEL = KernelStore.OUTPUT.collector();
	
	private static Path pathToBin(String strModule, boolean bIsEclipse) {
		if (bIsEclipse) {
			Path pathApp = Kernel.getAppDirPath();
			Path pathWorkspace = pathApp.resolve("..").normalize();
			StringBuilder sbPath = new StringBuilder(strModule);
			sbPath.append("\\bin\\");
			Path pathDir = pathWorkspace.resolve(sbPath.toString());
			if (_verbose) {
				OBSV_KERNEL.write(ObsLevel.DEBUG, "path is " + pathDir);
			}
			return pathDir;
		} else {
			// TODO: implement
			throw new UnsupportedOperationException("not implemented yet");
		}
	}
	
	public static XResultOf<Class<?>> findClass(ReflectionProxy proxy, PackageClassLocator locator) {
		Objects.requireNonNull(proxy, "proxy");
		Objects.requireNonNull(locator, "locator");
		XResultCarrierOf<Class<?>> result = new XResultCarrierOf<Class<?>>(ACTIVITY_FIND_CLASS);
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
	
	public static <T> XResultOf<T> createInstanceOf(ReflectionProxy proxy, Class<T> classTarget, PackageClassLocator locator) {
		Objects.requireNonNull(proxy, "proxy");
		Objects.requireNonNull(classTarget, "classTarget");
		Objects.requireNonNull(locator, "locator");
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_FIND_CLASS);
		XResultOf<Class<?>> resultClass = findClass(proxy, locator);
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
	
	public static <T> XResultOf<T> createInstanceOf(ReflectionProxy proxy, Class<T> classTarget, PackageClassLocator locator,
			ParameterTypeSet typeParams, Object ... params) {
		Objects.requireNonNull(proxy, "proxy");
		Objects.requireNonNull(classTarget, "classTarget");
		Objects.requireNonNull(locator, "locator");
		Objects.requireNonNull(typeParams, "typeParams");
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(ACTIVITY_FIND_CLASS);
		XResultOf<Class<?>> resultClass = findClass(proxy, locator);
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
	
	public static XResultOf<Class<?>> forwardLoadClass(String strModule, PackageClassLocator locator, boolean bIsEclipse) {
		XResultCarrierOf<Class<?>> result = new XResultCarrierOf<Class<?>>(new SingleActivity("forward load"));
		List<Path> listPaths = new LinkedList<Path>();
		Path pathBin = pathToBin(strModule, bIsEclipse);
		listPaths.add(pathBin);
		List<URL> listUrls = new ArrayList<URL>(listPaths.size());
		Iterator<Path> iterPaths = listPaths.iterator();
		while (iterPaths.hasNext()) {
			URL url;
			try {
				url = iterPaths.next().toUri().toURL();
			} catch (MalformedURLException exc) {
				result.caughtException(exc);
				return result;
			}
			if (_verbose) {
				OBSV_KERNEL.write(ObsLevel.DEBUG, "add URL " + url);
			}
			listUrls.add(url);
		}
		URL[] urls = listUrls.toArray(new URL[0]);
		try (URLClassLoader loader = new URLClassLoader(urls, _loaderLocal)) {
			String strFullName = locator.getFullName();
			if (_verbose) {
				OBSV_KERNEL.write(ObsLevel.DEBUG, "looking for " + strFullName);
			}
			Class<?> classTarget = loader.loadClass(strFullName);
			result.setResult(classTarget);
		} catch (IOException exc) {
			if (result.isValid()) {
				result.setModifierFlag(MOD_EXCEPT_ON_CLOSE);
			} else {
				result.caughtException(exc);
				result.setModifierFlag(MOD_FROM_SURROUND);
			}
		} catch (ClassNotFoundException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		}
		return result;
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
	
	public static XResultInt loadModuleEagerly(String strModule, boolean bIsEclipse) {
		XResultIntCarrier result = new XResultIntCarrier(new SingleActivity("forward load"));
		Path pathBin = pathToBin(strModule, bIsEclipse);
		ModuleFinder finder = ModuleFinder.of(pathBin);
		Set<ModuleReference> references = finder.findAll();
		List<String> listModuleNames = references.stream()
				.map(ModuleReference::descriptor)
				.map(ModuleDescriptor::name)
				.collect(Collectors.toList());
		OBSV_KERNEL.write(ObsLevel.DEBUG, "found " + listModuleNames.size() + " modules");
		
		ModuleLayer layerBoot = ModuleLayer.boot();
		Configuration config = layerBoot.configuration().resolve(finder, ModuleFinder.of(), listModuleNames);
		
		ClassLoader cloaderSys = ClassLoader.getSystemClassLoader();
		ModuleLayer layer = layerBoot.defineModulesWithOneLoader(config, cloaderSys);
		KernelStore.addModuleLayer(strModule, layer);
		OBSV_KERNEL.write(ObsLevel.DEBUG, "created new layer " + layer);
		
		IntegerCounter counter = new IntegerCounter();
		for (ModuleReference refMod : references) {
			ModuleDescriptor descriptor = refMod.descriptor();
			String strModule2 = descriptor.name();
			ClassLoader cloaderModule = layer.findLoader(strModule2);
			try (ModuleReader reader = refMod.open()) {
				reader.list().forEach(res -> {
					if (res.endsWith(".class") && !res.equals("module-info.class")) {
						String strClassName = res.substring(0, res.length() - 6).replace('/', '.');
						try {
							@SuppressWarnings("unused")
							Class<?> classNew = cloaderModule.loadClass(strClassName);
							counter.increment();
						} catch (ClassNotFoundException exc) {
							OBSV_KERNEL.write(ObsLevel.ERROR, "cannot load class " + strClassName);
						}
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		OBSV_KERNEL.write(ObsLevel.INFO, "loaded " + counter.getValue() + " classes");
		result.setResult(counter.getValue());
		return result;
	}
}
