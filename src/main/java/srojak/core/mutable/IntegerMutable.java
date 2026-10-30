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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Stephen
 *
 */
public class IntegerMutable
		extends MutableBase
		implements Serializable, Comparable<IntegerMutable> {
	private final AtomicInteger _value;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2459254108562193781L;
	
	public IntegerMutable(int valueInitial) {
		super();
		_value = new AtomicInteger(valueInitial);
	}
	
	public int getValue() {
		return _value.get();
	}
	
	public boolean setValue(int valueNew) {
		int valuePrior = _value.getAndSet(valueNew);
		if (valuePrior != valueNew) {
			whenValueChanged();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(IntegerMutable o) {
		Objects.requireNonNull(o, "o");
		return Integer.compare(_value.get(), o._value.get());
	}

	@Override
	public int hashCode() {
		return _value.get();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof IntegerMutable other) {
			return _value.get() == other._value.get();
		} else if (obj instanceof Integer other) {
			return _value.get() == other.intValue();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return Integer.toString(_value.get());
	}
	
	public static IntegerMutable parse(String str)
			throws NumberFormatException {
		Objects.requireNonNull(str, "str");
		return new IntegerMutable(Integer.parseInt(str));
	}

	public static IntegerMutable parse(String str, int radix)
			throws NumberFormatException {
		Objects.requireNonNull(str, "str");
		return new IntegerMutable(Integer.parseInt(str, radix));
	}
}
