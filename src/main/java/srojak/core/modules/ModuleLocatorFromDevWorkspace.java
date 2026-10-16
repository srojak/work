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
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class ModuleLocatorFromDevWorkspace 
		extends ModuleLocatorBase {

	/**
	 * @param name
	 */
	public ModuleLocatorFromDevWorkspace(String strWorkspace, ModuleName name) {
		super(name);
		Objects.requireNonNull(strWorkspace, "strWorkspace");
		if (strWorkspace.isBlank()) {
			throw new IllegalArgumentException("strWorkspace is blank");
		}
		addPath(Path.of(strWorkspace));
	}

	@Override
	public boolean isJar() {
		return false;
	}

	@Override
	public boolean isDevEnv() {
		return true;
	}

	@Override
	public boolean useClassPath() {
		return false;
	}
}
