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
package srojak.psq.swing;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import srojak.cdo.AWTGeometry;
import srojak.cdo.Scaler;
import srojak.cdo.swing.ScalableDrawingComponent;
import srojak.core.Ordered;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.numerics.CircleOctant;
import srojak.psq.PlaneSquare;
import srojak.psq.PlaneSquareGrid;
import srojak.psq.PlaneSquareOffGridException;
import srojak.psq.PlaneSquarePath;
import srojak.spatial.InvalidLocationException;
import srojak.spatial.NoValidMoveException;
import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Coords;
import srojak.spatial.S2FieldSize;
import srojak.spatial.S2Geometry;
import srojak.spatial.S2OffsetRay;
import srojak.spatial.S2Orientation;
import srojak.spatial.S2Segment;
import srojak.spatial.S2Surface;
import srojak.spatial.S2UnitRay;
import srojak.spatial.S2UnitRayAStarVisitor;

/**
 * @author Stephen
 *
 */
public class PlaneSquareDrawingControl
		implements S2Surface {
	protected final ScalableDrawingComponent _owner;
	protected final Scaler _scaler;
	protected final PlaneSquareGrid<? extends PlaneSquare> _grid;
	protected final PSqGridLinesControl _ctlGridLines;
	protected final PlaneSquarePath _pathSelect;
	protected final PlaneSquareScalerTool _toolScaler;
	protected final S2UnitRayAStarVisitor _visitor;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PlaneSquareDrawingControl.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	public PlaneSquareDrawingControl(ScalableDrawingComponent owner,
			PlaneSquareGrid<? extends PlaneSquare> grid) {
		Objects.requireNonNull(owner, "owner");
		Objects.requireNonNull(grid, "grid");
		_owner = owner;
		_scaler = _owner.getScaler();
		_grid = grid;
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, 
				() -> "constructor: owner=" + _owner.getNameTag() 
					+ ", grid size " + _grid.getFieldSize());
		_ctlGridLines = new PSqGridLinesControl();
		_pathSelect = new PlaneSquarePath(_grid);
		_toolScaler = new PlaneSquareScalerTool(_scaler, _grid);
		_visitor = new S2UnitRayAStarVisitor(_grid);
	}
	
	public PlaneSquareGrid<? extends PlaneSquare> getGrid() {
		return _grid;
	}

	@Override
	public S2Orientation getOrientation() {
		return _grid.getOrientation();
	}

	@Override
	public S2FieldSize getFieldSize() {
		return _grid.getFieldSize();
	}
	
	public PSqGridLinesControl getGridLinesControl() {
		return _ctlGridLines;
	}
	
	public PlaneSquareScalerTool getScalerTool() {
		return _toolScaler;
	}
	
	public PlaneSquareRenderingInfo getCurrentRendering() {
		return new PlaneSquareRenderingInfo(_scaler.getScale(), _toolScaler.getScaledSquareSize());
	}
	
	public PSqSquareOctant findSquareOctantFor(Point pt) 
			throws PlaneSquareOffGridException {
		S2FieldSize szField = _grid.getFieldSize();
		S2Coords coords = _toolScaler.getCoordsFromPoint(pt);
		if (!szField.isInBounds(coords)) {
			throw new PlaneSquareOffGridException(coords, "point is off grid");
		}
		Rectangle2D rectSquare = _toolScaler.getSquareRectangle(coords);
		Point2D ptCenter = _toolScaler.getMidPointFromSquareOrigin(coords);
		if (!rectSquare.contains(ptCenter)) {
			_swDebugClass.write(ObsLevel.ERROR, 
					() -> "center point " + ptCenter + " is not in " + rectSquare);
		}
		CircleOctant octant = AWTGeometry.findRelativeOctant(pt, ptCenter);
		return new PSqSquareOctant(coords, rectSquare, octant);
	}
	
	public PlaneSquarePath getSelectionPath() {
		return _pathSelect;
	}
	
	public void clearSelection() {
		if (!_pathSelect.isEmpty()) {
			_pathSelect.clear();
			_owner.repaint();
		}
	}
	
	public void extendSelectionTo(S2Coords coords) 
			throws InvalidLocationException, NoValidMoveException {
		boolean bCanAdd = true;
		boolean bFoundPoint = false;
		if (!_pathSelect.isEmpty()) {
			List<S2Coords> listCoords = _pathSelect.getAll();
			if (_pathSelect.size() > 1) {
				// if the coords are on the existing path, it cuts the path at that point.
				List<S2Segment> segments = S2Geometry.pointsToSegments(listCoords, true);
				Ordered<S2Segment> segord = S2Geometry.findSegmentContainingPoint(segments, coords);
				if (segord != null) {
					_swDebugClass.write(ObsLevel.DEBUG, () -> "found point on segment " + segord.getValue());
					bFoundPoint = true;
					_pathSelect.removeAllStartingWith(segord.getValue().getEnd());
					if (coords.equals(segord.getValue().getStart())) {
						bCanAdd = false;
					}
				}
			}
			if (!bFoundPoint) {
				// may require making turns
				S2Coords coordsLast = _pathSelect.getLast();
				S2Orientation orientation = _grid.getOrientation();
				S2OffsetRay ray = new S2OffsetRay(orientation, coordsLast, coords);
				boolean bDone = false;
				while (!bDone) {
					ObservationCollector collect = _swDebugClass.createCollector(ObsLevel.DEBUG);
					if (collect.isActive()) {
						collect.append("analyzing ray ");
						collect.append(ray);
						collect.commit();
					}
					S2CompassDirection direction = ray.getNearestDirection();
					List<S2UnitRay> listRays = _visitor.expand(ray);
					_swDebugClass.write(ObsLevel.DEBUG, "expanded to list of " + listRays.size()
								+ " unit rays");
					Iterator<S2UnitRay> iterator = listRays.iterator();
					while (iterator.hasNext()) {
						S2UnitRay uray = iterator.next();
						S2CompassDirection dirRay = uray.getDirection();
						if (!dirRay.equals(direction)) {
							_pathSelect.add(uray.getOrigin());
							direction = dirRay;
						}
					}
					bDone = true;
				}
			}
		}
		if (bCanAdd) {
			_swDebugClass.write(ObsLevel.DEBUG, () -> "adding coords " + coords.toEnclosedString());
			_pathSelect.add(coords);
		}
		_owner.repaint();
	}
}
