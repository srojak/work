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

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author Stephen
 *
 */
public class UnicodeOutputStreamWriter {
	private final OutputStreamWriter _writer;
	
	public UnicodeOutputStreamWriter(OutputStream streamOut) {
		_writer = new OutputStreamWriter(streamOut, StandardCharsets.UTF_8);
	}
	
	public void write(String strText) throws IOException {
		_writer.write(strText);
	}
	
	public void writeln() throws IOException {
		_writer.write('\n');
	}
	
	public void flush() throws IOException {
		_writer.flush();
	}
}
