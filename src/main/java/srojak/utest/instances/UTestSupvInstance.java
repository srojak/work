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
import java.util.ArrayList;
import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;
import srojak.utest.impl.UTestInstance;

/**
 * @author Stephen
 *
 * An instance of a supervised test, which catches and processed exceptions.
 */
public abstract class UTestSupvInstance 
		extends UTestInstance {
	private final ArrayList<Type> _listExpected;
	private final TestOutcome _outcomeTryBlock;

	/**
	 * @param utest The {@code UnitTestSeries} that created this instance.
	 * @param strInstance The name of the test instance.
	 * @param outcomeTryBlock The outcome that should be asserted if the code gets through
	 *     the try block without throwing an exception.
	 */
	public UTestSupvInstance(UnitTestSeries utest, String strInstance,
			TestOutcome outcomeTryBlock) {
		super(utest, strInstance);
		if (outcomeTryBlock == TestOutcome.NONE) {
			throw new IllegalStateException("outcomeTryBlock: not a valid outcome");
		}
		_listExpected = new ArrayList<Type>();
		_outcomeTryBlock = outcomeTryBlock;
	}
	
	/**
	 * Mark a class of exception as expected.
	 * @param classException The class of the exception to consider expected.
	 */
	public void expect(Class<?> classException) {
		Objects.requireNonNull(classException, "classException");
		if (!Exception.class.isAssignableFrom(classException)) {
			throw new IllegalArgumentException("classException is not an exception");
		}
		_listExpected.add(classException);
	}
	
	protected boolean endTryBlock() {
		StringBuilder sb = super.getInitialString();
		sb.append("did not throw exceptions");
		if (_outcomeTryBlock == TestOutcome.FAIL) {
			sb.append(" as expected");
			writeMessage(ObsLevel.ERROR, sb.toString());
			setOutcome(TestOutcome.FAIL);
			checkStopOnFailure();
			return false;
		} else {
			writeMessage(ObsLevel.NOTICE, sb.toString());
			setOutcome(TestOutcome.PASS);
			return true;
		}
	}

	protected boolean isExpected(Exception exc) {
		return _listExpected.contains(exc.getClass());
	}
	
	protected void analyzeException(Exception exc) {
		Class<?> classExc = exc.getClass();
		if (_listExpected.contains(exc.getClass())) {
			setOutcome(TestOutcome.PASS);
			writeMessage(ObsLevel.NOTICE,
					"Caught exception " + classExc.getSimpleName() + " as expected");
			writeMessage(ObsLevel.DETAIL, exc.getMessage());
		} else {
			setOutcome(TestOutcome.FAIL);
			writeMessage(ObsLevel.ERROR, "Caught exception " + classExc.getSimpleName());
			writeMessage(ObsLevel.ERROR, "  " + exc.getMessage());
			writeStack(ObsLevel.DETAIL, exc);
			checkStopOnFailure();
		}
	}
}
