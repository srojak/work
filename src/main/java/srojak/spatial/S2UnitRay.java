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

import srojak.numerics.CompassDegrees;

/**
 * @author Stephen
 *
 */
public class S2UnitRay 
		extends S2RayHeadingBase
		implements S2Vector {
	private final S2CompassDirection _direction;
	private S2Coords _coordsEnd;

	private static final float _sqrt2 = (float) Math.sqrt(2.0);
	/**
	 * @param coordsStart
	 * @param direction
	 */
	public S2UnitRay(S2Coords coordsStart, S2CompassDirection direction) {
		super(coordsStart);
		Objects.requireNonNull(direction, "direction");
		_direction = direction;
		_coordsEnd = null;
	}

	public S2CompassDirection getDirection() {
		return _direction;
	}

	@Override
	public CompassDegrees getHeading() {
		return _direction.getDegrees();
	}

	@Override
	public float getLength() {
		return _direction.isCardinalDirection() ? 1.0f : _sqrt2;
	}

	@Override
	protected S2Coords findEndpoint(S2Orientation orientation) {
		if (_coordsEnd == null) {
			S2Offset offset = orientation.offsetByOne(_direction);
			_coordsEnd = _coordsStart.getNewLocationFrom(offset);
		}
		return _coordsEnd;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(_coordsStart, _direction);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof S2UnitRay other) {
			return Objects.equals(_coordsStart, other._coordsStart) 
					&& Objects.equals(_direction, other._direction);
		}
		return false;
	}

	@Override
	public String toString() {
		return "S2UnitRay [" + _coordsStart.toEnclosedString() + ", " 
				+ _direction.getAbbrev() + "]";
	}
}
