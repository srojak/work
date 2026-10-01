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
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

import srojak.core.CommonPrintWriter;
import srojak.core.field.SetOnce;

/**
 * @author Stephen
 *
 */
public final class PrintStreamOverlay
		extends ClosableBase 
		implements CommonPrintWriter {
	private final SetOnce<PrintStream> _stream;
	
	/**
	 * 
	 */
	public PrintStreamOverlay() {
		super();
		_stream = new SetOnce<PrintStream>(SetOnce.DEFAULT);
		_stream.useActionWhenNotSet(() -> { throw new IllegalStateException("output is not ready"); });
	}
	
	public PrintStreamOverlay(PrintStream stream) {
		this();
		SetOutput(stream);
	}
	
	public void SetOutput(PrintStream stream) {
		Objects.requireNonNull(stream, "stream");
		_stream.set(stream);
		setReady();
	}

	@Override
	public boolean isFaulted() {
		if (_stream.isEmpty()) {
			return false;
		} else {
			return _stream.get().checkError();
		}
	}

	@Override
	public void flush() {
		_stream.get().flush();
	}

	@Override
	public CommonPrintWriter format(String format, Object ... args) {
		_stream.get().format(format, args);
		return this;
	}

	@Override
	public CommonPrintWriter format(Locale lcl, String format, Object ... args) {
		_stream.get().format(lcl, format, args);
		return this;
	}

	@Override
	public CommonPrintWriter printf(Locale lcl, String format, Object ... args) {
		_stream.get().printf(lcl, format, args);
		return this;
	}

	@Override
	public CommonPrintWriter printf(String format, Object ... args) {
		_stream.get().printf(format, args);
		return this;
	}

	@Override
	public void write(byte buf[], int off, int len) {
		_stream.get().write(buf, off, len);
	}

	@Override
	public void write(int nValue) {
		_stream.get().write(nValue);
	}

	@Override
	public void write(byte[] buf) {
		// never really throws
		try {
			_stream.get().write(buf);
		} catch (IOException e) {
			
		}
	}

	@Override
	public void write(char[] buf, int off, int len) {
		CharBuffer bufChar = CharBuffer.wrap(buf, off, len);
		ByteBuffer bufByte = StandardCharsets.UTF_8.encode(bufChar);
		_stream.get().write(bufByte.array(), bufByte.position(), bufByte.limit());
	}

	@Override
	public void write(String s) {
		_stream.get().print(s);
	}

	@Override
	public void write(String str, int off, int len) {
		Objects.requireNonNull(str, "str");
		_stream.get().print(str.substring(off, off + len));
	}

	@Override
	public void write(char[] ca) {
		_stream.get().print(ca);
	}

	@Override
	public void print(boolean bValue) {
		_stream.get().print(bValue);
	}

	@Override
	public void print(String s) {
		_stream.get().print(s);
	}

	@Override
	public void print(char[] ca) {
		_stream.get().print(ca);
	}

	@Override
	public void print(long lnValue) {
		_stream.get().print(lnValue);
	}

	@Override
	public void print(double dValue) {
		_stream.get().print(dValue);
	}

	@Override
	public void print(float fValue) {
		_stream.get().print(fValue);
	}

	@Override
	public void print(char c) {
		_stream.get().print(c);
	}

	@Override
	public void print(int nValue) {
		_stream.get().print(nValue);
	}

	@Override
	public void print(Object obj) {
		_stream.get().print(obj);
	}

	@Override
	public void println() {
		_stream.get().println();
	}

	@Override
	public void println(String s) {
		_stream.get().println(s);
	}

	@Override
	public void println(Object obj) {
		_stream.get().println(obj);
	}

	@Override
	public void println(float fValue) {
		_stream.get().println(fValue);
	}

	@Override
	public void println(double dValue) {
		_stream.get().println(dValue);
	}

	@Override
	public void println(char[] ca) {
		_stream.get().println(ca);
	}

	@Override
	public void println(boolean bValue) {
		_stream.get().println(bValue);
	}

	@Override
	public void println(char c) {
		_stream.get().println(c);
	}

	@Override
	public void println(int nValue) {
		_stream.get().println(nValue);
	}

	@Override
	public void println(long lnValue) {
		_stream.get().println(lnValue);
	}

	@Override
	public boolean checkError() {
		if (_stream.isEmpty()) {
			return false;
		} else {
			return _stream.get().checkError();
		}
	}

	@Override
	protected void innerClose() throws IOException {
		if (!_stream.isEmpty()) {
			_stream.get().close();
		}

	}

}
