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
import srojak.numerics.NullableValue;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public sealed interface NullableInt 
		extends NullableValue, Equatable<NullableInt>, Comparable<NullableInt>
		permits NullableIntBox {

	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	int value();
	
	/**
	 * Get the value of the object, or a default value if the object is not {@code null}.
	 * @param valueIfNull The value to return if the object is {@code null}.
	 * @return the value of the object, or the parameter value.
	 */
	int valueOr(int valueIfNull);
	
	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 */
	boolean isEqualTo(int value);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, int value);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, NullableInt other);
	
	/**
	 * Increment the value of the object in place.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt increment();
	
	/**
	 * Increment the value of the object in place by a value.
	 * @param delta The value to which to increment.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt increment(int delta);
	
	/**
	 * Decrement the value of the object in place.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt decrement();
	
	/**
	 * Decrement the value of the object in place by a value.
	 * @param delta The value to which to increment.
	 * @return A reference to the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt decrement(int delta);
	
	/**
	 * Add a value to the current object.
	 * @param value The value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt plus(int value);
	
	/**
	 * Add a value to the current object.
	 * @param operand Contains the value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt plus(NullableInt operand);
	
	/**
	 * Subtract a value from the current object.
	 * @param value The value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt minus(int value);
	
	/**
	 * Subtract a value from the current object.
	 * @param operand Contains the value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt minus(NullableInt operand);
	
	/**
	 * Subtract the current object from a value.
	 * @param value Contains the value from which to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt subtractFrom(int value);
	
	/**
	 * Multiply the current object by a value.
	 * @param value The value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt times(int value);
	
	/**
	 * Multiply the current object by a value.
	 * @param operand Contains the value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt times(NullableInt operand);
	
	/**
	 * Divide the current object by a value.
	 * @param value The divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableInt dividedBy(int value);
	
	/**
	 * Divide the current object by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableInt dividedBy(NullableInt operand);
	
	/**
	 * Divide a value by the current object.
	 * @param value The dividend.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableInt dividedInto(int value);
	
	/**
	 * Take the remainder of division by a value.
	 * @param value The divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableInt remainder(int value);
	
	/**
	 * Take the remainder of division by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableInt remainder(NullableInt operand);
	
	/**
	 * Test the masked bits in the value.
	 * @param mask The mask of bits to test.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and any bit from the mask
	 * 	is non-zero in the value of the object.
	 */
	NullableBoolean bitTest(int mask);
	
	/**
	 * Take the absolute value of the current object.
	 * @return  A new object containing the absolute value or {@code null}.
	 */
	NullableInt abs();
	
	/**
	 * Negate the current object.
	 * @return A new object containing the negated value or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableInt negate();
	
	/**
	 * Create a {@code NullableLong} object like the current object.
	 * @return A {@code NullableLong} having the properties of the current object.
	 */
	NullableLong toLong();
	
	/**
	 * Create a {@code NullableSmallint} object like the current object.
	 * @return A {@code NullableSmallint} having the properties of the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableSmallint toSmallint();
	
	/**
	 * Create a {@code NullableTinyint} object like the current object.
	 * @return A {@code NullableTinyint} having the properties of the current object.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableTinyint toTinyint();
	
	/**
	 * Get the string representation of the current object in the given radix.
	 * @param radix The radix to use.
	 * @return A string value or "null".
	 */
	String toString(int radix);
	
	/**
	 * Return a nullable object containing a value.
	 * @param value The value to use.
	 * @return The {@code NullableInt} object representing the value.
	 */
	public static NullableInt from(int value) {
		return new NullableIntBox(value);
	}
	
	/**
	 * Convert an {@code Integer} value to a nullable object.
	 * @param value The value to use, which can be {@code null}.
	 * @return The {@code NullableInt} object representing the value.
	 */
	public static NullableInt from(Integer value) {
		if (value == null) {
			return new NullableIntBox();
		} else {
			return new NullableIntBox(value.intValue());
		}
	}
	
	/**
	 * Create a nullable object representing {@code null}.
	 * @return The {@code NullableInt} object representing {@code null}.
	 */
	public static NullableInt fromNull() {
		return new NullableIntBox();
	}
	
	/**
	 * Take the maximum value of one or more {@code NullableInt} objects.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableInt} object.
	 */
	public static NullableInt max(NullableInt first, NullableInt ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		return NullableIntBox.max(first, rest);
	}
	
	/**
	 * Take the minimum value of one or more {@code NullableInt} objects.
	 * If any of the objects are {@code null}, the result will be {@code null}.
	 * @param first The first object to consider.
	 * @param rest Additional objects to consider.
	 * @return The resulting {@code NullableInt} object.
	 */
	public static NullableInt min(NullableInt first, NullableInt ... rest) {
		Objects.requireNonNull(first, "first");
		Objects.requireNonNull(rest, "rest");
		return NullableIntBox.min(first, rest);
	}

	/**
	 * Parse a {@code String} containing a representation of a {@code NullableInt}
	 * 	in base 10.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @return A {@code NullableInt} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
	 * @throws NumberFormatException if the input does not contain a parseable representation
	 * 	of a {@code NullableInt}
	 * @see Integer#parseInt(String, int)
	 */
	public static NullableInt parse(String strInput) {
		return NullableIntBox.parse(strInput, 10);
	}
	
	/**
	 * Parse a {@code String} containing a representation of a {@code NullableInt}
	 *  in the given radix.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @param radix The radix to be used when parsing.
	 * @return A {@code NullableInt} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
	 * @throws NumberFormatException if the input does not contain a parseable representation
	 * 	of a {@code NullableInt}
	 * @see Integer#parseInt(String, int)
	 */
	public static NullableInt parse(String strInput, int radix) {
		return NullableIntBox.parse(strInput, radix);
	}
}
