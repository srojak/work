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
package srojak.kernel.modules.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.IndirectReference;
import srojak.core.containers.IndirectStrongReference;
import srojak.core.containers.IndirectWeakReference;
import srojak.core.modules.ModuleName;
import srojak.core.reflect.ReflectionMethods;
import srojak.kernel.modules.NamedModuleList;

/**
 * @author Stephen
 *
 */
public class NamedModuleListObj 
		implements NamedModuleList {
	private final ModuleName _name;
	private final boolean _isJRE;
	private final LinkedList<IndirectReference<Module>> _listModules;
	
	public NamedModuleListObj(ModuleName name) {
		Objects.requireNonNull(name, "name");
		_name = name;
		_isJRE = ReflectionMethods.isNameJRE(name.getName());
		_listModules = new LinkedList<IndirectReference<Module>>();
	}
	
	public void addModule(Module module, boolean bUseStrongReference) {
		Objects.requireNonNull(module, "module");
		if (!module.isNamed() || !module.getName().equals(_name.getName())) {
			throw new IllegalArgumentException("module does not belong");
		}
		IndirectReference<Module> reference = bUseStrongReference
				? new IndirectStrongReference<Module>(module) : new IndirectWeakReference<Module>(module);
		_listModules.add(reference);
	}

	@Override
	public ModuleName name() {
		return _name;
	}

	@Override
	public boolean isNameEqual(ModuleName name) {
		return _name.equals(name);
	}

	@Override
	public boolean isJRE() {
		return _isJRE;
	}

	@Override
	public List<IndirectReference<Module>> modules() {
		return List.copyOf(_listModules);
	}

}
