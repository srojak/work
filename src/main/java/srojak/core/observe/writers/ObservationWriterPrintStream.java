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
import java.io.PrintStream;
import java.util.Objects;

import srojak.core.field.SetOnce;

/**
 * @author Stephen
 *
 */
public class ObservationWriterPrintStream 
		extends ObservationWriterCommonTextBase {
	private final SetOnce<PrintStream> _stream;

	/**
	 * @param stream
	 */
	public ObservationWriterPrintStream(PrintStream stream) {
		super();
		_stream = new SetOnce<PrintStream>(SetOnce.DEFAULT);
		assignPrintStream(stream);
	}
	
	public ObservationWriterPrintStream() {
		super();
		_stream = new SetOnce<PrintStream>(SetOnce.DEFAULT);
	}
	
	protected void assignPrintStream(PrintStream stream) {
		Objects.requireNonNull(stream, "stream");
		if (stream.checkError())
			throw new IllegalArgumentException("stream is not valid");
		_stream.set(stream);
		setCanWrite(true);
		if (stream != System.out && stream != System.err) {
			setIsClosable();
		}
	}

	@Override
	protected void writeln(String strText) throws IOException {
		_stream.get().println(strText);		
	}

	@Override
	protected void flushOutput() throws IOException {
		_stream.get().flush();	
	}

	@Override
	protected void closeOutput() throws IOException {
		_stream.get().close();
	}
}
