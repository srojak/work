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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.Objects;

import srojak.core.excepts.NullValueException;
import srojak.numerics.NullableComparable;
import srojak.numerics.OrderedComparison;
import srojak.numerics.TemporalValue;

/**
 * @author Stephen
 *
 */
public sealed interface NullableDate
		extends NullableComparable<NullableDate>, TemporalValue
		permits NullableDateValue {

	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	LocalDate value();
	
	/**
	 * Is the value of the object equal to another value?
	 * @param dtValue The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 */
	boolean isEqualTo(LocalDate dtValue);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, LocalDate dtValue);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, NullableDate other);
	
	NullableDate plus(long lnAmount, TemporalUnit unit);
	
	NullableDate plus(NullableLong amount, TemporalUnit unit);
	
	NullableDate minus(long lnAmount, TemporalUnit unit);
	
	NullableDate minus(NullableLong amount, TemporalUnit unit);
	
	NullableLong until(Temporal endExclusive, TemporalUnit unit);
	
	NullableLong until(NullableDate endExclusive, TemporalUnit unit);
	
	default Comparator<NullableDate> getComparator() {
		return new NullableGenericComparator<NullableDate>();
	}
	
	public static NullableDate from(LocalDate ldtValue) {
		if (ldtValue == null) {
			return new NullableDateValue();
		} else {
			return new NullableDateValue(ldtValue);
		}
	}
	
	public static NullableDate fromNonNull(LocalDate ldtValue) {
		Objects.requireNonNull(ldtValue, "ldtValue");
		return new NullableDateValue(ldtValue);
	}
	
	public static NullableDate fromNull() {
		return new NullableDateValue();
	}
	
	public static NullableDate parseIso(String strInput) {
		return NullableDateValue.parseIso(strInput);
	}
	
	public static NullableDate parse(String strInput, DateTimeFormatter formatter) {
		return NullableDateValue.parse(strInput, formatter);
	}
}
