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
package srojak.psq.generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.OrderedComparison;
import srojak.psq.PlaneSquare;
import srojak.psq.PlaneSquareGrid;
import srojak.spatial.PolarCoords;
import srojak.spatial.R2Geometry;
import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Coords;
import srojak.spatial.S2Direction;
import srojak.spatial.S2FieldSize;
import srojak.spatial.S2Geometry;
import srojak.spatial.S2Offset;
import srojak.spatial.S2Orientation;
import srojak.spatial.S2RandomMover;

/**
 * @author Stephen
 *
 */
public class PlaneSquarePoissonDiscPlacer {
	private final PSqCommonGenTool _toolCommon;
	private final PlaneSquareGrid<? extends PlaneSquare> _grid;
	private final S2RandomMover _rmover;
	private final int _limitTries;
	
	private S2FieldSize _szWork;
	private S2Coords[][] _gridWork;
	
	private static final DebugSwitch _swDebugClass;
	private static final DoublePrecisionComparer _comparerDbl;
	private static final double _dSqrt2;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PlaneSquarePoissonDiscPlacer.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
		_comparerDbl = DoublePrecisionComparer.DEFAULT_COMPARER;
		_dSqrt2 = Math.sqrt(2.0);
	}
	
	public PlaneSquarePoissonDiscPlacer(PSqCommonGenTool toolCommon, 
			PlaneSquareGrid<? extends PlaneSquare> grid, int nMaxTriesOneLocation) {
		Objects.requireNonNull(toolCommon, "toolCommon");
		Objects.requireNonNull(grid, "grid");
		if (nMaxTriesOneLocation < 10) {
			throw new IllegalArgumentException("nMaxTriesOneLocation must be at least 10");
		}
		_toolCommon = toolCommon;
		_grid = grid;
		_rmover = new S2RandomMover(toolCommon, grid);
		_limitTries = nMaxTriesOneLocation;
		_szWork = null;
		_gridWork = null;
	}
	
	private boolean validate(S2Coords coords, final double dMinDistance, final double dCellSize,
			List<S2Coords> listPoints) {
		int cellX = (int) (coords.getX() / dCellSize);
		int cellY = (int) (coords.getY() / dCellSize);
		
		int startX = Math.max(0,  cellX - 2);
		int endX = Math.min(_gridWork.length - 1, cellX + 2);
		int startY = Math.max(0,  cellY - 2);
		int endY = Math.min(_gridWork[0].length - 1, cellY + 2);
		final double dMinDistSq = dMinDistance * dMinDistance;
		
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				S2Coords coordsAt = _gridWork[x][y];
				if (coordsAt != null) {
					S2Offset offset = coords.getOffsetTo(coordsAt);
					double dDist = offset.getDistanceSquare();
					if (_comparerDbl.compare(dDist, OrderedComparison.LT, dMinDistSq)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public List<S2Coords> generateLocations(final double dMinDistance) {
		if (_comparerDbl.compare(dMinDistance, OrderedComparison.LT, 2.0d)) {
			throw new IllegalArgumentException("dMinDistance must be at least 2.0");
		}
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "dMinDistance = " + dMinDistance);
		final double dCellSize = dMinDistance / _dSqrt2;
		final S2FieldSize szField = _grid.getFieldSize();
		_szWork = new S2FieldSize((int) (szField.width / dCellSize) + 1,
				(int) (szField.height / dCellSize) + 1);
		_gridWork = new S2Coords[_szWork.width][_szWork.height];
		
		List<S2Coords> listPoints = new ArrayList<S2Coords>();
		List<S2Coords> listSpawn = new ArrayList<S2Coords>();
		
		// start in the center
		S2FieldSize szGrid = _grid.getFieldSize();
		S2Offset offset = new S2Offset(szGrid.width >> 1, szGrid.height >> 1);
		S2Coords coordsCenter = new S2Coords(offset.dx, offset.dy);
		S2Coords coordsFirst = new S2Coords(coordsCenter);
		PlaneSquare square = _grid.getSquare(coordsFirst);
		if (!square.canBeOccupied()) {
			offset = _rmover.moveRandomDirection(1);
			while (!square.canBeOccupied()) {
				coordsFirst = coordsFirst.getNewLocationFrom(offset);
				square = _grid.getSquare(coordsFirst);
			}
		}
		listPoints.add(coordsFirst);
		listSpawn.add(coordsFirst);
		
		while (!listSpawn.isEmpty()) {
			_swDebugClass.write(ObsLevel.DEBUG, () -> "spawn list size = " + listSpawn.size());
			int idxSelect = _toolCommon.genIntInRange(listSpawn.size());
			S2Coords coords = listSpawn.get(idxSelect);
			_swDebugClass.write(ObsLevel.DEBUG, () -> "selected coords " + coords.toEnclosedString());
			boolean bAccept = false;
			loop2:
			for (int i = 0; i < _limitTries; i++) {
				PolarCoords polar 
					= R2Geometry.generateRandomPointInCircle(_toolCommon, dMinDistance, dCellSize);
				final String strIter = String.valueOf(i);
				_swDebugClass.write(ObsLevel.DEBUG, () -> "iteration " + strIter
						+ ", polar coords " + polar.formatInDegrees()
						+ ", x=" + (int) polar.getX() + ", y=" + (int) polar.getY());
				offset = S2Geometry.polarToOffset(polar);
				S2Coords coordsNew = coords.getNewLocationFrom(offset);
				{
					final S2Coords coordsTry = coordsNew;
					_swDebugClass.write(ObsLevel.DEBUG, () -> "iteration " + strIter 
							+ ", trying " + coordsTry.toEnclosedString());
				}
				if (szGrid.isInBounds(coordsNew)) {
					square = _grid.getSquare(coordsNew);
					if (!square.canBeOccupied()) {
						// walk toward center
						S2Orientation orient = _grid.getOrientation();
						offset = coordsNew.getOffsetTo(coordsCenter);
						S2Direction dirWalk = orient.findNearestDirection(offset);
						_swDebugClass.write(ObsLevel.DEBUG, 
								() -> "walking in direction " + dirWalk.getAbbrev());
						S2CompassDirection direction = dirWalk.getAsCompassDirection();
						offset = orient.offsetByOne(direction);
						while (!square.canBeOccupied()) {
							coordsNew = coordsNew.getNewLocationFrom(offset);
							if (!szGrid.isInBounds(coordsNew)) {
								continue loop2;
							}
							square = _grid.getSquare(coordsNew);
						}
					}
					if (validate(coordsNew, dMinDistance, dCellSize, listPoints)) {
						{
							final S2Coords coordsTry = coordsNew;
							_swDebugClass.write(ObsLevel.DEBUG,
									() -> "accepting " + coordsTry.toEnclosedString());
						}
						listPoints.add(coordsNew);
						listSpawn.add(coordsNew);
						int gridX = (int) (coordsNew.getX() / dCellSize);
						int gridY = (int) (coordsNew.getY() / dCellSize);
						_gridWork[gridX][gridY] = coordsNew;
						bAccept = true;
						break;
					}
				}
			}
			if (!bAccept) {
				listSpawn.remove(idxSelect);
			}
		}
		
		return listPoints;
	}
}
