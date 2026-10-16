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
package srojak.core.observe.code;

import srojak.core.observe.TextObservation;

/**
 * @author Stephen
 *
 */
public interface CodeObservationWriting {

	CodeObservationWriting appendContent(String strText);
	CodeObservationWriting appendContent(Object obj);
	CodeObservationWriting appendContent(boolean value);
	CodeObservationWriting appendContent(char value);
	CodeObservationWriting appendContent(int value);
	CodeObservationWriting appendContent(long value);
	CodeObservationWriting appendContent(float value);
	CodeObservationWriting appendContent(double value);
	CodeObservationWriting appendContent(StringBuffer sbuf);
	CodeObservationWriting appendContent(CharSequence cs);
    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	CodeObservationWriting appendContent(CharSequence cs, int start, int end);
	CodeObservationWriting appendContent(char[] str);
    /**
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	CodeObservationWriting appendContent(char[] str, int offset, int len);
	CodeObservationWriting formatContent(String format, Object... args);
	CodeObservationWriting appendValue(String strInitial, CodeValue cdValue);
	CodeObservationWriting appendFrom(TextObservation obsText);
}
