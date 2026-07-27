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
package srojak.mantle;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public final class ListIndexRange {
	private final int _szList;
	private final int _nFirst;
	private final int _nLast;

	public ListIndexRange(int szList, int first, int last) {
		if (szList < 0) {
			throw new IllegalArgumentException("szList cannot be negative");
		}
		if (first > last) {
			throw new IllegalArgumentException("first is greater than last");
		}
		if (first >= szList) {
			throw new IllegalArgumentException("first is too large for source list");
		}
		if (last >= szList) {
			throw new IllegalArgumentException("last is too large for source list");
		}
		_szList = szList;
		_nFirst = first;
		_nLast = last;
	}
	
	public int getListSize() {
		return _szList;
	}
	
	public int getFirst() {
		return _nFirst;
	}

	public int getLast() {
		return _nLast;
	}
	
	public boolean isListEmpty() {
		return _szList == 0;
	}
	
	public boolean isSelectionEmpty() {
		return _nFirst < 0;
	}
	
	public boolean isWithinRange(int value) {
		return value >= _nFirst && value <= _nLast;
	}
	
	public boolean isAtStartOfList() {
		return _nFirst == 0;
	}
	
	public boolean isAtEndOfList() {
		return _nLast == (_szList - 1);
	}

	@Override
	public int hashCode() {
		return Objects.hash(_szList, _nFirst, _nLast);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof ListIndexRange other) {
			return _szList == other._szList && _nFirst == other._nFirst && _nLast == other._nLast;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "list size=" + _szList + ", selection[" + _nFirst + ", " + _nLast + "]";
	}
}
