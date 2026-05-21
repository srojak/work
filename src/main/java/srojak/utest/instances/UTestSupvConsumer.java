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

import srojak.utest.TestMethodConsuming;
import srojak.utest.TestOutcome;
import srojak.utest.UnitTestSeries;

/**
 * @author Stephen
 *
 * A test instance supervising and processing exceptions that uses a consumer callback.
 * @param <T> The type of object accepted by the consumer.
 */
public class UTestSupvConsumer<T>
		extends UTestSupvInstance
		implements UnitTestSupervisedConsumer<T> {
	private final TestMethodConsuming<T> _method;

	/**
	 * Constructor.
	 * @param utest The {@code UnitTestSeries} that created this instance.
	 * @param strInstance The name of the test instance.
	 * @param outcomeTryBlock The outcome that should be asserted if the code gets through
	 *     the try block without throwing an exception.
	 * @param methodTest The test consumer callback.
	 */
	public UTestSupvConsumer(UnitTestSeries utest, String strInstance, 
			TestOutcome outcomeTryBlock, TestMethodConsuming<T> methodTest) {
		super(utest, strInstance, outcomeTryBlock);
		Objects.requireNonNull(methodTest, "methodTest");
		_method = methodTest;
	}

	/**
	 * Execute the test under supervision.
	 * @param input The object to be passed to the consumer.
	 * 
	 * input could be {@code null}; the purpose of the test may be to examine the behavior with
	 *     {@code null} input.
	 */
	public void execute(T input) {
		try {
			_method.invoke(input);
			endTryBlock();
		} catch (Exception exc) {
			analyzeException(exc);
		}
	}
}
