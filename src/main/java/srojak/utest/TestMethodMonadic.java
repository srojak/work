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

/**
 * @author Stephen
 *
 * A functional interface allowing exceptions to pass through for capture.
 * @param <T> The type of the input to the function.
 * @param <R> The type of the result of the function.
 */
@FunctionalInterface
public interface TestMethodMonadic<T, R> {
	
	/**
	 * Executes this function with the given argument.
	 * 
	 * @param input
	 * @return An object of type {@code R}.
	 * @throws Exception Any exception originated within it.
	 */
	R invoke(T input) throws Exception;
}
