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

import java.util.Locale;
import java.util.Objects;

import srojak.core.CommonPrintWriter;

/**
 * @author Stephen
 *
 */
public class StdMultiChannelFullWriterTextCollector 
		extends AbstractMultiChannelBufferedTextCollector
		implements MultiChannelPrintWriterCollector {

	/**
	 * 
	 */
	public StdMultiChannelFullWriterTextCollector() {
		super();
	}

	@Override
	public void println(TextChannelNumber channel) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println();
	}

	@Override
	public void println(TextChannelNumber channel, String s) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println(s);
	}

	@Override
	public void println(TextChannelNumber channel, Object obj) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println(obj);
	}

	@Override
	public void println(TextChannelNumber channel, float fValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println(fValue);
	}

	@Override
	public void println(TextChannelNumber channel, double dValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println(dValue);
	}

	@Override
	public void println(TextChannelNumber channel, char[] ca) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println(ca);
	}

	@Override
	public void println(TextChannelNumber channel, boolean bValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println(bValue);
	}

	@Override
	public void println(TextChannelNumber channel, char c) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println(c);
	}

	@Override
	public void println(TextChannelNumber channel, int nValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println(nValue);
	}

	@Override
	public void println(TextChannelNumber channel, long lnValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.println(lnValue);
	}

	@Override
	public void flush(TextChannelNumber channel) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.flush();
	}

	@Override
	public MultiChannelPrintWriter format(TextChannelNumber channel, String format, Object... args) {
		Objects.requireNonNull(channel, "channel");
		Objects.requireNonNull(format, "format");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.format(format, args);
		return this;
	}

	@Override
	public MultiChannelPrintWriter format(TextChannelNumber channel, Locale lcl, String format, Object... args) {
		Objects.requireNonNull(channel, "channel");
		Objects.requireNonNull(lcl, "lcl");
		Objects.requireNonNull(format, "format");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.format(lcl, format, args);
		return this;
	}

	@Override
	public MultiChannelPrintWriter printf(TextChannelNumber channel, Locale lcl, String format, Object... args) {
		Objects.requireNonNull(channel, "channel");
		Objects.requireNonNull(lcl, "lcl");
		Objects.requireNonNull(format, "format");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.printf(lcl, format, args);
		return this;
	}

	@Override
	public MultiChannelPrintWriter printf(TextChannelNumber channel, String format, Object... args) {
		Objects.requireNonNull(channel, "channel");
		Objects.requireNonNull(format, "format");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.printf(format, args);
		return this;
	}

	@Override
	public void write(TextChannelNumber channel, byte[] buf, int off, int len) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.write(buf, off, len);
	}

	@Override
	public void write(TextChannelNumber channel, char[] buf, int off, int len) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.write(buf, off, len);
	}

	@Override
	public void write(TextChannelNumber channel, String s) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.write(s);
	}

	@Override
	public void write(TextChannelNumber channel, String str, int off, int len) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.write(str, off, len);
	}

	@Override
	public void write(TextChannelNumber channel, char[] ca) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.write(ca);
	}

	@Override
	public void write(TextChannelNumber channel, int c) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.write(c);
	}

	@Override
	public void write(TextChannelNumber channel, byte[] buf) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.write(buf);
	}

	@Override
	public void print(TextChannelNumber channel, boolean bValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.print(bValue);
	}

	@Override
	public void print(TextChannelNumber channel, String s) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.print(s);
	}

	@Override
	public void print(TextChannelNumber channel, char[] ca) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.print(ca);
	}

	@Override
	public void print(TextChannelNumber channel, long lnValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.print(lnValue);
	}

	@Override
	public void print(TextChannelNumber channel, double dValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.print(dValue);
	}

	@Override
	public void print(TextChannelNumber channel, float fValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.print(fValue);
	}

	@Override
	public void print(TextChannelNumber channel, char c) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.print(c);
	}

	@Override
	public void print(TextChannelNumber channel, int nValue) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.print(nValue);
	}

	@Override
	public void print(TextChannelNumber channel, Object obj) {
		Objects.requireNonNull(channel, "channel");
		CommonPrintWriter writer = getWriterForChannel(channel);
		if (writer == null) {
			throw new IllegalArgumentException("channel is not defined");
		}
		writer.print(obj);
	}

}
