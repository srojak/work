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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class S2Vector {
	private final S2Coords _coordsStart;
	private final S2CompassDirection _direction;
	private final int _length;

	/**
	 * 
	 */
	public S2Vector(S2Coords coordsStart, S2CompassDirection direction, int nLength) {
		Objects.requireNonNull(coordsStart, "coordsStart");
		Objects.requireNonNull(direction, "direction");
		if (nLength < 0) {
			throw new IllegalArgumentException("nLength is negative");
		}
		_coordsStart = coordsStart;
		_direction = direction;
		_length = nLength;
	}

	public S2Coords getStart() {
		return _coordsStart;
	}
	
	public S2CompassDirection getDirection() {
		return _direction;
	}
	
	public int getLength() {
		return _length;
	}
	
	public S2Coords getEnd(S2Orientation orientation) {
		Objects.requireNonNull(orientation, "orientation");
		S2Offset offset = orientation.offset(_direction, _length);
		return _coordsStart.getOffsetCoords(offset);
	}
	
	public List<S2CoordsMove> getCoordsAlong(S2Orientation orientation) {
		Objects.requireNonNull(orientation, "orientation");
		S2Offset offset = orientation.offsetByOne(_direction);
		List<S2CoordsMove> list = new LinkedList<S2CoordsMove>();
		list.add(new S2CoordsMove(SpatialMove.Start, _coordsStart));
		S2Coords coords = _coordsStart;
		for (int n = 0; n < _length; n++) {
			coords = coords.getNewLocationFrom(offset);
			list.add(new S2CoordsMove(SpatialMove.Move, coords));
		}
		return list;
	}
}
