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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import srojak.core.CommonCollectionSize;
import srojak.core.reflect.TypeBearingValue;

/**
 * @author Stephen
 *
 */
public abstract class TypedValueListBase<E>
		implements CommonCollectionSize, List<E> {
	protected final List<TypeBearingValue<E>> _list;

	/**
	 * 
	 */
	protected TypedValueListBase(List<TypeBearingValue<E>> list) {
		_list = list;
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public int size() {
		return _list.size();
	}

	@Override
	public boolean contains(Object o) {
		for (TypeBearingValue<E> entry : _list) {
			if (entry.equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		MirrorList mirror = new MirrorList();
		return mirror.iterator();
	}

	@Override
	public Object[] toArray() {
		return _list.stream().map(e -> e.getValue()).toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// if I find this is being used more, I will investigate how to write it better.
		MirrorList mirror = new MirrorList();
		return mirror.toArray(a);
	}

	@Override
	public boolean add(E e) {
		TypeBearingValue<E> entryNew = new TypeBearingValue<E>(e);
		return _list.add(entryNew);
	}

	@Override
	public boolean remove(Object o) {
		for (TypeBearingValue<E> entry : _list) {
			if (entry.equals(o)) {
				_list.remove(entry);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		MirrorList mirror = new MirrorList();
		return mirror.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		LinkedList<TypeBearingValue<E>> listAdd = new LinkedList<TypeBearingValue<E>>();
		for (E value : c) {
			TypeBearingValue<E> entryNew = new TypeBearingValue<E>(value);
			listAdd.add(entryNew);
		}
		return _list.addAll(listAdd);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		LinkedList<TypeBearingValue<E>> listAdd = new LinkedList<TypeBearingValue<E>>();
		for (E value : c) {
			TypeBearingValue<E> entryNew = new TypeBearingValue<E>(value);
			listAdd.add(entryNew);
		}
		return _list.addAll(index, listAdd);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean bResult = false;
		for (Object objValue : c) {
			bResult |= this.remove(objValue);
		}
		return bResult;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean bResult = false;
		LinkedList<TypeBearingValue<E>> listRemove = new LinkedList<TypeBearingValue<E>>();	
		for (TypeBearingValue<E> entry : _list) {
			if (!c.contains(entry.getValue())) {
				listRemove.add(entry);
			}
		}
		for (TypeBearingValue<E> entry : listRemove) {
			bResult |= _list.remove(entry);
		}
		return bResult;
	}

	@Override
	public void clear() {
		_list.clear();
	}
	
	public TypeBearingValue<E> getTypedValue(int index) {
		return _list.get(index);
	}

	@Override
	public E get(int index) {
		TypeBearingValue<E> entry = _list.get(index);
		return entry.getValue();
	}
	
	public <T extends E> T getAs(Class<T> classReturn, int index) {
		Objects.requireNonNull(classReturn, "classReturn");
		TypeBearingValue<E> entry = _list.get(index);
		return entry != null ? entry.getValueAs(classReturn) : null;
	}

	@Override
	public E set(int index, E element) {
		Objects.requireNonNull(element, "element");
		TypeBearingValue<E> entry = new TypeBearingValue<E>(element);
		TypeBearingValue<E> entryPrior = _list.set(index, entry);
		return entryPrior != null ? entryPrior.getValue() : null;
	}

	@Override
	public void add(int index, E element) {
		TypeBearingValue<E> entryNew = new TypeBearingValue<E>(element);
		_list.add(index, entryNew);
	}

	@Override
	public E remove(int index) {
		TypeBearingValue<E> entryPrior = _list.remove(index);
		return entryPrior != null ? entryPrior.getValue() : null;
	}

	@Override
	public int indexOf(Object o) {
		if (o != null) {
			for (int idx = 0; idx < _list.size(); idx++) {
				if (_list.get(idx).equals(o)) {
					return idx;
				}
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		if (o != null) {
			for (int idx = _list.size() - 1; idx >= 0; idx--) {
				if (_list.get(idx).equals(o)) {
					return idx;
				}
			}
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		MirrorList mirror = new MirrorList();
		return mirror.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		MirrorList mirror = new MirrorList();
		return mirror.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		List<TypeBearingValue<E>> subList = _list.subList(fromIndex, toIndex);
		return subList.stream().map(e -> e.getValue()).toList();
	}
	
	public void forEachValue(Consumer<? super E> consumer) {
		_list.forEach(e -> consumer.accept(e.getValue()));
	}
	
	public void forEach(BiConsumer<Class<?>, ? super E> consumer) {
		_list.forEach(e -> consumer.accept(e.getValueClass(), e.getValue()));
	}
	
	@SuppressWarnings("serial")
	private class MirrorList
		extends ArrayList<E> {
		
		public MirrorList() {
			super(_list.stream().map(e -> e.getValue()).toList());
		}
	}
}
