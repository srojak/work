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

import java.util.function.BiPredicate;

/**
 * @author Stephen
 *
 */
public enum StringCondition {
	EQUALS((a, e) -> a.equals(e)),
	STARTS_WITH((a, e) -> a.startsWith(e)),
	CONTAINS((a, e) -> a.contains(e)),
	MATCHES((a, e) -> a.matches(e));
	
	private final BiPredicate<String, String> _predicate;
	
	private StringCondition(BiPredicate<String, String> predicate) {
		_predicate = predicate;
	}
	
	public boolean evaluate(String strExpected, String strActual) {
		return _predicate.test(strActual, strExpected);
	}
}
