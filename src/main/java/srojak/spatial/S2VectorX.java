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

import srojak.numerics.SinglePrecisionComparer;

/**
 * @author Stephen
 *
 * This class should not be used directly. There is code I may want to recycle.
 */
class S2VectorX
		extends S2RayHeadingBase {
	private final S2CompassDirection _direction;
	private final float _length;

	/**
	 * 
	 */
	public S2VectorX(S2Coords coordsStart, S2CompassDirection direction, float fLength) {
		super(coordsStart);
		Objects.requireNonNull(direction, "direction");
		if (fLength < 0.0f) {
			throw new IllegalArgumentException("nLength is negative");
		}
		_direction = direction;
		_length = fLength;
	}

	public S2CompassDirection getDirection() {
		return _direction;
	}
	
	public float getLength() {
		return _length;
	}
	
	public S2Coords getEnd(S2Orientation orientation) {
		Objects.requireNonNull(orientation, "orientation");
		S2Offset offset = orientation.offset(_direction, (int) _length);
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

	@Override
	protected S2Coords findEndpoint(S2Orientation orientation) {
		S2Offset offset = orientation.offset(_direction, Math.round(_length));
		return _coordsStart.getNewLocationFrom(offset);
	}

	@Override
	public int hashCode() {
		return Objects.hash(_coordsStart, _direction, _length);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof S2VectorX other) {
			return Objects.equals(_coordsStart, other._coordsStart) 
					&& Objects.equals(_direction, other._direction)
					&& SinglePrecisionComparer.DEFAULT_COMPARER.areEqual(_length, other._length);
		}
		return false;
	}

	@Override
	public String toString() {
		return "S2VectorX [" + _coordsStart.toEnclosedString() + ", " 
				+ _direction.getAbbrev() + ", " + _formatLength.format(_length) + "]";
	}
}
