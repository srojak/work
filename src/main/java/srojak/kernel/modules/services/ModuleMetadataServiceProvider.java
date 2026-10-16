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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import srojak.core.KeyedIndirectReference;
import srojak.core.ObjectExpiredException;
import srojak.core.containers.KeyedIndirectStrongReference;
import srojak.core.containers.KeyedIndirectWeakReference;
import srojak.core.modules.ModuleName;
import srojak.core.sequence.OrderedSequence;
import srojak.core.sequence.SimpleSequencer;
import srojak.core.tools.ListMethods;
import srojak.kernel.KernelCentralStore;
import srojak.kernel.modules.ModuleLayerKey;
import srojak.kernel.modules.ModuleServiceNames;
import srojak.kernel.modules.NamedModuleList;
import srojak.kernel.modules.impl.ModuleLayerKeyObj;
import srojak.kernel.modules.impl.NamedModuleListObj;
import srojak.kernel.services.AppKernelService;
import srojak.kernel.services.KernelServiceProviderBase;

/**
 * @author Stephen
 *
 */
@AppKernelService(name = ModuleServiceNames.MODULE_META)
public class ModuleMetadataServiceProvider 
		extends KernelServiceProviderBase
		implements KernelModuleMetadataService {
	private final OrderedSequence _seqLayers;
	private final ModuleLayer _layerBoot;
	private final Map<ModuleName, NamedModuleListObj> _mapModules;
	private final List<KeyedIndirectReference<ModuleLayerKey, ModuleLayer>> _listLayers;
	private final List<Module> _listUnnamed;

	/**
	 * 
	 */
	public ModuleMetadataServiceProvider() {
		super();
		_seqLayers = new SimpleSequencer(ModuleLayerKeyObj.serialClassUID);
		_layerBoot = ModuleLayer.boot();
		_mapModules = new HashMap<ModuleName, NamedModuleListObj>();
		_listLayers = new LinkedList<KeyedIndirectReference<ModuleLayerKey, ModuleLayer>>();
		_listUnnamed = new LinkedList<Module>();
	}
	
	private void getModulesFromLayer(ModuleLayer layer, boolean bUseStrongReference) {
		for (Module module : layer.modules()) {
			if (module.isNamed()) {
				ModuleName name = ModuleName.fromModule(module);
				NamedModuleListObj listModules = _mapModules.get(name);
				if (listModules != null) {
					listModules = new NamedModuleListObj(name);
					_mapModules.put(name, listModules);
					listModules.addModule(module, bUseStrongReference);
				}
			} else {
				_listUnnamed.add(module);
			}
		}
	}
	
	private boolean isLayerKnown(ModuleLayer layer) {
		return ListMethods.isTrueForAny(_listLayers, i -> i.get() == layer);
	}
	
	private ModuleLayerKey applyLayers(ModuleLayer layer) {
		if (!isLayerKnown(layer)) {
			ModuleLayerKey key = new ModuleLayerKeyObj(_seqLayers.next());
			KeyedIndirectReference<ModuleLayerKey, ModuleLayer> reference
					= new KeyedIndirectWeakReference<ModuleLayerKey, ModuleLayer>(key, layer);
			_listLayers.add(reference);
			getModulesFromLayer(layer, false);
			for (ModuleLayer p : layer.parents()) {
				applyLayers(p);
			}
			return key;
		} else {
			return null;
		}
	}

	@Override
	public void initialize(KernelCentralStore store) {
		// the boot layer gets a strong reference
		ModuleLayerKey key = new ModuleLayerKeyObj(_seqLayers.next());
		KeyedIndirectReference<ModuleLayerKey, ModuleLayer> reference
				= new KeyedIndirectStrongReference<ModuleLayerKey, ModuleLayer>(key, _layerBoot);
		_listLayers.add(reference);
		getModulesFromLayer(_layerBoot, true);
		for (ModuleLayer p : _layerBoot.parents()) {
			applyLayers(_layerBoot);
		}
	}

	@Override
	public NamedModuleList getModules(ModuleName nmModule) {
		return _mapModules.get(nmModule);
	}

	@Override
	public ModuleLayer getBootModuleLayer() {
		return _layerBoot;
	}

	@Override
	public ModuleLayerKey addModuleLayer(ModuleLayer layer) {
		Objects.requireNonNull(layer, "layer");
		ModuleLayerKey key = applyLayers(layer);
		if (key == null) {
			KeyedIndirectReference<ModuleLayerKey, ModuleLayer> reference
					= ListMethods.findInList(_listLayers, i -> i.get() == layer);
			key = reference.key();
		}
		return key;
	}

	@Override
	public ModuleLayer getModuleLayer(ModuleLayerKey key) {
		Objects.requireNonNull(key, "key");
		KeyedIndirectReference<ModuleLayerKey, ModuleLayer> reference
					= ListMethods.findInList(_listLayers, i -> i.isKeyEqual(key));
		if (reference == null) {
			throw new NoSuchElementException();
		} else {
			ModuleLayer layer = reference.get();
			if (layer == null) {
				_listLayers.remove(reference);
				throw new ObjectExpiredException(ModuleLayer.class);
			}
			return layer;
		}
	}
}
