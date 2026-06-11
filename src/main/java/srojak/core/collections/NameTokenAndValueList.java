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

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.containers.NameTokenModableValueContainer;

/**
 * @author Stephen
 *
 */
public class NameTokenAndValueList<V>
		implements NameTokenAndValueListReadOnly<V> {
	private final LinkedList<NameTokenModableValueContainer<V>> _list;
	
	public NameTokenAndValueList() {
		_list = new LinkedList<NameTokenModableValueContainer<V>>();
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public int size() {
		return _list.size();
	}
	
	private NameTokenModableValueContainer<V> findFirstByName(NameToken name) {
		ListIterator<NameTokenModableValueContainer<V>> iterator = _list.listIterator();
		while (iterator.hasNext()) {
			NameTokenModableValueContainer<V> container = iterator.next();
			if (container.isNameTokenEqual(name)) {
				return container;
			}
		}
		return null;
	}

	@Override
	public boolean containsKey(NameToken name) {
		if (name == null) {
			return false;
		} else {
			return findFirstByName(name) != null;
		}
	}

	@Override
	public V findFirst(NameToken name) {
		NameTokenModableValueContainer<V> container = null;
		if (name != null) {
			container = findFirstByName(name);
		}
		return container != null ? container.getValue() : null;
	}
	
	public void add(NameToken name, V value) {
		Objects.requireNonNull(name, "name");
		NameTokenModableValueContainer<V> container = new NameTokenModableValueContainer<V>(name, value);
		_list.add(container);
	}

	public void addOrReplace(NameToken name, V value) {
		Objects.requireNonNull(name, "name");
		NameTokenModableValueContainer<V> container = findFirstByName(name);
		if (container == null) {
			container = new NameTokenModableValueContainer<V>(name, value);
			_list.add(container);
		} else {
			container.setValue(value);
		}
	}
	
	public boolean remove(NameToken name) {
		ListIterator<NameTokenModableValueContainer<V>> iterator = _list.listIterator();
		while (iterator.hasNext()) {
			NameTokenModableValueContainer<V> container = iterator.next();
			if (container.isNameTokenEqual(name)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}
}
