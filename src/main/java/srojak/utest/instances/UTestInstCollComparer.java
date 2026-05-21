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
package srojak.utest.instances;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.helpers.UnitTestClassElementMethods;
import srojak.utest.impl.UTestInstValueBase;

/**
 * @author Stephen
 *
 * A test instance for comparing a collection to a set of expected values.
 * @param <E> The type of the elements of the collection.
 */
public class UTestInstCollComparer<E> 
		extends UTestInstValueBase
		implements UnitTestCollectionComparer<E> {
	private final UnitTestClassElementMethods<E> _methods;
	private final LinkedList<String> _listFails;
	
	/**
	 * Constructor.
	 * @param utest The {@code UnitTestSeries} that created this instance.
	 * @param strInstance The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods The container with methods to use for the test.
	 */
	public UTestInstCollComparer(UnitTestSeries utest, String strInstance, 
			String strValueName, UnitTestClassElementMethods<E> methods) {
		super(utest, strInstance, strValueName);
		Objects.requireNonNull(methods, "methods");
		_methods = methods;
		_listFails = new LinkedList<String>();
	}
	
	/**
	 * The type of the elements in the collection.
	 * @return The type of the elements in the collection.
	 */
	public Type getElementType() {
		return _methods.getElementClass();
	}
	
	private void compareLengths(int szExpected, int szActual) {
		if (szExpected != szActual) {
			_listFails.add("expected " + szExpected + " elements, actual has " + szActual);
		}
	}
	
	private void compareItems(int index, E expected, E actual, BiPredicate<E, E> comparer) {
		if (expected == null) {
			if (actual != null) {
				_listFails.add("expected item " + index + " to be null, but actual is not");
			}
		} else {
			if (actual == null) {
				_listFails.add("expected item " + index + " to be " 
						+ _methods.format(expected)
						+ ", but actual is null");
			} else if (!comparer.test(expected, actual)) {
				_listFails.add("expected item " + index + " to be "
						+ _methods.format(expected)
						+ ", but actual is " + _methods.format(actual));
			}
		}
	}
	
	private void evaluateResults() {
		TestOutcome outcome = TestOutcome.NONE;
		StringBuilder sb = getInitialString();
		sb.append("collection of ");
		sb.append(_methods.getElementSimpleName());
		if (_listFails.isEmpty()) {
			sb.append(" matched expected values");
			outcome = TestOutcome.PASS;
		} else {
			sb.append(" did not match expected values:");
			for (String s : _listFails) {
				sb.append("\n    ");
				sb.append(s);
			}
			outcome = TestOutcome.FAIL;
		}
		setOutcome(outcome);
		checkStopOnFailure();
		writeOutcomeMessage(outcome, sb.toString());
	}

	/**
	 * Compare an array of expected values to a collection of actual values.
	 * The {@code equals( )} method of the object will be used to compare elements.
	 * @param expected The array of expected values.
	 * @param actual The collection of actual values.
	 */
	public void compare(E[] expected, Collection<E> actual, BiPredicate<E, E> comparerEq) {
		Objects.requireNonNull(comparerEq, "comparerEq");
		compareLengths(expected.length, actual.size());
		Iterator<E> iter = actual.iterator();
		for (int index = 0; index < expected.length; index++) {
			if (!iter.hasNext()) {
				break;
			}
			E itemActual = iter.next();
			compareItems(index, expected[index], itemActual, comparerEq);
		}
		evaluateResults();	
	}
	
	/**
	 * Compare an array of expected values to a collection of actual values.
	 * @param expected The array of expected values.
	 * @param actual The collection of actual values.
	 * @param comparerEq The callback to compare objects of type {@code <E>}
	 *   for equality.
	 */
	public void compare(E[] expected, Collection<E> actual) {
		compare(expected, actual, (a, b) -> a.equals(b));
	}
	
	/**
	 * Compare a list of expected values to a collection of actual values.
	 * The {@code equals( )} method of the object will be used to compare elements.
	 * @param expected The list of expected values.
	 * @param actual The collection of actual values.
	 */
	public void compare(List<E> expected, Collection<E> actual, BiPredicate<E, E> comparerEq) {
		Objects.requireNonNull(comparerEq, "comparerEq");
		compareLengths(expected.size(), actual.size());
		Iterator<E> iter = actual.iterator();
		for (int index = 0; index < expected.size(); index++) {
			if (!iter.hasNext()) {
				break;
			}
			E itemActual = iter.next();
			compareItems(index, expected.get(index), itemActual, comparerEq);
		}
		evaluateResults();	
	}
	
	/**
	 * Compare a list of expected values to a collection of actual values.
	 * @param expected The list of expected values.
	 * @param actual The collection of actual values.
	 * @param comparerEq The callback to compare objects of type {@code <E>}
	 *   for equality.
	 */
	public void compare(List<E> expected, Collection<E> actual) {
		compare(expected, actual, (a, b) -> a.equals(b));
	}
}
