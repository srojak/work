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

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Comparator;
import java.util.Objects;

import srojak.core.excepts.NullValueException;
import srojak.numerics.HasTimeZone;
import srojak.numerics.NullableComparable;
import srojak.numerics.OrderedComparison;
import srojak.numerics.TemporalValue;

/**
 * @author Stephen
 *
 */
public sealed interface NullableDateTime
		extends NullableComparable<NullableDateTime>, TemporalValue, HasTimeZone
		permits NullableDateTimeValue {

	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	ZonedDateTime value();
	
	/**
	 * Get the local value of the object mapped into the given time zone.
	 * @param idZone Identifies the time zone of interest.
	 * @return A {@code LocalDateTime} for the time in the chosen time zone.
	 * @throws NullValueException if the object is {@code null}.
	 */
	LocalDateTime localValueUsing(ZoneId idZone);
	
	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 */
	boolean isEqualTo(ZonedDateTime zdtValue);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, ZonedDateTime zdtValue);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, NullableDateTime other);
	
	/**
	 * Get a new object containing the same point in time rendered in another time zone.
	 * @return A {@code LocalDateTime} for the time in the chosen time zone.
	 * @return A new object containing the calculated value or {@code null}.
     * @throws DateTimeException If the result exceeds the supported date range.
 	 */
	NullableDateTime shiftZoneTo(ZoneId idZone);
	
	/**
	 * Get a new object for an adjusted point in time.
	 * @param adjuster The adjuster to use.
	 * @return A new object containing the calculated value or {@code null}.
     * @throws DateTimeException If the adjustment cannot be made.
     * @throws ArithmeticException If numeric overflow occurs.
     * @see ZonedDateTime#with(TemporalAdjuster)
	 */
	NullableDateTime with(TemporalAdjuster adjuster);
	
	/**
	 * Get a new object for a point in time truncated to the given unit.
	 * @param unit The unit to which to truncate.
	 * 	All components smaller than the given unit will be set to zero.
	 * @return A new object containing the calculated value or {@code null}.
     * @throws DateTimeException If unable to truncate.
     * @throws UnsupportedTemporalTypeException If the unit is not supported.
     * @see ZonedDateTime#truncatedTo(TemporalUnit)
	 */
	NullableDateTime truncatedTo(TemporalUnit unit);
	
	NullableDateTime plus(TemporalAmount amount);
	
	NullableDateTime plus(NullableDuration duration);
	
	NullableDateTime minus(TemporalAmount amount);
	
	NullableDateTime minus(NullableDuration duration);
	
	/**
	 * Get a new object by adding the duration to midnight of the day of the current object.
	 * @param duration The duration to add in the calculation.
	 * @return A new object containing the calculated value or {@code null}.
	 * @throws DateTimeException If unable to truncate or add the duration.
     * @throws ArithmeticException if numeric overflow occurs.
	 * @see ZonedDateTime#truncatedTo(TemporalUnit)
	 * @see ZonedDateTime#plus(TemporalAmount)
 	 */
	NullableDateTime fromMidnight(NullableDuration duration);
	
	NullableLong until(Temporal endExclusive, TemporalUnit unit);
	
	NullableLong until(NullableDateTime endExclusive, TemporalUnit unit);
	
	NullableLong computeBetween(NullableDateTime end, ChronoUnit unit);
	
	public static NullableDateTime from(ZonedDateTime zdtValue) {
		if (zdtValue == null) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(zdtValue);
		}
	}
	
	default Comparator<NullableDateTime> getComparator() {
		return new NullableGenericComparator<NullableDateTime>();
	}
	
	public static NullableDateTime fromNonNull(ZonedDateTime zdtValue) {
		Objects.requireNonNull(zdtValue, "zdtValue");
		return new NullableDateTimeValue(zdtValue);
	}
	
	public static NullableDateTime fromLocal(LocalDateTime ldtValue, ZoneId idZone) {
		Objects.requireNonNull(ldtValue, "ldtValue");
		Objects.requireNonNull(idZone, "idZone");
		return new NullableDateTimeValue(ldtValue.atZone(idZone));
	}
	
	public static NullableDateTime fromLocal(LocalDate ldValue, ZoneId idZone) {
		Objects.requireNonNull(ldValue, "ldValue");
		Objects.requireNonNull(idZone, "idZone");
		return new NullableDateTimeValue(ldValue.atStartOfDay().atZone(idZone));
	}
	
	public static NullableDateTime fromLocal(LocalDate ldValue, LocalTime ltValue, ZoneId idZone) {
		Objects.requireNonNull(ldValue, "ldValue");
		Objects.requireNonNull(ltValue, "ltValue");
		Objects.requireNonNull(idZone, "idZone");
		return new NullableDateTimeValue(ldValue.atTime(ltValue).atZone(idZone));
	}
	
	public static NullableDateTime fromNull() {
		return new NullableDateTimeValue();
	}
	
	public static NullableDateTime parseIso(String strInput) {
		return NullableDateTimeValue.parseIso(strInput);
	}
	
	public static NullableDateTime parse(String strInput, DateTimeFormatter formatter) {
		return NullableDateTimeValue.parse(strInput, formatter);
	}
	
	public static NullableDateTime parseAndSetZone(String strInput, DateTimeFormatter formatter, ZoneId idZone) {
		return NullableDateTimeValue.parseAndSetZone(strInput, formatter, idZone);
	}
}
