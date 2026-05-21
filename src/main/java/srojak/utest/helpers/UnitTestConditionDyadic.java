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
package srojak.utest.helpers;

import java.util.Objects;

import srojak.utest.UnitTestDyadicPredicate;
import srojak.utest.conditions.UnitTestCondition;

/**
 * @author Stephen
 *
 */
public class UnitTestConditionDyadic<T>
		extends UnitTestCondition {
	private final UnitTestDyadicPredicate<T> _predicate;
	
	/**
	 * 
	 */
	public UnitTestConditionDyadic(String strCondition, UnitTestDyadicPredicate<T> predicate) {
		super(strCondition);
		Objects.requireNonNull(predicate, "predicate");
		_predicate = predicate;
	}

	public boolean test(T expected, T actual) {
		return _predicate.test(expected, actual);
	}
}
