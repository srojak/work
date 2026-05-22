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
public class R3CoordsLong
		extends R3CoordsBase
		implements R3Coords {
	private final long _x;
	private final long _y;
	private final long _z;
	
	public R3CoordsLong(long i, long j, long k) {
		_x = i;
		_y = j;
		_z = k;
	}
	
	public R3CoordsLong(R3Coords locFrom) {
		Objects.requireNonNull(locFrom, "locFrom");
		if (locFrom instanceof R3CoordsLong locFromLong) {
			_x = locFromLong._x;
			_y = locFromLong._y;
			_z = locFromLong._z;
		} else {
			_x = Math.round(locFrom.getX());
			_y = Math.round(locFrom.getY());
			_z = Math.round(locFrom.getZ());
		}
	}
	
	@Override
	public double getX() {
		return (double) _x;
	}

	@Override
	public double getY() {
		return (double) _y;
	}

	@Override
	public double getZ() {
		return (double) _z;
	}

	public long getLongX() {
		return _x;
	}
	
	public long getLongY() {
		return _y;
	}
	
	public long getLongZ() {
		return _z;
	}
	
	public R3CoordsLong makeMidPoint(R3CoordsLong locOther) {
		Objects.requireNonNull(locOther, "locOther");
		return new R3CoordsLong((locOther._x + _x) / 2, (locOther._y + _y) / 2,
				(locOther._z + _z) / 2);
	}
}
