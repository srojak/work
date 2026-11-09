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
import srojak.numerics.SinglePrecisionComparer;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public final class NullableFloatBox 
		extends NullableValueBase implements NullableFloat {
	private final float _value;

	/**
	 * Null value constructor.
	 */
	NullableFloatBox() {
		super(true);
		_value = Float.NaN;
	}
	
	/**
	 * Value constructor.
	 * @param value Value for the object.
	 */
	NullableFloatBox(float value) {
		super(false);
		_value = value;
	}
	
	/**
	 * Value constructor for expressions
	 * @param value
	 */
	NullableFloatBox(double value, boolean bAllowUnderflow) {
		super(false);
		if (Double.isNaN(value)) {
			_value = Float.NaN;
		} else if (value == Double.POSITIVE_INFINITY) {
			_value = Float.POSITIVE_INFINITY;
		} else if (value == Double.NEGATIVE_INFINITY) {
			_value = Float.NEGATIVE_INFINITY;
		} else {
			double dAbs = Math.abs(value);
			if (dAbs > Float.MAX_VALUE) {
	            throw new ArithmeticException("float overflow");
			} else if (!bAllowUnderflow) {
				if (value != 0.0d && dAbs < Float.MIN_VALUE) {
					throw new ArithmeticException("float underflow");
				}
			}
			_value = (float) value;
		}
	}

	/**
	 * Is the there a value for the current object, and is it NaN?
	 * @return {@code true} if the object has a value, and the value is NaN.
	 * @see Double#isNaN(double)
	 */
	@Override
	public boolean isNaN() {
		return (!_isNull && Float.isNaN(_value));
	}

	/**
	 * Is the there a value for the current object, and is it infinite?
	 * @return {@code true} if the object has a value, and the value is +/- infinity.
	 * @see Double#isInfinite(double)
	 */
	@Override
	public boolean isInfinite() {
		return (!_isNull && Float.isInfinite(_value));
	}

	/**
	 * Is the there a value for the current object, and is it finite?
	 * @return {@code true} if the object has a value, and the value is not NaN or +/- infinity.
	 */
	@Override
	public boolean isFinite() {
		if (_isNull) {
			return false;
		} else {
			return !Float.isNaN(_value) && !Float.isInfinite(_value);
		}
	}

	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	@Override
	public float value() {
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
	public float valueOr(float valueIfNull) {
		if (_isNull) {
			return valueIfNull;
		}
		return _value;
	}

	/**
	 * Is the value equal to another instance of a {@code NullableFloat}?
	 * @return {@code true} if the object and other instance are not {@code null}
	 *  and both have the same value.
	 *  
	 * This will not return {@code true} if {@code obj} is the same as {@code this}
	 *  and the object is {@code null}.
	 */
	@Override
	public boolean isEqual(NullableFloat obj) {
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
	public int compareTo(NullableFloat o) {
		Objects.requireNonNull(o, "o");
		if (_isNull || o.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return SinglePrecisionComparer.DEFAULT_COMPARER.compare(_value, o.value());
		}
	}

	/**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * 
     * @param other The other object to which to compare.
 	 * @param comparer The comparer to use.
     * @return A negative integer, zero, or a positive integer as this object
     * 	is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object <i>reference</i> is {@code null}
     * 	or if the {@code comparer} is null.
     * @throws NullValueException if at least one operand is {@code null}.
     * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(SinglePrecisionComparer comparer, NullableFloat other) {
		Objects.requireNonNull(comparer, "comparer");
		Objects.requireNonNull(other, "other");
		if (_isNull || other.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return comparer.compare(_value, other.value());
		}
	}

	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 */
	@Override
	public boolean isEqualTo(float value) {
		if (_isNull) {
			return false;
		} else {
			return SinglePrecisionComparer.DEFAULT_COMPARER.areEqual(_value, value);
		}
	}

	/**
	 * Is the value of the object equal to another value?
	 * @param comparer The comparer to use.
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 */
	@Override
	public boolean isEqualTo(SinglePrecisionComparer comparer, float value) {
		Objects.requireNonNull(comparer, "comparer");
		if (_isNull) {
			return false;
		} else {
			return comparer.areEqual(_value, value);
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
	public NullableBoolean compareTo(OrderedComparison comparison, float value) {
		Objects.requireNonNull(comparison, "comparison");
		if (_isNull) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(SinglePrecisionComparer.DEFAULT_COMPARER.compare(_value, value));
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
	public NullableBoolean compareTo(OrderedComparison comparison, NullableFloat other) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(other, "other");
		if (_isNull || other.isNull()) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(SinglePrecisionComparer.DEFAULT_COMPARER.compare(_value, other.value()));
			return new NullableBooleanBox(bCompar);
		}
	}

	/**
	 * Compare the value of the object to another value.
	 * @param comparer The comparer to use.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	@Override
	public NullableBoolean compareTo(SinglePrecisionComparer comparer, OrderedComparison comparison, float value) {
		Objects.requireNonNull(comparer, "comparer");
		Objects.requireNonNull(comparison, "comparison");
		if (_isNull) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(comparer.compare(_value, value));
			return new NullableBooleanBox(bCompar);
		}
	}

	/**
	 * Compare the value of the object to another value.
	 * @param comparer The comparer to use.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	@Override
	public NullableBoolean compareTo(SinglePrecisionComparer comparer, OrderedComparison comparison,
			NullableFloat other) {
		Objects.requireNonNull(comparer, "comparer");
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(other, "other");
		if (_isNull || other.isNull()) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(comparer.compare(_value, other.value()));
			return new NullableBooleanBox(bCompar);
		}
	}

	/**
	 * Add a value to the current object.
	 * @param value The value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableFloat plus(float value) {
		if (_isNull) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value + value);
	}

	/**
	 * Add a value to the current object.
	 * @param operand Contains the value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableFloat plus(NullableFloat operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value + operand.value());
	}

	/**
	 * Subtract a value from the current object.
	 * @param value The value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableFloat minus(float value) {
		if (_isNull) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value - value);
	}

	/**
	 * Subtract a value from the current object.
	 * @param operand Contains the value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableFloat minus(NullableFloat operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value - operand.value());
	}

	/**
	 * Subtract the current object from a value.
	 * @param value Contains the value from which to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableFloat subtractFrom(float value) {
		if (_isNull) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(value - _value);
	}

	/**
	 * Multiply the current object by a value.
	 * @param value The value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableFloat times(float value) {
		if (_isNull) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value * value);
	}

	/**
	 * Multiply the current object by a value.
	 * @param operand Contains the value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableFloat times(NullableFloat operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value * operand.value());
	}

	/**
	 * Divide the current object by a value.
	 * @param value The divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableFloat dividedBy(float value) {
		if (_isNull) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value / value);
	}

	/**
	 * Divide the current object by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableFloat dividedBy(NullableFloat operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value / operand.value());
	}

	/**
	 * Divide a value by the current object.
	 * @param value The dividend.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableFloat dividedInto(float value) {
		if (_isNull) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(value / _value);
	}

	/**
	 * Take the remainder of division by a value.
	 * @param value The divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableFloat remainder(float value) {
		if (_isNull) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value % value);
	}

	/**
	 * Take the remainder of division by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	@Override
	public NullableFloat remainder(NullableFloat operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableFloatBox();
		}
		return new NullableFloatBox(_value % operand.value());
	}

	/**
	 * Take the absolute value of the current object.
	 * @return  A new object containing the absolute value or {@code null}.
	 */
	@Override
	public NullableFloat abs() {
		if (_isNull) {
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(Math.abs(_value));
		}
	}

	/**
	 * Negate the current object.
	 * @return A new object containing the negated value or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	@Override
	public NullableFloat negate() {
		if (_isNull) {
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(- _value);
		}
	}

	/**
     * Returns the smallest (closest to negative infinity)
     *  {@code double} value that is greater than or equal to the
     *  value of the argument and is equal to a mathematical integer.
     * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
	 * 
     * @return  An object containing the smallest (closest to negative infinity)
     * 	floating-point value that is greater than or equal to
     *  the value of the argument and is equal to a mathematical integer.
     * @see Math#ceil(double)
	 */
	@Override
	public NullableFloat ceil() {
		if (_isNull) {
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(Math.ceil(_value), true);
		}
	}

	/**
     * Returns the largest (closest to positive infinity)
     * {@code double} value that is less than or equal to the
     * value of the argument and is equal to a mathematical integer.
     * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
	 * 
     * @return  An object containing the largest (closest to negative infinity)
     * 	floating-point value that is less than or equal to
     *  the value of the argument and is equal to a mathematical integer.
     * @see Math#floor(double)
	 */
	@Override
	public NullableFloat floor() {
		if (_isNull) {
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(Math.floor(_value), true);
		}
	}

	/**
     * Returns the {@code double} value that is closest in value
     * to the argument and is equal to a mathematical integer.
	 * If two {@code double} values that are mathematical integers are equally close,
	 * the result is the integer value that is even.
     * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
     *  
	 * @return An object containing the closest floating-point value
	 *  to the value of the object that is equal to a mathematical integer.
	 * @see Math#rint(double)
	 */
	@Override
	public NullableFloat round() {
		if (_isNull) {
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(Math.rint(_value), true);
		}
	}

	/**
     * Returns the closest {@code long} to the argument, with ties
     * rounding to positive infinity.
     * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
	 * 
	 * @return A {@code NullableInt} object containing the result.
	 * @see Math#toIntExact(long)
	 */
	@Override
	public NullableInt roundtoInt() {
		if (_isNull) {
			return new NullableIntBox();
		} else {
			int value = Math.round(_value);
			return new NullableIntBox(value);
		}
	}

	/**
     * Returns a {@code NullableFloat} containing the signum function of the value:
     * zero if the value is zero, 
     * 1.0 if the argument is greater than zero, 
     * -1.0 if the argument is less than zero.
	 * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
	 * 
	 * @return A {@code NullableFloat} containing the result.
	 * @see Math#signum(double)
	 */
	@Override
	public NullableFloat signum() {
		if (_isNull) {
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(Math.signum(_value));
		}
	}

	/**
	 * Returns a {@code NullableFloat} containing the magnitude of the object
	 * 	and the sign of the argument.
	 * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
     *  
	 * @param sign The parameter whose sign is to be applied.
	 * @return A {@code NullableFloat} containing the result.
	 * @see Math#copySign(double, double)
	 */
	@Override
	public NullableFloat applySign(float sign) {
		if (_isNull) {
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(Math.copySign(_value, sign));
		}
	}

	/**
	 * Returns the exponent from the value of the object.
	 * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
	 * 
	 * @return A {@code NullableInt} object containing the result.
	 * @see Math#getExponent(double)
	 */
	@Override
	public NullableInt getExponent() {
		if (_isNull) {
			return new NullableIntBox();
		} else {
			return new NullableIntBox(Math.getExponent(_value));
		}
	}

	/**
	 * Create a {@code NullableDouble} object like the current object.
	 * @return A {@code NullableDouble} having the properties of the current object.
	 */
	@Override
	public NullableDouble toDouble() {
		if (_isNull) {
			return new NullableDoubleBox();
		} else {
			return new NullableDoubleBox(_value);
		}
	}

	/**
	 * Return a hash code for the object.
	 * @return A hash code for the instance.
	 * @see Float#hashCode(int)
	 */
	@Override
	public int hashCode() {
		if (_isNull) {
			return 0;
		} else {
			return Float.hashCode(_value);
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
		} else if (obj instanceof NullableFloat other) {
			return isEqual(other);
		} else if (obj instanceof Float other) {
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
			return Float.toString(_value);
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
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(_value);
		}
	}

	/**
	 * Take the maximum value of one or more {@code NullableFloat} objects.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param comparer The comparer to use.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableFloat} object.
	 */
	public static NullableFloat max(SinglePrecisionComparer comparer, NullableFloat first, NullableFloat ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (first.isNull()) {
			return new NullableFloatBox();
		}
		float answer = first.value();
		for (NullableFloat next : rest) {
			if (next.isNull()) {
				return new NullableFloatBox();
			}
			float value = next.value();
			if (comparer.compare(answer, OrderedComparison.LT, value)) {
				answer = value;
			}
		}
		return new NullableFloatBox(answer);
	}
	
	/**
	 * Take the minimum value of one or more {@code NullableFloat} objects.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param comparer The comparer to use.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableFloat} object.
	 */
	public static NullableFloat min(SinglePrecisionComparer comparer, NullableFloat first, NullableFloat ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		if (first.isNull()) {
			return new NullableFloatBox();
		}
		float answer = first.value();
		for (NullableFloat next : rest) {
			if (next.isNull()) {
				return new NullableFloatBox();
			}
			float value = next.value();
			if (comparer.compare(answer, OrderedComparison.GT, value)) {
				answer = value;
			}
		}
		return new NullableFloatBox(answer);
	}
	
	/**
	 * Parse a {@code String} containing a representation of a {@code NullableFloat}.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @return A {@code NullableFloat} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
	 * @throws NumberFormatException if the input does not contain a parseable representation
	 * 	of a {@code NullableFloat}
	 * @see Float#parseFloat(String)
	 */
	static NullableFloat parse(String strInput)
			throws NumberFormatException {
		Objects.requireNonNull(strInput, "strInput");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(Float.parseFloat(strInput));
		}
	}
}
