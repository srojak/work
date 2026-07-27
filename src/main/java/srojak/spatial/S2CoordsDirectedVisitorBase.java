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

import srojak.core.observe.ObsLevel;
import srojak.debug.DebugSwitch;
import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.compass.CompassOrdinals;

/**
 * @author Stephen
 *
 */
public abstract class S2CoordsDirectedVisitorBase<A>
		extends S2CoordVisitorBase
		implements CompassOrdinals {
	
	// transient work variables
	private S2OffsetDirectionPair _odHoriz;
	private S2OffsetDirectionPair _odVert;
	
	protected static final int MOVE_INVALID = 0;
	protected static final int MOVE_HORIZONTAL = 1;
	protected static final int MOVE_VERTICAL = 2;
	
	protected S2CoordsDirectedVisitorBase(DebugSwitch swDebug, S2Surface surface)
	{
		super(swDebug, surface);
		_odHoriz = null;
		_odVert = null;
	}
	
	protected abstract void visitLocation(int nSequence, S2UnitRay ray, A arg)
			throws InvalidLocationException;
	
	protected void walkInDirection(S2CompassDirection direction, A arg) 
			throws InvalidLocationException {
		S2UnitRay ray = new S2UnitRay(getCoordsFrom(), direction);
		int nSeq = 0;
		while (true) {
			visitLocation(nSeq++, ray, arg);
			S2Coords coords = ray.computeEndpoint(_orientation);
			if (isEqualCoordsTo(coords)) {
				return;
			}
			if (!_szField.isInBounds(coords)) {
				throw new InvalidLocationException(coords, "off surface");
			}
			ray = new S2UnitRay(coords, direction);
		}
	}
	
	private void resolveMovePairs(S2CompassDirection direction) {
		switch (direction.getOrdinal()) {
		case OrdNorthEast:
			_odHoriz = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.East);
			_odVert = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.North);
			break;
			
		case OrdSouthEast:
			_odHoriz = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.East);
			_odVert = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.South);
			break;
			
		case OrdSouthWest:
			_odHoriz = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.West);
			_odVert = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.South);
			break;
			
		case OrdNorthWest:
			_odHoriz = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.West);
			_odVert = S2OffsetDirectionPair.makeOneUnitPair(_orientation, S2CompassDirection.North);
			break;
		}
	}
	
	protected abstract int chooseBetween(S2Coords coordsHorizontal, S2Coords coordsVertical)
			throws NoValidMoveException ;
		
	protected void walk(A arg, boolean bAllowDiagonal)
			throws InvalidLocationException {
		if (isOffsetToZero()) {
			return;
		}
		S2CompassDirection direction = null;
		try {
			direction = getOverallCompassDirection();
		} catch (NoValidMoveException e) {
			// this should not happen as it was previously defended against.
			_swDebug.writeException(ObsLevel.ERROR, e, true);
			return;
		}
		if (direction.isCardinalDirection()) {
			// can move in a straight line until the end is reached
			walkInDirection(direction, arg);
			return;
		}
		if (bAllowDiagonal) {
			if (isExactDiagonal()) {
				// can move in a straight line along the diagonal
				walkInDirection(direction, arg);
				return;
			}
		}
		
		resolveMovePairs(direction);
		int nSeq = 0;
		S2Coords coords = getCoordsFrom();
		while (!isEqualCoordsTo(coords)) {
			S2Coords coordsH = _odHoriz.moveFrom(coords);
			S2Coords coordsV = _odVert.moveFrom(coords);
			S2UnitRay ray = null;
			double dDistH = getCoordDistanceNumerator(coordsH) / getOverallLength();
			double dDistV = getCoordDistanceNumerator(coordsV) / getOverallLength();
			int nCompar = DoublePrecisionComparer.DEFAULT_COMPARER.compare(dDistH, dDistV);
			int nMove = MOVE_INVALID;
			if (nCompar < 0) {
				nMove = MOVE_HORIZONTAL;
			} else if (nCompar > 0) {
				nMove = MOVE_VERTICAL;
			} else {
				try {
					nMove = chooseBetween(coordsH, coordsV);
				} catch (NoValidMoveException exc) {
					throw new InvalidLocationException(coords, "cannot move forward", exc);
				}
			}
			switch (nMove) {
			case MOVE_HORIZONTAL:
				ray = new S2UnitRay(coords, _odHoriz.getDirection());
				coords = coordsH;
				break;
				
			case MOVE_VERTICAL:
				ray = new S2UnitRay(coords, _odVert.getDirection());
				coords = coordsV;
				break;
				
			default:
				throw new InvalidLocationException(coords, "invalid direction from chooseBetween");
			}
			if (!_szField.isInBounds(coords)) {
				throw new InvalidLocationException(coords, "off surface");
			}
			visitLocation(nSeq++, ray, arg);
		}
	}
}
