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
package srojak.core.text;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class PartialTextBuffer 
		implements TextBufferSegment {
	private final char[] _buffer;
	private final int _start;
	private final int _length;
	
	public PartialTextBuffer(char[] buffer, int start, int length) {
		Objects.requireNonNull(buffer, "buffer");
		_buffer = buffer;
		_start = start;
		_length = length;
	}

	@Override
	public char[] getBuffer() {
		return _buffer;
	}

	@Override
	public int getStart() {
		return _start;
	}

	@Override
	public int getLength() {
		return _length;
	}

	@Override
	public char charAtOffset(int index) {
		Objects.checkIndex(index, _length);
		return _buffer[index + _start];
	}

	@Override
	public String copySegment() {
		StringBuilder sb = new StringBuilder();
		sb.append(_buffer, _start, _length);
		return sb.toString();
	}

	@Override
	public String toString() {
		return copySegment();
	}

}
