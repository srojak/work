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
import srojak.utest.identifiers.TestInstanceIdentifier;
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
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param sense the comparison sense of the test.
	 * @param actual The actual object reference.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectNull(TestIdentifier idTest, String strValueName,
			ConditionSense sense, Object actual) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		StringBuilder sb = UTestCommonMessages.startTestMessageLine(this, idInstance);
		sb.append(strValueName);
		sb.append(' ');
		sb.append(sense.getVerb());
		sb.append(" null? ");
		TestOutcome outcome = TestOutcome.evaluate(() -> sense.isExpectedResult(actual == null));
		sb.append(outcome);
		writeOutcomeMessage(outcome, sb.toString());
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code boolean} value.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param expected The expected boolean value.
	 * @param actual The actual boolean value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValue(TestIdentifier idTest, String strValueName, boolean expected, boolean actual) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		UTestInstDyadicBool instance
			= new UTestInstDyadicBool(this, idInstance, strValueName);
		instance.execute(expected, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code int} value.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The condition to evaluate for the actual value.
	 * @param actual The actual integer value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValueWhere(TestIdentifier idTest, String strValueName,
			UnitTestConditionInt condition, int actual) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		UTestInstCondInt instance
			= new UTestInstCondInt(this, idInstance, strValueName);
		instance.execute(condition, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code long} value.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The condition to evaluate for the actual value.
	 * @param actual The actual long integer value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValueWhere(TestIdentifier idTest, String strValueName,
			UnitTestConditionLong condition, long actual) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		UTestInstCondLong instance
			= new UTestInstCondLong(this, idInstance, strValueName);
		instance.execute(condition, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code float} value.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The condition to evaluate for the actual value.
	 * @param actual The actual float value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValueWhere(TestIdentifier idTest, String strValueName,
			UnitTestConditionFloat condition, float actual) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		UTestInstCondFloat instance
			= new UTestInstCondFloat(this, idInstance, strValueName,
					_options.getFloatComparer());
		instance.execute(condition, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code double} value.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The condition to evaluate for the actual value.
	 * @param actual The actual double value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectValueWhere(TestIdentifier idTest, String strValueName,
			UnitTestConditionDouble condition, double actual) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		UTestInstCondDouble instance
			= new UTestInstCondDouble(this, idInstance, strValueName,
					_options.getDoubleComparer());
		instance.execute(condition, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a {@code String} value.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param condition The string condition to evaluate for the actual value.
	 * @param expected The expected value, which is an input to the condition.
	 * @param actual The actual String value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public TestOutcome expectString(TestIdentifier idTest, String strValueName,
			StringCondition condition, String expected, String actual) {
		Objects.requireNonNull(idTest, "idTest");
		Objects.requireNonNull(condition, "condition");
		Objects.requireNonNull(expected, "expected");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		StringBuilder sb = UTestCommonMessages.startTestMessageLine(this, idInstance);
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
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is an enum value.
	 * @param <T> The type of the enum value.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param sense the comparison sense of the test.
	 * @param expected The expected value.
	 * @param actual The actual value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <T extends Enum<T>> TestOutcome expectEnumValue(TestIdentifier idTest, String strValueName,
			ConditionSense sense, T expected, T actual) {
		Objects.requireNonNull(idTest, "idTest");
		Objects.requireNonNull(sense, "sense");
		Objects.requireNonNull(expected, "expected");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		StringBuilder sb = UTestCommonMessages.startTestMessageLine(this, idInstance);
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
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test for equality where the result is an object.
	 * @param <T> The type of the object being evaluated.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods The container with methods to use for the test.
	 * @param expected The expected value.
	 * @param actual The actual value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <T> TestOutcome expectValueEquals(TestIdentifier idTest, String strValueName,
			UnitTestEqualsMethods<T> methods, T expected, T actual)
	{
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		UTestInstEquals<T> instance
			= new UTestInstEquals<T>(this, idInstance, strValueName, methods);
		instance.areEqual(expected, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test for inequality where the result is an object.
	 * The {@code equals( )} method of the object will be used.
	 * @param <T> The type of the object being evaluated.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods The container with methods to use for the test.
	 * @param expected The expected value.
	 * @param actual The actual value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <T> TestOutcome expectValueNotEquals(TestIdentifier idTest, String strValueName,
			UnitTestEqualsMethods<T> methods, T expected, T actual)
	{
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		UTestInstEquals<T> instance
			= new UTestInstEquals<T>(this, idInstance, strValueName, methods);
		instance.areNotEqual(expected, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is a value that can be compared to another.
	 * @param <T> The type of the result, which must implement {@code Comparable<T>}.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param comparison The {@code ValueComparison} defining the comparison.
	 * @param expected The expected value.
	 * @param actual The actual value.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <T extends Comparable<T>> TestOutcome expectValue(TestIdentifier idTest, String strValueName,
			OrderedComparison comparison, T expected, T actual) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		UTestInstDyadic<T> instance 
			= new UTestInstDyadic<T>(this, idInstance, strValueName,
					new UnitTestConditionComparison<T>(comparison));
		instance.execute(expected, actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Evaluate a test where the result is an object of a specific type.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param classExpected The {@code Class} of the expected type.
	 * @param actual The actual object produced by the test.
	 * @return the {@code TestOutcome} for the test.
	 * @throws NullPointerException if classExpected is {@value null}.
	 */
	public TestOutcome expectType(TestIdentifier idTest, String strValueName, 
			Class<?> classExpected, Object actual) {
		Objects.requireNonNull(idTest, "idTest");
		Objects.requireNonNull(classExpected, "classExpected");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		StringBuilder sb = UTestCommonMessages.startTestMessageLine(this, idInstance);
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
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Create a comparing test instance for a collection.
	 * @param <E> The type of the elements in the collection.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods The container with methods to use for the test.
	 * @return The test instance object that will perform the test.
	 */
	public <E> UnitTestCollectionComparer<E> createCollectionComparer(TestIdentifier idTest, 
			String strValueName, UnitTestClassElementMethods<E> methods) {
		Objects.requireNonNull(idTest, "idTest");
		Objects.requireNonNull(methods, "methods");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		return new UTestInstCollComparer<E>(this, idInstance, strValueName, methods);
	}
	
	/**
	 * Test all elements of an collection for a condition.
	 * @param <E> The type of the elements in the collection.
	 * @param idTest The identifier for the test instance.
	 * @param strValueName The name of the value under test.
	 * @param methods The container with methods to use for the test.
	 * @param strExpected A description of the expected condition.
	 * @param predicateExpected A predicate that tests for the expected condition.
	 * @param actual The actual collection produced by the test.
	 * @return the {@code TestOutcome} for the test.
	 */
	public <E> TestOutcome expectAllElementsToHave(TestIdentifier idTest,
			String strValueName, UnitTestClassElementMethods<E> methods,
			String strExpected,	Predicate<E> predicateExpected, Collection<E> actual) {
		Objects.requireNonNull(idTest, "idTest");
		Objects.requireNonNull(predicateExpected, "predicateExpected");
		Objects.requireNonNull(methods, "methods");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		UTestInstItemHas<E> instance = new UTestInstItemHas<E>(this, idInstance, strValueName,
				methods, strExpected, predicateExpected);
		instance.executeOver(actual);
		TestOutcome outcome = instance.getOutcome();
		// instance writes
		checkStopOnFailure(idInstance, outcome);
		return outcome;
	}
	
	/**
	 * Create a supervised test instance for a test returning an object of a specific type.
	 * @param <T> The type of the object returned from the test.
	 * @param idTest The identifier for the test instance.
	 * @param outcomeTryBlock The {@code TestOutcome} if the code completes without throwing
	 * 		an exception.
	 * @param methodTest The expression to execute to perform the text.
	 * @return The test instance object that will perform the test.
	 */
	public <T> UnitTestSupervisedVoid<T> createVoidInstance(TestIdentifier idTest,
			TestOutcome outcomeTryBlock, TestMethodProducing<T> methodTest) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		return new UTestSupvVoid<T>(this, idInstance, outcomeTryBlock, methodTest);	
	}
	
	/**
	 * Create a supervised test instance for a test taking a parameter of a specific type
	 * 		and returning an object of a specific type.
	 * @param <T> The type of the parameter.
	 * @param <R> The type of the object returned from the test. 
	 * @param idTest The identifier for the test instance.
	 * @param outcomeTryBlock The {@code TestOutcome} if the code completes without throwing
	 * 		an exception.
	 * @param methodTest The expression to execute to perform the text.
	 * @return The test instance object that will perform the test.
	 */
	public <T, R> UnitTestSupervisedFunction<T, R>  createMonadicInstance(TestIdentifier idTest,
			TestOutcome outcomeTryBlock, TestMethodMonadic<T, R> methodTest) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		return new UTestSupvFunction<T, R>(this, idInstance, outcomeTryBlock, methodTest);
	}
	
	/**
	 * Create a supervised test instance for a test taking a parameter of a specific type
	 * 
	 * @param <T> The type of the parameter.
	 * @param idTest The identifier for the test instance.
	 * @param outcomeTryBlock The {@code TestOutcome} if the code completes without throwing
	 * 		an exception.
	 * @param methodTest The expression to execute to perform the text.
	 * @return The test instance object that will perform the test.
	 */
	public <T> UnitTestSupervisedConsumer<T> createConsumerInstance(TestIdentifier idTest,
			TestOutcome outcomeTryBlock, TestMethodConsuming<T> methodTest) {
		Objects.requireNonNull(idTest, "idTest");
		TestInstanceIdentifier idInstance = idTest.createInstance();
		return new UTestSupvConsumer<T>(this, idInstance, outcomeTryBlock, methodTest);
	}
}
