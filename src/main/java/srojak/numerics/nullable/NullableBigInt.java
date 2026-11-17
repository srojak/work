package srojak.numerics.nullable;

import java.math.BigInteger;

import srojak.core.Equatable;
import srojak.core.excepts.NullValueException;
import srojak.numerics.NullableValue;
import srojak.numerics.OrderedComparison;

public sealed interface NullableBigInt
		extends NullableValue, Equatable<NullableBigInt>, Comparable<NullableBigInt> 
		permits NullableBigIntValue {

	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	BigInteger value();
	
	/**
	 * Get the value of the object, or a default value if the object is not {@code null}.
	 * @param valueIfNull The value to return if the object is {@code null}.
	 * @return the value of the object, or the parameter value.
	 */
	BigInteger valueOr(BigInteger valueIfNull);
	
	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 * @throws NullPointerException if {@code value} is reference null.
	 */
	boolean isEqualTo(BigInteger value);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, BigInteger value);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, NullableBigInt other);
	
	/**
	 * Add a value to the current object.
	 * @param value The value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableBigInt plus(BigInteger value);
	
	/**
	 * Add a value to the current object.
	 * @param operand Contains the value to add.
	 * @return A new object containing the sum or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableBigInt plus(NullableBigInt operand);
	
	/**
	 * Subtract a value from the current object.
	 * @param value The value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableBigInt minus(BigInteger value);
	
	/**
	 * Subtract a value from the current object.
	 * @param operand Contains the value to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableBigInt minus(NullableBigInt operand);
	
	/**
	 * Subtract the current object from a value.
	 * @param value Contains the value from which to subtract.
	 * @return A new object containing the difference or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableBigInt subtractFrom(BigInteger value);
	
	/**
	 * Multiply the current object by a value.
	 * @param value The value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableBigInt times(BigInteger value);
	
	/**
	 * Multiply the current object by a value.
	 * @param operand Contains the value by which to multiply.
	 * @return A new object containing the product or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableBigInt times(NullableBigInt operand);
	
	/**
	 * Divide the current object by a value.
	 * @param value The divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableBigInt dividedBy(BigInteger value);
	
	/**
	 * Divide the current object by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableBigInt dividedBy(NullableBigInt operand);
	
	/**
	 * Divide a value by the current object.
	 * @param value The dividend.
	 * @return A new object containing the quotient or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableBigInt dividedInto(BigInteger value);
	
	/**
	 * Take the remainder of division by a value.
	 * @param value The divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableBigInt remainder(BigInteger value);
	
	/**
	 * Take the remainder of division by a value.
	 * @param operand Contains the divisor.
	 * @return A new object containing the remainder or {@code null}.
	 * @throws ArithmeticException if the divisor is zero.
	 */
	NullableBigInt remainder(NullableBigInt operand);
	
	/**
	 * Return the quotient and the remainder of a division.
	 * @param operand Contains the divisor.
	 * @return An array of two {@code NullableBigInt} objects,
	 * 	which will both be {@code null} if either operand is {@code null},
	 *  or the first will be the quotient and the second the remainder.
	 */
	NullableBigInt[] divideAndRemainder(NullableBigInt operand);
	
	/**
	 * Take the absolute value of the current object.
	 * @return  A new object containing the absolute value or {@code null}.
	 */
	NullableBigInt abs();
	
	/**
	 * Negate the current object.
	 * @return A new object containing the negated value or {@code null}.
	 * @throws ArithmeticException if the operation would overflow.
	 */
	NullableBigInt negate();
	
	NullableBigInt max(NullableBigInt operand);

	NullableBigInt min(NullableBigInt operand);
	
	/**
	 * Get the string representation of the current object in the given radix.
	 * @param radix The radix to use.
	 * @return A string value or "null".
	 */
	String toString(int radix);
	
	static NullableBigInt from(BigInteger value) {
		return new NullableBigIntValue(value);
	}
	
	static NullableBigInt from(long lnValue) {
		return new NullableBigIntValue(BigInteger.valueOf(lnValue));
	}
	
	static NullableBigInt fromNull() {
		return new NullableBigIntValue();
	}
	
	static NullableBigInt parse(String strInput) {
		return parse(strInput, 10); 
	}
	
	static NullableBigInt parse(String strInput, int radix) {
		return parse(strInput, radix); 
	}
}
