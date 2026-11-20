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
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Objects;

import srojak.core.excepts.NullValueException;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public final class NullableDateValue 
		extends NullableValueBase 
		implements NullableDate {
	private final LocalDate _value;
	
	NullableDateValue(LocalDate dtValue) {
		super(false);
		Objects.requireNonNull(dtValue, "dtValue");
		_value = dtValue;		
	}
	
	NullableDateValue() {
		super(true);
		_value = LocalDate.EPOCH;
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
	public boolean isEqual(NullableDate obj) {
		if (obj == null) {
			return false;
		} else if (_isNull || obj.isNull()) {
			return false;
		} else {
			return _value.equals(obj.value());
		}
	}

	@Override
	public int compareTo(NullableDate o) {
		Objects.requireNonNull(o, "o");
		if (_isNull || o.isNull()) {
			throw new NullValueException("at least one operand in comparison is null");
		} else {
			return _value.compareTo(o.value());
		}
	}

	@Override
	public LocalDate value() {
		if (_isNull) {
			throw new NullValueException("value is null");
		}
		return _value;
	}

	@Override
	public boolean isEqualTo(LocalDate dtValue) {
		Objects.requireNonNull(dtValue, "zdtValue");
		if (_isNull) {
			return false;
		} else {
			return _value.equals(dtValue);
		}
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, LocalDate dtValue) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(dtValue, "zdtValue");
		if (_isNull) {
			return new NullableBooleanValue();
		} else {
			boolean bCompar = comparison.evaluate(_value.compareTo(dtValue));
			return new NullableBooleanValue(bCompar);
		}
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, NullableDate other) {
		Objects.requireNonNull(comparison, "comparison");
		Objects.requireNonNull(other, "other");
		if (_isNull || other.isNull()) {
			return new NullableBooleanValue();
		} else {
			boolean bCompar = comparison.evaluate(_value.compareTo(other.value()));
			return new NullableBooleanValue(bCompar);
		}
	}

	@Override
	public NullableDate plus(long lnAmount, TemporalUnit unit) {
		Objects.requireNonNull(unit, "unit");
		if (_isNull) {
			return new NullableDateValue();
		} else {
			return new NullableDateValue(_value.plus(lnAmount, unit));
		}
	}

	@Override
	public NullableDate plus(NullableLong amount, TemporalUnit unit) {
		Objects.requireNonNull(amount, "amount");
		Objects.requireNonNull(unit, "unit");
		if (_isNull || amount.isNull()) {
			return new NullableDateValue();
		} else {
			return new NullableDateValue(_value.plus(amount.value(), unit));
		}
	}

	@Override
	public NullableDate minus(long lnAmount, TemporalUnit unit) {
		Objects.requireNonNull(unit, "unit");
		if (_isNull) {
			return new NullableDateValue();
		} else {
			return new NullableDateValue(_value.minus(lnAmount, unit));
		}
	}

	@Override
	public NullableDate minus(NullableLong amount, TemporalUnit unit) {
		Objects.requireNonNull(amount, "amount");
		Objects.requireNonNull(unit, "unit");
		if (_isNull || amount.isNull()) {
			return new NullableDateValue();
		} else {
			return new NullableDateValue(_value.minus(amount.value(), unit));
		}
	}

	@Override
	public NullableLong until(Temporal endExclusive, TemporalUnit unit) {
		Objects.requireNonNull(endExclusive, "endExclusive");
		Objects.requireNonNull(unit, "unit");
		if (_isNull) {
			return new NullableLongValue();
		} else {
			return new NullableLongValue(_value.until(endExclusive, unit));
		}
	}

	@Override
	public NullableLong until(NullableDate endExclusive, TemporalUnit unit) {
		Objects.requireNonNull(endExclusive, "endExclusive");
		Objects.requireNonNull(unit, "unit");
		if (_isNull || endExclusive.isNull()) {
			return new NullableLongValue();
		} else {
			return new NullableLongValue(_value.until(endExclusive.value(), unit));
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
		} else if (obj instanceof NullableDate other) {
			return isEqual(other);
		} else if (obj instanceof LocalDate other) {
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
			return new NullableDateValue();
		} else {
			return new NullableDateValue(_value);
		}
	}
	
	
	/**
	 * Parse a string in ISO-8601 format.
	 * 
	 * Example: @{code "2026-11-20" }
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @return A {@code NullableDate} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
     * @throws DateTimeParseException if the input does not contain a parseable representation
	 * 	of a {@code LocalDate} or "null"
	 * @see LocalDate#parse(CharSequence);
	 */
	static NullableDate parseIso(String strInput) {
		Objects.requireNonNull(strInput, "strInput");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableDateValue();
		} else {
			return new NullableDateValue(LocalDate.parse(strInput));
		}		
	}
	
	/**
	 * Parse a text string using a formatter.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @param formatter The formatter to use for parsing.
	 * @return A {@code NullableDate} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
     * @throws DateTimeParseException if the input does not contain a parseable representation
	 * 	of a {@code LocalDate} or "null"
	 * @see LocalDate#parse(CharSequence, DateTimeFormatter)
	 * @see DateTimeFormatter#parse(CharSequence, java.text.ParsePosition)
	 */
	static NullableDate parse(String strInput, DateTimeFormatter formatter) {
		Objects.requireNonNull(strInput, "strInput");
		Objects.requireNonNull(formatter, "formatter");
		if (strInput.isEmpty() || strInput.equalsIgnoreCase(STR_NULL)) {
			return new NullableDateValue();
		} else {
			return new NullableDateValue(formatter.parse(strInput, LocalDate::from));
		}		
	}
}
