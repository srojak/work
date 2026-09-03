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
package srojak.core.observe.writers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObsPassThroughList;

/**
 * @author Stephen
 *
 */
public class ObservationWriterLogFile 
		extends ObservationWriterLevelFilterBase {
	private Path _pathDir;
	private PrintStream _print;
	private String _strAppName;
	
	public ObservationWriterLogFile(Path pathDir, String strName, Object objApp) 
			throws IOException {
		super();
		Objects.requireNonNull(pathDir, "pathDir");
		Objects.requireNonNull(strName, "strName");
		if (strName.isEmpty() || strName.isBlank()) {
			throw new IllegalArgumentException("strName is empty");
		}
		Objects.requireNonNull(objApp, "objApp");
		_pathDir = pathDir;
		_strAppName = objApp.getClass().getName();
		Path pathFile = _pathDir.resolve(strName);
		OutputStream streamOut = Files.newOutputStream(pathFile);
		_print = new PrintStream(streamOut);
		_print.println("log created for " + _strAppName);
	}

	@Override
	public void write(ObsLevel level, String strText) {
		if (isObsLevelAtLeast(level)) {
			_print.println(level.getName() + ": " + strText);			
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, Consumer<StringBuilder> message) {
		if (isObsLevelAtLeast(level)) {
			StringBuilder sb = new StringBuilder(level.getName());
			sb.append(": ");
			message.accept(sb);
			_print.println(sb.toString());
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, int i, ObjIntConsumer<StringBuilder> message) {
		if (isObsLevelAtLeast(level)) {
			StringBuilder sb = new StringBuilder(level.getName());
			sb.append(": ");
			message.accept(sb, i);
			_print.println(sb.toString());
		}
	}

	@Override
	public void buildAndWrite(ObsLevel level, ObsPassThroughList listPassThrough,
			BiConsumer<StringBuilder, ObsPassThroughList> messageBuilder) {
		if (isObsLevelAtLeast(level)) {
			StringBuilder sb = new StringBuilder(level.getName());
			sb.append(": ");
			messageBuilder.accept(sb, listPassThrough);
			_print.println(sb.toString());
		}
	}

	@Override
	public void writeDiagnostic(String strText) {
		_print.println("*DIAG: " + strText);	
	}

	@Override
	public void writeTimeStamp(ObsLevel level) {
		if (isObsLevelAtLeast(level)) {
			_print.println(level.getName() + ": time " + getDateAndTimeStamp());
		}
	}

	@Override
	public void flush() {
		_print.flush();
	}

}
