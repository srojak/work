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
package srojak.cdo;

import java.awt.Point;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class PointOffset {
	public final int _dx;
	public final int _dy;
	
	public PointOffset(int dx, int dy) {
		_dx = dx;
		_dy = dy;
	}
	
	public Point moveFrom(Point ptFrom) {
		Objects.requireNonNull(ptFrom, "ptFrom");
		return new Point(ptFrom.x + _dx, ptFrom.y + _dy);
	}

	@Override
	public String toString() {
		return "PointOffset [dx=" + _dx + ", dy=" + _dy + "]";
	}
}
