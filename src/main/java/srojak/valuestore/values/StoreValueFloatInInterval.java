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
import srojak.numerics.intervals.IntervalFloat;

/**
 * @author Stephen
 *
 */
public class StoreValueFloatInInterval 
		extends StoreValueFloatInstance {
	private final ConditionSense _sense;
	private final IntervalFloat _interval;

	/**
	 * @param key
	 * @param valueInitial
	 */
	public StoreValueFloatInInterval(NamedKey key, float valueInitial,
			ConditionSense sense, IntervalFloat interval) {
		super(key, valueInitial);
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(interval, "interval");
		_sense = sense;
		_interval = interval;
	}
	
	public StoreValueFloatInInterval(NamedKey key, float valueInitial,
			ConditionSense sense, IntervalType typeInterval, float valueMin, float valueMax) {
		super(key, valueInitial);
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(typeInterval, "typeInterval");
		_sense = sense;
		_interval = new IntervalFloat(typeInterval, valueMin, valueMax);
	}

	@Override
	protected void validate(float value) {
		if (!_sense.isExpectedResult(_interval.isInInterval(value))) {
			faultInvalid(getKey(), "value");
		}
	}

}
