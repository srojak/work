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

import srojak.core.modules.ModuleName;
import srojak.kernel.modules.ModuleLayerKey;
import srojak.kernel.modules.NamedModuleList;
import srojak.kernel.services.KernelService;

/**
 * @author Stephen
 *
 */
public interface KernelModuleMetadataService 
		extends KernelService {

	NamedModuleList getModules(ModuleName nmModule);
	ModuleLayer getBootModuleLayer();
	ModuleLayerKey getBootModuleLayerKey();
	ModuleLayerKey addModuleLayer(ModuleLayer layer);
	ModuleLayer getModuleLayer(ModuleLayerKey key);
	
	KernelModuleMetadataInfo getInfo();
}
