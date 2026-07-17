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

/**
 * @author Stephen
 *
 */
public class S2CoordsMove {
	private final S2Coords _coords;
	private final SpatialMove _move;
	
	public S2CoordsMove(SpatialMove move, S2Coords coords) {
		Objects.requireNonNull(move, "move");
		Objects.requireNonNull(coords, "coords");
		_move = move;
		_coords = coords;
	}
	
	public S2Coords getCoords() {
		return _coords;
	}
	
	public SpatialMove getMove() {
		return _move;
	}

	@Override
	public String toString() {
		return _move.toString() + " " + _coords.toEnclosedString();
	}
}
