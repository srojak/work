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
package srojak.sql.names;

import java.util.Objects;

import srojak.core.Equatable;
import srojak.core.StringComparable;

/**
 * @author Stephen
 *
 */
public class SQLNamePart
		implements Equatable<SQLNamePart>, Comparable<SQLNamePart>, StringComparable {
	private final String _text;
	private final boolean _mustQuote;
	
	public SQLNamePart(String strName) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_text = strName;
		boolean mustQuote = false;
		if (Character.isDigit(_text.charAt(0))) {
			mustQuote = true;
		} else if (_text.contains(" ")) {
			mustQuote = true;
		}
		_mustQuote = mustQuote;
	}

	@Override
	public int compareToString(String other) {
		Objects.requireNonNull(other, "other");
		return _text.compareTo(other);
	}

	@Override
	public int compareTo(SQLNamePart o) {
		Objects.requireNonNull(o, "o");
		return _text.compareTo(o._text);
	}

	@Override
	public boolean isEqual(SQLNamePart obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else {
			return _text.equals(obj._text);
		}
	}

	@Override
	public int hashCode() {
		return _text.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SQLNamePart other) {
			return isEqual(other);
		} else if (obj instanceof String other) {
			return _text.equals(other);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		if (_mustQuote) {
			return "[" + _text + "]";
		} else {
			return _text;
		}
	}
}
