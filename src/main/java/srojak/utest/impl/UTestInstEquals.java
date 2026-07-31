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
package srojak.utest.impl;

import java.util.Objects;

import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestEqualsMethods;
import srojak.utest.identifiers.TestInstanceIdentifier;

/**
 * @author Stephen
 *
 * A test instance for comparing values for equality.
 * @param <T> The type of the values.
 */
public class UTestInstEquals<T> 
		extends UTestInstValueBase {
	private final UnitTestEqualsMethods<T> _methods;

	/**
	 * Constructor.
	 * @param utest The {@code UnitTestSeries} that created this instance.
	 * @param strInstance The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods
	 */
	public UTestInstEquals(UnitTestSeries utest, TestInstanceIdentifier idInstance, 
			String strValueName, UnitTestEqualsMethods<T> methods) {
		super(utest, idInstance, strValueName);
		Objects.requireNonNull(methods, "methods");
		_methods = methods;
	}

	public void areEqual(T expected, T actual) {
		StringBuilder sb = getInitialString();
		sb.append(" expect=");
		sb.append(_methods.format(expected));
		sb.append(" equals actual=");
		sb.append(_methods.format(actual));
		setOutcome(TestOutcome.evaluate(() -> _methods.areEqual(expected, actual)));
		writeOutcomeMessage(getOutcome(), sb.toString());
	}
	
	public void areNotEqual(T expected, T actual) {
		StringBuilder sb = getInitialString();
		sb.append(" expect=");
		sb.append(_methods.format(expected));
		sb.append(" not equals actual=");
		sb.append(_methods.format(actual));
		setOutcome(TestOutcome.evaluate(() -> !_methods.areEqual(expected, actual)));
		writeOutcomeMessage(getOutcome(), sb.toString());	
	}
}
