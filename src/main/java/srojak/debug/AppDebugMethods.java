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
package srojak.debug;

import java.io.IOException;
import java.nio.file.Path;

import srojak.core.observe.ObservationWriter;
import srojak.debug.DebugNexus.PropertyKeys;
import srojak.debug.impl.DebugNexusCore;

/**
 * @author Stephen
 *
 */
public class AppDebugMethods {

	private static final DebugProperties _properties = DebugNexusCore.getProperties();
	
	public static void setAutoFlush(boolean bState) {
		DebugNexusCore.setAutoFlush(bState);
	}
	
	public static void readDebugPropertiesFromCurrentDir(int codeExit) {
		try {
			_properties.loadFromCurrentDirectory(DebugNexusCore.PROPERTIES_FILE_NAME);
		} catch (IOException exc) {
			System.err.println("cannot load properties: " + exc.getMessage());
			System.exit(codeExit);
		}
	}
	
	public static boolean tryCreateLogFile(Class<?> classApp) {
		String strPath = _properties.getProperty(PropertyKeys.LOG_DIR);
		if (strPath == null) {
			System.err.println("property " + PropertyKeys.LOG_DIR + " is not defined");
			return false;
		}
		Path pathLogDir = Path.of(strPath);
		try {
			ObservationWriter writer = DebugWriterLogFile.create(pathLogDir, classApp);
			DebugNexusCore.setWriter(writer);
		} catch (IOException exc) {
			System.err.println("cannot create log file: " + exc.getMessage());
			return false;
		}
		return true;
	}
	
	public static boolean tryCreateLogFileIn(Class<?> classApp, Path pathDir) {
		try {
			ObservationWriter writer = DebugWriterLogFile.create(pathDir, classApp);
			DebugNexusCore.setWriter(writer);
		} catch (IOException exc) {
			System.err.println("cannot create log file: " + exc.getMessage());
			return false;
		}
		return true;
	}
}
