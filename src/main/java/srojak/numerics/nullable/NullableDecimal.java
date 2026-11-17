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

import java.math.BigDecimal;
import java.math.MathContext;

import srojak.core.Equatable;
import srojak.core.excepts.NullValueException;
import srojak.numerics.NullableValue;
import srojak.numerics.OrderedComparison;
import srojak.numerics.extmath.MathContextCarrying;
import srojak.numerics.extmath.MathContextDyadicRule;

/**
 * @author Stephen
 *
 */
public interface NullableDecimal 
		extends NullableValue, MathContextCarrying, Equatable<NullableDecimal>, 
			Comparable<NullableDecimal> {

	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	BigDecimal value();
	
	/**
	 * Get the value of the object, or a default value if the object is not {@code null}.
	 * @param valueIfNull The value to return if the object is {@code null}.
	 * @return the value of the object, or the parameter value.
	 */
	BigDecimal valueOr(BigDecimal valueIfNull);
	
	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 * @throws NullPointerException if {@code value} is reference null.
	 */
	boolean isEqualTo(BigDecimal value);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, BigDecimal value);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, NullableDecimal other);
	
	/**
	 * Add a value to the current object.
	 * @param value The value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableDecimal plus(BigDecimal value);
	
	/**
	 * Add a value to the current object.
	 * @param operand Contains the value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableDecimal plus(NullableDecimal operand, MathContextDyadicRule ruleDyad);
	
	/**
	 * Subtract a value from the current object.
	 * @param value The value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableDecimal minus(BigDecimal value);
	
	/**
	 * Subtract a value from the current object.
	 * @param operand Contains the value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableDecimal minus(NullableDecimal operand, MathContextDyadicRule ruleDyad);
	
	/**
	 * Subtract the current object from a value.
	 * @param value Contains the value from which to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableDecimal subtractFrom(BigDecimal value);
	
	/**
	 * Multiply the current object by a value.
	 * @param value The value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableDecimal times(BigDecimal value);
	
	/**
	 * Multiply the current object by a value.
	 * @param operand Contains the value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableDecimal times(NullableDecimal operand, MathContextDyadicRule ruleDyad);
	
	/**
	 * Divide the current object by a value.
	 * @param value The divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableDecimal dividedBy(BigDecimal value);
	
	/**
	 * Divide the current object by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableDecimal dividedBy(NullableDecimal operand, MathContextDyadicRule ruleDyad);
	
	/**
	 * Divide a value by the current object.
	 * @param value The dividend.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableDecimal dividedInto(BigDecimal value);
	
	/**
	 * Take the remainder of division by a value.
	 * @param value The divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableDecimal remainder(BigDecimal value);
	
	/**
	 * Take the remainder of division by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableDecimal remainder(NullableDecimal operand, MathContextDyadicRule ruleDyad);
	
	/**
	 * Return the quotient and the remainder of a division.
	 * @param operand Contains the divisor.
	 * @return An array of two {@code NullableDecimal} objects,
	 * 	which will both be {@code null} if either operand is {@code null},
	 *  or the first will be the quotient and the second the remainder.
	 */
	NullableDecimal[] divideAndRemainder(NullableDecimal operand);
	
	NullableDecimal pow(int power);
	
	NullableDecimal pow(NullableInt power);
	
	NullableDecimal round();
	
	NullableDecimal sqrt();
	
	NullableDecimal movePointLeft(int places);
	
	NullableDecimal movePointRight(int places);
	
	/**
	 * Take the absolute value of the current object.
	 * @return  A new object containing the absolute value or {@code null}.
	 */
	NullableDecimal abs();
	
	/**
	 * Negate the current object.
	 * @return A new object containing the negated value or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableDecimal negate();
	
	NullableDecimal max(NullableDecimal operand);

	NullableDecimal min(NullableDecimal operand);
	
	static NullableDecimal from(BigDecimal value, MathContext context) {
		return new NullableDecimalValue(value, context);
	}
	
	static NullableDecimal fromNull() {
		return new NullableDecimalValue();
	}
}
