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
package srojak.psq;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import srojak.core.observe.ObsLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Coords;
import srojak.spatial.S2CoordsOutOfBoundsException;
import srojak.spatial.S2FieldSize;
import srojak.spatial.S2Offset;
import srojak.spatial.S2Orientation;
import srojak.spatial.S2Rect;

/**
 * @author Stephen
 *
 */
public class PlaneSquareGridArray<S extends PlaneSquare>
		implements PlaneSquareGrid<S> {
	protected final S2Orientation _orient;
	protected final S2FieldSize _szGrid;
	protected final S _grid[][];
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PlaneSquareGridArray.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	@SuppressWarnings("unchecked")
	public PlaneSquareGridArray(S2Orientation orientation, S2FieldSize szGrid,
			Class<S> classSq, PlaneSquareFactory<S> factory) {
		Objects.requireNonNull(orientation, "orientation");
		Objects.requireNonNull(szGrid, "szGrid");
		Objects.requireNonNull(classSq, "classSq");
		Objects.requireNonNull(factory, "factory");
		_orient = orientation;
		_szGrid = szGrid;
		int[] asize = new int[] { _szGrid.width, _szGrid.height };
		_grid = (S[][]) Array.newInstance(classSq, asize);
		for (int i = 0; i < _szGrid.width; i++) {
			for (int j = 0; j < _szGrid.height; j++) {
				S2Coords coords = new S2Coords(i, j);
				_grid[i][j] = factory.create(coords);
			}
		}
	}

	@Override
	public S2Orientation getOrientation() {
		return _orient;
	}

	@Override
	public S2FieldSize getFieldSize() {
		return _szGrid;
	}

	@Override
	public S getSquare(int nRow, int nColumn) {
		if (nRow < 0 || nRow >= _szGrid.width) {
			String strMessage = "Row index out of range: " + nRow;
			_swDebugClass.write(ObsLevel.ERROR, strMessage);
			throw new IndexOutOfBoundsException(strMessage);
		}
		if (nColumn < 0 || nColumn > _szGrid.height) {
			String strMessage = "Column index out of range: " + nColumn;
			_swDebugClass.write(ObsLevel.ERROR, strMessage);
			throw new IndexOutOfBoundsException(strMessage);
		}
		return _grid[nRow][nColumn];
	}

	@Override
	public S getSquare(S2Coords coords) {
		if (!_szGrid.isInBounds(coords)) {
			_swDebugClass.write(ObsLevel.ERROR, () -> "Coordinates " + coords + "out of range");
			throw new S2CoordsOutOfBoundsException(coords);
		}
		return _grid[coords.getX()][coords.getY()];
	}

	@Override
	public S getSquareOffset(S2Coords coordsBase, int nRow, int nColumn) {
		Objects.requireNonNull(coordsBase, "coordsBase");
		return getSquare(coordsBase.getX() + nRow, coordsBase.getY() + nColumn);
	}

	@Override
	public void forEntireMap(Consumer<S> action) {
		Objects.requireNonNull(action, "action");
		for (int i = 0; i < _szGrid.width; i++) {
			for (int j = 0; j < _szGrid.height; j++) {
				action.accept(_grid[i][j]);
			}
		}
	}

	@Override
	public void forPartialMap(int startX, int startY, int width, int height, Consumer<S> action) {
		Objects.requireNonNull(action, "action");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				action.accept(_grid[startX + i][startY + j]);
			}
		}		
	}

	@Override
	public void forPartialMap(S2Rect rect, Consumer<S> action) {
		Objects.requireNonNull(rect, "rect");
		Objects.requireNonNull(action, "action");
		int startX = rect.getOrigin().getX();
		int startY = rect.getOrigin().getY();
		for (int i = 0; i < rect.getWidth(); i++) {
			for (int j = 0; j < rect.getHeight(); j++) {
				action.accept(_grid[startX + i][startY + j]);
			}
		}		
	}
	
	@Override
	public List<S> getEligibleSquares(S2Rect rect, Predicate<S> predicate) {
		Objects.requireNonNull(rect, "rect");
		Objects.requireNonNull(predicate, "predicate");
		ArrayList<S> list = new ArrayList<S>(rect.getWidth() * rect.getHeight());
		forPartialMap(rect, s -> {
			if (predicate.test(s)) {
				list.add(s);
			}
		});
		list.trimToSize();
		return list;
	}

	private S getSquareFrom(S2Coords coords, S2CompassDirection direction)
			throws PlaneSquareOffGridException {
		S2Offset offset = _orient.offsetByOne(direction);
		S2Coords coordsTo = coords.getNewLocationFrom(offset);
		try {
			return getSquare(coordsTo);
		} catch (IndexOutOfBoundsException exc) {
			throw new PlaneSquareOffGridException(coordsTo, "is off grid", exc);
		}
	}

	@Override
	public S getSquareInDirection(S squareFrom, S2CompassDirection direction)
			throws PlaneSquareOffGridException {
		Objects.requireNonNull(squareFrom, "squareFrom");
		Objects.requireNonNull(direction, "direction");
		return getSquareFrom(squareFrom._coords, direction);
	}

	@Override
	public S getSquareInDirection(S2Coords coords, S2CompassDirection direction) 
			throws PlaneSquareOffGridException {
		Objects.requireNonNull(coords, "coords");
		Objects.requireNonNull(direction, "direction");
		if (!_szGrid.isInBounds(coords)) {
			throw new PlaneSquareOffGridException(coords, "is off grid");			
		}
		return getSquareFrom(coords, direction);
	}
}
