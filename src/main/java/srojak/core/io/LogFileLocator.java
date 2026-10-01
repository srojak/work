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
package srojak.core.io;

import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;

import srojak.core.EnvironmentCharacteristicException;
import srojak.core.kernel.priv.KernelStore;
import srojak.core.observe.ObservedActivity;
import srojak.core.props.DebugPropertyKeys;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public class LogFileLocator
		implements DebugPropertyKeys {
	private final Path _pathDir;
	private final String _strPrefix;
	private boolean _bValidated;
	
	public static final String PREFIX_DEBUG = "debug";
	
	private static String getLogDir() {
		return KernelStore._propsDebug.getProperty(LOG_DIR);
	}
	
	public LogFileLocator() {
		String strLogDir = getLogDir();
		if (strLogDir == null) {
			_pathDir = null;
		} else {
			_pathDir = Path.of(strLogDir).normalize();
		}
		_strPrefix = PREFIX_DEBUG;
		_bValidated = false;
	}
	
	public LogFileLocator(String strPrefix) {
		Objects.requireNonNull(strPrefix, "strPrefix");
		if (strPrefix.isBlank()) {
			strPrefix = PREFIX_DEBUG;
		}
		String strLogDir = getLogDir();
		if (strLogDir == null) {
			_pathDir = null;
		} else {
			_pathDir = Path.of(strLogDir).normalize();
		}
		_strPrefix = strPrefix;
		_bValidated = false;
	}
	
	public LogFileLocator(Path pathDir) {
		Objects.requireNonNull(pathDir, "pathDir");
		_pathDir = pathDir;
		_strPrefix = PREFIX_DEBUG;
		_bValidated = false;
	}
	
	public LogFileLocator(Path pathDir, String strPrefix) {
		Objects.requireNonNull(pathDir, "pathDir");
		Objects.requireNonNull(strPrefix, "strPrefix");
		if (strPrefix.isBlank()) {
			strPrefix = PREFIX_DEBUG;
		}
		_pathDir = pathDir;
		_strPrefix = strPrefix;
		_bValidated = false;
	}
	
	public XResult validate() {
		XResultStatusCarrier result = new XResultStatusCarrier(ObservedActivity.VALIDATE);
		if (_pathDir == null) {
			result.caughtException(
					new EnvironmentCharacteristicException("property " 
							+ LOG_DIR + " is not defined"));
		} else if (!Files.isDirectory(_pathDir)) {
			result.caughtException(
					new NotDirectoryException(_pathDir.toString()));
		} else {
			_bValidated = true;
			result.setValid();
		}
		return result;
	}
	
	public Path formFile(LocalDateTime dtNow) {
		if (!_bValidated) {
			throw new IllegalStateException("not validated");
		}
		return _pathDir.resolve(DatedFileNameMethods.formFileName(_strPrefix, "log", true, dtNow));
	}
}
