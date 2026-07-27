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

import srojak.numerics.compass.CompassDegrees;

/**
 * @author Stephen
 *
 */
public class S2OffsetRay 
		extends S2RayBase 
		implements S2Vector {
	private final S2Offset _offset;
	private final S2Orientation _orient;
	private final CompassDegrees _heading;

	public S2OffsetRay(S2Orientation orientation, S2Coords coordsStart, S2Offset offset) {
		super(coordsStart);
		Objects.requireNonNull(orientation, "orientation");
		Objects.requireNonNull(offset, "offset");
		_offset = offset;
		_orient = orientation;
		_heading = _orient.findDegreesFor(offset);
	}
	
	public S2OffsetRay(S2Orientation orientation, S2Coords coordsStart, S2Coords coordsEnd) {
		super(coordsStart);
		Objects.requireNonNull(orientation, "orientation");
		Objects.requireNonNull(coordsEnd, "coordsEnd");
		_offset = coordsStart.getOffsetTo(coordsEnd);
		_orient = orientation;
		_heading = _orient.findDegreesFor(_offset);
	}
	
	public S2Offset getOffset() {
		return _offset;
	}

	@Override
	public CompassDegrees getHeading() {
		return _heading;
	}

	@Override
	public float getLength() {
		return (float) _offset.getDistance();
	}
	
	public S2CompassDirection getNearestDirection() 
			throws NoValidMoveException {
		return _orient.findCompassDirection(_offset);
	}
	
	public S2Coords getEndpoint() {
		return _coordsStart.getNewLocationFrom(_offset);
	}

	@Override
	public int hashCode() {
		return Objects.hash(_coordsStart, _offset);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof S2OffsetRay other) {
			return Objects.equals(_coordsStart, other._coordsStart)
					&& Objects.equals(_offset, other._offset);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "S2Ray [" + _coordsStart.toEnclosedString() + ", offset=" 
				+  _offset + "]";
	}
}
