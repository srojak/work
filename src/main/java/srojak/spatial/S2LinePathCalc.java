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
import java.util.function.BiFunction;

/**
 * @author Stephen
 *
 */
public class S2LinePathCalc 
		implements S2LinePath {
	private final S2Orientation _orientation;
	private BiFunction<S2Coords, S2Coords, S2Coords> _fnTieBreaker;
	
	private S2Line _line;
	private S2Coords _coordsFrom;
	private S2Coords _coordsTo;
	private S2Offset _offsetTo;

	/**
	 * 
	 */
	public S2LinePathCalc(S2Orientation orientation) {
		Objects.requireNonNull(orientation, "orientation");
		_orientation = orientation;
		_fnTieBreaker = (c1, c2) -> c1;
	}

	@Override
	public void setTieBreaker(BiFunction<S2Coords, S2Coords, S2Coords> fnTieBreaker) {
		Objects.requireNonNull(fnTieBreaker, "fnTieBreaker");
		_fnTieBreaker = fnTieBreaker;
	}
	
	private void addCoordsInPrimaryDirection(LinkedList<S2Coords> list, S2CompassDirection direction) {
		S2Offset offset = _orientation.offsetByOne(direction);
		boolean bDone = false;
		while (!bDone) {
			S2Coords coordsNew = list.getLast().getOffsetCoords(offset);
			list.add(coordsNew);
			bDone = _coordsTo.equals(coordsNew);
		}
	}

	@Override
	public List<S2Coords> getCoordsOnLine(S2Line line, boolean bAllowDiagonal) {
		Objects.requireNonNull(line, "line");
		
		// setup
		_line = line;
		_coordsFrom = _line.getStart();
		_coordsTo = _line.getEnd();
		_offsetTo = _line.getLineOffset();
		LinkedList<S2Coords> list = new LinkedList<S2Coords>();
		list.add(_coordsFrom);
		S2Direction direction = _orientation.findDirection(_offsetTo);
		if (direction == S2SymbolicDirection.None) {
			return list;
		}
		
		S2CompassDirection dirc = direction.getAsCompassDirection();
		if (dirc.isCardinalDirection()) {
			// can move in a straight line until the end is reached
			addCoordsInPrimaryDirection(list, dirc);
			return list;
		}
		
		S2Offset offset1a = null;
		S2Offset offset1b = null;
		S2Offset offsetTranspose = null;
		boolean bDone = false;
		switch (dirc.getDegrees().getValue()) {
		case 45:	// northeast
			if (bAllowDiagonal && _offsetTo.dx == - _offsetTo.dy) {
				addCoordsInPrimaryDirection(list, dirc);
				return list;				
			}
			offset1a = _orientation.offsetByOne(S2CompassDirection.East);
			offset1b = _orientation.offsetByOne(S2CompassDirection.North);
			offsetTranspose = new S2Offset(1, 1);
			break;
			
		case 135:	// southeast
			if (bAllowDiagonal && _offsetTo.dx ==  _offsetTo.dy) {
				addCoordsInPrimaryDirection(list, dirc);
				return list;				
			}
			offset1a = _orientation.offsetByOne(S2CompassDirection.East);
			offset1b = _orientation.offsetByOne(S2CompassDirection.South);
			offsetTranspose = new S2Offset(1, -1);
			break;
			
		case 225:	// southwest
			if (bAllowDiagonal && _offsetTo.dx == - _offsetTo.dy) {
				addCoordsInPrimaryDirection(list, dirc);
				return list;				
			}
			offset1a = _orientation.offsetByOne(S2CompassDirection.West);
			offset1b = _orientation.offsetByOne(S2CompassDirection.South);
			offsetTranspose = new S2Offset(-1, -1);
			break;
			
		case 315:	// northwest
			if (bAllowDiagonal && _offsetTo.dx ==  _offsetTo.dy) {
				addCoordsInPrimaryDirection(list, dirc);
				return list;				
			}
			offset1a = _orientation.offsetByOne(S2CompassDirection.East);
			offset1b = _orientation.offsetByOne(S2CompassDirection.North);
			offsetTranspose = new S2Offset(-1, 1);
			break;
		}
		
		
		int m = 2 * Math.abs(_offsetTo.dy);
		int m_err = m - Math.abs(_offsetTo.dx);
		while (!bDone) {
			S2Coords coordsLast = list.getLast();
			S2Coords coords1a = coordsLast.getOffsetCoords(offset1a);
			S2Coords coords1b = coordsLast.getOffsetCoords(offset1b);
			int nError1a = _line.getCoordDistanceNumerator(coords1a);
			int nError1b = _line.getCoordDistanceNumerator(coords1b);
			S2Coords coordsNext = coords1a;
			if (nError1a == nError1b) {
				coordsNext = _fnTieBreaker.apply(coords1a, coords1b);
			} else if (nError1a > nError1b) {
				coordsNext = coords1b;
			}
			list.add(coordsNext);
			bDone = _coordsTo.equals(coordsNext);
		}
		return list;
	}

}
