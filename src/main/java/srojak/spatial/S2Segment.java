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

import srojak.core.Lockable;
import srojak.core.logic.LockGate;

/**
 * @author Stephen
 *
 */
public class S2Segment
		implements S2CoordsPair, Lockable {
	private S2Coords _coordsStart;
	private S2Coords _coordsEnd;
	private final LockGate _gateLock;
	private S2Offset _offsetTo;
	
	private static final double _fuzzCalcY = 0.25d;
	
	public S2Segment(S2Coords coordsStart, S2Coords coordsEnd, boolean bIsLocked) {
		Objects.requireNonNull(coordsStart, "coordsStart");
		Objects.requireNonNull(coordsEnd, "coordsEnd");
		_coordsStart = coordsStart;
		_coordsEnd = coordsEnd;
		_offsetTo = _coordsStart.getOffsetTo(_coordsEnd);
		_gateLock = new LockGate();
		if (bIsLocked) {
			_gateLock.lock();
		}
	}
	
	public S2Segment(S2Coords coordsStart, S2Coords coordsEnd) {
		this(coordsStart, coordsEnd, false);
	}
	
	public S2Segment(S2Coords coordsStart, S2Offset offset, boolean bIsLocked) {
		Objects.requireNonNull(coordsStart, "coordsStart");
		Objects.requireNonNull(offset, "offset");
		_coordsStart = coordsStart;
		_coordsEnd = coordsStart.getNewLocationFrom(offset);
		_offsetTo = offset;
		_gateLock = new LockGate();
		if (bIsLocked) {
			_gateLock.lock();
		}
	}
	
	public S2Segment(S2Coords coordsStart, S2Offset offset) {
		this (coordsStart, offset, false);
	}
	
	public S2Segment(S2Segment segSource) {
		Objects.requireNonNull(segSource, "segSource");
		_coordsStart = segSource._coordsStart;
		_coordsEnd = segSource._coordsEnd;
		_offsetTo = segSource._offsetTo;
		_gateLock = new LockGate();
	}
	
	@Override
	public S2Coords getStart() {
		return _coordsStart;
	}
	
	@Override
	public S2Coords getEnd() {
		return _coordsEnd;
	}
	
	S2Offset getOffset() {
		return _offsetTo;
	}
	
	@Override
	public boolean isLocked() {
		return _gateLock.isLocked();
	}
	
	@Override
	public void lock() {
		_gateLock.lock();
	}
	
	public void changeStart(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		_gateLock.testLock("S2Segment");
		_coordsStart = coords;
		_offsetTo = _coordsStart.getOffsetTo(_coordsEnd);
	}
	
	public void changeEnd(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		_gateLock.testLock("S2Segment");
		_coordsEnd = coords;	
		_offsetTo = _coordsStart.getOffsetTo(_coordsEnd);
	}
	
	/**
	 * 
	 * @param coordPoint
	 * @return
	 * @see https://stackoverflow.com/questions/30559799/function-for-finding-the-distance-between-a-point-and-an-edge-in-java
	 * @see https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
	 */
	public double getCoordDistanceNumerator(S2Coords coordPoint) {
		Objects.requireNonNull(coordPoint, "coordPoint");
		R2Coords rcdsEnd = _coordsEnd.getR2Coords();
		R2Coords rcdsPoint = coordPoint.getR2Coords();
		return Math.abs(_offsetTo.dy * rcdsPoint._x - _offsetTo.dx * rcdsPoint._y
				+ rcdsEnd._x * _coordsStart._y - rcdsEnd._y * _coordsStart._x);
	}
	
	public S2Rect getBoundingRect() {
		int dx = _offsetTo.dx >= 0 ? _offsetTo.dx + 1 : _offsetTo.dx - 1;
		int dy = _offsetTo.dy >= 0 ? _offsetTo.dy + 1 : _offsetTo.dy - 1;
		return S2Rect.normalize(_coordsStart._x, _coordsStart._y, dx, dy);
	}
	
	public boolean isPointOnSegment(S2Coords coordPoint) {
		Objects.requireNonNull(coordPoint, "coordPoint");
		S2Rect rectBound = getBoundingRect();
		if (!rectBound.contains(coordPoint)) {
			return false;
		}
		double dnumer = getCoordDistanceNumerator(coordPoint);
		return dnumer < _fuzzCalcY;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_coordsStart, _coordsEnd);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof S2Segment other) {
			return _coordsStart.equals(other._coordsStart)
					&& _coordsEnd.equals(other._coordsEnd);
		}
		return false;
	}

	@Override
	public String toString() {
		return "S2Segment [start=" + _coordsStart.toEnclosedString() 
				+ ", end=" + _coordsEnd.toEnclosedString() + "]";
	}
}
