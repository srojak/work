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
package srojak.mantle.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public class TextReaderMethods {
	
	public static XResult forEachLine(File file, Consumer<String> consumerLines) {
		Objects.requireNonNull(file, "file");
		Objects.requireNonNull(consumerLines, "consumerLines");
		XResultStatusCarrier result = new XResultStatusCarrier();
		try (InputStream stream = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			reader.lines().forEach(consumerLines);
			result.setValid();
		} catch (FileNotFoundException exc) {
			result.caughtException(exc);
		} catch (IOException exc) {
			result.caughtException(exc);
		}
		return result;
	}
}
