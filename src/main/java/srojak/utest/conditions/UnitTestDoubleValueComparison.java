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

import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public class UnitTestDoubleValueComparison
		extends UnitTestConditionDoubleBase {
	private OrderedComparison _comparison;
	private double _expected;

	/**
	 * @param strCondition
	 */
	public UnitTestDoubleValueComparison(OrderedComparison comparison, double valueExpected) {
		super();
		Objects.requireNonNull(comparison, "comparison");
		_comparison = comparison;
		_expected = valueExpected;
		setConditionDesc("is " + comparison + " " + _expected);
	}

	@Override
	public boolean test(double actual, DoublePrecisionComparer comparer) {
		return _comparison.evaluate(comparer.compare(actual, _expected));
	}

}
