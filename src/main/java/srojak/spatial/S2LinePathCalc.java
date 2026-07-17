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
import java.util.function.ToIntFunction;

import srojak.core.observe.ObsLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class S2LinePathCalc 
		implements S2LinePath, S2CompassOrdinals {
	private final S2Orientation _orientation;
	private ToIntFunction<S2Coords> _fnWeight;
	
	private S2Line _line;
	private S2Coords _coordsFrom;
	private S2Coords _coordsTo;
	private S2Offset _offsetTo;

	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = S2LinePathCalc.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	/**
	 * 
	 */
	public S2LinePathCalc(S2Orientation orientation) {
		Objects.requireNonNull(orientation, "orientation");
		_orientation = orientation;
		_fnWeight = c -> 5;
	}

	@Override
	public void setWeightFunction(ToIntFunction<S2Coords> fnWeight) {
		Objects.requireNonNull(fnWeight, "fnWeight");
		_fnWeight = fnWeight;
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
	
	protected void walkLineInDirection(S2Line line, S2CompassDirection direction,
			S2CoordMoveConsumer consumer) {
		S2Offset offset = _orientation.offsetByOne(direction);
		S2OffsetDirectionPair pair = new S2OffsetDirectionPair(offset, direction);
		S2Coords coords = line.getStart();
		int nSeq = 0;
		while (true) {
			consumer.accept(coords, pair, nSeq++);
			coords = coords.getNewLocationFrom(offset);
			if (coords.equals(line.getEnd())) {
				return;
			}
		}
	}
	
	protected void walkLine(S2Line line, boolean bAllowDiagonal,
			S2CoordMoveConsumer consumer) {
		
		// setup
		_line = line;
		_coordsFrom = _line.getStart();
		_coordsTo = _line.getEnd();
		_offsetTo = _line.getLineOffset();
		if (_offsetTo.isZero()) {
			return;
		}
		S2CompassDirection direction = null;
		try {
			direction = _orientation.findCompassDirection(_offsetTo);
		} catch (NoValidMoveException e) {
			// this should not happen as it was previously defended against.
			_swDebugClass.writeException(ObsLevel.ERROR, e, true);
			return;
		}
		if (direction.isCardinalDirection()) {
			// can move in a straight line until the end is reached
			walkLineInDirection(line, direction, consumer);
			return;
		}
		if (bAllowDiagonal) {
			if (Math.abs(_offsetTo.dx) == Math.abs(_offsetTo.dy) ) {
				// can move in a straight line along the diagonal
				walkLineInDirection(line, direction, consumer);
				return;
			}
		}
		
		S2OffsetDirectionPair odHoriz = null;
		S2OffsetDirectionPair odVert = null;
		// TODO this is where I would like to get a Bresenham implementation
		int m = 2 * Math.abs(_offsetTo.dy);
		@SuppressWarnings("unused")
		int m_err = m - Math.abs(_offsetTo.dx);
		
		switch (direction.getOrdinal()) {
		case OrdNorthEast:
			odHoriz = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.East);
			odVert = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.North);
			break;
			
		case OrdSouthEast:
			odHoriz = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.East);
			odVert = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.South);
			break;
			
		case OrdSouthWest:
			odHoriz = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.West);
			odVert = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.South);
			break;
			
		case OrdNorthWest:
			odHoriz = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.West);
			odVert = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.North);
			break;
		}
		
		int nSeq = 0;
		S2Coords coords = _coordsFrom;
		while (!coords.equals(_coordsTo)) {
			S2Coords coordsH = odHoriz.moveFrom(coords);
			S2Coords coordsV = odVert.moveFrom(coords);
			int nErrorH = _line.getCoordDistanceNumerator(coordsH) + _fnWeight.applyAsInt(coordsH);
			int nErrorV = _line.getCoordDistanceNumerator(coordsV) + _fnWeight.applyAsInt(coordsV);
			if (nErrorH <= nErrorV) {
				consumer.accept(coords, odHoriz, nSeq);
				coords = coordsH;
			} else {
				consumer.accept(coords, odVert, nSeq);
				coords = coordsV;
			}
			nSeq++;
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
		switch (dirc.getOrdinal()) {
		case OrdNorthEast:
			if (bAllowDiagonal && _offsetTo.dx == - _offsetTo.dy) {
				addCoordsInPrimaryDirection(list, dirc);
				return list;				
			}
			offset1a = _orientation.offsetByOne(S2CompassDirection.East);
			offset1b = _orientation.offsetByOne(S2CompassDirection.North);
			offsetTranspose = new S2Offset(1, 1);
			break;
			
		case OrdSouthEast:
			if (bAllowDiagonal && _offsetTo.dx ==  _offsetTo.dy) {
				addCoordsInPrimaryDirection(list, dirc);
				return list;				
			}
			offset1a = _orientation.offsetByOne(S2CompassDirection.East);
			offset1b = _orientation.offsetByOne(S2CompassDirection.South);
			offsetTranspose = new S2Offset(1, -1);
			break;
			
		case OrdSouthWest:
			if (bAllowDiagonal && _offsetTo.dx == - _offsetTo.dy) {
				addCoordsInPrimaryDirection(list, dirc);
				return list;				
			}
			offset1a = _orientation.offsetByOne(S2CompassDirection.West);
			offset1b = _orientation.offsetByOne(S2CompassDirection.South);
			offsetTranspose = new S2Offset(-1, -1);
			break;
			
		case OrdNorthWest:
			if (bAllowDiagonal && _offsetTo.dx ==  _offsetTo.dy) {
				addCoordsInPrimaryDirection(list, dirc);
				return list;				
			}
			offset1a = _orientation.offsetByOne(S2CompassDirection.East);
			offset1b = _orientation.offsetByOne(S2CompassDirection.North);
			offsetTranspose = new S2Offset(-1, 1);
			break;
		}
				
		boolean bDone = false;
		while (!bDone) {
			S2Coords coordsLast = list.getLast();
			S2Coords coords1a = coordsLast.getOffsetCoords(offset1a);
			S2Coords coords1b = coordsLast.getOffsetCoords(offset1b);
			int nError1a = _line.getCoordDistanceNumerator(coords1a) + _fnWeight.applyAsInt(coords1a);
			int nError1b = _line.getCoordDistanceNumerator(coords1b) + _fnWeight.applyAsInt(coords1b);
			S2Coords coordsNext = coords1a;
			if (nError1a > nError1b) {
				coordsNext = coords1b;
			}
			list.add(coordsNext);
			bDone = _coordsTo.equals(coordsNext);
		}
		return list;
	}

	@Override
	public List<S2UnitRay> getUnitVectorPath(S2Line line, boolean bAllowDiagonal) {
		List<S2UnitRay> list = new LinkedList<S2UnitRay>();
		walkLine(line, bAllowDiagonal, (cd, pair, n) -> {
			S2UnitRay vector = new S2UnitRay(cd, pair.getDirection());
			list.add(vector);
		});
		return list;
	}

}
