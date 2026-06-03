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

import java.util.Iterator;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class ReadOnlyIteratorFacade<E>
		implements Iterator<E> {
	private final Iterator<E> _source;
	
	public ReadOnlyIteratorFacade(Iterator<E> iterator) {
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
}
