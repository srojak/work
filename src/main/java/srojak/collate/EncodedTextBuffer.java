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
package srojak.collate;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class EncodedTextBuffer
		implements EncodedText {
	private final Charset _charset;
	private final byte[] _bytes;
	
	public EncodedTextBuffer(CharsetEncoder encoder, String strText) throws CharacterCodingException {
		Objects.requireNonNull(encoder, "encoder");
		Objects.requireNonNull(strText, "strText");
		_charset = encoder.charset();
		CharBuffer cbuf = CharBuffer.wrap(strText);
		ByteBuffer ebuf = encoder.encode(cbuf);
		_bytes = new byte[ebuf.remaining()];
		ebuf.get(_bytes);
	}

	@Override
	public Charset encoding() {
		return _charset;
	}

	@Override
	public boolean isEmpty() {
		return _bytes.length == 0;
	}

	@Override
	public int length() {
		return _bytes.length;
	}

	@Override
	public byte[] content() {
		return _bytes;
	}

	@Override
	public int compareTo(EncodedText other) {
		return Arrays.compareUnsigned(_bytes, other.content());
	}

	@Override
	public int indexOf(EncodedText other, int fromIndex) {
		if (other.isEmpty()) {
			return -1;
		}
		byte byteStart = other.content()[0];
		int lenOther = other.length();
		int idx = fromIndex;
		while (idx < _bytes.length) {
			if (_bytes[idx] == byteStart) {
				if (Arrays.compareUnsigned(_bytes, idx, idx + lenOther, other.content(), 0, other.length()) == 0) {
					return idx;
				}
				idx++;
			}
		}
		return 0;
	}
}
