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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public abstract class SpecializedFileWriterBase {

	protected SpecializedFileWriterBase() {
		
	}
	
	protected abstract XResult writeContent(FileOutputStream streamOut);
	
	public XResult writeFile(File fileWrite) {
		Objects.requireNonNull(fileWrite, "fileWrite");
		XResultStatusCarrier result = new XResultStatusCarrier();
		try (FileOutputStream streamOut = new FileOutputStream(fileWrite)) {
			return writeContent(streamOut);
		} catch (FileNotFoundException exc) {
			result.caughtException(exc);
		} catch (IOException e) {
			// catches the possible exception closing streamOut
			result.caughtException(e);
		}
		return result;
	}
}
