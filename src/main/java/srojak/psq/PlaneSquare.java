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

import srojak.core.logic.FlagsInt;
import srojak.spatial.S2Coords;
import srojak.spatial.S2CoordsBearing;

/**
 * @author Stephen
 *
 */
public class PlaneSquare
		implements S2CoordsBearing {
	protected final S2Coords _coords;
	protected final FlagsInt _flags;
	
	public PlaneSquare(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		_coords = coords;
		_flags = new FlagsInt();
	}

	@Override
	public S2Coords getCoords() {
		return _coords;
	}
	
	public void buildSquareInfo(StringBuilder sb) {
		sb.append("coords ");
		sb.append(_coords.toEnclosedString());
	}

	public String getSquareInfo() {
		StringBuilder sb = new StringBuilder();
		buildSquareInfo(sb);
		return sb.toString();
	}
	
	public boolean canBeOccupied() {
		return true;
	}
}
