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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import srojak.core.collections.OwnerReferenceList;
import srojak.core.events.ListChangeVerbEvent;
import srojak.core.events.ListChangeVerbListener;

/**
 * @author Stephen
 *
 */
public class ChangeEventingListRelay<E>
		implements ChangeEventingList<E> {
	private OwnerReferenceList<ListChangeVerbListener> _listeners;
	private ChangeRelay _relay;
	private ChangeEventingList<E> _listBound;

	/**
	 * 
	 */
	public ChangeEventingListRelay() {
		_listeners = new OwnerReferenceList<ListChangeVerbListener>();
		_relay = new ChangeRelay();
		_listBound = null;
	}

	public void bind(ChangeEventingList<E> list) {
		if (_listBound != null) {
			_listBound.removeChangeListeners(this);
		}
		_listBound = list;
		_listBound.addChangeListener(this, _relay);
		ListChangeVerbEvent event
			= new ListChangeVerbEvent(this, ListChangeVerbEvent.VERB_BIND);
		_listeners.forEach(ls -> ls.listChanged(event));
	}
	
	public void unbind() {
		if (_listBound != null) {
			_listBound.removeChangeListeners(this);
			_listBound = null;
			ListChangeVerbEvent event
			= new ListChangeVerbEvent(this, ListChangeVerbEvent.VERB_UNBIND);
			_listeners.forEach(ls -> ls.listChanged(event));
		}
	}
	
	public boolean isBound() {
		return _listBound != null;
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

	@Override
	public int size() {
		return _listBound != null ? _listBound.size() : 0;
	}

	@Override
	public boolean isEmpty() {
		return _listBound != null ? _listBound.isEmpty() : true;
	}

	@Override
	public boolean contains(Object o) {
		return _listBound != null ? _listBound.contains(o) : false;
	}

	@Override
	public Iterator<E> iterator() {
		return _listBound != null ? List.copyOf(_listBound).iterator() : null;
	}

	@Override
	public Object[] toArray() {
		return _listBound != null ? _listBound.toArray() : new Object[0];
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return _listBound != null ? _listBound.toArray(a) : a;
	}

	@Override
	public boolean add(E e) {
		return _listBound != null ? _listBound.add(e) : false;
	}

	@Override
	public boolean remove(Object o) {
		return _listBound != null ? _listBound.remove(o) : false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return _listBound != null ? _listBound.containsAll(c) : false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (_listBound != null) {
			boolean bResult = _listBound.addAll(c);
			return bResult;
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (_listBound != null) {
			boolean bResult = _listBound.addAll(index, c);
			return bResult;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (_listBound != null) {
			boolean bResult = _listBound.removeAll(c);
			return bResult;
		} else {
			return false;
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (_listBound != null) {
			boolean bResult = _listBound.retainAll(c);
			return bResult;
		} else {
			return false;
		}
	}

	@Override
	public void clear() {
		if (_listBound != null) {
			_listBound.clear();
		}
	}

	@Override
	public E get(int index) {
		assert _listBound != null : "no bound list";
		return _listBound != null ? _listBound.get(index) : null;
	}

	@Override
	public E set(int index, E element) {
		assert _listBound != null : "no bound list";
		if (_listBound != null) {
			E item = _listBound.set(index, element);
			return item;
		} else {
			return null;
		}
	}

	@Override
	public void add(int index, E element) {
		if (_listBound != null) {
			_listBound.add(index, element);
		}
	}

	@Override
	public E remove(int index) {
		if (_listBound != null) {
			return _listBound.remove(index);
		} else {
			return null;
		}
	}

	@Override
	public int indexOf(Object o) {
		return _listBound != null ? _listBound.indexOf(o) : -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		return _listBound != null ? _listBound.lastIndexOf(o) : -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		return _listBound != null ? _listBound.listIterator() : null;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return _listBound != null ? _listBound.listIterator(index) : null;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		if (_listBound != null) {
			return _listBound.subList(fromIndex, toIndex);
		} else {
			return null;
		}
	}

	private class ChangeRelay
			implements ListChangeVerbListener {

		@Override
		public void listChanged(ListChangeVerbEvent e) {
			_listeners.forEach(ls -> ls.listChanged(e));		
		}		
	}
}
