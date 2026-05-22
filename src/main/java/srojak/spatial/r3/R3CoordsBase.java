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
package srojak.spatial.r3;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
abstract class R3CoordsBase
		implements R3Coords {
	protected double x;
	
	public R3CoordsBase() {
	}
	
	protected double getDistance(double x, double y, double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}

	@Override
	public double getDistance() {
		return getDistance(getX(), getY(), getZ());
	}

	@Override
	public double getDistanceTo(R3Coords other) {
		Objects.requireNonNull(other, "other");
		return getDistance(other.getX() - getX(), other.getY() - getY(), other.getZ() - getZ());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY(), getZ());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (obj instanceof R3CoordsBase other) {
			return Double.doubleToLongBits(getX()) == Double.doubleToLongBits(other.getX())
					&& Double.doubleToLongBits(getY()) == Double.doubleToLongBits(other.getY())
					&& Double.doubleToLongBits(getZ()) == Double.doubleToLongBits(other.getZ());
		} else
			return false;
	}
	
	@Override
	public String toString() {
		return String.format("(x=%.3f, y=%.3f, z=%.3f)", getX(), getY(), getZ());
	}
}
