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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public class S2FieldCalcs {
	private final S2FieldSize _field;	
	
	private static final DebugSwitch swDebugClass;
	
	public S2FieldCalcs(S2FieldSize sizeField) {
		Objects.requireNonNull(sizeField, "sizeField");
		_field = sizeField;		
	}
	
	static {
		DebugNexus debug = new DebugNexus();
		swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(S2FieldSize.class));
	}
	
	public S2Rect getRectangleCenteredOn(S2Coords coordsCenter, int nRadius) {
		Objects.requireNonNull(coordsCenter, "coordsCenter");
		if (nRadius < 1) {
			throw new IllegalArgumentException("nRadius must be at least 1");
		}
		swDebugClass.writeTraceEnter(TraceLevel.HIGH,
				() -> "center=" + coordsCenter + ", radius=" + nRadius);
		S2MovableCoords coordsTopLeft = new S2MovableCoords(coordsCenter._x - nRadius,
				coordsCenter._y - nRadius);
		coordsTopLeft.moveInBounds(_field);
		S2MovableCoords coordsLowRight = new S2MovableCoords(coordsCenter._x + nRadius + 1,
				coordsCenter._y + nRadius + 1);
		coordsLowRight.moveInBounds(_field);
		S2Offset offset = coordsTopLeft.getOffsetTo(coordsLowRight);
		S2Rect rectAround = new S2Rect(new S2Coords(coordsTopLeft), offset);
		swDebugClass.write(ObsLevel.DEBUG, "rect around = " + rectAround);
		return rectAround;
	}
	
	public List<S2Coords> getAllPointsInCircle(S2Coords coordsCenter, double dRadius) {
		Objects.requireNonNull(coordsCenter, "coordsCenter");
		final DoublePrecisionComparer comparerDbl = DoublePrecisionComparer.DEFAULT_COMPARER;
		if (comparerDbl.compare(dRadius, OrderedComparison.LT, 1.0)) {
			throw new IllegalArgumentException("dRadius must be at least 1.0");
		}
		double dRadiusSq = dRadius * dRadius;
		// limit the set of points
		S2Rect rectAround = getRectangleCenteredOn(coordsCenter, (int) Math.ceil(dRadius));
		List<S2Coords> list = new LinkedList<S2Coords>();
		for (S2Coords coords : rectAround.getAllPoints()) {
			S2Offset offset = coordsCenter.getOffsetTo(coords);
			if (comparerDbl.compare((double) offset.getDistanceSquare(),
					OrderedComparison.LE, dRadiusSq)) {
				list.add(coords);
			}
		}
		return list;
	}
}
