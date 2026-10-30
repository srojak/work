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

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Stephen
 *
 */
public class SimpleConsoleLineReader 
		implements Closeable {
	private InputStreamReader _readerStream;
	private BufferedReader _readerBuf;
	
	public SimpleConsoleLineReader() {
		_readerStream = new InputStreamReader(System.in);
		_readerBuf = new BufferedReader(_readerStream);
	}
	
	public String readLine() {
		String strLine = null;
		try {
			strLine = _readerBuf.readLine();
		} catch (IOException exc) {
			System.err.println("IO exception: " + exc.getMessage());
		}
		return strLine;
	}

	@Override
	public void close() throws IOException {
		if (_readerBuf != null) {
			_readerBuf.close();
			_readerBuf = null;
		}
		if (_readerStream != null) {
			_readerStream.close();
			_readerStream = null;
		}
	}

}
