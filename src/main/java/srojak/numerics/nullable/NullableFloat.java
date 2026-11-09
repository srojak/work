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

import srojak.core.Equatable;
import srojak.core.excepts.NullValueException;
import srojak.numerics.NullableValue;
import srojak.numerics.OrderedComparison;
import srojak.numerics.SinglePrecisionComparer;

/**
 * @author Stephen
 *
 */
public sealed interface NullableFloat 
		extends NullableValue, Equatable<NullableFloat>, Comparable<NullableFloat>
		permits NullableFloatBox {
	
	/**
	 * Is the there a value for the current object, and is it NaN?
	 * @return {@code true} if the object has a value, and the value is NaN.
	 * @see Float#isNaN(double)
	 */
	boolean isNaN();
	
	/**
	 * Is the there a value for the current object, and is it infinite?
	 * @return {@code true} if the object has a value, and the value is +/- infinity.
	 * @see Float#isInfinite(double)
	 */
	boolean isInfinite();
	
	/**
	 * Is the there a value for the current object, and is it finite?
	 * @return {@code true} if the object has a value, and the value is not NaN or +/- infinity.
	 */
	boolean isFinite();

	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	float value();
	
	/**
	 * Get the value of the object, or a default value if the object is not {@code null}.
	 * @param valueIfNull The value to return if the object is {@code null}.
	 * @return the value of the object, or the parameter value.
	 */
	float valueOr(float valueIfNull);
	
	/**
	 * Is the value of the object equal to another value?
	 * Uses {@code SinglePrecisionComparer.DEFAULT_COMPARER}.
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 */
	boolean isEqualTo(float value);
	
	/**
	 * Is the value of the object equal to another value?
	 * @param comparer The comparer to use.
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 */
	boolean isEqualTo(SinglePrecisionComparer comparer, float value);
	
	/**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * 
     * @param o The other object to which to compare.
 	 * @param comparer The comparer to use.
     * @return A negative integer, zero, or a positive integer as this object
     * 	is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object <i>reference</i> is {@code null}
     * 	or if the {@code comparer} is null.
     * @throws NullValueException if at least one operand is {@code null}.
     * @see Comparable#compareTo(Object)
	 */
	int compareTo(SinglePrecisionComparer comparer, NullableFloat other);
	
	/**
	 * Compare the value of the object to another value.
	 * Uses {@code SinglePrecisionComparer.DEFAULT_COMPARER}.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, float value);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparer The comparer to use.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(SinglePrecisionComparer comparer, OrderedComparison comparison, float value);
	
	/**
	 * Compare the value of the object to another value.
	 * Uses {@code SinglePrecisionComparer.DEFAULT_COMPARER}.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, NullableFloat other);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparer The comparer to use.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(SinglePrecisionComparer comparer, OrderedComparison comparison, NullableFloat other);
	
	/**
	 * Add a value to the current object.
	 * @param value The value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableFloat plus(float value);
	
	/**
	 * Add a value to the current object.
	 * @param operand Contains the value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableFloat plus(NullableFloat operand);
	
	/**
	 * Subtract a value from the current object.
	 * @param value The value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableFloat minus(float value);
	
	/**
	 * Subtract a value from the current object.
	 * @param operand Contains the value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableFloat minus(NullableFloat operand);
	
	/**
	 * Subtract the current object from a value.
	 * @param value Contains the value from which to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableFloat subtractFrom(float value);
	
	/**
	 * Multiply the current object by a value.
	 * @param value The value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableFloat times(float value);
	
	/**
	 * Multiply the current object by a value.
	 * @param operand Contains the value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableFloat times(NullableFloat operand);
	
	/**
	 * Divide the current object by a value.
	 * @param value The divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableFloat dividedBy(float value);
	
	/**
	 * Divide the current object by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableFloat dividedBy(NullableFloat operand);
	
	/**
	 * Divide a value by the current object.
	 * @param value The dividend.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableFloat dividedInto(float value);
	
	/**
	 * Take the remainder of division by a value.
	 * @param value The divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableFloat remainder(float value);
	
	/**
	 * Take the remainder of division by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableFloat remainder(NullableFloat operand);
	
	/**
	 * Take the absolute value of the current object.
	 * @return  A new object containing the absolute value or {@code null}.
	 */
	NullableFloat abs();
	
	/**
	 * Negate the current object.
	 * @return A new object containing the negated value or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableFloat negate();

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
	NullableFloat ceil();
	
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
	NullableFloat floor();
	
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
	NullableFloat round();
	
	/**
     * Returns the closest {@code int} to the argument, with ties
     * rounding to positive infinity.
     * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
	 * 
	 * @return A {@code NullableInt} object containing the result.
	 * @see Math#round(float)
	 */
	NullableInt roundtoInt();
	
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
	 * @see Math#signum(float)
	 */
	NullableFloat signum();
	
	/**
	 * Returns a {@code NullableFloat} containing the magnitude of the object
	 * 	and the sign of the argument.
	 * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
     *  
	 * @param sign The parameter whose sign is to be applied.
	 * @return A {@code NullableFloat} containing the result.
	 * @see Math#copySign(float, float)
	 */
	NullableFloat applySign(float sign);
	
	/**
	 * Returns the exponent from the value of the object.
	 * 
     * Adds the special case that, if the object is {@code null},
     *  the result will also be {@code null}.
	 * 
	 * @return A {@code NullableInt} object containing the result.
	 * @see Math#getExponent(double)
	 */
	NullableInt getExponent();
	
	/**
	 * Create a {@code NullableDouble} object like the current object.
	 * @return A {@code NullableDouble} having the properties of the current object.
	 */
	NullableDouble toDouble();
	
	/**
	 * Return a nullable object containing a value.
	 * @param value The value to use.
	 * @return The {@code NullableFloat} object representing the value.
	 */
	public static NullableFloat from(float value) {
		return new NullableFloatBox(value);
	}
	
	/**
	 * Convert an {@code Integer} value to a nullable object.
	 * @param value The value to use, which can be {@code null}.
	 * @return The {@code NullableFloat} object representing the value.
	 */
	public static NullableFloat from(Float value) {
		if (value == null) {
			return new NullableFloatBox();
		} else {
			return new NullableFloatBox(value.floatValue());
		}
	}
	
	/**
	 * Create a nullable object representing {@code null}.
	 * @return The {@code NullableFloat} object representing {@code null}.
	 */
	public static NullableFloat fromNull() {
		return new NullableFloatBox();
	}
	
	/**
	 * Take the maximum value of one or more {@code NullableFloat} objects.
	 * Uses {@code SinglePrecisionComparer.DEFAULT_COMPARER}.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableDouble} object.
	 */
	public static NullableFloat max(NullableFloat first, NullableFloat ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		return NullableFloatBox.max(SinglePrecisionComparer.DEFAULT_COMPARER, first, rest);
	}
	
	/**
	 * Take the maximum value of one or more {@code NullableFloat} objects.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param comparer The comparer to use.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableDouble} object.
	 */
	public static NullableFloat max(SinglePrecisionComparer comparer, NullableFloat first, NullableFloat ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		return NullableFloatBox.max(comparer, first, rest);
	}
	
	/**
	 * Take the minimum value of one or more {@code NullableFloat} objects.
	 * Uses {@code SinglePrecisionComparer.DEFAULT_COMPARER}.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableFloat} object.
	 */
	public static NullableFloat min(NullableFloat first, NullableFloat ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		return NullableFloatBox.min(SinglePrecisionComparer.DEFAULT_COMPARER, first, rest);
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
		return NullableFloatBox.min(comparer, first, rest);
	}

	/**
	 * Parse a {@code String} containing a representation of a {@code NullableFloat}.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @return A {@code NullableFloat} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
	 * @throws NumberFormatException if the input does not contain a parseable representation
	 * 	of a {@code NullableFloat}
	 * @see Double#parseDouble(String)
	 */
	public static NullableFloat parse(String strInput) {
		return NullableFloatBox.parse(strInput);
	}
}
