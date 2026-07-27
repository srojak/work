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

import srojak.spatial.S2Dimension;

/**
 * @author Stephen
 *
 */
public class PlaneSquareZoneDefn {
	private final S2Dimension _szZone;
	private final S2Dimension _zones;
	
	public PlaneSquareZoneDefn(S2Dimension szZone, int nZonesHorizontal, int nZonesVertical) {
		Objects.requireNonNull(szZone, "szZone");
		if (nZonesHorizontal <= 0) {
			throw new IllegalArgumentException("nZonesHorizontal must be positive");
		}
		if (nZonesVertical <= 0) {
			throw new IllegalArgumentException("nZonesVertical must be positive");
		}
		_szZone = szZone;
		_zones = new S2Dimension(nZonesHorizontal, nZonesVertical);
	}
	
	public S2Dimension getZoneSize() {
		return _szZone;
	}
	
	public S2Dimension getMapZones() {
		return _zones;
	}
}
