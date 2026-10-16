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

import java.util.Objects;

import srojak.core.StringComparable;

/**
 * @author Stephen
 *
 */
public class TextMultiPartKeyPart 
		implements MultiPartKeyPart, StringComparable {
	private final String _text;
	
	public TextMultiPartKeyPart(String strText) {
		Objects.requireNonNull(strText, "strText");
		if (strText.isBlank()) {
			throw new IllegalArgumentException("strText is blank");
		}
		_text = strText;
	}

	@Override
	public String content() {
		return _text;
	}

	@Override
	public boolean isEqual(MultiPartKeyPart obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else {
			return _text.equals(obj.content());
		}
	}

	@Override
	public int hashCode() {
		return _text.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MultiPartKeyPart other) {
			return isEqual(other);
		} else if (obj instanceof String s) {
			return _text.equals(s);
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(MultiPartKeyPart o) {
		if (o == null) {
			return 1;
		} else {
			return _text.compareTo(o.content());
		}
	}

	@Override
	public int compareToString(String other) {
		if (other == null) {
			return 1;
		} else {
			return _text.compareTo(other);
		}
	}

	@Override
	public String toString() {
		return _text;
	}
}
