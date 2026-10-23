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
package srojak.kernel.services;

import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.util.Set;

import srojak.core.modules.ModuleLocator;
import srojak.core.observe.CoreObservers;
import srojak.core.reflect.ReflectionActivityNames;
import srojak.core.reflect.lib.ReflectionModuleHelper;
import srojak.core.result.XResult;
import srojak.core.result.XResultModifierFlags;
import srojak.core.specialized.StatusAccumulator;

/**
 * @author Stephen
 *
 */
public abstract class KernelReflectionServiceProviderBase
		extends KernelServiceProviderBase
		implements XResultModifierFlags, ReflectionActivityNames, CoreObservers {
	private final ReflectionModuleHelper _helperModule;

	/**
	 * 
	 */
	public KernelReflectionServiceProviderBase() {
		super();
		_helperModule = new ReflectionModuleHelper(isVerbose());
	}

	protected Path pathToBin(ModuleLocator locModule) {
		return _helperModule.pathToBin(locModule);
	}
	
	protected ModuleFinder createModuleFinder(ModuleLocator locModule) {
		return _helperModule.createModuleFinder(locModule);
	}
	
	protected ModuleLayer createNewModuleLayer(ModuleLayer layerBase, ModuleFinder finder, 
			Set<ModuleReference> references) {
		return _helperModule.createNewModuleLayer(layerBase, finder, references);
	}
	
	protected XResult loadLayerClasses(ModuleLayer layer, Set<ModuleReference> references, StatusAccumulator status) {
		return _helperModule.loadLayerClasses(layer, references, status);
	}
}
