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
package srojak.numerics;

import java.util.Objects;

/**
 * @author Stephen
 *
 * Methods for working with doubles and handling floating point finite accuracy.
 */
public class DoubleMethods {
	
	private static final DoublePrecisionComparer _comparer
		= DoublePrecisionComparer.DEFAULT_COMPARER;
	
	/**
	 * Compares two numbers for approximate equality.
	 * @param dA The first number to compare.
	 * @param dB The second number to compare.
	 * @return {@code true) if the numbers are equal within the comparison tolerance.
	 */
	public static boolean areEqual(double dA, double dB) {
		return _comparer.areEqual(dA, dB);
	}
	
	/**
	 * Compares two numbers, applying the comparison tolerance.
	 * @param comparison The comparison to use.
	 * @param dA The first number to compare.
	 * @param dB The second number to compare.
	 * @return {@code true) if the comparison between the first and second numbers is true.
	 * @throws NullPointerException If {@code comparison} is {@code null}.
	 */
	public static boolean compare(OrderedComparison comparison, double dA, double dB) {
		Objects.requireNonNull(comparison, "comparison");
		return comparison.evaluate(_comparer.compare(dA, dB));
	}
}
