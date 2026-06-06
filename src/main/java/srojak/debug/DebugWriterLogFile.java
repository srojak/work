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

import srojak.core.io.DatedFileNameMethods;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterBase;
import srojak.core.specialized.StringBox;
import srojak.core.tools.EnvTool;
import srojak.debug.impl.DebugNexusCore;

/**
 * @author Stephen
 *
 */
public class DebugWriterLogFile
		extends ObservationWriterBase
		implements ObservationWriter {
	private Path _pathDir;
	private PrintStream _print;
	private String _strAppName;
	
	protected DebugWriterLogFile(Path pathDir, Class<?> classApp)
			throws IOException {
		super();
		Objects.requireNonNull(pathDir, "pathDir");
		Objects.requireNonNull(classApp, "classApp");
		_pathDir = pathDir;
		_strAppName = classApp.getName();
		LocalDateTime dtNow = LocalDateTime.now();
		Path pathFile = _pathDir.resolve(DatedFileNameMethods.formFileName("debug", "log", true, dtNow));
		Files.createFile(pathFile);
		OutputStream streamOut = Files.newOutputStream(pathFile);
		_print = new PrintStream(streamOut);
		_print.println("Java version " + EnvTool.getJavaVersion());
		_print.println("log created for " + _strAppName + " on "
				+ DebugNexusCore.FORMAT_TIME_STAMP.format(dtNow));
		System.out.println("Created log file " + pathFile);
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
	public void flush() {
		_print.flush();
	}

	public static DebugWriterLogFile create(Path pathDir, Class<?> classApp)
			throws IOException {
		return new DebugWriterLogFile(pathDir, classApp);
	}
	
	public static DebugWriterLogFile tryCreate(Path pathDir, Class<?> classApp, StringBox boxFailure) {
		Objects.requireNonNull(boxFailure, "boxFailure");
		boxFailure.reset();
		DebugWriterLogFile output = null;
		try {
			output = new DebugWriterLogFile(pathDir, classApp);
		} catch (IOException exc) {
			boxFailure.setContent(exc.getMessage());
		}
		return output;
	}
}
