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

import java.util.ListIterator;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class ReadOnlyListIteratorFacade<E>
		implements ListIterator<E> {
	private final ListIterator<E> _source;
	
	public ReadOnlyListIteratorFacade(ListIterator<E> iterator) {
		Objects.requireNonNull(iterator, "iterator");
		_source = iterator;
	}

	@Override
	public boolean hasNext() {
		return _source.hasNext();
	}

	@Override
	public E next() {
		return _source.next();
	}

	@Override
	public boolean hasPrevious() {
		return _source.hasPrevious();
	}

	@Override
	public E previous() {
		return _source.previous();
	}

	@Override
	public int nextIndex() {
		return _source.nextIndex();
	}

	@Override
	public int previousIndex() {
		return _source.previousIndex();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("read-only iterator");
	}

	@Override
	public void set(E e) {
		throw new UnsupportedOperationException("read-only iterator");
	}

	@Override
	public void add(E e) {
		throw new UnsupportedOperationException("read-only iterator");
	}
}
