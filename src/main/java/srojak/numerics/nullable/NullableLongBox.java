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

import srojak.core.excepts.NullValueException;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public final class NullableLongBox 
		extends NullableValueBase 
		implements NullableLong {
	private long _value;
	
	/**
	 * Null value constructor.
	 */
	public NullableLongBox() {
		super(true);
		_value = 0L;
	}

	/**
	 * Value constructor.
	 * @param value Value for the object.
	 */
	public NullableLongBox(long value) {
		super(false);
		_value = value;
	}

	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	@Override
	public long value() {
		if (_isNull) {
			throw new NullValueException("value is null");
		}
		return _value;
	}

	/**
	 * Get the value of the object, or a default value if the object is not {@code null}.
	 * @param valueIfNull The value to return if the object is {@code null}.
	 * @return the value of the object, or the parameter value.
	 */
	@Override
	public long valueOr(long valueIfNull) {
		if (_isNull) {
			return valueIfNull;
		}
		return _value;
	}

	/**
	 * Is the value equal to another instance of a {@code NullableLong}?
	 * @return {@code true} if the object and other instance are not {@code null}
	 *  and both have the same value.
	 *  
	 * This will not return {@code true} if {@code obj} is the same as {@code this}
	 *  and the object is {@code null}.
	 */
	@Override
	public boolean isEqual(NullableLong obj) {
		if (obj == null) {
			return false;
		} else if (_isNull || obj.isNull()) {
			return false;
		} else {
			return _value == obj.value();
		}
	}

	/**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * 
     * @param o The other object to which to compare.
     * @return A negative integer, zero, or a positive integer as this object
     * 	is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object <i>reference</i> is {@code null}.
     * @throws NullValueException if at least one operand is {@code null}.
     * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(NullableLong o) {
		Objects.requireNonNull(o, "o");
		if (_isNull || o.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return Long.compare(_value, o.value());
		}
	}

	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 */
	@Override
	public boolean isEqualTo(long value) {
		if (_isNull) {
			return false;
		} else {
			return _value == value;
		}
	}

	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, long value) {
		Objects.requireNonNull(comparison, "comparison");
		if (_isNull) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(Long.compare(_value, value));
			return new NullableBooleanBox(bCompar);
		}
	}

	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, NullableLong other) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(other, "other");
		if (_isNull || other.isNull()) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(Long.compare(_value, other.value()));
			return new NullableBooleanBox(bCompar);
		}
	}

	/**
	 * Increment the value of the object in place.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong increment() {
		if (!_isNull) {
			_value = Math.incrementExact(_value);
		}
		return this;
	}

	/**
	 * Decrement the value of the object in place.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong decrement() {
		if (!_isNull) {
			_value = Math.decrementExact(_value);
		}
		return this;
	}

	/**
	 * Increment the value of the object in place by a value.
	 * @param delta The value to which to increment.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong increment(long delta) {
		if (delta <= 0) {
			throw new IllegalArgumentException("delta must be positive");
		}
		if (!_isNull) {
			_value = Math.addExact(_value, delta);
		}
		return this;
	}

	/**
	 * Decrement the value of the object in place by a value.
	 * @param delta The value to which to increment.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong decrement(long delta) {
		if (delta <= 0) {
			throw new IllegalArgumentException("delta must be positive");
		}
		if (!_isNull) {
			_value = Math.subtractExact(_value, delta);
		}
		return this;
	}

	/**
	 * Add a value to the current object.
	 * @param value The value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong plus(long value) {
		if (_isNull) {
			return new NullableLongBox();
		}
		return new NullableLongBox(Math.addExact(_value, value));
	}

	/**
	 * Add a value to the current object.
	 * @param operand Contains the value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong plus(NullableLong operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableLongBox();
		}
		return new NullableLongBox(Math.addExact(_value, operand.value()));
	}

	/**
	 * Subtract a value from the current object.
	 * @param value The value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong minus(long value) {
		if (_isNull) {
			return new NullableLongBox();
		}
		return new NullableLongBox(Math.subtractExact(_value, value));
	}

	/**
	 * Subtract a value from the current object.
	 * @param operand Contains the value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong minus(NullableLong operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableLongBox();
		}
		return new NullableLongBox(Math.subtractExact(_value, operand.value()));
	}

	/**
	 * Subtract the current object from a value.
	 * @param value Contains the value from which to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong subtractFrom(long value) {
		if (_isNull) {
			return new NullableLongBox();
		}
		return new NullableLongBox(Math.subtractExact(value, _value));
	}

	/**
	 * Multiply the current object by a value.
	 * @param value The value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong times(long value) {
		if (_isNull) {
			return new NullableLongBox();
		}
		return new NullableLongBox(Math.multiplyExact(_value, value));
	}

	/**
	 * Multiply the current object by a value.
	 * @param operand Contains the value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong times(NullableLong operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableLongBox();
		}
		return new NullableLongBox(Math.multiplyExact(_value, operand.value()));
	}

	/**
	 * Divide the current object by a value.
	 * @param value The divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableLong dividedBy(long value) {
		if (_isNull) {
			return new NullableLongBox();
		}
		return new NullableLongBox(_value / value);
	}

	/**
	 * Divide the current object by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableLong dividedBy(NullableLong operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableLongBox();
		}
		return new NullableLongBox(_value / operand.value());
	}

	/**
	 * Divide a value by the current object.
	 * @param value The dividend.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableLong dividedInto(long value) {
		if (_isNull) {
			return new NullableLongBox();
		}
		return new NullableLongBox(value / _value);
	}

	/**
	 * Take the remainder of division by a value.
	 * @param value The divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableLong remainder(long value) {
		if (_isNull) {
			return new NullableLongBox();
		}
		return new NullableLongBox(_value % value);
	}

	/**
	 * Take the remainder of division by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableLong remainder(NullableLong operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableLongBox();
		}
		return new NullableLongBox(_value % operand.value());
	}

	/**
	 * Test the masked bits in the value.
	 * @param mask The mask of bits to test.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and any bit from the mask
	 * 	is non-zero in the value of the object.
	 */
	@Override
	public NullableBoolean bitTest(long mask) {
		if (_isNull) {
			return new NullableBooleanBox();
		} else {
			return new NullableBooleanBox((_value & mask) != 0);
		}
	}

	/**
	 * Take the absolute value of the current object.
	 * @return  A new object containing the absolute value or {@code null}.
	 */
	@Override
	public NullableLong abs() {
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(Math.abs(_value));
		}
	}

	/**
	 * Negate the current object.
	 * @return A new object containing the negated value or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableLong negate() {
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(Math.negateExact(_value));
		}
	}

	/**
	 * Create a {@code NullableInt} object like the current object.
	 * @return A {@code NullableInt} having the properties of the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 * @see Math#toIntExact(long)
	 */
	@Override
	public NullableInt toInt() {
		if (_isNull) {
			return new NullableIntBox();
		} else {
			return new NullableIntBox(Math.toIntExact(_value));
		}
	}

	/**
	 * Create a {@code NullableSmallint} object like the current object.
	 * @return A {@code NullableSmallint} having the properties of the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint toSmallint() {
		if (_isNull) {
			return new NullableSmallintBox();
		} else {
			return new NullableSmallintBox(Math.toIntExact(_value));
		}
	}

	/**
	 * Create a {@code NullableTinyint} object like the current object.
	 * @return A {@code NullableTinyint} having the properties of the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableTinyint toTinyint() {
		if (_isNull) {
			return new NullableTinyintBox();
		} else {
			return new NullableTinyintBox(Math.toIntExact(_value));
		}
	}

	/**
	 * Return a hash code for the object.
	 * @return A hash code for the instance.
	 * @see Long#hashCode(int)
	 */
	@Override
	public int hashCode() {
		if (_isNull) {
			return 0;
		} else {
			return Long.hashCode(_value);
		}
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
		} else if (obj instanceof NullableLong other) {
			return isEqual(other);
		} else if (obj instanceof Integer other) {
			return _value == other.intValue();
		} else {
			return false;
		}
	}

	/**
	 * Return a string representation of the current object.
	 * @return Either "null" or the string representation of the object's value.
	 */
	@Override
	public String toString() {
		if (_isNull) {
			return STR_NULL;
		} else {
			return Long.toString(_value);
		}
	}

	/**
	 * Return a string representation of the current object.
	 * @param radix The radix to use when converting the value.
	 * @return Either "null" or the string representation of the object's value
	 * 	in the given radix.
	 * @see Long#toString(int)
	 */
	@Override
	public String toString(int radix) {
		if (_isNull) {
			return STR_NULL;
		} else {
			return Long.toString(_value, radix);
		}
	}

	/**
	 * Clone the current object.
	 * @return another instance with the same properties as this object.
	 */
	@Override
	protected Object clone() 
			throws CloneNotSupportedException {
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(_value);
		}
	}

	/**
	 * Take the maximum value of one or more {@code NullableLong} objects.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableLong} object.
	 */
	public static NullableLong max(NullableLong first, NullableLong ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (first.isNull()) {
			return new NullableLongBox();
		}
		long answer = first.value();
		for (NullableLong next : rest) {
			if (next.isNull()) {
				return new NullableLongBox();
			}
			long value = next.value();
			if (answer < value) {
				answer = value;
			}
		}
		return new NullableLongBox(answer);
	}
	
	/**
	 * Take the minimum value of one or more {@code NullableLong} objects.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableLong} object.
	 */
	public static NullableLong min(NullableLong first, NullableLong ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (first.isNull()) {
			return new NullableLongBox();
		}
		long answer = first.value();
		for (NullableLong next : rest) {
			if (next.isNull()) {
				return new NullableLongBox();
			}
			long value = next.value();
			if (answer > value) {
				answer = value;
			}
		}
		return new NullableLongBox(answer);
	}
	
	/**
	 * Parse a {@code String} containing a representation of a {@code NullableLong}
	 *  in the given radix.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @param radix The radix to be used when parsing.
	 * @return A {@code NullableLong} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
	 * @throws NumberFormatException if the input does not contain a parseable representation
	 * 	of a {@code NullableLong}
	 * @see Long#parseLong(String, int)
	 */
	static NullableLong parse(String strInput, int radix)
			throws NumberFormatException {
		Objects.requireNonNull(strInput, "strInput");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(Long.parseLong(strInput, radix));
		}
	}
}
