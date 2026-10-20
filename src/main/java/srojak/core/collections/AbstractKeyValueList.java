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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import srojak.core.CommonCollectionSize;
import srojak.core.KeyMutableValue;
import srojak.core.containers.KeyMutableValueContainer;
import srojak.core.tools.StreamMethods;

/**
 * @author Stephen
 *
 */
public abstract class AbstractKeyValueList<K, V>
		implements CommonCollectionSize, Map<K, V> {

	/**
	 * 
	 */
	public AbstractKeyValueList() {
		
	}
	
	protected abstract List<KeyMutableValue<K, V>> list();
	
	protected KeyMutableValue<K, V> bindPair(K key, V value) {
		Objects.requireNonNull(key, "key");
		return new KeyMutableValueContainer<K, V>(key, value);
	}

	@Override
	public int size() {
		return list().size();
	}

	@Override
	public boolean isEmpty() {
		return list().isEmpty();
	}
	
	public KeyMutableValue<K, V> findPairByKey(Object key) {
		Iterator<KeyMutableValue<K, V>> iterator = list().iterator();
		while (iterator.hasNext()) {
			KeyMutableValue<K, V> pair = iterator.next();
			if (pair.key().equals(key)) {
				return pair;
			}
		}
		return null;
	}

	@Override
	public boolean containsKey(Object key) {
		KeyMutableValue<K, V> pair = findPairByKey(key);
		return pair != null;
	}

	@Override
	public boolean containsValue(Object value) {
		Iterator<KeyMutableValue<K, V>> iterator = list().iterator();
		while (iterator.hasNext()) {
			KeyMutableValue<K, V> pair = iterator.next();
			if (pair.value().equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public V get(Object key) {
		KeyMutableValue<K, V> pair = findPairByKey(key);
		if (pair != null) {
			return pair.value();
		} else {
			return null;
		}
	}

	@Override
	public V put(K key, V value) {
		KeyMutableValue<K, V> pair = findPairByKey(key);
		V valuePrior = null;
		if (pair != null) {
			valuePrior = pair.value();
			pair.setValue(value);
		} else {
			pair = bindPair(key, value);
			list().add(pair);
		}
		return valuePrior;
	}

	@Override
	public V remove(Object key) {
		Iterator<KeyMutableValue<K, V>> iterator = list().iterator();
		V valuePrior = null;
		while (iterator.hasNext()) {
			KeyMutableValue<K, V> pair = iterator.next();
			if (pair.key().equals(key)) {
				valuePrior = pair.value();
				iterator.remove();
				return valuePrior;
			}
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            K key = e.getKey();
            V value = e.getValue();
            put(key, value);
        }
	}

	@Override
	public void clear() {
		list().clear();
	}

	@Override
	public Set<K> keySet() {
		List<KeyMutableValue<K, V>> list = list();
		return StreamMethods.toSet(list.stream().map(e -> e.key()), list.size());
	}

	@Override
	public Collection<V> values() {
		return list().stream().map(e -> e.value()).toList();
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		List<KeyMutableValue<K, V>> list = list();
		return StreamMethods.toSet(list.stream().map(e -> new MapEntry<K, V>(e)), list.size());
	}

}
