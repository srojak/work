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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import srojak.core.collections.OwnerReferenceList;
import srojak.core.events.ListChangeVerbEvent;
import srojak.core.events.ListChangeVerbListener;

/**
 * @author Stephen
 *
 */
public class ChangeEventingLinkedList<T>
		extends LinkedList<T> 
		implements ChangeEventingList<T> {
	private OwnerReferenceList<ListChangeVerbListener> _listeners;

	/**
	 * 
	 */
	private static final long serialVersionUID = 245329965857783934L;

	/**
	 * 
	 */
	public ChangeEventingLinkedList() {
		super();
		_listeners = new OwnerReferenceList<ListChangeVerbListener>();		
	}

	/**
	 * @param c
	 */
	public ChangeEventingLinkedList(Collection<? extends T> c) {
		super(c);
		_listeners = new OwnerReferenceList<ListChangeVerbListener>();		
	}

	private void raiseChangeEvent(int nVerb) {
		ListChangeVerbEvent event = new ListChangeVerbEvent(this, nVerb);
		_listeners.forEach(ls -> ls.listChanged(event));
	}

	private void raiseChangeEvent(int nVerb, Object objItem) {
		ListChangeVerbEvent event = new ListChangeVerbEvent(this, nVerb, objItem);
		_listeners.forEach(ls -> ls.listChanged(event));
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
	public Object clone() {
		return new ChangeEventingLinkedList<T>(this);
	}

	@Override
	public T removeFirst() {
		T item = super.removeFirst();
		raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T removeLast() {
		T item = super.removeLast();
		raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public void addFirst(T e) {
		super.addFirst(e);
		raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, e);
	}

	@Override
	public void addLast(T e) {
		super.addLast(e);
		raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, e);
	}

	@Override
	public boolean add(T e) {
		if (super.add(e)) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, e);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean remove(Object o) {
		if (super.remove(o)) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, o);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean bResult = super.addAll(c);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_ADD_MULT);
		}
		return bResult;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean bResult = super.addAll(index, c);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_ADD_MULT);
		}
		return bResult;
	}

	@Override
	public void clear() {
		super.clear();
		raiseChangeEvent(ListChangeVerbEvent.VERB_CLEAR);
	}

	@Override
	public T set(int index, T element) {
		T elementWas = super.set(index, element);
		raiseChangeEvent(ListChangeVerbEvent.VERB_SET_ITEM, element);
		return elementWas;
	}

	@Override
	public void add(int index, T element) {
		super.add(index, element);
		raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, element);
	}

	@Override
	public T remove(int index) {
		T item = super.remove(index);
		raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T poll() {
		T item = super.poll();
		raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T remove() {
		T item = super.remove();
		raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T pollFirst() {
		T item = super.pollFirst();
		raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T pollLast() {
		T item = super.pollLast();
		raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public void push(T e) {
		super.push(e);
		raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, e);
	}

	@Override
	public T pop() {
		T item = super.pop();
		raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		if (super.removeFirstOccurrence(o)) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, o);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		if (super.removeLastOccurrence(o)) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, o);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// prevent iterator from changing the list
		return List.copyOf(this).listIterator(index);
	}

	@Override
	public Iterator<T> descendingIterator() {
		// prevent iterator from changing the list
		LinkedList<T> listCopy = new LinkedList<T>(this);
		return listCopy.descendingIterator();
	}

	@Override
	public Iterator<T> iterator() {
		// prevent iterator from changing the list
		return List.copyOf(this).iterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		// prevent iterator from changing the list
		return List.copyOf(this).listIterator();
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		super.removeRange(fromIndex, toIndex);
		if (fromIndex < toIndex) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE_MULT);
		}
	}
}
