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
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;

import srojak.core.backplane.ApplicationBackplane;
import srojak.core.io.DatedFileNameMethods;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.observe.writers.ObservationWriterUnicodeStream;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;
import srojak.core.tools.EnvTool;
import srojak.debug.impl.DebugNexusCore;

/**
 * @author Stephen
 *
 */
public abstract class DebugLogFileWriterSourceBase {
	protected final LinkedList<OpenOption> _listOptions;
	private Path _pathFile;
	
	protected ObservedActivity ACTIVITY_OPEN_WRITE = new SingleActivity("open log file for writing");
	public static final String PREFIX_DEBUG = "debug";
	
	protected static Path formFilePath(Path pathDir, String strPrefix) {
		// it is the caller's responsibility to protect from null or blank parameters
		LocalDateTime dtNow = LocalDateTime.now();
		return pathDir.resolve(DatedFileNameMethods.formFileName(strPrefix, "log", true, dtNow));
	}
	
	protected DebugLogFileWriterSourceBase() {
		_listOptions = new LinkedList<OpenOption>();
		_pathFile = null;
	}
	
	protected void setFilePath(Path pathFile) {
		Objects.requireNonNull(pathFile, "pathFile");
		_pathFile = pathFile;
	}
	
	public XResult useLogFile(Class<?> classApp) {
		Objects.requireNonNull(classApp, "classApp");
		if (_pathFile == null) {
			throw new IllegalStateException("no file name assigned");
		}
		SourceLocation location = SourceLocation.here();
		XResultStatusCarrier result = new XResultStatusCarrier(ACTIVITY_OPEN_WRITE);
		try {
			OutputStream stream = Files.newOutputStream(_pathFile, _listOptions.toArray(new OpenOption[0]));
			ObservationWriterUnicodeStream writer = new ObservationWriterUnicodeStream();
			LocalDateTime dtNow = LocalDateTime.now();
			writer.assignOutputStream(stream);
			writer.setAutoFlush(true);
			writer.write(ObsLevel.NOTICE, SourceLocation.start(), 
					"log created for " + classApp.getName() + " on "
							+ DebugNexusCore.FORMAT_TIME_STAMP.format(dtNow));
			writer.write(ObsLevel.NOTICE, SourceLocation.start(), 
						"Java version " + EnvTool.getJavaVersion());
			DebugNexusCore.setWriter(writer);
			ApplicationBackplane.writeToOutput(ObsLevel.INFO, "Created log file " + _pathFile);
			result.setValid();
		} catch (IOException exc) {
			result.caughtException(exc);
			// use the existing debug writer to announce the error
			ObservationWriter writer = DebugNexusCore.getWriter();
			writer.writeException(ObsLevel.ALERT, location, ACTIVITY_OPEN_WRITE, exc, false);
		}
		return result;
	}
}
