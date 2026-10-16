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

import java.nio.file.Path;

import srojak.core.main.CoreCentral;
import srojak.core.modules.ModuleLocator;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.props.DevEnvProperties;
import srojak.core.reflect.ReflectionActivityNames;
import srojak.core.result.XResultModifierFlags;
import srojak.kernel.priv.KernelStore;

/**
 * @author Stephen
 *
 */
public abstract class KernelReflectionServiceProviderBase
		extends KernelServiceProviderBase
		implements XResultModifierFlags, ReflectionActivityNames, CoreObservers {

	/**
	 * 
	 */
	public KernelReflectionServiceProviderBase() {
		super();
	}

	protected Path pathToBin(ModuleLocator locModule) {
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
}
