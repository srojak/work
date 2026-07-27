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

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import srojak.core.observe.ObsLevel;
import srojak.numerics.ConditionSense;
import srojak.numerics.OrderedComparison;
import srojak.utest.conditions.StringCondition;
import srojak.utest.helpers.UnitTestClassElementMethods;
import srojak.utest.helpers.UnitTestConditionComparison;
import srojak.utest.helpers.UnitTestEqualsMethods;
import srojak.utest.impl.UTestCommonMessages;
import srojak.utest.impl.UTestInstCondDouble;
import srojak.utest.impl.UTestInstCondFloat;
import srojak.utest.impl.UTestInstCondInt;
import srojak.utest.impl.UTestInstCondLong;
import srojak.utest.impl.UTestInstDyadic;
import srojak.utest.impl.UTestInstDyadicBool;
import srojak.utest.impl.UTestInstEquals;
import srojak.utest.impl.UTestInstItemHas;
import srojak.utest.impl.UnitTestSeriesBase;
import srojak.utest.instances.UTestInstCollComparer;
import srojak.utest.instances.UTestSupvConsumer;
import srojak.utest.instances.UTestSupvFunction;
import srojak.utest.instances.UTestSupvVoid;
import srojak.utest.instances.UnitTestCollectionComparer;
import srojak.utest.instances.UnitTestSupervisedConsumer;
import srojak.utest.instances.UnitTestSupervisedFunction;
import srojak.utest.instances.UnitTestSupervisedVoid;

/**
 * @author Stephen Rojak
 *
 * The central store for a series of unit tests.
 */
