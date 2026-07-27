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

/**
 * @author Stephen
 *
 */
public class DoubleComparer 
		implements DoublePrecisionComparer {
	private final double _epsilon;
	
	/**
	 * 
	 */
	public DoubleComparer(double epsilon) {
		if (epsilon < Double.MIN_NORMAL) {
			throw new IllegalArgumentException("value must be positive");
		} else if (epsilon >= EPSILON_LIMIT) {
			throw new IllegalArgumentException("value is meaninglessly large");
		}
		_epsilon = epsilon;
	}

	/**
	 * Gets the current comparison tolerance value.
	 * @return The current comparison tolerance value.
	 */
	@Override
	public double getEpsilon() {
		return _epsilon;
	}
	
	
	/**
	 * Compares two numbers for numerical equality.
	 * @param d1 The first number to compare.
	 * @param d2 The second number to compare.
	 * @return {@code true) if the numbers are equal within the comparison tolerance.
	 */
	@Override
	public boolean areEqual(double d1, double d2) {
		return Math.abs(d1 - d2) < _epsilon;
	}

	/**
	 * Compares two numbers, applying the comparison tolerance.
	 * @param d1 The first number to compare.
	 * @param d2 The second number to compare.
     * @return  the value {@code 0} if {@code d1} is
     *          numerically equal to {@code d2} within the comparison tolerance;
     *          ; a value less than {@code 0} if {@code d1} is numerically less than {@code d2};
     *          and a value greater than {@code 0} if {@code d1} is numerically greater than {@code d2}.
	 */
	@Override
	public int compare(double d1, double d2) {
		if (Math.abs(d1 - d2) < _epsilon) {
			return 0;
		} else {
			return d1 < d2 ? -1 : 1;
		}
	}
}
