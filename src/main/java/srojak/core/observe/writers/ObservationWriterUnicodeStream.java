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
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import srojak.core.field.SetOnce;

/**
 * @author Stephen
 *
 */
public class ObservationWriterUnicodeStream
		extends ObservationWriterCommonTextBase
		implements AutoCloseable {
	private final SetOnce<OutputStream> _stream;
	private OutputStreamWriter _writer;

	/**
	 * 
	 */
	public ObservationWriterUnicodeStream() {
		_stream = new SetOnce<OutputStream>(SetOnce.DEFAULT);
		setIsClosable();
		_writer = null;
	}
	
	public ObservationWriterUnicodeStream(OutputStream stream) {
		this();
		assignOutputStream(stream);
	}
	
	public void assignOutputStream(OutputStream stream) {
		_stream.set(stream);
		_writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
		setCanWrite(true);
	}

	@Override
	protected void writeln(String strText) throws IOException {
		_writer.write(strText);
		_writer.write('\n');
	}

	@Override
	protected void flushOutput() throws IOException {
		_writer.flush();
	}

	@Override
	public void closeOutput() throws IOException {
		if (_writer != null) {
			_writer.close();
			_writer = null;
		}
	}
}
