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

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class ModuleLocatorBase 
		implements ModuleLocator {
	private final ModuleName _name;
	private final List<Path> _listPaths;
	
	protected ModuleLocatorBase(ModuleName name) {
		Objects.requireNonNull(name, "name");
		_name = name;
		_listPaths = new LinkedList<Path>();
	}
	
	protected void addPath(Path path) {
		Objects.requireNonNull(path, "path");
		_listPaths.add(path);
	}

	@Override
	public ModuleName name() {
		return _name;
	}

	@Override
	public List<Path> getStartPaths() {
		return List.copyOf(_listPaths);
	}
}
