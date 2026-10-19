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
package srojak.mantle.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import srojak.core.collections.OwnerReferenceList;
import srojak.core.events.ListChangeVerbEvent;
import srojak.core.events.ListChangeVerbListener;
import srojak.mantle.ListChangeListening;

/**
 * @author Stephen
 *
 */
public class ChangeEventingListFacade<E> 
		implements ListChangeListening, List<E> {
	private OwnerReferenceList<ListChangeVerbListener> _listeners;
	private List<E> _list;
	
	public ChangeEventingListFacade(List<E> list) {
		Objects.requireNonNull(list, "list");
		_listeners = new OwnerReferenceList<ListChangeVerbListener>();		
		_list = list;
	}

	private void raiseChangeEvent(int nVerb) {
		ListChangeVerbEvent event = new ListChangeVerbEvent(this, nVerb);
		_listeners.forEach(ls -> ls.listChanged(event));
	}

	private void raiseChangeEvent(int nVerb, Object objItem) {
		ListChangeVerbEvent event = new ListChangeVerbEvent(this, nVerb, objItem);
		_listeners.forEach(ls -> ls.listChanged(event));
	}
	
	public void forEach(Consumer<? super E> action) {
		_list.forEach(action);
	}
	
	public int size() {
		return _list.size();
	}
	
	public boolean isEmpty() {
		return _list.isEmpty();
	}
	
	public boolean contains(Object o) {
		return _list.contains(o);
	}
	
	public Iterator<E> iterator() {
		return new VisibleIterator<E>(_list.iterator());
	}
	
	public Object[] toArray() {
		return _list.toArray();
	}
	
	public <T> T[] toArray(T[] a) {
		return _list.toArray(a);
	}
	
	public boolean add(E e) {
		if (_list.add(e)) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, e);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean remove(Object o) {
		if (_list.remove(o)) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, o);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean containsAll(Collection<?> c) {
		return _list.containsAll(c);
	}
	
	public boolean addAll(Collection<? extends E> c) {
		boolean bResult = _list.addAll(c);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_ADD_MULT);
		}
		return bResult;
	}
	
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean bResult = _list.addAll(index, c);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_ADD_MULT);
		}
		return bResult;
	}
	
	public boolean removeAll(Collection<?> c) {
		boolean bResult = _list.removeAll(c);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE_MULT);
		}
		return bResult;
	}
	
	public boolean retainAll(Collection<?> c) {
		boolean bResult = _list.retainAll(c);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE_MULT);
		}
		return bResult;
	}
	
	public void replaceAll(UnaryOperator<E> operator) {
		_list.replaceAll(operator);
		raiseChangeEvent(ListChangeVerbEvent.VERB_REPLACE_MULT);
	}
	
	public <T> T[] toArray(IntFunction<T[]> generator) {
		return _list.toArray(generator);
	}
	
	public void sort(Comparator<? super E> c) {
		_list.sort(c);
		raiseChangeEvent(ListChangeVerbEvent.VERB_SORT);
	}
	
	public void clear() {
		_list.clear();
		raiseChangeEvent(ListChangeVerbEvent.VERB_CLEAR);
	}
	
	public boolean equals(Object o) {
		return _list.equals(o);
	}
	
	public int hashCode() {
		return _list.hashCode();
	}
	
	public E get(int index) {
		return _list.get(index);
	}
	
	public E set(int index, E element) {
		E elementWas = _list.set(index, element);
		raiseChangeEvent(ListChangeVerbEvent.VERB_SET_ITEM, element);
		return elementWas;
	}
	
	public void add(int index, E element) {
		_list.add(index, element);
		raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, element);
	}
	
	public boolean removeIf(Predicate<? super E> filter) {
		boolean bResult = _list.removeIf(filter);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE_MULT);
		}
		return bResult;
	}
	
	public E remove(int index) {
		E element = _list.remove(index);
		if (element != null) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, element);
		}
		return element;
	}
	
	public int indexOf(Object o) {
		return _list.indexOf(o);
	}
	
	public int lastIndexOf(Object o) {
		return _list.lastIndexOf(o);
	}
	
	public ListIterator<E> listIterator() {
		return new VisibleListIterator<E>(_list.listIterator());
	}
	
	public ListIterator<E> listIterator(int index) {
		return new VisibleListIterator<E>(_list.listIterator(index));
	}
	
	public List<E> subList(int fromIndex, int toIndex) {
		return _list.subList(fromIndex, toIndex);
	}
	
	public Spliterator<E> spliterator() {
		return _list.spliterator();
	}
	
	public Stream<E> stream() {
		return _list.stream();
	}
	
	public Stream<E> parallelStream() {
		return _list.parallelStream();
	}

	@Override
	public void addChangeListener(Object owner, ListChangeVerbListener listener) {
		_listeners.add(owner, listener);
	}

	@Override
	public boolean removeChangeListener(Object owner, ListChangeVerbListener listener) {
		return _listeners.remove(owner, listener);
	}

	@Override
	public boolean removeChangeListeners(Object owner) {
		return _listeners.removeOwned(owner);
	}
	
	private class VisibleIterator<T>
			implements Iterator<T> {
		private final Iterator<T> _inner;
		private T _last;
		
		VisibleIterator(Iterator<T> inner) {
			_inner = inner;
			_last = null;
		}

		public boolean hasNext() {
			return _inner.hasNext();
		}

		public T next() {
			_last = _inner.next();
			return _last;
		}

		public void remove() {
			_inner.remove();
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, _last);
			_last = null;
		}

		public void forEachRemaining(Consumer<? super T> action) {
			_inner.forEachRemaining(action);
		}
	}
	
	private class VisibleListIterator<T>
			implements ListIterator<T> {
		private final ListIterator<T> _inner;
		private T _last;
		
		VisibleListIterator(ListIterator<T> inner) {
			_inner = inner;
			_last = null;
		}

		public boolean hasNext() {
			return _inner.hasNext();
		}

		public T next() {
			_last = _inner.next();
			return _last;
		}

		public boolean hasPrevious() {
			return _inner.hasPrevious();
		}

		public void forEachRemaining(Consumer<? super T> action) {
			_inner.forEachRemaining(action);
		}

		public T previous() {
			_last = _inner.previous();
			return _last;
		}

		public int nextIndex() {
			return _inner.nextIndex();
		}

		public int previousIndex() {
			return _inner.previousIndex();
		}

		public void remove() {
			_inner.remove();
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, _last);
			_last = null;
		}

		public void set(T e) {
			_inner.set(e);
			raiseChangeEvent(ListChangeVerbEvent.VERB_SET_ITEM, e);
		}

		public void add(T e) {
			_inner.add(e);
			raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, e);
		}
	}
}
