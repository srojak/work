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
public class BooleanMutable 
		implements Serializable, Comparable<BooleanMutable> {
	private boolean _value;
	
	private final static int HASH_TRUE = 1231;
	private final static int HASH_FALSE = 1237;
	private final static String STRING_TRUE = "true";
	private final static String STRING_FALSE = "false";
	/**
	 * 
	 */
	private static final long serialVersionUID = -8450444053086562099L;


	/**
	 * 
	 */
	public BooleanMutable(boolean bInitial) {
		_value = bInitial;
	}

	public boolean getValue() {
		return _value;
	}
	
	public boolean setValue(boolean bNew) {
		if (bNew != _value) {
			_value = bNew;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(BooleanMutable o) {
		Objects.requireNonNull(o, "o");
		return Boolean.compare(_value, o._value);
	}

	@Override
	public int hashCode() {
		return _value ? HASH_TRUE : HASH_FALSE;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof BooleanMutable other) {
			return _value == other._value;
		} else if (obj instanceof Boolean other) {
			return _value == other.booleanValue();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return _value ? STRING_TRUE : STRING_FALSE;
	}
	
	public static BooleanMutable parse(String str) {
		Objects.requireNonNull(str, "str");
		if (str.length() == 1) {
			return new BooleanMutable("t".equals(str));
		} else {
			return new BooleanMutable(STRING_TRUE.equalsIgnoreCase(str));
		}
	}
}
