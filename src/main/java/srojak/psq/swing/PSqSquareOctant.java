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

import java.awt.geom.Rectangle2D;
import java.util.Objects;

import srojak.numerics.CircleOctant;
import srojak.spatial.S2Coords;

/**
 * @author Stephen
 *
 */
public class PSqSquareOctant {
	private final S2Coords _coords;
	private final Rectangle2D _rectSquare;
	private final CircleOctant _octant;
	
	public PSqSquareOctant(S2Coords coords, Rectangle2D rectSquare, CircleOctant octant) {
		Objects.requireNonNull(coords, "coords");
		Objects.requireNonNull(rectSquare, "rectSquare");
		Objects.requireNonNull(octant, "octant");
		_coords = coords;
		_rectSquare = rectSquare;
		_octant = octant;
	}
	
	public S2Coords getCoords() {
		return _coords;
	}
	
	public Rectangle2D getRectSquare() {
		return _rectSquare;
	}
	
	public CircleOctant getOctant() {
		return _octant;
	}
}
