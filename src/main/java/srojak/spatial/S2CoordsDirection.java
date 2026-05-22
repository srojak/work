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
public class S2CoordsDirection
		implements S2CoordsBearing {
	private final S2Coords _coords;
	private final S2CompassDirection _direction;

	public S2CoordsDirection(S2Coords coords, S2CompassDirection direction) {
		Objects.requireNonNull(coords, "coords");
		Objects.requireNonNull(direction, "direction");
		_coords = coords;
		_direction = direction;
	}
	
	@Override
	public S2Coords getCoords() {
		return _coords;
	}
	
	public S2CompassDirection getDirection() {
		return _direction;
	}
}
