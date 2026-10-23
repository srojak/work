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
package srojak.core.reflect.lib;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import srojak.core.main.CoreCentral;
import srojak.core.modules.ModuleLocator;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.props.DevEnvProperties;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;
import srojak.core.specialized.StatusAccumulator;

/**
 * @author Stephen
 *
 */
public class ReflectionModuleHelper
		implements CoreObservers {
	private final boolean _bVerbose;
	
	private static final ObservedActivity ACTIVITY_LOAD_MODULE_CLASSES = new SingleActivity("load module classes");

	public ReflectionModuleHelper(boolean bVerbose) {
		_bVerbose = bVerbose;
	}
	
	protected boolean isVerbose() {
		return _bVerbose;
	}
	
	public Path pathToBin(ModuleLocator locModule) {
		Objects.requireNonNull(locModule, "locModule");
		if (locModule.isDevEnv()) {
			DevEnvProperties propsDevEnv = CoreCentral.getDevEnvProperties();
			if (propsDevEnv.isEclipse()) {
				Path pathWorkspace = locModule.getStartPaths().get(0);
				StringBuilder sbPath = new StringBuilder(locModule.name().getName());
				sbPath.append("\\bin\\");
				Path pathDir = pathWorkspace.resolve(sbPath.toString());
				if (isVerbose()) {
					OBS_OUT.record(ObsLevel.DEBUG, "path is " + pathDir);
				}
				return pathDir;
			}
		}
		// TODO: implement other options
		throw new UnsupportedOperationException("not implemented yet");
	}
	
	public ModuleFinder createModuleFinder(ModuleLocator locModule) {
		Objects.requireNonNull(locModule, "locModule");
		Path pathBin = pathToBin(locModule);
		ModuleFinder finder = ModuleFinder.of(pathBin);
		return finder;
	}
	
	public ModuleLayer createNewModuleLayer(ModuleLayer layerBase, ModuleFinder finder, Set<ModuleReference> references) {
		Objects.requireNonNull(layerBase, "layerBase");
		Objects.requireNonNull(finder, "finder");
		Objects.requireNonNull(references, "references");
		List<String> listModuleNames = references.stream()
				.map(ModuleReference::descriptor)
				.map(ModuleDescriptor::name)
				.collect(Collectors.toList());
		if (isVerbose()) {
			OBS_OUT.record(ObsLevel.DEBUG, "found " + listModuleNames.size() + " modules");
		}
		Configuration config = layerBase.configuration().resolve(finder, ModuleFinder.of(), listModuleNames);
		ClassLoader cloaderSys = ClassLoader.getSystemClassLoader();
		ModuleLayer layer = layerBase.defineModulesWithOneLoader(config, cloaderSys);
		if (isVerbose()) {
			OBS_OUT.record(ObsLevel.DEBUG, "created new layer " + layer);
		}
		return layer;
	}
	
	public XResult loadLayerClasses(ModuleLayer layer, Set<ModuleReference> references, StatusAccumulator status) {
		Objects.requireNonNull(layer, "layer");
		Objects.requireNonNull(references, "references");
		Objects.requireNonNull(status, "status");
		XResultStatusCarrier result = new XResultStatusCarrier(ACTIVITY_LOAD_MODULE_CLASSES);
		status.clearAll();
		for (ModuleReference refMod : references) {
			ModuleDescriptor descriptor = refMod.descriptor();
			String strModule2 = descriptor.name();
			ClassLoader cloaderModule = layer.findLoader(strModule2);
			boolean bDone = false;
			try (ModuleReader reader = refMod.open()) {
				reader.list().forEach(res -> {
					if (res.endsWith(".class") && !res.equals("module-info.class")) {
						String strClassName = res.substring(0, res.length() - 6).replace('/', '.');
						try {
							@SuppressWarnings("unused")
							Class<?> classNew = cloaderModule.loadClass(strClassName);
							status.successCounter().increment();
						} catch (ClassNotFoundException exc) {
							OBS_OUT.record(ObsLevel.ERROR, "cannot load class " + strClassName);
							status.errorCounter().increment();
							status.addFault(exc);
						}
					}
				});
				bDone = true;
			} catch (IOException exc) {
				// do not want to report exceptions from close()
				if (!bDone) {
					result.caughtException(exc);
					return result;
				}
			}
		}
		result.setValid();
		return result;
	}

}
