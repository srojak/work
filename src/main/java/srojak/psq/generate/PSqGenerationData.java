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
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.numerics.IRandomSource;
import srojak.psq.PlaneSquareZone;
import srojak.psq.PlaneSquareZoneDefn;
import srojak.spatial.S2CompassDirection;
import srojak.spatial.S2Coords;
import srojak.spatial.S2Dimension;
import srojak.spatial.S2Direction;
import srojak.spatial.S2FieldSize;
import srojak.spatial.S2SymbolicDirection;

/**
 * @author Stephen
 *
 */
public class PSqGenerationData {
	private final ArrayList<PlaneSquareZone> _listZones;
	private PlaneSquareZoneDefn _zones;
	private int _marginEdge;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PSqGenerationData.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public PSqGenerationData() {
		_listZones = new ArrayList<PlaneSquareZone>();
		_zones = null;
		_marginEdge = 0;
	}
	
	public List<PlaneSquareZone> getZones() {
		return _listZones;
	}
	
	public PlaneSquareZone getRandomZone(IRandomSource rsource) {
		Objects.requireNonNull(rsource, "rsource");
		return _listZones.get(rsource.genIntInRange(_listZones.size()));
	}
	
	public int getEdgeMargin() {
		return _marginEdge;
	}
	
	public void setEdgeMargin(int nMargin) {
		if (nMargin < 0) {
			throw new IllegalArgumentException("margin cannot be negative");
		}
		_marginEdge = nMargin;
	}
	
	public PlaneSquareZoneDefn getZoneDefinition() {
		if (_zones == null) {
			throw new IllegalStateException("value for zone definition has never been set");
		}
		return _zones;
	}
	
	public void setZoneDefinition(PlaneSquareZoneDefn defn) {
		Objects.requireNonNull(defn, "defn");
		_zones = defn;
		S2Dimension szMap = _zones.getMapZones();
		_listZones.ensureCapacity(szMap.getArea());
	}
	
	private void layoutZones(S2Dimension szMap, S2Dimension szZone) {
		S2Coords coordsNW = new S2Coords(_marginEdge, _marginEdge);
		int rowLast = szMap.height - 1;
		int columnLast = szMap.width - 1;
		for (int i = 0; i <= columnLast; i++) {
			for (int j = 0; j <= rowLast; j++) {
				S2Direction dirMove = S2SymbolicDirection.Any;
				if (i == 0) {
					if (j == 0) {
						dirMove = S2CompassDirection.SouthEast;
					} else if (j == rowLast) {
						dirMove = S2CompassDirection.NorthEast;
					} else {
						dirMove = S2CompassDirection.East;
					}
				} else if (i == columnLast) {
					if (j == 0) {
						dirMove = S2CompassDirection.SouthWest;
					} else if (j == rowLast) {
						dirMove = S2CompassDirection.NorthWest;
					} else {
						dirMove = S2CompassDirection.West;
					}
				} else {
					if (j == 0) {
						dirMove = S2CompassDirection.South;
					} else if (j == rowLast) {
						dirMove = S2CompassDirection.North;
					}
				}
				S2Coords coordsOrigin = coordsNW.getOffsetCoords(i * szZone.width, j * szZone.height);
				PlaneSquareZone zone = new PlaneSquareZone(coordsOrigin, szZone, dirMove);
				_swDebugClass.write(ObsLevel.DEBUG, () -> "zone " + zone);
				_listZones.add(zone);
			}
		}
	}
	
	public S2FieldSize computeOverallSize() {
		if (_zones == null) {
			throw new IllegalStateException("value for zone definition has never been set");
		}
		S2Dimension szMap = _zones.getMapZones();
		S2Dimension szZone = _zones.getZoneSize();
		S2FieldSize szField = new S2FieldSize((_marginEdge << 1) + szMap.width * szZone.width,
				(_marginEdge << 1) + szMap.height * szZone.height);
		// find all the zones
		layoutZones(szMap, szZone);
		return szField;
	}
}
