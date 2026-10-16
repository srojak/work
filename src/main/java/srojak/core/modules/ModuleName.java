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
package srojak.core.modules;

import java.util.Objects;

import srojak.core.keys.NameEquatableKey;

/**
 * @author Stephen
 *
 */
public interface ModuleName 
		extends NameEquatableKey, Comparable<ModuleName> {

	public static ModuleName module(String strName) {
		return new ModuleNameKey(strName);
	}
	
	public static ModuleName fromModule(Module module) {
		Objects.requireNonNull(module, "module");
		return new ModuleNameKey(module.getName());
	}
}
