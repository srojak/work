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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Objects;

import srojak.core.excepts.NullValueException;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public final class NullableDateTimeValue 
		extends NullableValueBase 
		implements NullableDateTime {
	private final ZonedDateTime _value;
	
	private static final ZonedDateTime VALUE_NULL = ZonedDateTime.of(1900, 1, 1, 0, 0, 0, 0, ZoneId.of("Z"));
	
	NullableDateTimeValue(ZonedDateTime dtValue) {
		super(false);
		Objects.requireNonNull(dtValue, "dtValue");
		_value = dtValue;
	}

	NullableDateTimeValue() {
		super(true);
		_value = VALUE_NULL;
	}

	@Override
	public ZoneId timeZone() {
		if (_isNull) {
			throw new NullValueException("value is null");
		}
		return _value.getZone();
	}

	@Override
	public String formatUsing(DateTimeFormatter formatter) {
		Objects.requireNonNull(formatter, "formatter");
		if (_isNull) {
			return STR_NULL;
		} else {
			return formatter.format(_value);
		}
	}

	@Override
	public boolean isEqual(NullableDateTime obj) {
		if (obj == null) {
			return false;
		} else if (_isNull || obj.isNull()) {
			return false;
		} else {
			return _value.equals(obj.value());
		}
	}

	@Override
	public int compareTo(NullableDateTime o) {
		Objects.requireNonNull(o, "o");
		if (_isNull || o.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return _value.compareTo(o.value());
		}
	}

	@Override
	public ZonedDateTime value() {
		if (_isNull) {
			throw new NullValueException("value is null");
		}
		return _value;
	}

	@Override
	public LocalDateTime localValueUsing(ZoneId idZone) {
		Objects.requireNonNull(idZone, "idZone");
		if (_isNull) {
			throw new NullValueException("value is null");
		}
		return _value.withZoneSameInstant(idZone).toLocalDateTime();
	}

	@Override
	public boolean isEqualTo(ZonedDateTime zdtValue) {
		Objects.requireNonNull(zdtValue, "zdtValue");
		if (_isNull) {
			return false;
		} else {
			return _value.equals(zdtValue);
		}
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, ZonedDateTime zdtValue) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(zdtValue, "zdtValue");
		if (_isNull) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(_value.compareTo(zdtValue));
			return new NullableBooleanBox(bCompar);
		}
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, NullableDateTime other) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(other, "other");
		if (_isNull || other.isNull()) {
			return new NullableBooleanBox();
		} else {
			boolean bCompar = comparison.evaluate(_value.compareTo(other.value()));
			return new NullableBooleanBox(bCompar);
		}
	}

	@Override
	public NullableDateTime shiftZoneTo(ZoneId idZone) {
		Objects.requireNonNull(idZone, "idZone");
		if (_isNull) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(_value.withZoneSameInstant(idZone));
		}
	}

	@Override
	public NullableDateTime with(TemporalAdjuster adjuster) {
		Objects.requireNonNull(adjuster, "adjuster");
		if (_isNull) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(_value.with(adjuster));
		}
	}

	@Override
	public NullableDateTime truncatedTo(TemporalUnit unit) {
		Objects.requireNonNull(unit, "unit");
		if (_isNull) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(_value.truncatedTo(unit));
		}
	}

	@Override
	public NullableDateTime plus(TemporalAmount amount) {
		Objects.requireNonNull(amount, "amount");
		if (_isNull) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(_value.plus(amount));
		}
	}

	@Override
	public NullableDateTime minus(TemporalAmount amount) {
		Objects.requireNonNull(amount, "amount");
		if (_isNull) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(_value.minus(amount));
		}
	}

	@Override
	public NullableDateTime plus(NullableDuration duration) {
		Objects.requireNonNull(duration, "duration");
		if (_isNull || duration.isNull()) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(_value.plus(duration.value()));
		}
	}

	@Override
	public NullableDateTime minus(NullableDuration duration) {
		Objects.requireNonNull(duration, "duration");
		if (_isNull || duration.isNull()) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(_value.plus(duration.value()));
		}
	}

	@Override
	public NullableDateTime fromMidnight(NullableDuration duration) {
		Objects.requireNonNull(duration, "duration");
		if (_isNull || duration.isNull()) {
			return new NullableDateTimeValue();
		} else {
			ZonedDateTime zdtMidnight = _value.truncatedTo(ChronoUnit.DAYS);
			return new NullableDateTimeValue(zdtMidnight.plus(duration.value()));
		}
	}

	@Override
	public NullableLong until(Temporal endExclusive, TemporalUnit unit) {
		Objects.requireNonNull(endExclusive, "endExclusive");
		Objects.requireNonNull(unit, "unit");
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(_value.until(endExclusive, unit));
		}
	}

	@Override
	public NullableLong until(NullableDateTime endExclusive, TemporalUnit unit) {
		Objects.requireNonNull(endExclusive, "endExclusive");
		Objects.requireNonNull(unit, "unit");
		if (_isNull || endExclusive.isNull()) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(_value.until(endExclusive.value(), unit));
		}
	}

	@Override
	public NullableLong computeBetween(NullableDateTime end, ChronoUnit unit) {
		Objects.requireNonNull(end, "end");
		Objects.requireNonNull(unit, "unit");
		if (_isNull || end.isNull()) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(unit.between(_value, end.value()));
		}
	}

	/**
	 * Return a hash code for the object.
	 * @return A hash code for the instance.
	 */
	@Override
	public int hashCode() {
		if (_isNull) {
			return 0;
		} else {
			return _value.hashCode();
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
		} else if (obj instanceof NullableDateTime other) {
			return isEqual(other);
		} else if (obj instanceof ZonedDateTime other) {
			return _value.equals(other);
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
			return _value.toString();
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
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(_value);
		}
	}
	
	/*
	 * 
	 */
	/**
	 * Parse a string in ISO-8601 format.
	 * 
	 * Example: @{code "2026-11-12T14:30:00-06:00[America/Chicago]" }
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @return A {@code NullableDateTime} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
     * @throws DateTimeParseException if the input does not contain a parseable representation
	 * 	of a {@code ZonedDateTime} or "null"
	 * @see ZonedDateTime#parse(CharSequence);
	 */
	static NullableDateTime parseIso(String strInput) {
		Objects.requireNonNull(strInput, "strInput");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(ZonedDateTime.parse(strInput));
		}		
	}
	
	/**
	 * Parse a text string using a formatter.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @param formatter The formatter to use for parsing.
	 * @return A {@code NullableDateTime} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
     * @throws DateTimeParseException if the input does not contain a parseable representation
	 * 	of a {@code ZonedDateTime} or "null"
	 * @see ZonedDateTime#parse(CharSequence, DateTimeFormatter)
	 * @see DateTimeFormatter#parse(CharSequence, java.text.ParsePosition)
	 */
	static NullableDateTime parse(String strInput, DateTimeFormatter formatter) {
		Objects.requireNonNull(strInput, "strInput");
		Objects.requireNonNull(formatter, "formatter");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableDateTimeValue();
		} else {
			return new NullableDateTimeValue(formatter.parse(strInput, ZonedDateTime::from));
		}		
	}
	
	/**
	 * Parse a text string using a formatter and apply a time zone setting.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @param formatter The formatter to use for parsing.
	 * @param idZone The {@code ZoneId} to assign.
	 * @return A {@code NullableDateTime} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
     * @throws DateTimeParseException if the input does not contain a parseable representation
	 * 	of a {@code LocalDateTime} or "null"
	 * @see LocalDateTime#parse(CharSequence, DateTimeFormatter)
	 * @see DateTimeFormatter#parse(CharSequence, java.text.ParsePosition)
	 */
	static NullableDateTime parseAndSetZone(String strInput, DateTimeFormatter formatter, ZoneId idZone) {
		Objects.requireNonNull(strInput, "strInput");
		Objects.requireNonNull(formatter, "formatter");
		Objects.requireNonNull(idZone, "idZone");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableDateTimeValue();
		} else {
			LocalDateTime dtLocal = formatter.parse(strInput, LocalDateTime::from);
			return new NullableDateTimeValue(dtLocal.atZone(idZone));
		}		
	}
}
