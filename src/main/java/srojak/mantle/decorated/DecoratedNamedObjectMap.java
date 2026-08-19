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
package srojak.mantle.decorated;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import srojak.core.CommonCollectionSize;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public class DecoratedNamedObjectMap<V extends DecoratedNamed<?>>
		implements CommonCollectionSize, Map<NameToken, V> {
	private final HashMap<NameToken, V> _map;
	
	public DecoratedNamedObjectMap() {
		_map = new HashMap<NameToken, V>();
	}
	
	public DecoratedNamedObjectMap(Collection<V> c) {
		_map = new HashMap<NameToken, V>();
		c.forEach(item -> _map.put(item.getNameToken(), item));
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public boolean containsKey(Object key) {
		return _map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return _map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return _map.get(key);
	}
	
	public void add(V obj) {
		Objects.requireNonNull(obj, "obj");
		NameToken token = obj.getNameToken();
		if (_map.containsKey(token)) {
			throw new IllegalArgumentException("tokenKey " + token + " already exists in map");
		}
		_map.put(token, obj);
	}

	@Override
	public V put(NameToken key, V value) {
		throw new UnsupportedOperationException("put is not supported");
	}

	@Override
	public V remove(Object key) {
		return _map.remove(key);
	}
	
	public void addAll(Collection<? extends V> c) {
		c.forEach(i -> add(i));
	}

	@Override
	public void putAll(Map<? extends NameToken, ? extends V> m) {
		throw new UnsupportedOperationException("put is not supported");		
	}

	@Override
	public void clear() {
		_map.clear();
	}

	@Override
	public Set<NameToken> keySet() {
		return _map.keySet();
	}

	@Override
	public Collection<V> values() {
		return _map.values();
	}

	@Override
	public Set<Entry<NameToken, V>> entrySet() {
		return _map.entrySet();
	}
	
	public void overAll(Consumer<V> consumer) {
		Objects.requireNonNull(consumer, "consumer");
		_map.values().forEach(v -> consumer.accept(v));
	}
	
	public List<V> findAllWhere(Predicate<V> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		LinkedList<V> list = new LinkedList<V>();
		_map.values().forEach(v -> {
			if (predicate.test(v)) {
				list.addLast(v);
			}
		});
		return list;
		
	}
}
