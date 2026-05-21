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
 * 
 */
package srojak.core.collections;

import java.util.Objects;

import srojak.core.EmptyCollectionException;

/**
 * @author Stephen
 *
 */
public class CircularIndexer<T> {
	private final T[] _array;
	private int _indexCurrent;

	public CircularIndexer(T[] array) {
		Objects.requireNonNull(array, "array");
		if (array.length == 0) {
			throw new EmptyCollectionException("array");
		}
		_array = array;
		_indexCurrent = 0;
	}
	
	public CircularIndexer(T[] array, int indexStart) {
		Objects.requireNonNull(array, "array");
		if (array.length == 0) {
			throw new EmptyCollectionException("array");
		}
		Objects.checkIndex(indexStart, array.length);
		_array = array;
		_indexCurrent = indexStart;
	}
	
	private void prepareAdvance() {
		if (_indexCurrent >= _array.length) {
			_indexCurrent = 0;
		}
	}
	
	public int size() {
		return _array.length;
	}

	public void reset() {
		_indexCurrent = 0;
	}
	
	public void advance() {
		prepareAdvance();
		_indexCurrent++;
	}
	
	public T next() {
		prepareAdvance();
		return _array[_indexCurrent++];
	}
	
	public void setPosition(int nPos) {
		Objects.checkIndex(nPos, _array.length);
		_indexCurrent = nPos;
	}
	
	public void setToLastPosition() {
		_indexCurrent = _array.length - 1;	
	}
	
	public CircularIndexer<T> copy() {
		int indexStart = _indexCurrent;
		if (indexStart >= _array.length) {
			indexStart = 0;
		}
		return new CircularIndexer<T>(_array, indexStart);
	}
}
