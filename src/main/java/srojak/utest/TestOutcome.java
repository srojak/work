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
import java.util.function.BooleanSupplier;

/**
 * @author Stephen
 *
 * The outcome of a test.
 */
public enum TestOutcome {
	/**
	 * The test has yet to be run.
	 */
	NONE,
	
	/**
	 * The test completed as expected.
	 */
	PASS,
	
	/**
	 * The test did not complete as expected.
	 */
	FAIL;
	
	/**
	 * Evaluate a boolean functional expression.
	 * @param expr the expression to evaluate.
	 * @return {@value PASS} if {@value true}; {@value FAIL} if {@value false}.
	 */
	public static TestOutcome evaluate(BooleanSupplier expr) {
		Objects.requireNonNull(expr, "expr");
		return expr.getAsBoolean() ? PASS : FAIL;
	}
}
