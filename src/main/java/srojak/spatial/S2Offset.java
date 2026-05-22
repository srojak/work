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
package srojak.spatial;

import java.util.Objects;

import srojak.core.LazyDouble;

/**
 * @author Stephen
 *
 */
public class S2Offset {
	public final int dx;
	public final int dy;
	private final LazyDouble _distance;
	
	public S2Offset(int nX, int nY) {
		dx = nX;
		dy = nY;
		_distance = new LazyDouble(() -> Math.sqrt(dx * dx + dy * dy));
	}
	
	public int getX() {
		return dx;
	}
	
	public int getY() {
		return dy;
	}
	
	public double getDistance() {
		return _distance.get();
	}
	
	public double getSlope() {
		if (dx == 0) {
			return dy >= 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
		}
		return ((double)dy) / ((double)dx);
	}

	@Override
	public int hashCode() {
		return Objects.hash(dx, dy);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof S2Offset other) {
			return dx == other.dx && dy == other.dy;
		} else
			return false;
	}

	@Override
	public String toString() {
		return "offset [dx=" + dx + ", dy=" + dy + "]";
	}
}
	
