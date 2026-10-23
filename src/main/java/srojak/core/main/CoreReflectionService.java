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

import java.util.List;

import srojak.core.modules.ModuleLocator;
import srojak.core.modules.ModuleName;
import srojak.core.observe.HasReplaceableObservationCollector;
import srojak.core.reflect.PackageClassLocator;
import srojak.core.reflect.ParameterTypeSet;
import srojak.core.reflect.Reflectory;
import srojak.core.result.XResultClass;
import srojak.core.result.XResultOf;
import srojak.core.specialized.StatusAccumulator;

/**
 * @author Stephen
 *
 * A reduced surface reflection service so that other modules that do not need the kernel
 * 	can work without it.
 */
public interface CoreReflectionService
		extends HasReplaceableObservationCollector {
	
	boolean isVerbose();
	
	void setVerbose(boolean bState);

	/**
	 * Find a class.
	 * @param reflectory The {@code Reflectory} of the module whose access rights are used.
	 * @param locator The locator for the class.
	 * @return An {@code XResultClass} object having the class if successful.
	 */
	XResultClass findClass(Reflectory reflectory, PackageClassLocator locator);
	
	/**
	 * Create an instance of a class implementing an interface.
	 * @param <T> The type of the interface.
	 * @param reflectory The {@code Reflectory} of the module whose access rights are used.
	 * @param classTarget The class of the interface.
	 * @param classImplementation The class of the implementation.
	 * @return An {@code XResultOf<T>} object having the instance if successful.
	 */
	<T> XResultOf<T> createInstanceOf(Reflectory reflectory, Class<T> classTarget, Class<?> classImplementation);
	
	/**
	 * Create an instance of a class implementing an interface, with constructor parameters.
	 * @param <T> The type of the interface.
	 * @param reflectory The {@code Reflectory} of the module whose access rights are used.
	 * @param classTarget The class of the interface.
	 * @param classImplementation The class of the implementation.
	 * @param typeParams A collection containing the types of the parameters as declared by the constructor.
	 * @param params Values for the parameters.
	 * @return An {@code XResultOf<T>} object having the instance if successful.
	 */
	<T> XResultOf<T> createInstanceOf(Reflectory reflectory, Class<T> classTarget, Class<?> classImplementation,
			ParameterTypeSet typeParams, Object ... params);
	
	/**
	 * Find all instances of a named module currently loaded.
	 * @param nameModule The name of the module to find.
	 * @return A list of {@code Module} objects; it will be empty if the module is not loaded.
	 */
	List<Module> locateModule(ModuleName nameModule);
	
	/**
	 * Load a module into a layer over another layer and load all classes of the module.
	 * @param layerBase The {@code ModuleLayer} that is the base for the new layer.
	 * @param locModule The locator for the module.
	 * @param status Accumulates status information for the operation.
	 * @return An {@code XResultOf<T>} object having the new layer if successful.
	 */
	XResultOf<ModuleLayer> loadModuleEagerly(ModuleLayer layerBase, ModuleLocator locModule,
			StatusAccumulator status);
}
