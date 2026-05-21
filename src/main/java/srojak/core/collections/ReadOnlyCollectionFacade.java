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
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Stephen
 *
 */
public class ReadOnlyCollectionFacade<E> 
		implements ReadOnlyCollection<E> {
	private final Collection<E> _collection;
	
	public ReadOnlyCollectionFacade(Collection<E> source) {
		Objects.requireNonNull(source, "source");
		_collection = source;
	}

	@Override
	public boolean isEmpty() {
		return _collection.isEmpty();
	}

	@Override
	public int size() {
		return _collection.size();
	}

	@Override
	public boolean contains(Object obj) {
		return _collection.contains(obj);
	}

	@Override
	public Iterator<E> iterator() {
		return _collection.iterator();
	}

	@Override
	public Object[] toArray() {
		return _collection.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return _collection.toArray(a);
	}

	@Override
	public void forEach(Consumer<E> consumer) {
		Iterator<E> iter = _collection.iterator();
		while (iter.hasNext()) {
			consumer.accept(iter.next());
		}
	}
}
