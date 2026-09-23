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
public interface SingleObservationCollector {
	
	public ObsLevel getLevel();
	public boolean isActive();
	public SingleObservationCollector append(boolean value);
	public SingleObservationCollector append(char value);
	public SingleObservationCollector append(int value);
	public SingleObservationCollector append(long value);
	public SingleObservationCollector append(float value);
	public SingleObservationCollector append(double value);
	public SingleObservationCollector append(String strText);
	public SingleObservationCollector append(Object obj);
	public SingleObservationCollector append(StringBuffer sbuf);
	public SingleObservationCollector append(CharSequence cs);
    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	public SingleObservationCollector append(CharSequence cs, int start, int end);
	public SingleObservationCollector append(char[] str);
    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	public SingleObservationCollector append(char[] str, int offset, int len);
	public SingleObservationCollector appendFormat(String format, Object... args);
	/**
	 * If called, must be called before commit.
	 * @param output
	 */
	public void alsoWriteTo(PrintStream output);
	public void commit();

}
