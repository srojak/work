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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestClassElementMethods;

/**
 * @author Stephen
 *
 * A test instance for evaluating properties of objects.
 * @param <T> The type of the object(s) to be evaluated.
 */
public class UTestInstItemHas<T>
		extends UTestInstValueBase {
	private final UnitTestClassElementMethods<T> _methods;
	private final String _strExpect;
	private final Predicate<T> _predicate;
	
	/**
	 * Constructor
	 * @param utest The {@code UnitTestSeries} that created this instance.
	 * @param strInstance The name of the test instance.
	 * @param methods The container with methods to use for the test.
	 * @param strExpected A description of the expected condition.
	 * @param predicateExpected A predicate that tests for the expected condition.
	 */
	public UTestInstItemHas(UnitTestSeries utest, String strInstance, String strValueName,
			UnitTestClassElementMethods<T> methods, String strExpect, Predicate<T> predicate) {
		super(utest, strInstance, strValueName);
		Objects.requireNonNull(strExpect, "strCharacteristic");
		if (strExpect.isEmpty()) {
			throw new IllegalArgumentException("strCharacteristic is empty");
		}
		Objects.requireNonNull(methods, "methods");
		Objects.requireNonNull(predicate, "predicate");
		_methods = methods;
		_strExpect = strExpect;
		_predicate = predicate;
	}
	
	/**
	 * Apply the tests to an actual object instance.
	 * @param actual The instance to test.
	 */
	public void execute(T actual) {
		StringBuilder sb = getInitialString();
		sb.append("expect ");
		sb.append(_strExpect);
		sb.append(": ");
		setOutcome(TestOutcome.evaluate(() -> _predicate.test(actual)));
		writeOutcomeMessage(getOutcome(), sb.toString());
	}
	
	/**
	 * Apply the tests to all elements of a collection.
	 * @param actual The collection to test.
	 */
	public void executeOver(Collection<T> actual)
	{
		LinkedList<String> listFails = new LinkedList<String>();
		StringBuilder sb = getInitialString();
		sb.append("collection of ");
		sb.append(_methods.getClass().getSimpleName());
		sb.append("expecting ");
		sb.append(_strExpect);
		Iterator<T> iter = actual.iterator();
		for (int index = 0; ; index++) {
			if (!iter.hasNext()) {
				break;
			}
			T itemActual = iter.next();
			if (itemActual == null) {
				listFails.add("actual item " + index + " is null");
			} else if (!_predicate.test(itemActual)) {
				listFails.add("actual item " + index + " (" + _methods.format(itemActual)
						+ ") does not have " + _strExpect);
			}
		}
		TestOutcome outcome = TestOutcome.NONE;
		if (listFails.isEmpty()) {
			outcome = TestOutcome.PASS;
		} else {
			sb.append(" exceptions:");
			for (String s : listFails) {
				sb.append("\n    ");
				sb.append(s);
			}
			outcome = TestOutcome.FAIL;
		}
		setOutcome(outcome);
		writeOutcomeMessage(outcome, sb.toString());
	}
}
