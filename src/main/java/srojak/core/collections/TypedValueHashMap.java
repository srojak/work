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

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import srojak.core.reflect.TypeBearingValue;

/**
 * @author Stephen
 *
 */
public final class TypedValueHashMap<K, V>
		implements CommonCollectionSize, Map<K, V> {
	private final HashMap<K, TypeBearingValue<V>> _map;
	
	public TypedValueHashMap() {
		_map = new HashMap<K, TypeBearingValue<V>>();
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
		for (TypeBearingValue<V> entry : _map.values()) {
			if (entry.equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	public TypeBearingValue<V> getTypedValue(Object key) {
		return _map.get(key);
	}
	
	public boolean isValueOfType(Class<?> classFor, Object key) {
		Objects.requireNonNull(classFor, "classFor");
		TypeBearingValue<V> entry = _map.get(key);
		if (entry == null) {
			return false;
		} else {
			return entry.isValueOfType(classFor);
		}
	}

	@Override
	public V get(Object key) {
		TypeBearingValue<V> entry = _map.get(key);
		return entry != null ? entry.getValue() : null;
	}
	
	public <T extends V> T getValueAs(Class<T> classReturn, Object key) {
		Objects.requireNonNull(classReturn, "classReturn");
		TypeBearingValue<V> entry = _map.get(key);
		return entry != null ? entry.getValueAs(classReturn) : null;
	}

	@Override
	public V put(K key, V value) {
		Objects.requireNonNull(key, "key");
		TypeBearingValue<V> entryNew = new TypeBearingValue<V>(value);
		TypeBearingValue<V> entry = _map.put(key, entryNew);
		return entry != null ? entry.getValue() : null;
	}

	@Override
	public V remove(Object key) {
		TypeBearingValue<V> entry = _map.remove(key);
		return entry != null ? entry.getValue() : null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Objects.requireNonNull(m, "m");
		m.forEach((key, value) -> {
			TypeBearingValue<V> entry = new TypeBearingValue<V>(value);
			_map.put(key, entry);
		});
	}

	@Override
	public void clear() {
		_map.clear();
	}

	@Override
	public Set<K> keySet() {
		return _map.keySet();
	}

	@Override
	public Collection<V> values() {
		return _map.values().stream().map(e -> e.getValue()).toList();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entries = new HashSet<Entry<K, V>>();
		for (Entry<K, TypeBearingValue<V>> entry : _map.entrySet()) {
			entries.add(new AbstractMap.SimpleEntry<K, V>(entry.getKey(), entry.getValue().getValue()));
		}
		return entries;
	}

	public void forEachValue(Consumer<? super V> consumer) {
		_map.values().forEach(e -> consumer.accept(e.getValue()));
	}
}
