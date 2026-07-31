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
import srojak.numerics.OrderedComparison;
import srojak.numerics.SinglePrecisionComparer;
import srojak.numerics.intervals.IntervalFloat;
import srojak.utest.conditions.UnitTestFloatIntervalComparison;
import srojak.utest.conditions.UnitTestFloatValueComparison;

/**
 * @author Stephen
 *
 */
public interface UnitTestConditionFloat {
	String getConditionDesc();
	boolean test(float actual, SinglePrecisionComparer comparer);
	
	public static UnitTestConditionFloat makeValueCondition(OrderedComparison comparison, 
			float valueExpected) {
		Objects.requireNonNull(comparison);
		return new UnitTestFloatValueComparison(comparison, valueExpected);
	}
	
	public static UnitTestConditionFloat makeIntervalCondition(ConditionSense sense,
			IntervalFloat interval) {
		Objects.requireNonNull(sense);
		Objects.requireNonNull(interval);
		return new UnitTestFloatIntervalComparison(sense, interval);
	}
}
