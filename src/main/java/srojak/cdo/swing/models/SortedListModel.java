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
package srojak.cdo.swing.models;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractListModel;

import srojak.core.collections.SortedList;

/**
 * @author Stephen
 *
 */
public class SortedListModel<E>
		extends AbstractListModel<E>
		implements ModifiableListModel<E> {
	private final SortedList<E> _list;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5620438197609423379L;
	
	public SortedListModel(SortedList<E> listBase) {
		super();
		Objects.requireNonNull(listBase, "listBase");
		_list = listBase;
	}

	@Override
	public int getSize() {
		return _list.size();
	}

	@Override
	public E getElementAt(int index) {
		return _list.get(index);
	}
	
	@Override
	public List<E> getElements() {
		return List.copyOf(_list);
	}
	
	@Override
	public boolean containsElement(E e) {
		return _list.contains(e);
	}
	
	@Override
	public int indexOfElement(E e) {
		return _list.indexOf(e);
	}
	
	@Override
	public void clear() {
		if (!_list.isEmpty()) {
			int indexLast = _list.size() - 1;
			_list.clear();
			this.fireIntervalRemoved(this, 0, indexLast);
		}
	}
	
	@Override
	public boolean addElement(E e) {
		int indexNew = _list.addAndGetNewIndex(e);
		if (indexNew < 0) {
			return false;
		} else {
			this.fireIntervalAdded(this, indexNew, indexNew);
			return true;
		}
	}
	
	@Override
	public boolean insertElement(int index, E e) {
		throw new UnsupportedOperationException("indexed insert is not supported on a sorted list");
	}
	
	@Override
	public boolean removeElement(E e) {
		int index = _list.findIndex(e);
		if (index < 0) {
			return false;
		} else {
			_list.remove(index);
			this.fireIntervalRemoved(this, index, index);
			return true;
		}
	}

	@Override
	public boolean removeElementAt(int index) {
		Objects.checkIndex(index, _list.size());
		_list.remove(index);
		this.fireIntervalRemoved(this, index, index);
		return true;
	}

	@Override
	public boolean addElements(Collection<? extends E> collection) {
		int indexStart = _list.size();
		if (_list.addAll(collection)) {
			int indexEnd = _list.size() - 1;
			this.fireIntervalAdded(this,  indexStart, indexEnd);
			return true;
		} else {
			return false;
		}
	}
}
