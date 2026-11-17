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
package srojak.mantle.text;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import srojak.core.CommonPrintWriter;
import srojak.core.Disposable;
import srojak.core.logic.OnceFlag;

/**
 * @author Stephen
 *
 */
class ChannelWriterBuffer 
		implements CommonPrintWriter, Disposable {
	private final TextChannelOutputDestination _output;
	private final TextChannelNumber _channel;
	private final ByteArrayOutputStream _buffer;
	private final PrintWriter _writer;
	private final OnceFlag _fDisposed;
	
	public ChannelWriterBuffer(TextChannelOutputDestination output, TextChannelNumber channel, int szBufferStart) {
		_output = output;
		_channel = channel;
		_buffer = new ByteArrayOutputStream(szBufferStart);
		_writer = new PrintWriter(_buffer, true);
		_fDisposed = new OnceFlag();
	}
	
	private void innerFlush(boolean bImpliedNewline) {
		_writer.flush();
		if (_buffer.size() > 0 || bImpliedNewline) {
			String strText = _buffer.toString();
			_output.writeToAll(_channel, strText, bImpliedNewline);
			_buffer.reset();
		}
	}
	
	public TextChannelNumber channel() {
		return _channel;
	}

	@Override
	public void flush() {
		innerFlush(false);
	}

	@Override
	public CommonPrintWriter format(String format, Object... args) {
		_writer.format(format, args);
		return this;
	}

	@Override
	public CommonPrintWriter format(Locale lcl, String format, Object... args) {
		_writer.format(lcl, format, args);
		return this;
	}

	@Override
	public CommonPrintWriter printf(Locale lcl, String format, Object... args) {
		_writer.printf(lcl, format, args);
		return this;
	}

	@Override
	public CommonPrintWriter printf(String format, Object... args) {
		_writer.printf(format, args);
		return this;
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		ByteBuffer bufByte = ByteBuffer.wrap(buf, off, len);
		CharBuffer bufChar = StandardCharsets.UTF_8.decode(bufByte);
		_writer.write(bufChar.get());
	}

	@Override
	public void write(char[] buf, int off, int len) {
		_writer.write(buf, off, len);		
	}

	@Override
	public void write(String s) {
		_writer.write(s);
	}

	@Override
	public void write(String str, int off, int len) {
		_writer.write(str, off, len);
	}

	@Override
	public void write(char[] ca) {
		_writer.write(ca);
	}

	@Override
	public void write(int c) {
		_writer.write(c);
	}

	@Override
	public void write(byte[] buf) {
		ByteBuffer bufByte = ByteBuffer.wrap(buf);
		CharBuffer bufChar = StandardCharsets.UTF_8.decode(bufByte);
		_writer.write(bufChar.get());
	}

	@Override
	public void print(boolean bValue) {
		_writer.print(bValue);
	}

	@Override
	public void print(String s) {
		_writer.print(s);
	}

	@Override
	public void print(char[] ca) {
		_writer.print(ca);
	}

	@Override
	public void print(long lnValue) {
		_writer.print(lnValue);
	}

	@Override
	public void print(double dValue) {
		_writer.print(dValue);
	}

	@Override
	public void print(float fValue) {
		_writer.print(fValue);
	}

	@Override
	public void print(char c) {
		_writer.print(c);
	}

	@Override
	public void print(int nValue) {
		_writer.print(nValue);
	}

	@Override
	public void print(Object obj) {
		_writer.print(obj);
	}

	@Override
	public void println() {
		innerFlush(true);
	}

	@Override
	public void println(String s) {
		_writer.print(s);
		innerFlush(true);
	}

	@Override
	public void println(Object obj) {
		_writer.print(obj);
		innerFlush(true);
	}

	@Override
	public void println(float fValue) {
		_writer.print(fValue);
		innerFlush(true);
	}

	@Override
	public void println(double dValue) {
		_writer.print(dValue);
		innerFlush(true);
	}

	@Override
	public void println(char[] ca) {
		_writer.print(ca);
		innerFlush(true);
	}

	@Override
	public void println(boolean bValue) {
		_writer.print(bValue);
		innerFlush(true);
	}

	@Override
	public void println(char c) {
		_writer.print(c);
		innerFlush(true);
	}

	@Override
	public void println(int nValue) {
		_writer.print(nValue);
		innerFlush(true);
	}

	@Override
	public void println(long lnValue) {
		_writer.print(lnValue);
		innerFlush(true);
	}

	@Override
	public boolean checkError() {
		return false;
	}

	@Override
	public boolean isDisposed() {
		return _fDisposed.isSet();
	}

	@Override
	public void dispose() {
		if (!_fDisposed.isSet()) {
			_buffer.reset();
			_fDisposed.set();
		}	
	}
}
