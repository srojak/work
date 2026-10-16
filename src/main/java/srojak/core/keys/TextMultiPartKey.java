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
package srojak.core.keys;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class TextMultiPartKey 
		implements MultiPartKey {
	private final TextMultiPartKeyPart[] _parts;
	
	public static final char SEPARATOR = '|';
	public static final TextMultiPartKey NULL = new TextMultiPartKey();
	
	public TextMultiPartKey(TextMultiPartKeyPart ... parts) {
		Objects.requireNonNull(parts, "parts");
		if (parts.length == 0) {
			throw new IllegalArgumentException("key must have at least one part");
		}
		_parts = parts;
	}
	
	private TextMultiPartKey() {
		_parts = new TextMultiPartKeyPart[0];
	}

	@Override
	public int size() {
		return _parts.length;
	}

	@Override
	public MultiPartKeyPart getPart(int index) {
		Objects.checkIndex(index, _parts.length);
		return _parts[index];
	}

	@Override
	public boolean isEqual(MultiPartKey obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else {
			if (_parts.length == obj.size()) {
				for (int idx = 0; idx < _parts.length; idx++) {
					if (!_parts[idx].equals(obj.getPart(idx).content())) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
			return Arrays.hashCode(_parts);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MultiPartKey other) {
			return isEqual(other);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(MultiPartKey o) {
		if (o == null) {
			return 1;
		} else {
			int stop = Math.min(_parts.length, o.size());
			for (int idx = 0; idx < stop; idx++) {
				int ncompar = _parts[idx].compareTo(o.getPart(idx));
				if (ncompar != 0) {
					return ncompar;
				}
			}
			return _parts.length - o.size();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int idx = 0;
		if (idx < _parts.length) {
			sb.append(_parts[idx++]);
		}
		while (idx < _parts.length) {
			sb.append(SEPARATOR);
			sb.append(_parts[idx++]);
		}
		return sb.toString();
	}
}
