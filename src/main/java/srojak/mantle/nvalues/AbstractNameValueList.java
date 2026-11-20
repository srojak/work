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
package srojak.mantle.nvalues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Stephen
 *
 */
public abstract class AbstractNameValueList<E extends NameAnd>
		implements NamedValueList<E> {
	protected final ArrayList<E> _list;
	
	protected AbstractNameValueList() {
		_list = new ArrayList<E>();
	}
	
	protected AbstractNameValueList(int size) {
		_list = new ArrayList<E>(size);
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public int size() {
		return _list.size();
	}
	
	protected E find(Predicate<E> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		Iterator<E> iterator = _list.iterator();
		while (iterator.hasNext()) {
			E element = iterator.next();
			if (predicate.test(element)) {
				return element;
			}
		}
		return null;
	}

	@Override
	public E findByName(String strName) {
		return find(e -> e.isNameEqual(strName));
	}
	
	@Override
	public Stream<E> stream() {
		return _list.stream();
	}

	public void add(E element) {
		Objects.requireNonNull(element, "element");
		_list.add(element);
	}
}
