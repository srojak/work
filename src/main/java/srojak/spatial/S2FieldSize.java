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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class S2FieldSize
		extends S2Dimension {
	
	@SuppressWarnings("unused")
	private static final DebugSwitch swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(S2FieldSize.class));
	}
	
	public S2FieldSize(int nWidth, int nHeight) {
		super(nWidth, nHeight);
	}
	
	private boolean isValueInBounds(int bound, int value) {
		return value >= 0 && value < bound;
	}
	
	private int boundValue(int bound, int value) {
		if (value < 0)
			return 0;
		else if (value >= bound)
			return bound - 1;
		else
			return value;
	}
	
	public boolean isInBounds(int x, int y) {
		return isValueInBounds(width, x) && isValueInBounds(height, y);
	}
	
	public boolean isInBounds(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		return isValueInBounds(width, coords._x) && isValueInBounds(height, coords._y);
	}
	
	public S2Coords addBounded(S2Coords coords, S2Offset offset) {
		Objects.requireNonNull(coords, "coords");
		Objects.requireNonNull(offset, "offset");
		return new S2Coords(boundValue(width, coords._x + offset.dx),
				boundValue(height, coords._y + offset.dy));
	}
	
	public S2Coords subtractBounded(S2Coords coords, S2Offset offset) {
		Objects.requireNonNull(coords, "coords");
		Objects.requireNonNull(offset, "offset");
		return new S2Coords(boundValue(width, coords._x - offset.dx),
				boundValue(height, coords._y - offset.dy));
	}
	
	public List<S2UnitRay> getUnitRaysInBoundsFor(S2Orientation orientation, 
			S2Coords coords, boolean bAllowDiagonals)
			throws InvalidLocationException {
		Objects.requireNonNull(orientation, "orientation");
		Objects.requireNonNull(coords, "coords");
		if (!isInBounds(coords)) {
			throw new InvalidLocationException(coords, "starting point is not valid");
		}
		List<S2CompassDirection> listDirections =
				bAllowDiagonals ? S2CompassDirection.AllDirs : S2CompassDirection.CardinalDirs;
		ArrayList<S2UnitRay> list = new ArrayList<S2UnitRay>(listDirections.size());
		for (S2CompassDirection direction : listDirections) {
			S2UnitRay ray = new S2UnitRay(coords, direction);
			S2Coords coordEnd = ray.computeEndpoint(orientation);
			if (isInBounds(coordEnd)) {
				list.add(ray);
			}
		}
		list.trimToSize();
		return list;
	}

	@Override
	protected Object clone() 
			throws CloneNotSupportedException {
		return new S2FieldSize(width, height);
	}
}
