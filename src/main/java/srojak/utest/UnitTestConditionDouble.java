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
package srojak.utest;

import java.util.Objects;

import srojak.numerics.ConditionSense;
import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.OrderedComparison;
import srojak.numerics.intervals.IntervalDouble;
import srojak.utest.conditions.UnitTestDoubleIntervalComparison;
import srojak.utest.conditions.UnitTestDoubleValueComparison;

/**
 * @author Stephen
 *
 * A unit test condition that applies to {@code double} values.
 */
public interface UnitTestConditionDouble {
	String getConditionDesc();
	boolean test(double actual, DoublePrecisionComparer comparer);
	
	public static UnitTestConditionDouble makeValueCondition(OrderedComparison comparison, 
			double valueExpected) {
		Objects.requireNonNull(comparison);
		return new UnitTestDoubleValueComparison(comparison, valueExpected);
	}
	
	public static UnitTestConditionDouble makeIntervalCondition(ConditionSense sense,
			IntervalDouble interval) {
		Objects.requireNonNull(sense);
		Objects.requireNonNull(interval);
		return new UnitTestDoubleIntervalComparison(sense, interval);
	}
}
