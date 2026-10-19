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
package srojak.numerics.vstore.collections;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.collections.ArrayListBased;
import srojak.core.keys.ClassKey;
import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.StoreKeyNotFoundException;
import srojak.numerics.vstore.StoreValue;

/**
 * @author Stephen
 *
 */
public class NamedValueStoreAdapter<S extends StoreValue> 
		implements NameKeyedCollectionCommon {
	private final ClassKey _ckStore;
	private final ClassKey _ckElements;
	private final Map<NamedKey, S> _store;

	/**
	 * 
	 */
	public NamedValueStoreAdapter(Class<?> classStore, Class<?> classElements, Map<NamedKey, S> storeBase) {
		Objects.requireNonNull(classStore, "classStore");
		Objects.requireNonNull(classElements, "classElements");
		Objects.requireNonNull(storeBase, "storeBase");
		_ckStore = new ClassKey(classStore);
		_ckElements = new ClassKey(classElements);
		_store = storeBase;
	}

	@Override
	public ClassKey getStoreKey() {
		return _ckStore;
	}

	@Override
	public boolean containsKey(NamedKey key) {
		return _store.containsKey(key);
	}

	@Override
	public boolean canSetValue(NamedKey key) {
		S entry = _store.get(key);
		if (entry == null) {
			throw new StoreKeyNotFoundException(getStoreKey(), key.toString());
		}
		return entry.canSet();
	}

	@Override
	public boolean isEmpty() {
		return _store.isEmpty();
	}

	@Override
	public int size() {
		return _store.size();
	}

	@Override
	public Class<?> getElementClass() {
		return _ckElements.getReferencedClass();
	}

	@Override
	public ClassKey getElementClassKey() {
		return _ckElements;
	}
	
	@Override
	public Stream<NamedKey> allKeys() {
		return _store.keySet().stream();
	}

	public S get(NamedKey key) {
		return _store.get(key);
	}
	
	protected S put(NamedKey key, S value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		return _store.put(key, value);
	}
	
	protected S putIfAbsent(NamedKey key, S value) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
		return _store.putIfAbsent(key, value);
	}
	
	public void trimToSize() {
		// only takes effect for ArrayList
		if (_store instanceof ArrayListBased array) {
			array.trimToSize();
		}
	}
}
