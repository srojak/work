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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Objects;

import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public final class NullableDurationValue 
		extends NullableValueBase 
		implements NullableDuration {
	private final Duration _value;
	
	NullableDurationValue(Duration duration) {
		super(false);
		Objects.requireNonNull(duration, "duration");
		_value = duration;
	}
	
	NullableDurationValue() {
		super(true);
		_value = Duration.ZERO;
	}

	@Override
	public boolean isEqual(NullableDuration obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(NullableDuration o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Duration value() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TemporalUnit> getUnits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isNegative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isZero() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqualTo(Duration dur) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, Duration dur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, NullableDuration other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableLong toSeconds() {
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(_value.toSeconds());
		}
	}

	@Override
	public NullableLong toMinutes() {
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(_value.toMinutes());
		}
	}

	@Override
	public NullableLong toHours() {
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(_value.toHours());
		}
	}

	@Override
	public NullableLong toDays() {
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(_value.toDays());
		}
	}

	@Override
	public NullableLong toChronoUnit(ChronoUnit unit) {
		Objects.requireNonNull(unit, "unit");
		if (_isNull) {
			return new NullableLongBox();
		} else {
			return new NullableLongBox(_value.dividedBy(unit.getDuration()));
		}
	}

	@Override
	public NullableDuration plus(Duration dur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration plus(NullableDuration operand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration plus(long lnAmount, TemporalUnit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration minus(Duration dur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration minus(NullableDuration operand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration minus(long lnAmount, TemporalUnit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration subtractFrom(Duration dur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration multipledBy(NullableLong operand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration dividedBy(NullableLong operand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration abs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDuration negate() {
		// TODO Auto-generated method stub
		return null;
	}

}
