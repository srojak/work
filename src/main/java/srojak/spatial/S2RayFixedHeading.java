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
import srojak.numerics.SinglePrecisionComparer;

/**
 * @author Stephen
 *
 */
public class S2RayFixedHeading
		extends S2RayHeadingBase
		implements S2Vector {
	/**
	 * heading is going to be fixed with 0=North.
	 */
	private final CompassDegrees _heading;
	private final float _length;

	/**
	 * @param coordsStart
	 */
	public S2RayFixedHeading(S2Coords coordsStart, CompassDegrees heading, float fLength) {
		super(coordsStart);
		Objects.requireNonNull(heading, "heading");
		if (SinglePrecisionComparer.DEFAULT_COMPARER.compare(fLength, 0.0f) < 0) {
			throw new IllegalArgumentException("fLength is negative");
		}
		_heading = heading;
		_length = fLength;
	}

	public S2CompassDirection getDirection(S2Orientation orientation) {
		Objects.requireNonNull(orientation, "orientation");
		// TODO: have to work out how to do this
		return S2CompassDirection.findDirectionFor(_heading);
	}
	
	@Override
	public CompassDegrees getHeading() {
		return _heading;
	}

	@Override
	public float getLength() {
		return _length;
	}

	public S2Offset computeOffset(S2Orientation orientation) {
		double dRadians = _heading.convertToRadians();
		return orientation.offset(dRadians, _length);
	}

	@Override
	protected S2Coords findEndpoint(S2Orientation orientation) {
		S2Offset offsetMove = computeOffset(orientation);
		return _coordsStart.getNewLocationFrom(offsetMove);
	}

	@Override
	public int hashCode() {
		return Objects.hash(_coordsStart, _heading, _length);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof S2RayFixedHeading other) {
			return Objects.equals(_coordsStart, other._coordsStart)
					&& Objects.equals(_heading, other._heading)
					&& SinglePrecisionComparer.DEFAULT_COMPARER.areEqual(_length, other._length);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "S2RayFH [" + _coordsStart.toEnclosedString() + ", " 
				+ _heading + ", " + _formatLength.format(_length) + "]";
	}
}
