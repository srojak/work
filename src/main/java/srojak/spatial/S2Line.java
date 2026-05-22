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
public class S2Line {
	private final S2Coords _coordsFrom;
	private final S2Coords _coordsTo;
	private final S2Offset _offsetTo;
	
	public S2Line(S2Coords coordsStart, S2Coords coordsEnd) {
		Objects.requireNonNull(coordsStart, "coordsStart");
		Objects.requireNonNull(coordsEnd, "coordsEnd");
		_coordsFrom = new S2Coords(coordsStart);
		_coordsTo = new S2Coords(coordsEnd);
		_offsetTo = coordsStart.getOffsetTo(coordsEnd);
	}
	
	public S2Coords getStart() {
		return _coordsFrom;
	}
	
	public S2Coords getEnd() {
		return _coordsTo;
	}
	
	public S2Offset getLineOffset() {
		return _offsetTo;
	}
	
	public double getLength() {
		return _offsetTo.getDistance();
	}
	
	public S2Direction getDirection(S2Orientation orientation) {
		Objects.requireNonNull(orientation, "orientation");
		return orientation.findDirection(_offsetTo);
	}
	
	/**
	 * 
	 * @param coordPoint
	 * @return
	 * @see https://stackoverflow.com/questions/30559799/function-for-finding-the-distance-between-a-point-and-an-edge-in-java
	 * @see https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
	 */
	public int getCoordDistanceNumerator(S2Coords coordPoint) {
		Objects.requireNonNull(coordPoint, "coordPoint");
		return Math.abs(_offsetTo.dy * coordPoint._x - _offsetTo.dx * coordPoint._y
				+ _coordsTo._x * _coordsFrom._y - _coordsTo._y * _coordsFrom._x);
	}
}
