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
import srojak.core.Lockable;
import srojak.core.logic.LockGate;

/**
 * @author Stephen
 *
 */
public class S2Segment
		implements Lockable {
	public S2Coords _coordsStart;
	public S2Coords _coordsEnd;
	public final LockGate _gateLock;
	
	public S2Segment(S2Coords coordsStart, S2Coords coordsEnd, boolean bIsLocked) {
		Objects.requireNonNull(coordsStart, "coordsStart");
		Objects.requireNonNull(coordsStart, "_coordsEnd");
		_coordsStart = coordsStart;
		_coordsEnd = coordsEnd;
		_gateLock = new LockGate();
		if (bIsLocked) {
			_gateLock.lock();
		}
	}
	
	public S2Segment(S2Coords coordsStart, S2Coords coordsEnd) {
		this(coordsStart, coordsEnd, false);
	}
	
	public S2Segment(S2Segment segSource) {
		Objects.requireNonNull(segSource, "segSource");
		_coordsStart = segSource._coordsStart;
		_coordsEnd = segSource._coordsEnd;
		_gateLock = new LockGate();
	}
	
	public S2Coords getStart() {
		return _coordsStart;
	}
	
	public S2Coords getEnd() {
		return _coordsEnd;
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
	}
	
	public void changeEnd(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		_gateLock.testLock("S2Segment");
		_coordsEnd = coords;	
	}
	
	public boolean merge(S2Segment segment) {
		Objects.requireNonNull(segment, "segment");
		_gateLock.testLock("S2Segment");
		if (_coordsEnd.equals(segment._coordsStart)) {
			_coordsEnd = segment._coordsEnd;
			return true;
		} else if (_coordsStart.equals(segment._coordsEnd)) {
			_coordsStart = segment._coordsStart;
			return true;
		} else {
			return false;
		}
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
