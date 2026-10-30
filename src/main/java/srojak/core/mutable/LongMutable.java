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
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Stephen
 *
 */
public class LongMutable 
		extends MutableBase
		implements Serializable, Comparable<LongMutable> {
	private AtomicLong _value;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1205287627908292355L;

	/**
	 * 
	 */
	public LongMutable(long valueInitial) {
		super();
		_value = new AtomicLong(valueInitial);
	}
	
	public long getValue() {
		return _value.get();
	}
	
	public boolean setValue(long valueNew) {
		long valuePrior = _value.getAndSet(valueNew);
		if (valuePrior != valueNew) {
			whenValueChanged();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(LongMutable o) {
		Objects.requireNonNull(o, "o");
		return Long.compare(_value.get(), o._value.get());
	}

	@Override
	public int hashCode() {
		return Long.hashCode(_value.get());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof LongMutable other) {
			return _value.get() == other._value.get();
		} else if (obj instanceof Long other) {
			return _value.get() == other.longValue();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return Long.toString(_value.get());
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
