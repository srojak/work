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
package srojak.kernel.modules.services;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import srojak.core.IndirectReference;
import srojak.core.field.SetOnce;
import srojak.core.modules.ModuleLocator;
import srojak.core.modules.ModuleName;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.result.XResult;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultClass;
import srojak.core.result.XResultClassCarrier;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.core.result.XResultOf;
import srojak.core.specialized.StatusAccumulator;
import srojak.kernel.Kernel;
import srojak.kernel.KernelCentralStore;
import srojak.kernel.KernelServiceException;
import srojak.kernel.modules.ModuleLayerKey;
import srojak.kernel.modules.ModuleServiceNames;
import srojak.kernel.modules.NamedModuleList;
import srojak.kernel.services.AppKernelService;
import srojak.kernel.services.DependsOnService;
import srojak.kernel.services.KernelReflectionServiceProviderBase;

/**
 * @author Stephen
 *
 */
@AppKernelService(name = ModuleServiceNames.MODULE_LOADER)
@DependsOnService(ModuleServiceNames.MODULE_META)
public class ModuleLoaderServiceProvider 
		extends KernelReflectionServiceProviderBase
		implements KernelModuleLoaderService {
	private final SetOnce<KernelModuleMetadataService> _serviceCatalog;
	
	private static ClassLoader _loaderLocal = Thread.currentThread().getContextClassLoader();

	/**
	 * 
	 */
	public ModuleLoaderServiceProvider() {
		super();
		_serviceCatalog = new SetOnce<KernelModuleMetadataService>(SetOnce.DEFAULT);
	}

	@Override
	public void initialize(KernelCentralStore store) 
			throws KernelServiceException {
		KernelModuleMetadataService serviceCatalog = Kernel.getServiceOfType(ModuleServiceNames.MODULE_META);
		_serviceCatalog.set(serviceCatalog);
	}

	@Override
	public XResultClass moduleLoadClass(ModuleLocator locModule, PackageClassLocator locator) {
		Objects.requireNonNull(locModule, "locModule");
		Objects.requireNonNull(locator, "locator");
		ModuleName nameModule = locModule.name();
		// TODO: provide more control
		NamedModuleList listModules = _serviceCatalog.get().getModules(nameModule);
		if (listModules == null) {
			throw new IllegalArgumentException("named module " + nameModule + " not found");
		}
		// TODO: needs work
		IndirectReference<Module> reference = listModules.modules().get(0);
		Module module = reference.get();
		XResultClassCarrier result = new XResultClassCarrier(new SingleActivity("module-based load"));
		ClassLoader cloader = module.getClassLoader();
		try {
			Class<?> classTarget = Class.forName(locator.getFullName(), true, cloader);
			result.setResult(classTarget);
		} catch (ClassNotFoundException exc) {
			result.caughtException(exc);
			result.setModifierFlag(MOD_FROM_SURROUND);
		}
		return result;
	}

	@Override
	public XResultClass forwardLoadClass(ModuleLocator locModule, PackageClassLocator locator) {
		Objects.requireNonNull(locModule, "locModule");
		XResultClassCarrier result = new XResultClassCarrier(new SingleActivity("forward load"));
		ModuleName nameModule = locModule.name();
		List<Path> listPaths = new LinkedList<Path>();
		Path pathBin = pathToBin(locModule);
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
			if (isVerbose()) {
				OBS_OUT.record(ObsLevel.DEBUG, "add URL " + url);
			}
			listUrls.add(url);
		}
		URL[] urls = listUrls.toArray(new URL[0]);
		try (URLClassLoader loader = new URLClassLoader(urls, _loaderLocal)) {
			String strFullName = locator.getFullName();
			if (isVerbose()) {
				OBS_OUT.record(ObsLevel.DEBUG, "looking for " + strFullName);
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

	@Override
	public XResultOf<ModuleLayerKey> loadModuleEagerly(ModuleLayerKey keyOverLayer, ModuleLocator locModule,
			StatusAccumulator status) {
		Objects.requireNonNull(keyOverLayer, "keyOverLayer");
		Objects.requireNonNull(locModule, "locModule");
		Objects.requireNonNull(status, "status");
		XResultCarrierOf<ModuleLayerKey> result = new XResultCarrierOf<ModuleLayerKey>(new SingleActivity("forward load"));
		ModuleLayer layerBase = _serviceCatalog.get().getModuleLayer(keyOverLayer);
		if (layerBase == null) {
			result.caughtException(new NoSuchElementException("module layer " + keyOverLayer));
			return result;
		}
		ModuleFinder finder = createModuleFinder(locModule);
		Set<ModuleReference> references = finder.findAll();
		ModuleLayer layer = createNewModuleLayer(layerBase, finder, references);
		ModuleLayerKey key = _serviceCatalog.get().addModuleLayer(layer);
		if (isVerbose()) {
			OBS_OUT.record(ObsLevel.DEBUG, "created new layer " + layer);
		}
		
		XResult resultClassLoad = loadLayerClasses(layer, references, status);
		result.copyFrom(resultClassLoad);
		if (resultClassLoad.isValid()) {
			result.setResult(key);
			if (isVerbose()) {
				OBS_OUT.record(ObsLevel.INFO, "loaded " + status.successes() + " classes");
			}
		}
		return result;
	}
}
