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

/**
 * @author Stephen
 *
 */
public class ObservationWriterLogFile 
		extends ObservationWriterPrintStream {
	private Path _pathDir;
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
		PrintStream print = new PrintStream(streamOut);
		assignPrintStream(print);
		print.println("log created for " + _strAppName);
	}
}
