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
public class FloatComparer 
		implements SinglePrecisionComparer {
	private final float _epsilon;

	/**
	 * 
	 */
	public FloatComparer(float epsilon) {
		if (epsilon < Float.MIN_NORMAL) {
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
	public float getEpsilon() {
		return _epsilon;
	}

	@Override
	public boolean areEqual(float f1, float f2) {
		return Math.abs(f1 - f2) < _epsilon;
	}

	@Override
	public int compare(float f1, float f2) {
		if (Math.abs(f1 - f2) < _epsilon) {
			return 0;
		} else {
			return f1 < f2 ? -1 : 1;
		}
	}

}
