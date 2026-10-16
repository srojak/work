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
package srojak.kernel;

import java.nio.file.Path;

import srojak.core.EnvironmentCharacteristicException;
import srojak.core.main.CoreCentral;
import srojak.core.modules.ModuleLocatorBase;
import srojak.core.modules.ModuleName;
import srojak.core.props.DevEnvProperties;
import srojak.core.props.DevEnvPropertyKeys;

/**
 * @author Stephen
 *
 */
public class JavaModuleLocatorFromDevProjectFiles 
		extends ModuleLocatorBase
		implements DevEnvPropertyKeys {

	/**
	 * @param name
	 */
	public JavaModuleLocatorFromDevProjectFiles(ModuleName name) {
		super(name);
		DevEnvProperties properties = CoreCentral.getDevEnvProperties();
		String strWorkspace = properties.getProperty(DIR_WORKSPACE);
		if (strWorkspace == null) {
			throw new EnvironmentCharacteristicException("no dev env workspace");
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
