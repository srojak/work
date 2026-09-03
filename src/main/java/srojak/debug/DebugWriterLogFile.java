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
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import srojak.core.io.DatedFileNameMethods;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.writers.ObservationWriterBase;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;
import srojak.core.tools.EnvTool;
import srojak.debug.impl.DebugNexusCore;

/**
 * @author Stephen
 *
 */
public class DebugWriterLogFile
		extends ObservationWriterBase
		implements ObservationWriter {
	private final Path _pathDir;
	private final Path _pathFile;
	private final PrintStream _print;
	private final String _strAppName;
	
	public static final String PREFIX_DEBUG = "debug";
	
	protected DebugWriterLogFile(Path pathDir, Class<?> classApp, String strPrefix)
			throws IOException {
		super();
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
		_print.println("Java version " + EnvTool.getJavaVersion());
		_print.println("log created for " + _strAppName + " on "
				+ DebugNexusCore.FORMAT_TIME_STAMP.format(dtNow));
		// TODO functionally organize, create a writer for the announcement
		System.out.println("Created log file " + _pathFile);
	}
	
	public Path getDirectoryPath() {
		return _pathDir;
	}
	
	public Path getFilePath() {
		return _pathFile;
	}

	@Override
	public boolean isLevelAccepted(ObsLevel level) {
		return true;
	}

	@Override
	public void write(ObsLevel level, String strText) {
		_print.print(level.getName());
		_print.print(": ");
		_print.println(strText);
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message) {
		StringBuilder sb = new StringBuilder(level.getName());
		sb.append(": ");
		message.accept(sb);
		_print.println(sb.toString());
	}

	@Override
	public void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> message) {
		StringBuilder sb = new StringBuilder(level.getName());
		sb.append(": ");
		message.accept(sb, i);
		_print.println(sb.toString());
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		StringBuilder sb = new StringBuilder(level.getName());
		sb.append(": ");
		messageBuilder.accept(sb, listPassThrough);
		_print.println(sb.toString());
	}

	@Override
	public void writeDiagnostic(String strText) {
		_print.print("*DIAG: ");
		_print.println(strText);
	}

	@Override
	public void writeTimeStamp(ObsLevel level) {
		_print.println(level.getName() + ": time " + getDateAndTimeStamp());
	}
	
	@Override
	public void write(ObservationCollector collector, SourceLocation locOrigin, String strText) {
		write(collector.getLevel(), strText);
	}
	
	@Override
	public void flush() {
		_print.flush();
	}
	
	public static DebugWriterLogFile create(Path pathDir, Class<?> classApp, 
			String strPrefix)
			throws IOException {
		return new DebugWriterLogFile(pathDir, classApp, strPrefix);
	}

	public static XResultOf<DebugWriterLogFile> tryCreate(Path pathDir, Class<?> classApp, 
			String strPrefix) {
		XResultCarrierOf<DebugWriterLogFile> result = new XResultCarrierOf<DebugWriterLogFile>();
		try {
			result.setResult(new DebugWriterLogFile(pathDir, classApp, strPrefix));
		} catch (IOException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
}
