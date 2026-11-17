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

import srojak.core.CommonPrintWriter;

/**
 * @author Stephen
 *
 */
public interface MultiChannelPrintWriter {
	
	void flush(TextChannelNumber channel);
	MultiChannelPrintWriter format(TextChannelNumber channel, String format, Object ... args);
	MultiChannelPrintWriter format(TextChannelNumber channel, Locale lcl, String format, Object ... args);
	MultiChannelPrintWriter printf(TextChannelNumber channel, Locale lcl, String format, Object ... args);
	MultiChannelPrintWriter printf(TextChannelNumber channel, String format, Object ... args);
	void write(TextChannelNumber channel, byte buf[], int off, int len);
	void write(TextChannelNumber channel, char buf[], int off, int len);
	void write(TextChannelNumber channel, String s);
	void write(TextChannelNumber channel, String str, int off, int len);
	void write(TextChannelNumber channel, char[] ca);
	void write(TextChannelNumber channel, int c);
	void write(TextChannelNumber channel, byte buf[]);
	void print(TextChannelNumber channel, boolean bValue);
	void print(TextChannelNumber channel, String s);
	void print(TextChannelNumber channel, char[] ca);
	void print(TextChannelNumber channel, long lnValue);
	void print(TextChannelNumber channel, double dValue);
	void print(TextChannelNumber channel, float fValue);
	void print(TextChannelNumber channel, char c);
	void print(TextChannelNumber channel, int nValue);
	void print(TextChannelNumber channel, Object obj);
	void println(TextChannelNumber channel);
	void println(TextChannelNumber channel, String s);
	void println(TextChannelNumber channel, Object obj);
	void println(TextChannelNumber channel, float fValue);
	void println(TextChannelNumber channel, double dValue);
	void println(TextChannelNumber channel, char[] ca);
	void println(TextChannelNumber channel, boolean bValue);
	void println(TextChannelNumber channel, char c);
	void println(TextChannelNumber channel, int nValue);
	void println(TextChannelNumber channel, long lnValue);
}
