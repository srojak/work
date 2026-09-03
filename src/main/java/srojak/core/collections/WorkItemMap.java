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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import srojak.core.CommonCollectionSize;
import srojak.core.InvalidOperationException;

/**
 * @author Stephen
 *
 */
public class WorkItemMap<K, V>
	implements CommonCollectionSize {
	private final Map<K, V> _map;
	
	public WorkItemMap() {
		_map = new HashMap<K, V>();
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public int size() {
		return _map.size();
	}
	
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}
	
	public void clear() {
		_map.clear();
	}
	
	public V get(K key) {
		// it is an error if the key is not found
		V value = _map.get(key);
		if (value == null) {
			throw new NoSuchElementException("no entry for " + key);
		}
		return value;
	}
	
	public <T extends V> T getAs(K key) {
		@SuppressWarnings("unchecked")
		T value = (T) get(key);
		return value;
	}
	
	public V take(K key) {
		V value = _map.remove(key);
		if (value == null) {
			throw new NoSuchElementException("no entry for " + key);
		}
		return value;
	}
	
	public <T extends V> T takeAs(K key) {
		@SuppressWarnings("unchecked")
		T value = (T) take(key);
		return value;
	}
	
	public void assign(K key, V value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		if (_map.containsKey(key)) {
			throw new InvalidOperationException(key.toString(), "already assigned");
		}
		_map.put(key, value);
	}
	
	public void removeIfAssigned(K key) {
		_map.remove(key);
	}
	
	public List<K> getAllKeys() {
		return List.copyOf(_map.keySet());
	}
}
