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
 * It is the responsibility of the caller to ensure that the offset and direction
 * 	belong together for the orientation in use.
 */
public class S2OffsetDirectionPair {
	private final S2Offset _offset;
	private final S2CompassDirection _direction;
	
	public S2OffsetDirectionPair(S2Offset offset, S2CompassDirection direction) {
		Objects.requireNonNull(offset, "offset");
		Objects.requireNonNull(direction, "direction");
		_offset = offset;
		_direction = direction;
	}
	
	public S2Offset getOffset() {
		return _offset;
	}
	
	public S2CompassDirection getDirection() {
		return _direction;
	}
	
	public S2Coords moveFrom(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		return coords.getNewLocationFrom(_offset);
	}
	
	public static S2OffsetDirectionPair makeOneUnitPair(S2Orientation orientation,
			S2CompassDirection direction) {
		Objects.requireNonNull(orientation, "orientation");
		Objects.requireNonNull(direction, "direction");
		S2Offset offset = orientation.offsetByOne(direction);
		return new S2OffsetDirectionPair(offset, direction);
	}
}
