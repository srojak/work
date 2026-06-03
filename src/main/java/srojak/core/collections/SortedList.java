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
package srojak.core.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.ToIntFunction;

/**
 * @author Stephen
 *
 */
public class SortedList<E>
		implements List<E> {
	private final TypedArrayList<E> _list;
	private final Comparator<E> _comparer;
	
	/**
	 * 
	 * @param comparer
	 */
	public SortedList(Class<E> classElement, Comparator<E> comparer) {
		Objects.requireNonNull(comparer, "comparer");
		_list = new TypedArrayList<E>(classElement);
		_comparer = comparer;
	}
	
	/**
	 * 
	 * @param comparer
	 * @param c
	 */
	public SortedList(Class<E> classElement, Comparator<E> comparer, Collection<? extends E> c) {
		Objects.requireNonNull(comparer, "comparer");
		_list = new TypedArrayList<E>(classElement, c);
		_comparer = comparer;
		_list.sort(_comparer);
	}
	
	public int findIndex(E key) {
		return Collections.binarySearch(_list, key, _comparer);
	}
	
	public E find(E key) {
		Objects.requireNonNull(key, "key");
		int index = findIndex(key);
		return index >= 0 ? _list.get(index) : null;
	}
	
	public E find(ToIntFunction<E> fnMatch) {
		Objects.requireNonNull(fnMatch, "fnMatch");
		int idxLow = 0;
		int idxHigh = _list.size() - 1;
		while (idxLow <= idxHigh) {
			int idx = idxLow + (idxHigh - idxLow) >> 1;
			E item = _list.get(idx);
			int nResult = fnMatch.applyAsInt(item);
			if (nResult == 0) {
				return item;
			} else if (nResult < 0) {
				idxLow = idx + 1;
			} else {
				idxHigh = idx - 1;
			}
		}
		return null;
	}
	
	@Override
	public int size() {
		return _list.size();
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		if (o == null || _list.isEmpty()) {
			return false;
		}
		if (_list.isElementAssignableFrom(o.getClass())) {
			@SuppressWarnings("unchecked")
			E other = (E)o;
			return findIndex(other) >= 0;
		} else {
			return _list.contains(o);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new ReadOnlyIteratorFacade<E>(_list.iterator());
	}

	@Override
	public Object[] toArray() {
		return _list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return _list.toArray(a);
	}

	@Override
	public boolean add(E e) {
		int index = addAndGetNewIndex(e);
		return index >= 0;
	}
	
	public int addAndGetNewIndex(E e) {
		Objects.requireNonNull(e);
		int index = findIndex(e);
		if (index >= 0) {
			// already exists
			return -1;
		} else {
			index = -(index + 1);
			_list.add(index, e);
			return index;
		}
	}

	@Override
	public boolean remove(Object o) {
		return _list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if (c == null || _list.isEmpty()) {
			return false;
		}
		for (Object co : c) {
			if (_list.isElementAssignableFrom(co.getClass())) {
				@SuppressWarnings("unchecked")
				E other = (E)co;
				if (findIndex(other) < 0) {
					return false;
				}
			} else {
				if (!_list.contains(co)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean bResult = _list.addAll(c);
		if (bResult) {
			_list.sort(_comparer);
		}
		return bResult;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("indexed addAll is not supported");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return _list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return _list.retainAll(c);
	}

	@Override
	public void clear() {
		_list.clear();
	}

	@Override
	public E get(int index) {
		return _list.get(index);
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException("set is not supported");
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("indexed add is not supported");
	}

	@Override
	public E remove(int index) {
		return _list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		if (o == null || _list.isEmpty()) {
			return -1;
		}
		if (_list.isElementAssignableFrom(o.getClass())) {
			@SuppressWarnings("unchecked")
			E other = (E)o;
			return findIndex(other);
		} else {
			return _list.indexOf(o);
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		if (o == null || _list.isEmpty()) {
			return -1;
		}
		if (_list.isElementAssignableFrom(o.getClass())) {
			@SuppressWarnings("unchecked")
			E other = (E)o;
			return findIndex(other);
		} else {
			return _list.lastIndexOf(o);
		}
	}

	@Override
	public ListIterator<E> listIterator() {
		return new ReadOnlyListIteratorFacade<E>(_list.listIterator());
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new ReadOnlyListIteratorFacade<E>(_list.listIterator(index));
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return _list.subList(fromIndex, toIndex);
	}

	public void resync() {
		_list.sort(_comparer);
	}
}
