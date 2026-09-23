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

/**
 * @author Stephen
 *
 */
public class DebugLogFileWriterSource 
		extends DebugLogFileWriterSourceBase {

	public DebugLogFileWriterSource(Path pathDir) {
		super();
		Objects.requireNonNull(pathDir, "pathDir");
		setFilePath(formFilePath(pathDir, PREFIX_DEBUG));
	}
	
	public DebugLogFileWriterSource(Path pathDir, String strPrefix) {
		super();
		Objects.requireNonNull(pathDir, "pathDir");
		Objects.requireNonNull(strPrefix, "strPrefix");
		if (strPrefix.isBlank()) {
			throw new IllegalArgumentException("strPrefix is blank");
		}
		setFilePath( formFilePath(pathDir, strPrefix));
	}
}
