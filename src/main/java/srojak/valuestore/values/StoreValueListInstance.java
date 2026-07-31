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
package srojak.valuestore.values;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import srojak.core.keys.NamedKey;
import srojak.core.logic.LockGate;
import srojak.mantle.collections.TypedLinkedList;
import srojak.valuestore.StoreValueList;

/**
 * @author Stephen
 *
 */
public class StoreValueListInstance<T> 
		extends StoreValueBase 
		implements StoreValueList<T> {
	private final TypedLinkedList<T> _list;
	private final LockGate _lock;
	
	public StoreValueListInstance(NamedKey key, Class<T> classItems) {
		super(key);
		Objects.requireNonNull(classItems, "classItems");
		_list = new TypedLinkedList<T>(classItems);
		_lock = new LockGate();
	}

	@Override
	public Class<?> getElementClass() {
		return _list.getElementClass();
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
	public boolean isLocked() {
		return _lock.isLocked();
	}

	@Override
	public void lock() {
		_lock.lock();
	}

	@Override
	public boolean contains(Object obj) {
		return _list.contains(obj);
	}

	@Override
	public List<T> getValues() {
		return List.copyOf(_list);
	}

	@Override
	public List<T> getValuesWhere(Predicate<T> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		return _list.where(predicate);
	}

	@Override
	public T getValueAt(int index) {
		Objects.checkIndex(index, _list.size());
		return _list.get(index);
	}

	@Override
	public boolean addValue(T value) {
		Objects.requireNonNull(value, "value");
		_lock.testLock(_key.getName());
		return _list.add(value);
	}

	@Override
	public boolean addMultiValues(Collection<? extends T> values) {
		Objects.requireNonNull(values, "values");
		_lock.testLock(_key.getName());
		return _list.addAll(values);
	}

	@Override
	public boolean removeValue(T value) {
		_lock.testLock(_key.getName());
		return _list.remove(value);
	}
}
