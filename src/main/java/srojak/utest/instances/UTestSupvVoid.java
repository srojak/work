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

import java.util.Objects;

import srojak.utest.TestMethodProducing;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 * A test instance supervising and processing exceptions that uses a supplier callback.
 * @param <T> The type of object produced by the supplier.
 */
public class UTestSupvVoid<T>
		extends UTestSupvInstance
		implements UnitTestSupervisedVoid<T> {
	private final TestMethodProducing<T> _method;
	
	/**
	 * Constructor.
	 * @param utest The {@code UnitTestSeries} that created this instance.
	 * @param strInstance The name of the test instance.
	 * @param outcomeTryBlock The outcome that should be asserted if the code gets through
	 *     the try block without throwing an exception.
	 * @param methodTest The test supplier callback.
	 */
	public UTestSupvVoid(UnitTestSeries utest, String strInstance, 
			TestOutcome outcomeTryBlock, TestMethodProducing<T> methodTest) {
		super(utest, strInstance, outcomeTryBlock);
		Objects.requireNonNull(methodTest, "methodTest");
		_method = methodTest;
	}

	/**
	 * Execute the test under supervision.
	 * @return The object produced if the test completed without throwing an exception.
	 */
	@Override
	public T execute() {
		try {
			T result = _method.invoke();
			endTryBlock();
			return result;
		} catch (Exception exc) {
			analyzeException(exc);
		}
		return null;
	}
}
