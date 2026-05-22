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
public class R3CoordsDouble
		extends R3CoordsBase
		implements R3Coords {
	private final double _x;
	private final double _y;
	private final double _z;

	/**
	 * 
	 */
	public R3CoordsDouble(double x, double y, double z) {
		_x = x;
		_y = y;
		_z = z;
	}
	
	public R3CoordsDouble(R3Coords locFrom) {
		Objects.requireNonNull(locFrom, "locFrom");
		if (locFrom instanceof R3CoordsDouble locFromDouble) {
			_x = locFromDouble._x;
			_y = locFromDouble._y;
			_z = locFromDouble._z;
		} else {
			_x = locFrom.getX();
			_y = locFrom.getY();
			_z = locFrom.getZ();
		}
	}

	@Override
	public double getX() {
		return _x;
	}

	@Override
	public double getY() {
		return _y;
	}

	@Override
	public double getZ() {
		return _z;
	}

}
