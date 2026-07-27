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

import java.util.Objects;

import srojak.spatial.S2Coords;
import srojak.spatial.S2CoordsOrigin;
import srojak.spatial.S2Dimension;
import srojak.spatial.S2Direction;
import srojak.spatial.S2Rect;

/**
 * @author Stephen
 *
 */
public class PlaneSquareZone 
		implements S2CoordsOrigin {
	private final S2Coords _origin;
	private final S2Dimension _szZone;
	private final S2Direction _direction;
	
	public PlaneSquareZone(S2Coords coordsOrigin, S2Dimension szZone, S2Direction dirInward) {
		Objects.requireNonNull(coordsOrigin, "direction");
		Objects.requireNonNull(szZone, "szZone");
		Objects.requireNonNull(dirInward, "dirInward");
		_origin = coordsOrigin;
		_szZone = szZone;
		_direction = dirInward;
	}

	@Override
	public S2Coords getOrigin() {
		return _origin;
	}

	public S2Dimension getZoneSize() {
		return _szZone;
	}
	
	public S2Direction getInwardDirection() {
		return _direction;
	}
	
	public S2Rect getZoneBox() {
		return new S2Rect(_origin, _szZone);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Zone [");
		builder.append(_origin.toEnclosedString());
		builder.append(", ");
		builder.append(_szZone);
		builder.append(", inward=");
		builder.append(_direction);
		builder.append("]");
		return builder.toString();
	}
}
