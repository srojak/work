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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import srojak.core.CommonPrintWriter;

/**
 * @author Stephen
 *
 */
public abstract class LineBasedTextPrinterBase
		extends ClosableBase 
		implements CommonPrintWriter {
	private final ByteArrayOutputStream _buffer;
	private final PrintWriter _writer;

	/**
	 * 
	 */
	protected LineBasedTextPrinterBase() {
		super();
		_buffer = new ByteArrayOutputStream();
		_writer = new PrintWriter(_buffer, true);
	}
	
	protected void setFaulted() {
		_flags.set(FLAGS_FAULTED);
	}
	
	protected void clearFaulted() {
		_flags.clear(FLAGS_FAULTED);
	}
	
	@Override
	public boolean isFaulted() {
		return _flags.test(FLAGS_FAULTED);
	}
	
	protected abstract void writeFromBuffer(ByteArrayOutputStream buffer)
			throws IOException;
	
	private void innerWrite() {
		try {
			writeFromBuffer(_buffer);
		} catch (IOException exc) {
			_flags.set(FLAGS_FAULTED);
		}
		_buffer.reset();
	}

	@Override
	public void flush() {
		innerWrite();
	}

	@Override
	public CommonPrintWriter format(String format, Object... args) {
		_writer.format(format, args);
		innerWrite();
		return this;
	}

	@Override
	public CommonPrintWriter format(Locale lcl, String format, Object... args) {
		_writer.format(lcl, format, args);
		innerWrite();
		return this;
	}

	@Override
	public CommonPrintWriter printf(Locale lcl, String format, Object... args) {
		_writer.printf(lcl, format, args);
		innerWrite();
		return this;
	}

	@Override
	public CommonPrintWriter printf(String format, Object... args) {
		_writer.printf(format, args);
		innerWrite();
		return this;
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		ByteBuffer bufByte = ByteBuffer.wrap(buf, off, len);
		CharBuffer bufChar = StandardCharsets.UTF_8.decode(bufByte);
		_writer.write(bufChar.get());
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
		_writer.println();
		innerWrite();
	}

	@Override
	public void println(String s) {
		_writer.println(s);
		innerWrite();
	}

	@Override
	public void println(Object obj) {
		_writer.println(obj);
		innerWrite();
	}

	@Override
	public void println(float fValue) {
		_writer.println(fValue);
		innerWrite();
	}

	@Override
	public void println(double dValue) {
		_writer.println(dValue);
		innerWrite();
	}

	@Override
	public void println(char[] ca) {
		_writer.println(ca);
		innerWrite();
	}

	@Override
	public void println(boolean bValue) {
		_writer.println(bValue);
		innerWrite();
	}

	@Override
	public void println(char c) {
		_writer.println(c);
		innerWrite();
	}

	@Override
	public void println(int nValue) {
		_writer.println(nValue);
		innerWrite();
	}

	@Override
	public void println(long lnValue) {
		_writer.println(lnValue);
		innerWrite();
	}

	@Override
	public boolean checkError() {
		return _flags.test(FLAGS_FAULTED);
	}

}
