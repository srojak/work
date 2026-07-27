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

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Coords;
import srojak.spatial.S2Rect;
import srojak.spatial.S2Surface;

/**
 * @author Stephen
 *
 */
public interface PlaneSquareGrid<S extends PlaneSquare>
		extends S2Surface {
	
	S getSquare(int nRow, int nColumn);
	S getSquare(S2Coords coords);
	S getSquareOffset(S2Coords coordsBase, int nRow, int nColumn);
	void forEntireMap(Consumer<S> action);
	void forPartialMap(int startX, int startY, int width, int height,
			Consumer<S> action);
	void forPartialMap(S2Rect rect, Consumer<S> action);
	List<S> getEligibleSquares(S2Rect rect, Predicate<S> predicate);
	S getSquareInDirection(S squareFrom, S2CompassDirection direction)
			throws PlaneSquareOffGridException;
	S getSquareInDirection(S2Coords coords, S2CompassDirection direction)
			throws PlaneSquareOffGridException;
}
