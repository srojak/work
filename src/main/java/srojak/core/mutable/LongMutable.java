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
package srojak.core.mutable;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class LongMutable 
		extends MutableBase
		implements Serializable, Comparable<LongMutable> {
	private long _value;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1205287627908292355L;

	/**
	 * 
	 */
	public LongMutable(long valueInitial) {
		super();
		_value = valueInitial;
	}
	
	public long getValue() {
		return _value;
	}
	
	public boolean setValue(long valueNew) {
		if (_value != valueNew) {
			_value = valueNew;
			whenValueChanged();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(LongMutable o) {
		Objects.requireNonNull(o, "o");
		return Long.compare(_value, o._value);
	}

	@Override
	public int hashCode() {
		return Long.hashCode(_value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof LongMutable other) {
			return _value == other._value;
		} else if (obj instanceof Long other) {
			return _value == other.longValue();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return Long.toString(_value);
	}
	
	public static LongMutable parse(String str)
			throws NumberFormatException {
		Objects.requireNonNull(str, "str");
		return new LongMutable(Long.parseLong(str));
	}

	public static LongMutable parse(String str, int radix)
			throws NumberFormatException {
		Objects.requireNonNull(str, "str");
		return new LongMutable(Long.parseLong(str, radix));
	}
}
