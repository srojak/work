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

import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public class UnitTestValueComparison<T extends Comparable<T>>
		extends UnitTestConditionValueBase<T> {
	private OrderedComparison _comparison;
	private T _expected;

	public UnitTestValueComparison(OrderedComparison comparison, T valueExpected) {
		super(comparison.toString());
		Objects.requireNonNull(valueExpected, "valueExpected");
		_comparison = comparison;
		_expected = valueExpected;
	}

	@Override
	public boolean test(T actual) {
		return _comparison.evaluate(actual.compareTo(_expected));
	}
}
