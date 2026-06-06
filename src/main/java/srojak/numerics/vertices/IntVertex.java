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
package srojak.numerics.vertices;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class IntVertex {
	public final int _x;
	public final int _y;
	
	public IntVertex(int x, int y) {
		_x = x;
		_y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_x, _y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		else if (obj instanceof IntVertex other) {
			return _x == other._x && _y == other._y;
		} else
			return false;
	}

	@Override
	public String toString() {
		return "IntVertex [x=" + _x + ", y=" + _y + "]";
	}
}
