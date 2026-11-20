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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.functional.IOSupplier;
import srojak.core.observe.ObservedActivity;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;
import srojak.core.specialized.IntegerCounter;
import srojak.mantle.functional.LineTokensConsumer;
import srojak.mantle.text.TextLineTokenizer;

/**
 * @author Stephen
 *
 */
public class FileLineReader {
	private final TextLineTokenizer _scanner;
	
	public FileLineReader() {
		_scanner = new TextLineTokenizer();
	}
	
	public XResult readFrom(IOSupplier<InputStream> supplierStream, LineTokensConsumer consumerTokens) {
		Objects.requireNonNull(supplierStream, "supplierStream");
		Objects.requireNonNull(consumerTokens, "consumerTokens");
		XResultStatusCarrier result = new XResultStatusCarrier(ObservedActivity.READ_FROM_FILE);
		IntegerCounter counterLines = new IntegerCounter();
		try (InputStream streamIn = supplierStream.get()) {
			InputStreamReader readerStream = new InputStreamReader(streamIn);
			BufferedReader reader = new BufferedReader(readerStream);
			while (true) {
				String strLine = reader.readLine();
				if (strLine == null) {
					break;
				}
				counterLines.increment();
				if (strLine.length() == 0) {
					continue;
				}
				if (strLine.charAt(0) == '#') {
					// it's a comment line
					continue;
				}
				List<String> listTokens = new LinkedList<String>();
				if (_scanner.processLine(strLine, listTokens)) {
					consumerTokens.accept(counterLines.value(), listTokens);
				}
			}
			result.setValid();
		} catch (IOException exc) {
			if (!result.isValid()) {
				result.caughtException(exc);
			}
		}
		return result;
	}
}
