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

import srojak.core.InvalidOperationException;

/**
 * @author Stephen
 *
 */
public class S2Segment {
	public S2Coords _coordsStart;
	public S2Coords _coordsEnd;
	public boolean _bLock;
	
	public S2Segment(S2Coords coordsStart, S2Coords coordsEnd, boolean bIsLocked) {
		Objects.requireNonNull(coordsStart, "coordsStart");
		Objects.requireNonNull(coordsStart, "_coordsEnd");
		_coordsStart = coordsStart;
		_coordsEnd = coordsEnd;
		_bLock = bIsLocked;
	}
	
	public S2Segment(S2Coords coordsStart, S2Coords coordsEnd) {
		this(coordsStart, coordsEnd, false);
	}
	
	public S2Coords getStart() {
		return _coordsStart;
	}
	
	public S2Coords getEnd() {
		return _coordsEnd;
	}
	
	public boolean isLocked() {
		return _bLock;
	}
	
	public void lock() {
		_bLock = true;
	}
	
	public void changeStart(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		if (_bLock) {
			throw new InvalidOperationException("S2Segment", "is locked");
		}
		_coordsStart = coords;
	}
	
	public void changeEnd(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		if (_bLock) {
			throw new InvalidOperationException("S2Segment", "is locked");
		}
		_coordsEnd = coords;	
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
