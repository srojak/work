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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import srojak.core.collections.OwnerReferenceList;
import srojak.core.events.ListChangeVerbEvent;
import srojak.core.events.ListChangeVerbListener;

/**
 * @author Stephen
 *
 */
public class ChangeEventingArrayList<T>
		extends ArrayList<T>
		implements ChangeEventingList<T> {

	private OwnerReferenceList<ListChangeVerbListener> _listeners;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7341912233175420783L;
	
	/**
	 * 
	 */
	public ChangeEventingArrayList() {
		super();
		_listeners = new OwnerReferenceList<ListChangeVerbListener>();
	}
	
	/**
	 * @param c
	 */
	public ChangeEventingArrayList(Collection<? extends T> c) {
		super(c);
		_listeners = new OwnerReferenceList<ListChangeVerbListener>();
	}
	
	/**
	 * @param initialCapacity
	 */
	public ChangeEventingArrayList(int initialCapacity) {
		super(initialCapacity);
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
		return new ChangeEventingArrayList<T>(this);
	}

	@Override
	public T set(int index, T element) {
		T elementWas = super.set(index, element);
		raiseChangeEvent(ListChangeVerbEvent.VERB_SET_ITEM, element);
		return elementWas;
	}

	@Override
	public boolean add(T e) {
		boolean bResult = super.add(e);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, e);
		}
		return bResult;
	}

	@Override
	public void add(int index, T element) {
		super.add(index, element);
		raiseChangeEvent(ListChangeVerbEvent.VERB_ADD, element);
	}

	@Override
	public T remove(int index) {
		T element = super.remove(index);
		if (element != null) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, element);
		}
		return element;
	}

	@Override
	public boolean remove(Object o) {
		boolean bResult = super.remove(o);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE, o);
		}
		return bResult;
	}

	@Override
	public void clear() {
		super.clear();
		raiseChangeEvent(ListChangeVerbEvent.VERB_CLEAR);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// prevent iterator from changing the list
		return List.copyOf(this).listIterator(index);
	}

	@Override
	public ListIterator<T> listIterator() {
		// prevent iterator from changing the list
		return List.copyOf(this).listIterator();
	}

	@Override
	public Iterator<T> iterator() {
		// prevent iterator from changing the list
		return List.copyOf(this).iterator();
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
	protected void removeRange(int fromIndex, int toIndex) {
		super.removeRange(fromIndex, toIndex);
		if (fromIndex < toIndex) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE_MULT);
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean bResult = super.removeAll(c);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE_MULT);
		}
		return bResult;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean bResult = super.retainAll(c);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE_MULT);
		}
		return bResult;
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		boolean bResult = super.removeIf(filter);
		if (bResult) {
			raiseChangeEvent(ListChangeVerbEvent.VERB_REMOVE_MULT);
		}
		return bResult;
	}

	@Override
	public void replaceAll(UnaryOperator<T> operator) {
		super.replaceAll(operator);
		raiseChangeEvent(ListChangeVerbEvent.VERB_REPLACE_MULT);
	}

	@Override
	public void sort(Comparator<? super T> c) {
		super.sort(c);
		raiseChangeEvent(ListChangeVerbEvent.VERB_SORT);
	}
}
