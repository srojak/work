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
public class IntegerMutable 
		implements Serializable, Comparable<IntegerMutable> {
	private int _value;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2459254108562193781L;
	
	public IntegerMutable(int valueInitial) {
		_value = valueInitial;
	}
	
	public int getValue() {
		return _value;
	}
	
	public boolean setValue(int valueNew) {
		if (_value != valueNew) {
			_value = valueNew;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(IntegerMutable o) {
		Objects.requireNonNull(o, "o");
		return Integer.compare(_value, o._value);
	}

	@Override
	public int hashCode() {
		return _value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof IntegerMutable other) {
			return _value == other._value;
		} else if (obj instanceof Integer other) {
			return _value == other.intValue();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return Integer.toString(_value);
	}
	
	public static IntegerMutable parse(String str)
			throws NumberFormatException {
		Objects.requireNonNull(str, "str");
		return new IntegerMutable(Integer.parseInt(str));
	}

}
