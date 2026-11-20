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

import java.math.BigInteger;
import java.util.Objects;

import srojak.core.excepts.NullValueException;
import srojak.core.field.LazyInt;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public final class NullableBigIntValue 
		extends NullableValueBase 
		implements NullableBigInt {
	private final BigInteger _value;
	private final LazyInt _hash;
	
	NullableBigIntValue(BigInteger value) {
		super(false);
		Objects.requireNonNull(value, "value");
		_value = value;
		_hash = new LazyInt(() -> _value.hashCode());
	}
	
	NullableBigIntValue() {
		super(true);
		_value = BigInteger.ZERO;
		_hash = new LazyInt(() -> 0);
	}

	@Override
	public boolean isEqual(NullableBigInt obj) {
		if (obj == null) {
			return false;
		} else if (_isNull || obj.isNull()) {
			return false;
		} else {
			return _value.equals(obj.value());
		}
	}

	@Override
	public int compareTo(NullableBigInt o) {
		Objects.requireNonNull(o, "o");
		if (_isNull || o.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return _value.compareTo(o.value());
		}
	}

	@Override
	public BigInteger value() {
		if (_isNull) {
			throw new NullValueException("value is null");
		}
		return _value;
	}

	@Override
	public BigInteger valueOr(BigInteger valueIfNull) {
		Objects.requireNonNull(valueIfNull, "valueIfNull");
		if (_isNull) {
			return valueIfNull;
		}
		return _value;
	}

	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 * @throws NullPointerException if {@code value} is reference null.
	 */
	@Override
	public boolean isEqualTo(BigInteger value) {
		Objects.requireNonNull(value, "value");
		if (_isNull) {
			return false;
		} else {
			return _value.equals(value);
		}
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, BigInteger value) {
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
	public NullableBoolean compareTo(OrderedComparison comparison, NullableBigInt other) {
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
	public NullableBigInt plus(BigInteger value) {
		Objects.requireNonNull(value, "value");
		if (_isNull) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.add(value));
	}

	@Override
	public NullableBigInt plus(NullableBigInt operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.add(operand.value()));
	}

	@Override
	public NullableBigInt minus(BigInteger value) {
		Objects.requireNonNull(value, "value");
		if (_isNull) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.subtract(value));
	}

	@Override
	public NullableBigInt minus(NullableBigInt operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.subtract(operand.value()));
	}

	@Override
	public NullableBigInt subtractFrom(BigInteger value) {
		Objects.requireNonNull(value, "value");
		if (_isNull) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(value.subtract(_value));
	}

	@Override
	public NullableBigInt times(BigInteger value) {
		Objects.requireNonNull(value, "value");
		if (_isNull) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.multiply(value));
	}

	@Override
	public NullableBigInt times(NullableBigInt operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.multiply(operand.value()));
	}

	@Override
	public NullableBigInt dividedBy(BigInteger value) {
		Objects.requireNonNull(value, "value");
		if (_isNull) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.divide(value));
	}

	@Override
	public NullableBigInt dividedBy(NullableBigInt operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.divide(operand.value()));
	}

	@Override
	public NullableBigInt dividedInto(BigInteger value) {
		Objects.requireNonNull(value, "value");
		if (_isNull) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(value.divide(_value));
	}

	@Override
	public NullableBigInt remainder(BigInteger value) {
		Objects.requireNonNull(value, "value");
		if (_isNull) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.remainder(value));
	}

	@Override
	public NullableBigInt remainder(NullableBigInt operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.remainder(operand.value()));
	}

	@Override
	public NullableBigInt[] divideAndRemainder(NullableBigInt operand) {
		Objects.requireNonNull(operand, "operand");
		NullableBigInt[] result = new NullableBigInt[2];
		if (_isNull || operand.isNull()) {
			result[0] = result[1] = new NullableBigIntValue();
		} else {
			BigInteger[] biResult = _value.divideAndRemainder(operand.value());
			result[0] = new NullableBigIntValue(biResult[0]);
			result[1] = new NullableBigIntValue(biResult[1]);
		}
		return result;
	}

	@Override
	public NullableBigInt abs() {
		if (_isNull) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.abs());
	}

	@Override
	public NullableBigInt negate() {
		if (_isNull) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.negate());
	}

	@Override
	public NullableBigInt max(NullableBigInt operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.max(operand.value()));
	}

	@Override
	public NullableBigInt min(NullableBigInt operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableBigIntValue();
		}
		return new NullableBigIntValue(_value.min(operand.value()));
	}

	/**
	 * Return a hash code for the object.
	 * @return A hash code for the instance.
	 * @see Long#hashCode(int)
	 */
	@Override
	public int hashCode() {
		return _hash.get();
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
		} else if (obj instanceof NullableBigInt other) {
			return isEqual(other);
		} else if (obj instanceof BigInteger other) {
			return _value.equals(other);
		} else {
			return false;
		}
	}

	@Override
	protected Object clone() throws 
			CloneNotSupportedException {
		if (_isNull) {
			return new NullableBigIntValue();
		} else {
			return new NullableBigIntValue(_value);
		}
	}

	@Override
	public String toString() {
		if (_isNull) {
			return STR_NULL;
		} else {
			return _value.toString();
		}
	}

	@Override
	public String toString(int radix) {
		if (_isNull) {
			return STR_NULL;
		} else {
			return _value.toString(radix);
		}
	}

	static NullableBigInt parse(String strInput, int radix)
			throws NumberFormatException {
		Objects.requireNonNull(strInput, "strInput");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableBigIntValue();
		} else {
			return new NullableBigIntValue(new BigInteger(strInput, radix));
		}
	}
}
