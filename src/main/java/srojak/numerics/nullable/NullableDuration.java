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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import srojak.numerics.NullableComparable;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public sealed interface NullableDuration 
		extends NullableComparable<NullableDuration>
		permits NullableDurationValue {

	Duration value();
	
	List<TemporalUnit> getUnits();
	
	boolean isNegative();
	
	boolean isZero();
	
	boolean isEqualTo(Duration dur);
	
	NullableBoolean compareTo(OrderedComparison comparison, Duration dur);
	
	NullableBoolean compareTo(OrderedComparison comparison, NullableDuration other);
	
	NullableLong toSeconds();
	
	NullableLong toMinutes();
	
	NullableLong toHours();
	
	NullableLong toDays();
	
	NullableLong toChronoUnit(ChronoUnit unit);
	
	NullableDuration plus(Duration dur);
	
	NullableDuration plus(NullableDuration operand);
	
	NullableDuration plus(long lnAmount, TemporalUnit unit);
	
	NullableDuration minus(Duration dur);
	
	NullableDuration minus(NullableDuration operand);
	
	NullableDuration minus(long lnAmount, TemporalUnit unit);
	
	NullableDuration subtractFrom(Duration dur);
	
	NullableDuration multipledBy(NullableLong operand);
	
	NullableDuration dividedBy(NullableLong operand);
	
	NullableDuration abs();
	
	NullableDuration negate();
	
	default Comparator<NullableDuration> getComparator() {
		return new NullableGenericComparator<NullableDuration>();
	}
	
	public static NullableDuration from(Duration dur) {
		if (dur == null) {
			return new NullableDurationValue();
		} else {
			return new NullableDurationValue(dur);
		}
	}
	
	public static NullableDuration fromNonNull(Duration dur) {
		Objects.requireNonNull(dur, "dur");
		return new NullableDurationValue(dur);
	}
	
	public static NullableDuration fromNull() {
		return new NullableDurationValue();
	}
}
