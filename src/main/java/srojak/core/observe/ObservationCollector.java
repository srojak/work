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
package srojak.core.observe;

import java.io.PrintStream;

/**
 * @author Stephen
 *
 */
public interface ObservationCollector {
	
	public ObsLevel getLevel();
	public boolean isActive();
	public ObservationCollector append(boolean value);
	public ObservationCollector append(char value);
	public ObservationCollector append(int value);
	public ObservationCollector append(long value);
	public ObservationCollector append(float value);
	public ObservationCollector append(double value);
	public ObservationCollector append(String strText);
	public ObservationCollector append(Object obj);
	public ObservationCollector append(StringBuffer sbuf);
	public ObservationCollector append(CharSequence cs);
    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	public ObservationCollector append(CharSequence cs, int start, int end);
	public ObservationCollector append(char[] str);
    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	public ObservationCollector append(char[] str, int offset, int len);
	public ObservationCollector appendFormat(String format, Object... args);
	/**
	 * If called, must be called before commit.
	 * @param output
	 */
	public void alsoWriteTo(PrintStream output);
	public void commit();

}
