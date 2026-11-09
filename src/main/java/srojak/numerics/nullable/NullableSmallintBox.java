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
public final class NullableSmallintBox 
		extends NullableValueBase 
		implements NullableSmallint {
	private short _value;

	/**
	 * Null value constructor.
	 */
	NullableSmallintBox() {
		super(true);
		_value = 0;
	}
	
	/**
	 * Value constructor.
	 * @param value Value for the object.
	 */
	NullableSmallintBox(short value) {
		super(false);
		_value = value;
	}
	
	/**
	 * Value constructor for expressions
	 * @param value {@code int} value for the object.
	 */
	NullableSmallintBox(int value) {
		super(false);
		short v = (short) value;
		if (v != value) {
            throw new ArithmeticException("smallint overflow");
		}
		_value = v;
	}


	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	@Override
	public short value() {
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
	public short valueOr(short valueIfNull) {
		if (_isNull) {
			return valueIfNull;
		}
		return _value;
	}

	/**
	 * Is the value equal to another instance of a {@code NullableSmallint}?
	 * @return {@code true} if the object and other instance are not {@code null}
	 *  and both have the same value.
	 *  
	 * This will not return {@code true} if {@code obj} is the same as {@code this}
	 *  and the object is {@code null}.
	 */
	@Override
	public boolean isEqual(NullableSmallint obj) {
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
	public int compareTo(NullableSmallint o) {
		Objects.requireNonNull(o, "o");
		if (_isNull || o.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return Integer.compare(_value, o.value());
		}
	}

	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 */
	@Override
	public boolean isEqualTo(short value) {
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
	public NullableBoolean compareTo(OrderedComparison comparison, short value) {
		Objects.requireNonNull(comparison, "comparison");
		if (_isNull) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(Short.compare(_value, value));
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
	public NullableBoolean compareTo(OrderedComparison comparison, NullableSmallint other) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(other, "other");
		if (_isNull || other.isNull()) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(Short.compare(_value, other.value()));
			return new NullableBooleanBox(bCompar);
		}
	}
	
	private static short safeAdd(short x, short y) {
		short r = (short) (x + y);
        if (((x ^ y) & (x ^ r)) < 0) {
            throw new ArithmeticException("smallint overflow");
        }
        return r;
	}
	
	private static short safeSubtract(short x, short y) {
		short r = (short) (x - y);
        if (((x ^ y) & (x ^ r)) < 0) {
            throw new ArithmeticException("smallint overflow");
        }
        return r;
	}

	private static short safeMultiply(short x, short y) {
		int r = x * y;
		short s = (short) r;
		if (s != r) {
            throw new ArithmeticException("smallint overflow");
		}
		return s;
	}

	/**
	 * Increment the value of the object in place.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint increment() {
		if (!_isNull) {
			if (_value == Short.MAX_VALUE) {
	            throw new ArithmeticException("smallint overflow");
			}
			_value++;
		}
		return this;
	}

	/**
	 * Decrement the value of the object in place.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint decrement() {
		if (!_isNull) {
			if (_value == Short.MIN_VALUE) {
	            throw new ArithmeticException("smallint overflow");
			}
			_value--;
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
	public NullableSmallint increment(short delta) {
		if (delta <= 0) {
			throw new IllegalArgumentException("delta must be positive");
		}
		if (!_isNull) {
			_value = safeAdd(_value, delta);
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
	public NullableSmallint decrement(short delta) {
		if (delta <= 0) {
			throw new IllegalArgumentException("delta must be positive");
		}
		if (!_isNull) {
			_value = safeSubtract(_value, delta);
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
	public NullableSmallint plus(short value) {
		if (_isNull) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(safeAdd(_value, value));
	}

	/**
	 * Add a value to the current object.
	 * @param operand Contains the value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint plus(NullableSmallint operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(safeAdd(_value, operand.value()));
	}

	/**
	 * Subtract a value from the current object.
	 * @param value The value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint minus(short value) {
		if (_isNull) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(safeSubtract(_value, value));
	}

	/**
	 * Subtract a value from the current object.
	 * @param operand Contains the value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint minus(NullableSmallint operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(safeSubtract(_value, operand.value()));
	}

	/**
	 * Subtract the current object from a value.
	 * @param value Contains the value from which to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint subtractFrom(short value) {
		if (_isNull) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(safeSubtract(value, _value));
	}

	/**
	 * Multiply the current object by a value.
	 * @param value The value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint times(short value) {
		if (_isNull) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(safeMultiply(_value, value));
	}

	/**
	 * Multiply the current object by a value.
	 * @param operand Contains the value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint times(NullableSmallint operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(safeMultiply(_value, operand.value()));
	}

	/**
	 * Divide the current object by a value.
	 * @param value The divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableSmallint dividedBy(short value) {
		if (_isNull) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox((short) (_value / value));
	}

	/**
	 * Divide the current object by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableSmallint dividedBy(NullableSmallint operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(_value / operand.value());
	}

	/**
	 * Divide a value by the current object.
	 * @param value The dividend.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableSmallint dividedInto(short value) {
		if (_isNull) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(value / _value);
	}

	/**
	 * Take the remainder of division by a value.
	 * @param value The divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableSmallint remainder(short value) {
		if (_isNull) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(_value % value);
	}

	/**
	 * Take the remainder of division by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableSmallint remainder(NullableSmallint operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableSmallintBox();
		}
		return new NullableSmallintBox(_value % operand.value());
	}

	/**
	 * Test the masked bits in the value.
	 * @param mask The mask of bits to test.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and any bit from the mask
	 * 	is non-zero in the value of the object.
	 */
	@Override
	public NullableBoolean bitTest(short mask) {
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
	public NullableSmallint abs() {
		if (_isNull) {
			return new NullableSmallintBox();
		} else {
			return new NullableSmallintBox(Math.abs(_value));
		}
	}

	/**
	 * Negate the current object.
	 * @return A new object containing the negated value or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableSmallint negate() {
		if (_isNull) {
			return new NullableSmallintBox();
		} else {
			if (_value == Short.MIN_VALUE) {
	            throw new ArithmeticException("smallint overflow");				
			}
			return new NullableSmallintBox(- _value);
		}
	}

	/**
	 * Create a {@code NullableLong} object like the current object.
	 * @return A {@code NullableLong} having the properties of the current object.
	 */
	@Override
	public NullableLong toLong() {
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(_value);
		}
	}

	/**
	 * Create a {@code NullableInt} object like the current object.
	 * @return A {@code NullableInt} having the properties of the current object.
	 */
	@Override
	public NullableInt toInt() {
		if (_isNull) {
			return new NullableIntBox();
		} else {
			return new NullableIntBox(_value);
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
			return new NullableTinyintBox(_value);
		}
	}

	/**
	 * Return a hash code for the object.
	 * @return A hash code for the instance.
	 * @see Integer#hashCode(int)
	 */
	@Override
	public int hashCode() {
		if (_isNull) {
			return 0;
		} else {
			return _value;
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
		} else if (obj instanceof NullableSmallint other) {
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
			return Short.toString(_value);
		}
	}

	/**
	 * Return a string representation of the current object.
	 * @param radix The radix to use when converting the value.
	 * @return Either "null" or the string representation of the object's value
	 * 	in the given radix.
	 * @see Integer#toString(int)
	 */
	@Override
	public String toString(int radix) {
		if (_isNull) {
			return STR_NULL;
		} else {
			return Integer.toString(_value, radix);
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
			return new NullableSmallintBox();
		} else {
			return new NullableSmallintBox(_value);
		}
	}

	/**
	 * Take the maximum value of one or more {@code NullableSmallint} objects.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableSmallint} object.
	 */
	public static NullableSmallint max(NullableSmallint first, NullableSmallint ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (first.isNull()) {
			return new NullableSmallintBox();
		}
		short answer = first.value();
		for (NullableSmallint next : rest) {
			if (next.isNull()) {
				return new NullableSmallintBox();
			}
			short value = next.value();
			if (answer < value) {
				answer = value;
			}
		}
		return new NullableSmallintBox(answer);
	}
	
	/**
	 * Take the minimum value of one or more {@code NullableSmallint} objects.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableSmallint} object.
	 */
	public static NullableSmallint min(NullableSmallint first, NullableSmallint ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (first.isNull()) {
			return new NullableSmallintBox();
		}
		short answer = first.value();
		for (NullableSmallint next : rest) {
			if (next.isNull()) {
				return new NullableSmallintBox();
			}
			short value = next.value();
			if (answer > value) {
				answer = value;
			}
		}
		return new NullableSmallintBox(answer);
	}
	
	/**
	 * Parse a {@code String} containing a representation of a {@code NullableSmallint}
	 *  in the given radix.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @param radix The radix to be used when parsing.
	 * @return A {@code NullableSmallint} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
	 * @throws NumberFormatException if the input does not contain a parseable representation
	 * 	of a {@code NullableSmallint}
	 * @see Short#parseShort(String, int)
	 */
	static NullableSmallint parse(String strInput, int radix)
			throws NumberFormatException {
		Objects.requireNonNull(strInput, "strInput");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableSmallintBox();
		} else {
			return new NullableSmallintBox(Short.parseShort(strInput, radix));
		}
	}
}
