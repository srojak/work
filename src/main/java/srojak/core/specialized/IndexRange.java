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
package srojak.core.specialized;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public final class IndexRange {
	private final int _nFirst;
	private final int _nLast;
	
	/**
	 * 
	 */
	public IndexRange(int first, int last) {
		if (first > last) {
			throw new IllegalArgumentException("first is greater than last");
		}
		_nFirst = first;
		_nLast = last;
	}
	
	public int getFirst() {
		return _nFirst;
	}

	public int getLast() {
		return _nLast;
	}
	
	public boolean isWithin(int value) {
		return value >= _nFirst && value <= _nLast;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_nFirst, _nLast);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof IndexRange other) {
			return _nFirst == other._nFirst && _nLast == other._nLast;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "range[" + _nFirst + ", " + _nLast + "]";
	}
}
