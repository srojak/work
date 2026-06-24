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
package srojak.valuestore.values;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.numerics.ConditionSense;
import srojak.numerics.IntervalType;
import srojak.numerics.intervals.IntervalDouble;

/**
 * @author Stephen
 *
 */
public class StoreValueDoubleInInterval
		extends StoreValueDoubleInstance {
	private final ConditionSense _sense;
	private final IntervalDouble _interval;

	/**
	 * @param key
	 * @param valueInitial
	 */
	public StoreValueDoubleInInterval(NamedKey key, double valueInitial,
			ConditionSense sense, IntervalDouble interval) {
		super(key, valueInitial);
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(interval, "interval");
		_sense = sense;
		_interval = interval;
	}
	
	public StoreValueDoubleInInterval(NamedKey key, double valueInitial,
			ConditionSense sense, IntervalType typeInterval, double valueMin, double valueMax) {
		super(key, valueInitial);
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(typeInterval, "typeInterval");
		_sense = sense;
		_interval = new IntervalDouble(typeInterval, valueMin, valueMax);
	}
	
	@Override
	protected void validate(double value) {
		if (!_sense.isExpectedResult(_interval.isInInterval(value))) {
			faultInvalid(getKey(), "value");
		}
	}
}
