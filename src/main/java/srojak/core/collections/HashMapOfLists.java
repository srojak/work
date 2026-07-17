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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

import srojak.core.CommonCollectionSize;
import srojak.core.specialized.IntegerCounter;

/**
 * @author Stephen
 *
 */
public class HashMapOfLists<K, V>
		implements CommonCollectionSize {
	private final HashMap<K, LinkedList<V>> _map;
	
	public HashMapOfLists() {
		_map = new HashMap<K, LinkedList<V>>();
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public int size() {
		return _map.size();
	}
	
	public int flatSize() {
		IntegerCounter counter = new IntegerCounter();
		_map.values().forEach(e -> counter.increment(e.size()));
		return counter.getValue();
	}
	
	public void clear() {
		_map.clear();
	}
	
	public int getListSize(K key) {
		List<V> list = _map.get(key);
		return list == null ? 0 : list.size();
	}
	
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}
	
	public List<V> get(K key) {
		List<V> list = _map.get(key);
		if (list == null) {
			return List.<V>of();
		} else {
			return List.copyOf(list);
		}
	}
	
	public void add(K key, V value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		LinkedList<V> list = _map.get(key);
		if (list == null) {
			list = new LinkedList<V>();
			_map.put(key, list);
		}
		list.add(value);
	}
	
	public boolean remove(K key, V value) {
		List<V> list = _map.get(key);
		if (list != null) {
			return list.remove(value);
		} else {
			return false;
		}
	}
	
	public void forEach(BiConsumer<? super K, ? super V> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		_map.forEach((key, list) -> {
			list.forEach(v -> consumer.accept(key, v));
		});
	}
	
	public Set<K> getKeySet() {
		return _map.keySet();
	}

}
