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
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;

import srojak.core.backplane.ApplicationBackplane;
import srojak.core.io.DatedFileNameMethods;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;
import srojak.core.tools.EnvTool;
import srojak.debug.impl.DebugNexusCore;

/**
 * @author Stephen
 *
 */
public class DebugWriterLogFile
		extends ObservationWriterPrintStream {
	// TODO: change base class
	private final Path _pathDir;
	private final Path _pathFile;
	private final PrintStream _print;
	private final String _strAppName;
	
	public static final String PREFIX_DEBUG = "debug";
	private static final ObservedActivity _activityCreate = new SingleActivity("create log file");
	
	protected DebugWriterLogFile(Path pathDir, Class<?> classApp, String strPrefix)
			throws IOException {
		super();
		// TODO: block level filtering
		Objects.requireNonNull(pathDir, "pathDir");
		Objects.requireNonNull(classApp, "classApp");
		Objects.requireNonNull(strPrefix, "strPrefix");
		if (strPrefix.isBlank()) {
			strPrefix = PREFIX_DEBUG;
		}
		_pathDir = pathDir;
		_strAppName = classApp.getName();
		LocalDateTime dtNow = LocalDateTime.now();
		_pathFile = _pathDir.resolve(DatedFileNameMethods.formFileName(strPrefix, "log", true, dtNow));
		Files.createFile(_pathFile);
		OutputStream streamOut = Files.newOutputStream(_pathFile);
		_print = new PrintStream(streamOut);
		assignPrintStream(_print);
		_print.println("Java version " + EnvTool.getJavaVersion());
		_print.println("log created for " + _strAppName + " on "
				+ DebugNexusCore.FORMAT_TIME_STAMP.format(dtNow));
		// TODO functionally organize, create a writer for the announcement
		ApplicationBackplane.writeToOutput(ObsLevel.INFO, "Created log file " + _pathFile);
	}
	
	public Path getDirectoryPath() {
		return _pathDir;
	}
	
	public Path getFilePath() {
		return _pathFile;
	}
	
	public static DebugWriterLogFile create(Path pathDir, Class<?> classApp, 
			String strPrefix)
			throws IOException {
		return new DebugWriterLogFile(pathDir, classApp, strPrefix);
	}

	public static XResultOf<DebugWriterLogFile> tryCreate(Path pathDir, Class<?> classApp, 
			String strPrefix) {
		XResultCarrierOf<DebugWriterLogFile> result = new XResultCarrierOf<DebugWriterLogFile>(_activityCreate);
		try {
			result.setResult(new DebugWriterLogFile(pathDir, classApp, strPrefix));
		} catch (IOException exc) {
			result.caughtException(exc);
		}
		return result;
	}
}
