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
package srojak.psq;

import java.util.Objects;

import srojak.numerics.SinglePrecisionComparer;
import srojak.spatial.InvalidLocationException;
import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Offset;
import srojak.spatial.S2Segment;
import srojak.spatial.S2UnitRay;

/**
 * @author Stephen
 *
 */
public class PlaneSquarePair<S extends PlaneSquare> {
	private final S _squareFirst;
	private final S _squareSecond;
	private final S2CompassDirection _direction;
	private final float _fDistance;
	
	public PlaneSquarePair(S squareFirst, S squareSecond, S2CompassDirection direction, float fDistance) {
		Objects.requireNonNull(squareFirst, "squareFirst");
		Objects.requireNonNull(squareSecond, "squareSecond");
		Objects.requireNonNull(direction, "direction");
		if (SinglePrecisionComparer.DEFAULT_COMPARER.compare(fDistance, 0.0f) <= 0) {
			throw new IllegalArgumentException("fDistance must be positive");
		}
		_squareFirst = squareFirst;
		_squareSecond = squareSecond;
		_direction = direction;
		_fDistance = fDistance;
	}
	
	public S getFirstSquare() {
		return _squareFirst;
	}
	
	public S getSecondSquare() {
		return _squareSecond;
	}
	
	public S2CompassDirection getDirection() {
		return _direction;
	}
	
	public float getDistance() {
		return _fDistance;
	}
	
	public S2Segment getSegment() {
		return new S2Segment(_squareFirst._coords, _squareSecond._coords, true);
	}
	
	public S2UnitRay getUnitRay() 
			throws InvalidLocationException {
		S2Offset offset = _squareFirst._coords.getOffsetTo(_squareSecond._coords);
		if (!offset.isAdjacent()) {
			throw new InvalidLocationException(_squareFirst._coords,
					"next square is not adjacent");
		}
		return new S2UnitRay(_squareFirst._coords, _direction);
	}
}
