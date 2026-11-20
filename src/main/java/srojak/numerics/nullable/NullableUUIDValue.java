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
package srojak.numerics.nullable;

import java.util.Objects;
import java.util.UUID;

import srojak.core.excepts.NullValueException;
import srojak.core.field.Lazy;
import srojak.numerics.GuidMethods;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public class NullableUUIDValue 
		extends NullableValueBase 
		implements NullableUUID {
	private final UUID _value;
	private final Lazy<String> _strValue;
	
	NullableUUIDValue(UUID value) {
		super(false);
		Objects.requireNonNull(value, "value");
		_value = value;
		_strValue = new Lazy<String>(() -> _value.toString());
	}
	
	NullableUUIDValue() {
		super(true);
		_value = ZERO;
		_strValue = new Lazy<String>(() -> STR_NULL);
	}

	@Override
	public boolean isEqual(NullableUUID obj) {
		if (obj == null) {
			return false;
		} else if (_isNull || obj.isNull()) {
			return false;
		} else {
			return _value.equals(obj.value());
		}
	}

	@Override
	public int compareTo(NullableUUID o) {
		Objects.requireNonNull(o, "o");
		if (_isNull || o.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return _value.compareTo(o.value());
		}
	}

	@Override
	public UUID value() {
		if (_isNull) {
			throw new NullValueException("value is null");
		}
		return _value;
	}

	@Override
	public UUID valueOrZero() {
		if (_isNull) {
			return ZERO;
		} else {
			return _value;
		}
	}

	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 * @throws NullPointerException if {@code value} is reference null.
	 */
	@Override
	public boolean isEqualTo(UUID value) {
		Objects.requireNonNull(value, "value");
		if (_isNull) {
			return false;
		} else {
			return _value.equals(value);
		}
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, UUID value) {
		Objects.requireNonNull(value, "value");
		Objects.requireNonNull(comparison, "comparison");
		if (_isNull) {
			return new NullableBooleanValue();
		} else {
			boolean bCompar = comparison.evaluate(_value.compareTo(value));
			return new NullableBooleanValue(bCompar);
		}
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, NullableUUID other) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(other, "other");
		if (_isNull || other.isNull()) {
			return new NullableBooleanValue();
		} else {
			boolean bCompar = comparison.evaluate(_value.compareTo(other.value()));
			return new NullableBooleanValue(bCompar);
		}
	}

	@Override
	public byte[] toGuidBytes() {
		if (_isNull) {
			return null;
		} else {
			return GuidMethods.toGuidBytes(_value);
		}
	}

	/**
	 * Return a hash code for the object.
	 * @return A hash code for the instance.
	 * @see Long#hashCode(int)
	 */
	@Override
	public int hashCode() {
		return _value.hashCode();
	}

	/**
	 * Test another object for equality to the current object.
	 * @return {@code true} if the other object should be considered equal.
	 *  
	 * This will not return {@code true} if {@code obj} is the same as {@code this}
	 *  and the object is {@code null}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (_isNull || obj == null) {
			return false;
		} else if (obj instanceof NullableUUIDValue other) {
			return isEqual(other);
		} else if (obj instanceof UUID other) {
			return _value.equals(other);
		} else {
			return false;
		}
	}

	@Override
	protected Object clone() throws 
			CloneNotSupportedException {
		if (_isNull) {
			return new NullableUUIDValue();
		} else {
			return new NullableUUIDValue(_value);
		}
	}

	@Override
	public String toString() {
		return _strValue.get();
	}

	static NullableUUID fromString(String strInput) {
		Objects.requireNonNull(strInput, "strInput");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableUUIDValue();
		} else {
			return new NullableUUIDValue(UUID.fromString(strInput));
		}
	}
}
