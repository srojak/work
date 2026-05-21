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

/**
 * @author Stephen
 *
 * The interface to a supervised test instance that uses a consumer callback.
 * @param <T> The type of object accepted by the consumer.
 */
public interface UnitTestSupervisedConsumer<T>
		extends UnitTestExceptionSupervised {
	
	/**
	 * Execute the test under supervision.
	 * @param input The object to be passed to the consumer.
	 * 
	 * input could be {@code null}; the purpose of the test may be to examine the behavior with
	 *     {@code null} input.
	 */
	void execute(T input);
}
