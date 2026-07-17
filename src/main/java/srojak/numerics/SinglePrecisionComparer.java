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
 */
public interface SinglePrecisionComparer {
	/**
	 * The standard comparison tolerance value.
	 */
	public static final float EPSILON_DEFAULT = 1.0e-14f;
	
	/**
	 * Comparison tolerance values must be less than this value.
	 */
	public static final float EPSILON_LIMIT = 1.0e-5f;
	
	/**
	 * The comparer using the default epsilon value.
	 */
	public static final SinglePrecisionComparer DEFAULT_COMPARER
		= new FloatComparer(EPSILON_DEFAULT);
	
	/**
	 * Gets the current comparison tolerance value.
	 * @return The current comparison tolerance value.
	 */
	float getEpsilon();
	
	/**
	 * Compares two numbers for numerical equality.
	 * @param f1 The first number to compare.
	 * @param f2 The second number to compare.
	 * @return {@code true) if the numbers are equal within the comparison tolerance.
	 */
	boolean areEqual(float f1, float f2);
	
	/**
	 * Compares two numbers, applying the comparison tolerance.
	 * @param f1 The first number to compare.
	 * @param f2 The second number to compare.
     * @return  the value {@code 0} if {@code f1} is
     *          numerically equal to {@code f2} within the comparison tolerance;
     *          a value less than {@code 0} if {@code f1} is numerically less than {@code f2};
     *          and a value greater than {@code 0} if {@code f1} is numerically greater than {@code f2}.
	 */
	int compare(float f1, float f2);
	
	/**
	 * Compares two numbers, applying the comparison tolerance.
	 * @param f1 The first number to compare.
	 * @param comparison The comparison to use, from the perspective of the first number.
	 * @param f2 The second number to compare.
	 * @return {@code true} if the required relationship is true.
	 */
	default public boolean compare(float f1, OrderedComparison comparison, float f2) {
		Objects.requireNonNull(comparison, "comparison");
		return comparison.evaluate(compare(f1, f2));
	}
}
