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
package srojak.utest.conditions;

import java.util.Objects;

import srojak.numerics.ConditionSense;
import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.intervals.IntervalDouble;

/**
 * @author Stephen
 *
 */
public class UnitTestDoubleIntervalComparison 
		extends UnitTestConditionDoubleBase {
	private final IntervalDouble _interval;
	private final ConditionSense _sense;
	
	public UnitTestDoubleIntervalComparison(ConditionSense sense, IntervalDouble interval) {
		super();
		Objects.requireNonNull(interval);
		if (interval.isDegenerate()) {
			throw new IllegalArgumentException("interval is degenerate");
		}
		_interval = interval;
		_sense = sense;
		setConditionDesc(sense.getVerb() + " in interval " + interval);
	}

	@Override
	public boolean test(double actual, DoublePrecisionComparer comparer) {
		return _sense.isExpectedResult(_interval.isInInterval(actual, comparer));
	}
}
