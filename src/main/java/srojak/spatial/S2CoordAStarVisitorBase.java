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

import java.util.Iterator;
import java.util.List;

import srojak.core.CycleDepthException;
import srojak.core.observe.ObsLevel;
import srojak.debug.DebugSwitch;

/**
 * @author Stephen
 *
 * A path finder that searches around each square progressively, finding the square that moves closest
 * 		to the ultimate destination.
 *
 * @see https://en.wikipedia.org/wiki/A*_search_algorithm
 */
public abstract class S2CoordAStarVisitorBase<A> 
		extends S2CoordVisitorBase {

	/**
	 * @param swDebug
	 * @param surface
	 */
	protected S2CoordAStarVisitorBase(DebugSwitch swDebug, S2Surface surface) {
		super(swDebug, surface);
	}
	
	protected boolean isLocationEligible(S2Coords coords) {
		return true;
	}
	
	protected abstract void visitLocation(int nSequence, S2UnitRay ray, A arg)
			throws InvalidLocationException;

	
	protected void walk(A arg, boolean bAllowDiagonal)
			throws InvalidLocationException {
		if (isOffsetToZero()) {
			return;
		}
		S2Coords coordsFrom = getCoordsFrom();
		_swDebug.write(ObsLevel.DEBUG, "starting at " + coordsFrom.toEnclosedString());
		int nStop = _szField.height + _szField.width;
		int nSequence = 0;
		boolean bDone = false;
		while (!bDone) {
			List<S2UnitRay> listRays 
				= _szField.getUnitRaysInBoundsFor(_orientation, coordsFrom, bAllowDiagonal);
			S2UnitRay rayBest = null;
			double dBestDistance = Double.MAX_VALUE;
			Iterator<S2UnitRay> iterator = listRays.iterator();
			loopRays:
			while (iterator.hasNext()) {
				S2UnitRay ray = iterator.next();
				S2Coords coordsEnd = ray.findEndpoint(_orientation);
				if (!isLocationEligible(coordsEnd)) {
					continue loopRays;
				}
				S2Offset offsetToEnd = getOffsetToEndpoint(coordsEnd);
				double dDistance = offsetToEnd.getDistance();
				if (dDistance < dBestDistance) {
					rayBest = ray;
					dBestDistance = dDistance;
				}
			}
			if (rayBest == null) {
				_swDebug.buildAndWrite(ObsLevel.ERROR, 
						DebugSwitch.passThrough(coordsFrom.toEnclosedString()),
						(sb, pt) -> {
							sb.append("cannot find a best unit ray from ");
							sb.append(pt.get(0));
						});
				throw new InvalidLocationException(coordsFrom, "cannot find best move from here");
			}
			visitLocation(nSequence++, rayBest, arg);
			if (nSequence > nStop) {
				String strMessage = "not converging after " + nStop + " visits";
				_swDebug.write(ObsLevel.ERROR, strMessage);
				throw new CycleDepthException(strMessage);
			}
			S2Coords coordsRayEnd = rayBest.findEndpoint(_orientation);
			if (isEqualCoordsTo(coordsRayEnd)) {
				bDone = true;
			} else {
				coordsFrom = coordsRayEnd;
			}
		}
	}
}
