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

/**
 * @author Stephen
 *
 */
public class S2MovableCoords
		extends S2Coords {

	/**
	 * @param x
	 * @param y
	 */
	public S2MovableCoords(int x, int y) {
		super(x, y);
	}
	
	public void adjustX(int nValue) {
		_x += nValue;
	}
	
	public void adjustY(int nValue) {
		_y += nValue;
	}
	
	public boolean moveInBounds(S2FieldSize szMap) {
		Objects.requireNonNull(szMap, "szMap");
		boolean bMoved = false;
		if (_x < 0) {
			_x = 0;
			bMoved = true;
		} else if (_x >= szMap.width) {
			_x = szMap.width - 1;
			bMoved = true;
		}
		if (_y < 0) {
			_y = 0;
			bMoved = true;
		} else if (_y >= szMap.height) {
			_y = szMap.height - 1;
			bMoved = true;
		}
		return bMoved;
	}
	
	public boolean tryToMove(S2Orientation orientation,	S2FieldSize szMap,
			S2CompassDirection direction, int nDistance) {
		Objects.requireNonNull(orientation, "orientation");
		Objects.requireNonNull(szMap, "szMap");
		if (nDistance <= 0) {
			throw new IllegalArgumentException("nDistance <= 0");
		}
		S2Offset offsetMove = orientation.offset(direction, nDistance);
		int xNew = _x + offsetMove.dx;
		int yNew = _y + offsetMove.dy;
		if (szMap.isInBounds(xNew, yNew)) {
			_x = xNew;
			_y = yNew;
			return true;
		} else {
			return false;
		}
	}
}
