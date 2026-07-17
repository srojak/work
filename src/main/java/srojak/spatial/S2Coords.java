/**
 * 
 */
package srojak.spatial;

import java.util.EnumSet;
import java.util.Objects;

import srojak.core.tools.StringMethods;
import srojak.numerics.IRandomSource;
import srojak.numerics.RadiansMethods;

/**
 * @author Stephen
 *
 */
public class S2Coords {
	protected int _x;
	protected int _y;
	
	public S2Coords(int x, int y, boolean bValidating) {
		if (bValidating) {
			if (x < 0) {
				throw new IllegalArgumentException("negative x");
			}
			if (y < 0) {
				throw new IllegalArgumentException("negative y");
			}
		}
		_x = x;
		_y = y;
		
	}
	
	public S2Coords(int x, int y) {
		this(x, y, false);
	}
	
	public S2Coords(S2Coords coordsCopy) {
		Objects.requireNonNull(coordsCopy, "coordsCopy");
		_x = coordsCopy._x;
		_y = coordsCopy._y;
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
	public R2Coords getR2Coords() {
		return new R2Coords((double) _x, (double) _y);
	}
	
	public S2Coords getOffsetCoords(int dx, int dy) {
		return new S2Coords(_x + dx, _y + dy);
	}
	
	public S2Coords getOffsetCoords(S2Offset offset) {
		Objects.requireNonNull(offset, "offset");
		return new S2Coords(_x + offset.dx, _y + offset.dy);
	}
	
	public boolean isInBounds(S2FieldSize szMap) {
		Objects.requireNonNull(szMap, "szMap");
		return (_x >= 0 && _y >= 0 && _x < szMap.width && _y < szMap.height);
	}
	
	public S2Offset getOffsetTo(S2Coords coordsTo) {
		Objects.requireNonNull(coordsTo, "coordsTo");
		return new S2Offset(coordsTo._x - _x, coordsTo._y - _y);
	}
	
	public double getDistanceTo(S2Coords coordsTo) {
		S2Offset offset = getOffsetTo(coordsTo);
		return offset.getDistance();
	}
	
	public boolean isAdjacentTo(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		return (Math.abs(coords._x - _x) <= 1 && Math.abs(coords._y - _y) <= 1);
	}
	
	public S2Direction getDirectionTo(S2Orientation orientation, S2Coords coords) {
		Objects.requireNonNull(orientation, "orientation");
		Objects.requireNonNull(coords, "coords");
		S2Offset offset = getOffsetTo(coords);
		return orientation.findDirection(offset);
	}
	
	public S2Coords getNewLocationFrom(S2Offset offset) {
		Objects.requireNonNull(offset, "offset");
		return new S2Coords(_x + offset.dx, _y + offset.dy);
	}
	
	public S2Rect getRectangleCenteredOn(int nRadius) {
		if (nRadius < 1) {
			throw new IllegalArgumentException("nRadius must be at least 1");
		}
		S2Coords coordsTopLeft = new S2Coords(_x - nRadius,	_y - nRadius);
		int nSide = (nRadius << 1) + 1;
		S2Offset offset = new S2Offset(nSide, nSide);
		return new S2Rect(coordsTopLeft, offset);
		
	}
	
	public S2Coords generateRandomPointInCircle(IRandomSource random, double dRadius) {
		PolarCoords ptPolar = R2Geometry.generateRandomPointInCircle(random, dRadius, 0.0d);
		S2Offset offset = S2Geometry.polarToOffset(ptPolar);
		return getNewLocationFrom(offset);
	}
	
	public S2Coords generateRandomPointInCircle(IRandomSource random, int nRadius) {
		PolarCoords ptPolar = R2Geometry.generateRandomPointInCircle(random, (double) nRadius, 0.0d);
		S2Offset offset = S2Geometry.polarToOffset(ptPolar);
		return getNewLocationFrom(offset);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(_x, _y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof S2Coords other) {
			return _x == other._x && _y == other._y;
		}
		return false;
	}

	@Override
	protected Object clone() 
			throws CloneNotSupportedException {
		return new S2Coords(_x, _y);
	}
	
	protected String toString(boolean bLabled, boolean bEnclosed) {
		StringBuilder sb = new StringBuilder();
		if (bEnclosed) {
			sb.append("(");
		}
		if (bLabled) {
			sb.append("x=");
		}
		sb.append(_x);
		sb.append(", ");
		if (bLabled) {
			sb.append("y=");
		}
		sb.append(_y);
		if (bEnclosed) {
			sb.append(')');		
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return toString(false, false);
	}
	
	public String toString(EnumSet<S2Formats> formats) {
		return toString(formats.contains(S2Formats.LABELED),
				formats.contains(S2Formats.ENCLOSED));
	}
	
	public String toEnclosedString() {
		return toString(false, true);
	}
	
	private static S2Coords parsePair(String strOrig, String strInput) {
		String[] strTokens = strInput.split(",\\s*");
		if (strTokens.length != 2) {
			throw new NumberFormatException("For input string: "
					+ StringMethods.encloseInQuotes(strInput));
		}
		int x;
		int y;
		try {
			x = Integer.parseInt(strTokens[0]);
		} catch (NumberFormatException exc) {
			throw new NumberFormatException("For input string: "
					+ StringMethods.encloseInQuotes(strInput)
					+ " x value");
		}
		try {
			y = Integer.parseInt(strTokens[1]);
		} catch (NumberFormatException exc) {
			throw new NumberFormatException("For input string: "
					+ StringMethods.encloseInQuotes(strInput)
					+ " y value");
		}
		return new S2Coords(x, y);		
	}
	
	public static S2Coords parse(String strInput) {
		Objects.requireNonNull(strInput, "strInput");
		return parsePair(strInput, strInput);
	}
	
	public static S2Coords parseEnclosed(String strInput) {
		Objects.requireNonNull(strInput, "strInput");
		int idxLast = strInput.length() - 1;
		if (strInput.charAt(0) == '(' && strInput.charAt(idxLast) == ')') {
			return parsePair(strInput, strInput.substring(1, idxLast));
		} else {
			throw new NumberFormatException("For input string: "
					+ StringMethods.encloseInQuotes(strInput));
		}
	}
}
