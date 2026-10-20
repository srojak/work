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
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Stephen
 *
 */
public abstract class AbstractValueList<V> 
			implements MutableValueList<V> {
	public AbstractValueList() {
		
	}
	
	protected abstract List<V> list();

	@Override
	public boolean contains(Object obj) {
		return list().contains(obj);
	}

	@Override
	public V get(int index) {
		return list().get(index);
	}

	@Override
	public int indexOf(Object o) {
		return list().indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list().lastIndexOf(o);
	}

	@Override
	public Object[] toArray() {
		return list().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list().toArray(a);
	}

	@Override
	public Stream<V> stream() {
		return list().stream();
	}

	@Override
	public Iterator<V> iterator() {
		return new ValueListIterator<V>(list().iterator());
	}

	@Override
	public void forEach(Consumer<? super V> action) {
		list().forEach(action);
	}

	@Override
	public boolean isEmpty() {
		return list().isEmpty();
	}

	@Override
	public int size() {
		return list().size();
	}

	@Override
	public void clear() {
		list().clear();
	}

	@Override
	public boolean add(V e) {
		return list().add(e);
	}

	@Override
	public void add(int index, V element) {
		list().add(index, element);
	}

	@Override
	public V remove(int index) {
		return list().remove(index);
	}

	@Override
	public boolean remove(Object o) {
		return list().remove(o);
	}

	@Override
	public boolean addAll(Collection<? extends V> c) {
		return list().addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends V> c) {
		return list().addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return list().removeAll(c);
	}

	@Override
	public V set(int index, V element) {
		return list().set(index, element);
	}

	@Override
	public ListIterator<V> listIterator(int index) {
		return list().listIterator(index);
	}

	@Override
	public ListIterator<V> listIterator() {
		return list().listIterator();
	}

	@Override
	public Iterator<V> mutableIterator() {
		return list().iterator();
	}

	@Override
	public boolean removeIf(Predicate<? super V> filter) {
		return list().removeIf(filter);
	}

	@Override
	public void sort(Comparator<? super V> c) {
		list().sort(c);
	}

}
