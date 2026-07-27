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
import srojak.core.events.EventingListChangeEvent;
import srojak.core.events.EventingListChangeListener;

/**
 * @author Stephen
 *
 */
public class ChangeEventingLinkedList<T>
		extends LinkedList<T> 
		implements ChangeEventingList<T> {
	private OwnerReferenceList<EventingListChangeListener> _listeners;

	/**
	 * 
	 */
	private static final long serialVersionUID = 245329965857783934L;

	/**
	 * 
	 */
	public ChangeEventingLinkedList() {
		super();
		_listeners = new OwnerReferenceList<EventingListChangeListener>();		
	}

	/**
	 * @param c
	 */
	public ChangeEventingLinkedList(Collection<? extends T> c) {
		super(c);
		_listeners = new OwnerReferenceList<EventingListChangeListener>();		
	}

	private void raiseChangeEvent(int nVerb) {
		EventingListChangeEvent event = new EventingListChangeEvent(this, nVerb);
		_listeners.forEach(ls -> ls.listChanged(event));
	}

	private void raiseChangeEvent(int nVerb, Object objItem) {
		EventingListChangeEvent event = new EventingListChangeEvent(this, nVerb, objItem);
		_listeners.forEach(ls -> ls.listChanged(event));
	}
	
	@Override
	public void addChangeListener(Object owner, EventingListChangeListener listener) {
		_listeners.add(owner, listener);
	}
	
	@Override
	public boolean removeChangeListener(Object owner, EventingListChangeListener listener) {
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
		raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T removeLast() {
		T item = super.removeLast();
		raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public void addFirst(T e) {
		super.addFirst(e);
		raiseChangeEvent(EventingListChangeEvent.VERB_ADD, e);
	}

	@Override
	public void addLast(T e) {
		super.addLast(e);
		raiseChangeEvent(EventingListChangeEvent.VERB_ADD, e);
	}

	@Override
	public boolean add(T e) {
		if (super.add(e)) {
			raiseChangeEvent(EventingListChangeEvent.VERB_ADD, e);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean remove(Object o) {
		if (super.remove(o)) {
			raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, o);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean bResult = super.addAll(c);
		if (bResult) {
			raiseChangeEvent(EventingListChangeEvent.VERB_ADD_MULT);
		}
		return bResult;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean bResult = super.addAll(index, c);
		if (bResult) {
			raiseChangeEvent(EventingListChangeEvent.VERB_ADD_MULT);
		}
		return bResult;
	}

	@Override
	public void clear() {
		super.clear();
		raiseChangeEvent(EventingListChangeEvent.VERB_CLEAR);
	}

	@Override
	public T set(int index, T element) {
		T elementWas = super.set(index, element);
		raiseChangeEvent(EventingListChangeEvent.VERB_SET_ITEM, element);
		return elementWas;
	}

	@Override
	public void add(int index, T element) {
		super.add(index, element);
		raiseChangeEvent(EventingListChangeEvent.VERB_ADD, element);
	}

	@Override
	public T remove(int index) {
		T item = super.remove(index);
		raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T poll() {
		T item = super.poll();
		raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T remove() {
		T item = super.remove();
		raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T pollFirst() {
		T item = super.pollFirst();
		raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public T pollLast() {
		T item = super.pollLast();
		raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public void push(T e) {
		super.push(e);
		raiseChangeEvent(EventingListChangeEvent.VERB_ADD, e);
	}

	@Override
	public T pop() {
		T item = super.pop();
		raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, item);
		return item;
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		if (super.removeFirstOccurrence(o)) {
			raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, o);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		if (super.removeLastOccurrence(o)) {
			raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE, o);
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
			raiseChangeEvent(EventingListChangeEvent.VERB_REMOVE_MULT);
		}
	}
}