public class UnitTestSeries
		extends UnitTestSeriesBase {
	
	public static final ObsLevel LEVEL_NON_FAILURE = ObsLevel.INFO;
	
	/**
	 * Constructor
	 * @param strName The name for the series.
	 * @throws NullPointerException if {@code strName} is {@value null}.
	 */
	public UnitTestSeries(String strName) {
		super(strName);
	}
	
	/**
	 * write a completion message for the test series.
	 */
	public void complete() {
		StringBuilder sb = new StringBuilder("End of tests for ");
		sb.append(getName());
		sb.append("; ");
		int nFailed = getFailedTestCount();
		if (nFailed > 0) {
			sb.append(nFailed);
			sb.append(" tests failed");
		} else {
			sb.append("all tests passed");
		}
		writeMessageLine(UnitTestSeries.LEVEL_NON_FAILURE, sb.toString());
	}
	
	/**
	 * Write an explanatory note to the output destination.
	 * 
	 * @param strText The text of the note.
	 */
	public void writeNote(String strText) {
		StringBuilder sb = UTestCommonMessages.startMessageLine(this);
		sb.append(": ");
		sb.append(strText);
		writeMessageLine(ObsLevel.INFO, sb.toString());
	}

	/**
	 * Evaluate a test where the expected value of an object reference is compared to {@value null}.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param sense the comparison sense of the test.
	 * @param actual The actual object reference.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectNull(String strTest, String strValueName,
			ConditionSense sense, Object actual) {
		StringBuilder sb = UTestCommonMessages.startTestMessageLine(this, strTest);
		sb.append(strValueName);
		sb.append(' ');
		sb.append(sense.getVerb());
		sb.append(" null? ");
		TestOutcome outcome = TestOutcome.evaluate(() -> sense.isExpectedResult(actual == null));
		sb.append(outcome);
		writeOutcomeMessage(outcome, sb.toString());
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code boolean} value.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param expected The expected boolean value.
	 * @param actual The actual boolean value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValue(String strTest, String strValueName, boolean expected, boolean actual) {
		UTestInstDyadicBool instance
			= new UTestInstDyadicBool(this, strTest, strValueName);
		instance.execute(expected, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code int} value.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The condition to evaluate for the actual value.
	 * @param actual The actual integer value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValueWhere(String strTest, String strValueName,
			UnitTestConditionInt condition, int actual) {
		UTestInstCondInt instance
			= new UTestInstCondInt(this, strTest, strValueName);
		instance.execute(condition, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code long} value.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The condition to evaluate for the actual value.
	 * @param actual The actual long integer value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValueWhere(String strTest, String strValueName,
			UnitTestConditionLong condition, long actual) {
		UTestInstCondLong instance
			= new UTestInstCondLong(this, strTest, strValueName);
		instance.execute(condition, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code float} value.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The condition to evaluate for the actual value.
	 * @param actual The actual float value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValueWhere(String strTest, String strValueName,
			UnitTestConditionFloat condition, float actual) {
		UTestInstCondFloat instance
			= new UTestInstCondFloat(this, strTest, strValueName,
					_options.getFloatComparer());
		instance.execute(condition, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code double} value.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The condition to evaluate for the actual value.
	 * @param actual The actual double value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValueWhere(String strTest, String strValueName,
			UnitTestConditionDouble condition, double actual) {
		UTestInstCondDouble instance
			= new UTestInstCondDouble(this, strTest, strValueName,
					_options.getDoubleComparer());
		instance.execute(condition, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code String} value.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The string condition to evaluate for the actual value.
	 * @param expected The expected value, which is an input to the condition.
	 * @param actual The actual String value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectString(String strTest, String strValueName,
			StringCondition condition, String expected, String actual) {
		Objects.requireNonNull(condition, "condition");
		Objects.requireNonNull(expected, "expected");
		StringBuilder sb = UTestCommonMessages.startTestMessageLine(this, strTest);
		sb.append(strValueName);
		sb.append(' ');
		sb.append(condition);
		sb.append(' ');
		sb.append(expected);
		sb.append("? ");
		TestOutcome outcome = TestOutcome.NONE;
		if (actual == null) {
			sb.append("actual is null");
			outcome = TestOutcome.FAIL;
		} else {
			sb.append("actual=\"");
			sb.append(actual);
			sb.append("\"");
			outcome = TestOutcome.evaluate(() -> condition.evaluate(expected, actual));
		}
		writeOutcomeMessage(outcome, sb.toString());
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is an enum value.
	 * @param <T> The type of the enum value.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param sense the comparison sense of the test.
	 * @param expected The expected value.
	 * @param actual The actual value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <T extends Enum<T>> TestOutcome expectEnumValue(String strTest, String strValueName,
			ConditionSense sense, T expected, T actual) {
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(expected, "expected");
		StringBuilder sb = UTestCommonMessages.startTestMessageLine(this, strTest);
		sb.append(strValueName);
		sb.append(' ');
		sb.append(sense.getVerb());
		sb.append(" equals ");
		sb.append(expected);
		sb.append("? ");
		TestOutcome outcome = TestOutcome.NONE;
		if (actual == null) {
			sb.append("actual is null");
			outcome = TestOutcome.FAIL;
		} else {
			sb.append("actual=");
			sb.append(actual);
			outcome = TestOutcome.evaluate(() -> sense.isExpectedResult(expected.equals(actual)));
		}
		writeOutcomeMessage(outcome, sb.toString());
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test for equality where the result is an object.
	 * @param <T> The type of the object being evaluated.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods The container with methods to use for the test.
	 * @param expected The expected value.
	 * @param actual The actual value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <T> TestOutcome expectValueEquals(String strTest, String strValueName,
			UnitTestEqualsMethods<T> methods, T expected, T actual)
	{
		UTestInstEquals<T> instance
			= new UTestInstEquals<T>(this, strTest, strValueName, methods);
		instance.areEqual(expected, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test for inequality where the result is an object.
	 * The {@code equals( )} method of the object will be used.
	 * @param <T> The type of the object being evaluated.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods The container with methods to use for the test.
	 * @param expected The expected value.
	 * @param actual The actual value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <T> TestOutcome expectValueNotEquals(String strTest, String strValueName,
			UnitTestEqualsMethods<T> methods, T expected, T actual)
	{
		UTestInstEquals<T> instance
			= new UTestInstEquals<T>(this, strTest, strValueName, methods);
		instance.areNotEqual(expected, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a value that can be compared to another.
	 * @param <T> The type of the result, which must implement {@code Comparable<T>}.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param comparison The {@code ValueComparison} defining the comparison.
	 * @param expected The expected value.
	 * @param actual The actual value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <T extends Comparable<T>> TestOutcome expectValue(String strTest, String strValueName,
			OrderedComparison comparison, T expected, T actual) {
		UTestInstDyadic<T> instance 
			= new UTestInstDyadic<T>(this, strTest, strValueName,
					new UnitTestConditionComparison<T>(comparison));
		instance.execute(expected, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is an object of a specific type.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param classExpected The {@code Class} of the expected type.
	 * @param actual The actual object produced by the test.
	 * @return the {@code TestOutcome} for the test.
	 * @throws NullPointerException if classExpected is {@value null}.
	 */
	public TestOutcome expectType(String strTest, String strValueName, 
			Class<?> classExpected, Object actual) {
		Objects.requireNonNull(classExpected, "classExpected");
		StringBuilder sb = UTestCommonMessages.startTestMessageLine(this, strTest);
		sb.append(strValueName);
		sb.append(" is of type ");
		sb.append(classExpected.getTypeName());
		sb.append("? ");
		TestOutcome outcome = TestOutcome.FAIL;
		if (actual != null) {
			Class<?> classActual = actual.getClass();
			outcome = TestOutcome.evaluate(() -> classExpected.isAssignableFrom(classActual));
		}
		sb.append(outcome);
		writeOutcomeMessage(outcome, sb.toString());
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Create a comparing test instance for a collection.
	 * @param <E> The type of the elements in the collection.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods The container with methods to use for the test.
	 * @return The test instance object that will perform the test.
	 */
	public <E> UnitTestCollectionComparer<E> createCollectionComparer(String strTest, 
			String strValueName, UnitTestClassElementMethods<E> methods) {
		Objects.requireNonNull(methods, "methods");
		return new UTestInstCollComparer<E>(this, strTest, strValueName, methods);
	}
	
	/**
	 * Test all elements of an collection for a condition.
	 * @param <E> The type of the elements in the collection.
	 * @param strTest The name of the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods The container with methods to use for the test.
	 * @param strExpected A description of the expected condition.
	 * @param predicateExpected A predicate that tests for the expected condition.
	 * @param actual The actual collection produced by the test.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <E> TestOutcome expectAllElementsToHave(String strTest,
			String strValueName, UnitTestClassElementMethods<E> methods,
			String strExpected,	Predicate<E> predicateExpected, Collection<E> actual) {
		Objects.requireNonNull(predicateExpected, "predicateExpected");
		Objects.requireNonNull(methods, "methods");
		UTestInstItemHas<E> instance = new UTestInstItemHas<E>(this, strTest, strValueName,
				methods, strExpected, predicateExpected);
		instance.executeOver(actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(strTest, outcome);
		return outcome;
	}
	
	/**
	 * Create a supervised test instance for a test returning an object of a specific type.
	 * @param <T> The type of the object returned from the test.
	 * @param strTest The name of the test instance.
	 * @param outcomeTryBlock The {@code TestOutcome} if the code completes without throwing
	 * 		an exception.
	 * @param methodTest The expression to execute to perform the text.
	 * @return The test instance object that will perform the test.
	 */
	public <T> UnitTestSupervisedVoid<T> createVoidInstance(String strTest,
			TestOutcome outcomeTryBlock, TestMethodProducing<T> methodTest) {
		return new UTestSupvVoid<T>(this, strTest, outcomeTryBlock, methodTest);	
	}
	
	/**
	 * Create a supervised test instance for a test taking a parameter of a specific type
	 * 		and returning an object of a specific type.
	 * @param <T> The type of the parameter.
	 * @param <R> The type of the object returned from the test. 
	 * @param strTest The name of the test instance.
	 * @param outcomeTryBlock The {@code TestOutcome} if the code completes without throwing
	 * 		an exception.
	 * @param methodTest The expression to execute to perform the text.
	 * @return The test instance object that will perform the test.
	 */
	public <T, R> UnitTestSupervisedFunction<T, R>  createMonadicInstance(String strTest,
			TestOutcome outcomeTryBlock, TestMethodMonadic<T, R> methodTest) {
		return new UTestSupvFunction<T, R>(this, strTest, outcomeTryBlock, methodTest);
	}
	
	/**
	 * Create a supervised test instance for a test taking a parameter of a specific type
	 * 
	 * @param <T> The type of the parameter.
	 * @param strTest The name of the test instance.
	 * @param outcomeTryBlock The {@code TestOutcome} if the code completes without throwing
	 * 		an exception.
	 * @param methodTest The expression to execute to perform the text.
	 * @return The test instance object that will perform the test.
	 */
	public <T> UnitTestSupervisedConsumer<T> createConsumerInstance(String strTest,
			TestOutcome outcomeTryBlock, TestMethodConsuming<T> methodTest) {
		return new UTestSupvConsumer<T>(this, strTest, outcomeTryBlock, methodTest);
	}
}
