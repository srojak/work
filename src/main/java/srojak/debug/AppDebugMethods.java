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

import java.nio.file.Path;
import java.util.Objects;

import srojak.core.EnvironmentCharacteristicException;
import srojak.core.backplane.ApplicationBackplane;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.props.DebugProperties;
import srojak.core.props.DebugPropertyKeys;
import srojak.core.result.XResult;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;
import srojak.debug.impl.DebugNexusCore;

/**
 * @author Stephen
 *
 */
public class AppDebugMethods
		implements DebugPropertyKeys {

	private static final DebugProperties _properties = ApplicationBackplane.getDebugProperties();
	private static final ObservedActivity _activityCreate = new SingleActivity("create log file");
	private static final boolean _useSourceObject = true;
	
	public static void setAutoFlush(boolean bState) {
		// TODO: are we keeping this?
	}

	public static XResult tryCreateLogFile(Class<?> classApp, String strPrefix) {
		Objects.requireNonNull(classApp, "classApp");
		XResultStatusCarrier result = new XResultStatusCarrier(_activityCreate);
		String strPath = _properties.getProperty(LOG_DIR);
		if (strPath == null) {
			result.caughtException(
					new EnvironmentCharacteristicException("property " 
							+ LOG_DIR + " is not defined"));
			return result;
		}
		Path pathLogDir = Path.of(strPath);
		if (_useSourceObject) {
			DebugLogFileWriterSource source = new DebugLogFileWriterSource(pathLogDir, strPrefix);
			XResult resultCreate = source.useLogFile(classApp);
			result.copyFrom(resultCreate);
		} else {
			XResultOf<DebugWriterLogFile> resultCreate
				= DebugWriterLogFile.tryCreate(pathLogDir, classApp, strPrefix);
			result.copyFrom(resultCreate);
			if (resultCreate.isValid()) {
				DebugNexusCore.setWriter(resultCreate.getResult());
			}
		}
		return result;
	}
	
	public static XResult tryCreateLogFile(Class<?> classApp) {
		return tryCreateLogFile(classApp, DebugWriterLogFile.PREFIX_DEBUG);
	}
	
	public static XResult tryCreateLogFileIn(Class<?> classApp, String strPrefix, Path pathDir) {
		Objects.requireNonNull(classApp, "classApp");
		Objects.requireNonNull(pathDir, "pathDir");
		XResultStatusCarrier result = new XResultStatusCarrier(_activityCreate);
		XResultOf<DebugWriterLogFile> resultCreate
			= DebugWriterLogFile.tryCreate(pathDir, classApp, strPrefix);
		result.copyFrom(resultCreate);
		if (resultCreate.isValid()) {
			DebugNexusCore.setWriter(resultCreate.getResult());
		}
		return result;
	}
	
	public static XResult tryCreateLogFileIn(Class<?> classApp, Path pathDir) {
		return tryCreateLogFileIn(classApp, DebugWriterLogFile.PREFIX_DEBUG, pathDir);
	}
}
