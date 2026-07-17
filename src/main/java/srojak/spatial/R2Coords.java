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

import srojak.numerics.DoublePrecisionComparer;

/**
 * @author Stephen
 *
 */
public class R2Coords {
	protected double _x;
	protected double _y;
	
	public R2Coords(double x, double y, boolean bValidating) {
		if (bValidating) {
			if (DoublePrecisionComparer.DEFAULT_COMPARER.compare(x, 0.0d) < 0) {
				throw new IllegalArgumentException("negative x");
			}
			if (DoublePrecisionComparer.DEFAULT_COMPARER.compare(y, 0.0d) < 0) {
				throw new IllegalArgumentException("negative y");
			}
		}
		_x = x;
		_y = y;		
	}
	
	public R2Coords(double x, double y) {
		this(x, y, false);
	}
	
	public R2Coords(R2Coords coordsCopy) {
		Objects.requireNonNull(coordsCopy, "coordsCopy");
		_x = coordsCopy._x;
		_y = coordsCopy._y;
	}
	
	public R2Coords(PolarCoords polar) {
		Objects.requireNonNull(polar, "polar");
		_x = polar.getX();
		_y = polar.getY();
	}
	
	public double getX() {
		return _x;
	}
	
	public double getY() {
		return _y;
	}
}
