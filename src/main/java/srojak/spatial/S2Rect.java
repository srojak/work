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
import java.util.function.Consumer;
import java.util.function.Predicate;

import srojak.core.tools.ArrayMethods;

/**
 * @author Stephen
 *
 */
public class S2Rect {
	private final S2Coords _coordsStart;
	private int _width;
	private int _height;
	
	public S2Rect(S2Coords coordsTopLeft, int nWidth, int nHeight) {
		Objects.requireNonNull(coordsTopLeft, "coordsTopLeft");
		checkSizeArgs(nWidth, nHeight);
		_coordsStart = new S2Coords(coordsTopLeft);
		_width = nWidth;
		_height = nHeight;
	}
	
	public S2Rect(int xLeft, int yTop, int nWidth, int nHeight) {
		checkSizeArgs(nWidth, nHeight);
		_coordsStart = new S2Coords(xLeft, yTop);
		_width = nWidth;
		_height = nHeight;
	}
	
	public S2Rect(S2Coords coordsTopLeft, S2FieldSize szRect) {
		Objects.requireNonNull(coordsTopLeft, "coordsTopLeft");
		_coordsStart = new S2Coords(coordsTopLeft);
		_width = szRect.width;
		_height = szRect.height;
	}
	
	public S2Rect(S2Coords coordsTopLeft, S2Offset offset) {
		Objects.requireNonNull(coordsTopLeft, "coordsTopLeft");
		Objects.requireNonNull(offset, "offset");
		_coordsStart = new S2Coords(coordsTopLeft);
		_width = offset.dx;
		_height = offset.dy;
	}
	
	public S2Rect(S2Rect rectCopy) {
		Objects.requireNonNull(rectCopy, "rectCopy");
		_coordsStart = rectCopy._coordsStart;
		_width = rectCopy._width;
		_height = rectCopy._height;
	}
	
	public S2Coords getOrigin() {
		return _coordsStart;
	}
	
	public S2FieldSize getSize() {
		return new S2FieldSize(_width, _height);
	}
	
	public boolean isEmpty() {
		return _width == 0 && _height == 0;
	}
	
	public boolean contains(int x, int y) {
		return x >= _coordsStart._x && y >= _coordsStart._y
				&& x < _coordsStart._x + _width
				&& y < _coordsStart._y + _height;				
	}
	
	public boolean contains(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		return contains(coords._x, coords._y);
	}
	
	public int getWidth() {
		return _width;
	}
	
	public int getHeight() {
		return _height;
	}
	
	public void setBounds(int nWidth, int nHeight) {
		checkSizeArgs(nWidth, nHeight);
		_width = nWidth;
		_height = nHeight;
	}
	
	public S2Coords getMidPoint() {
		return _coordsStart.getOffsetCoords(_width >> 1, _height >> 1);
	}
	
	public S2Coords getNECorner() {
		return _coordsStart.getOffsetCoords(_width - 1, 0);
	}
	
	public S2Coords getSWCorner() {
		return _coordsStart.getOffsetCoords(0, _height - 1);
	}
	
	public S2Coords getSECorner() {
		return _coordsStart.getOffsetCoords(_width - 1, _height - 1);
	}
	
	public List<S2Coords> getAllPoints() {
		LinkedList<S2Coords> list = new LinkedList<S2Coords>();
		this.overAll(c -> {
			list.add(c);
		});
		return list;
	}
	
	public List<S2Coords> getAllPointsExcept(S2Coords ... coords) {
		LinkedList<S2Coords> list = new LinkedList<S2Coords>();
		this.overAll(c -> {
			if (!ArrayMethods.equalsAny(coords, c)) {
				list.add(c);
			}
		});
		return list;
	}
	
	public List<S2Coords> getAllPointsWhere(Predicate<S2Coords> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		LinkedList<S2Coords> list = new LinkedList<S2Coords>();
		this.overAll(c -> {
			if (predicate.test(c)) {
				list.add(c);
			}
		});
		return list;
	}
	
