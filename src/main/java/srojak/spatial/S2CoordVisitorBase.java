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

import srojak.core.observe.TraceLevel;
import srojak.debug.DebugSwitch;

/**
 * @author Stephen
 *
 */
public abstract class S2CoordVisitorBase {
	protected final DebugSwitch _swDebug;
	protected final S2FieldSize _szField;
	protected final S2Orientation _orientation;
	// transient work variables
	private S2Coords _coordsFrom;
	private S2Coords _coordsTo;
	private S2Offset _offsetTo;
	private double _dLengthSegment;

	/**
	 * 
	 */
	protected S2CoordVisitorBase(DebugSwitch swDebug, S2Surface surface) {
		Objects.requireNonNull(swDebug, "swDebug");
		Objects.requireNonNull(surface, "surface");
		_swDebug = swDebug;
		_szField = surface.getFieldSize();
		_orientation = surface.getOrientation();
		_coordsFrom = null;
		_coordsTo = null;
		_offsetTo = new S2Offset(0, 0);
		_dLengthSegment = 1.0d;
	}
	
	public void setupUsing(S2Segment segment) {
		Objects.requireNonNull(segment, "segment");
		_swDebug.writeTraceEnter(TraceLevel.HIGH, () -> "walk segment " + segment);
		_coordsFrom = segment.getStart();
		_coordsTo = segment.getEnd();
		_offsetTo = segment.getOffset();
		_dLengthSegment = _offsetTo.getDistance();
	}
	
	public void setupUsing(S2OffsetRay ray) {
		Objects.requireNonNull(ray, "ray");
		_swDebug.writeTraceEnter(TraceLevel.HIGH, () -> "walk ray " + ray);
		_coordsFrom = ray.getOrigin();
		_coordsTo = ray.getEndpoint();
		_offsetTo = _coordsFrom.getOffsetTo(_coordsTo);
		_dLengthSegment = _offsetTo.getDistance();
	}
	
	protected S2Coords getCoordsFrom() {
		return _coordsFrom;
	}
	
	protected boolean isEqualCoordsTo(S2Coords coords) {
		return coords.equals(_coordsTo);
	}
	
	protected boolean isOffsetToZero() {
		return _offsetTo.isZero();
	}
	
	protected S2CompassDirection getOverallCompassDirection() 
			throws NoValidMoveException {
		return _orientation.findCompassDirection(_offsetTo);
	}
	
	protected boolean isExactDiagonal() {
		return Math.abs(_offsetTo.dx) == Math.abs(_offsetTo.dy);
	}
	
	protected double getOverallLength() {
		return _dLengthSegment;
	}
	
	/**
	 * Get the numerator of the distance from a point to a line.
	 * @param coordPoint
	 * @param coordsPoint The coordinates of the point for which to find the distance.
	 * @return the numerator of the distance formula.
	 * 
	 * @see https://stackoverflow.com/questions/30559799/function-for-finding-the-distance-between-a-point-and-an-edge-in-java
	 * @see https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
	 * @see S2Geometry.getDistanceNumerator
	 */
	protected int getCoordDistanceNumerator(S2Coords coordPoint) {
		Objects.requireNonNull(coordPoint, "coordPoint");
		return Math.abs(_offsetTo.dy * coordPoint._x - _offsetTo.dx * coordPoint._y
				+ _coordsTo._x * _coordsFrom._y - _coordsTo._y * _coordsFrom._x);
	}
	
	protected S2Offset getOffsetToEndpoint(S2Coords coords) {
		return coords.getOffsetTo(_coordsTo);
	}
}
