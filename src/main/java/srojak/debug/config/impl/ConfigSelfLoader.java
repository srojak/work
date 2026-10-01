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
package srojak.debug.config.impl;

import java.nio.file.Path;

import srojak.core.io.FileExistence;
import srojak.core.kernel.SelfLoaderBase;
import srojak.core.observe.ObsLevel;
import srojak.core.result.XResult;
import srojak.debug.config.DebugConfigFileReader;
import srojak.debug.config.DebugConfigNames;

/**
 * @author Stephen
 *
 */
public class ConfigSelfLoader
		extends SelfLoaderBase {

	/**
	 * 
	 */
	public ConfigSelfLoader() {
		
	}

	@Override
	protected XResult doLoad() {
		DebugConfigFileReader readerDebug = new DebugConfigFileReader();
		XResult resultSchema = readerDebug.loadSchema();
		if (!resultSchema.isValid()) {
			_obsKernel.write(ObsLevel.ERROR, "failed to load schema");
			return resultSchema;
		}
		Path pathDir = super.getAppDirPath();
		Path pathFile = pathDir.resolve(DebugConfigNames.FILE_SWITCHES);
		_obsKernel.write(ObsLevel.INFO, "reading config file " + pathFile);
		XResult resultRead = readerDebug.readConfigFile(pathFile, FileExistence.Any);
		return resultRead;
	}

}
