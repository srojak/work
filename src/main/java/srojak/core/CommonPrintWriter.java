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
package srojak.core;

import java.io.Closeable;
import java.util.Locale;

import srojak.core.events.LifeCycleEventOriginating;

/**
 * @author Stephen
 *
 */
public interface CommonPrintWriter
		extends Closeable, LifeCycleEventOriginating {

	boolean isFaulted();
	boolean isClosed();
	void flush();
	CommonPrintWriter format(String format, Object ... args);
	CommonPrintWriter format(Locale lcl, String format, Object ... args);
	CommonPrintWriter printf(Locale lcl, String format, Object ... args);
	CommonPrintWriter printf(String format, Object ... args);
	void write(byte buf[], int off, int len);
	void write(char buf[], int off, int len);
	void write(String s);
	void write(String str, int off, int len);
	void write(char[] ca);
	void write(int c);
	void write(byte buf[]);
	void print(boolean bValue);
	void print(String s);
	void print(char[] ca);
	void print(long lnValue);
	void print(double dValue);
	void print(float fValue);
	void print(char c);
	void print(int nValue);
	void print(Object obj);
	void println();
	void println(String s);
	void println(Object obj);
	void println(float fValue);
	void println(double dValue);
	void println(char[] ca);
	void println(boolean bValue);
	void println(char c);
	void println(int nValue);
	void println(long lnValue);
	boolean checkError();
	
}