	public boolean overlaps(S2Rect other) {
		Objects.requireNonNull(other, "other");
		if (_width <= 0 || _height <= 0 || other._width <= 0 || other._height <= 0) {
			return false;
		}
		int w1 = _coordsStart._x + _width;
		int h1 = _coordsStart._y + _height;
		int w2 = other._coordsStart._x + other._width;
		int h2 = other._coordsStart._y + other._height;
		/* suspicious
        int tw = this.width + this.x;
        int th = this.height + this.y;
        int rw = r.width + r.x;
        int rh = r.height + r.y;
        int tx = this.x;
        int ty = this.y;
        int rx = r.x;
        int ry = r.y;
        //      overflow || intersect
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
		 */
		return ((w1 < _coordsStart._x || w1 > other._coordsStart._x)
				&& (h1 < _coordsStart._y || h1 > other._coordsStart._y)
				&& (w2 < other._coordsStart._x || w2 > _coordsStart._x)
				&& (h2 < other._coordsStart._y || h2 > _coordsStart._y));
	}
	
	public S2Rect intersection(S2Rect other) {
		Objects.requireNonNull(other, "other");
		int tx1 = _coordsStart._x;
		int ty1 = _coordsStart._y;
		int rx1 = other._coordsStart._x;
		int ry1 = other._coordsStart._y;
		long tx2 = (long) tx1 + _width;
		long ty2 = (long) ty1 + _height;
		long rx2 = (long) rx1 + other._width;
		long ry2 = (long) ry1 + other._height;
		if (tx1 < rx1) tx1 = rx1;
		if (ty1 < ry1) tx1 = ry1;
		if (tx2 > rx2) tx2 = rx2;
		if (ty2 > ry2) ty2 = ry2;
		tx2 -= tx1;
		ty2 -= ty2;
		if (tx2 < Integer.MIN_VALUE) tx2 = Integer.MIN_VALUE;
		if (ty2 < Integer.MIN_VALUE) ty2 = Integer.MIN_VALUE;
		return new S2Rect(tx1, ty1, (int) tx2, (int) ty2);
	}
	
	public S2Rect union(S2Rect other) {
		Objects.requireNonNull(other, "other");
		// handle a rectangle with negative dimensions
		if (_width < 0 || _height < 0) {
			return new S2Rect(other);
		}
		if (other._width < 0 || other._height < 0) {
			return new S2Rect(this);
		}
		int tx1 = _coordsStart._x;
		int ty1 = _coordsStart._y;
		long tx2 = (long) tx1 + _width;
		long ty2 = (long) ty1 + _height;
		int rx1 = other._coordsStart._x;
		int ry1 = other._coordsStart._y;
		long rx2 = (long) rx1 + _width;
		long ry2 = (long) ry1 + _height;
        if (tx1 > rx1) tx1 = rx1;
        if (ty1 > ry1) ty1 = ry1;
        if (tx2 < rx2) tx2 = rx2;
        if (ty2 < ry2) ty2 = ry2;
        tx2 -= tx1;
        ty2 -= ty1;
        if (tx2 > Integer.MAX_VALUE) tx2 = Integer.MAX_VALUE;
        if (ty2 > Integer.MAX_VALUE) ty2 = Integer.MAX_VALUE;
        return new S2Rect(tx1, ty1, (int) tx2, (int) ty2);
	}
	
	public void overAll(Consumer<S2Coords> visitor) {
		for (int i = 0; i < _width; i++) {
			for (int j = 0; j < _height; j++) {
				visitor.accept(_coordsStart.getOffsetCoords(i, j));
			}
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(_coordsStart, _height, _width);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof S2Rect other) {
			return Objects.equals(_coordsStart, other._coordsStart) 
					&& _height == other._height && _width == other._width;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("rect(");
		sb.append(_coordsStart);
		sb.append(", width=");
		sb.append(_width);
		sb.append(", height=");
		sb.append(_height);
		sb.append(')');
		return sb.toString();
	}
	
	private static void checkSizeArgs(int nWidth, int nHeight) {
		if (nWidth < 0)
			throw new IllegalArgumentException("nWidth negative");
		if (nHeight < 0)
			throw new IllegalArgumentException("nHeight negative");
	}
}
