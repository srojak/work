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

import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class S2FieldSize {
	public final int width;
	public final int height;
	
	@SuppressWarnings("unused")
	private static final DebugSwitch swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(S2FieldSize.class));
	}
	
	public S2FieldSize(int nWidth, int nHeight) {
		if (nWidth <= 0) {
			throw new IllegalArgumentException("nWidth");
		}
		if (nHeight <= 0) {
			throw new IllegalArgumentException("nHeight");
		}
		width = nWidth;
		height = nHeight;
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

	@Override
	public int hashCode() {
		return Objects.hash(width, height);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof S2FieldSize other) {
			return width == other.width && height == other.height;
		}
		else
			return false;
	}

	@Override
	protected Object clone() 
			throws CloneNotSupportedException {
		return new S2FieldSize(width, height);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("MapSize(");
		sb.append(width);
		sb.append(", ");
		sb.append(height);
		sb.append(')');
		return sb.toString();
	}
}
