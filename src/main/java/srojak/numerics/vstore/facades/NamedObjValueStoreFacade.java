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
package srojak.numerics.vstore.facades;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.NamedObjValueStore;
import srojak.numerics.vstore.StoreKeyNotFoundException;
import srojak.numerics.vstore.StoreValueObj;
import srojak.numerics.vstore.collections.NameKeyedObjCollection;

/**
 * @author Stephen
 *
 */
public abstract class NamedObjValueStoreFacade<V> 
		extends TypedNamedStoreFacadeBase
		implements NamedObjValueStore<V> {

	/**
	 * @param classInstance
	 */
	public NamedObjValueStoreFacade() {
		super();
		registerSelf();
	}
	
	protected abstract NameKeyedObjCollection<V> storeTyped();

	@Override
	public V getValue(NamedKey key) {
		Objects.requireNonNull(key, "key");
		NameKeyedObjCollection<V> store = storeTyped();
		StoreValueObj<V> storeValue = store.get(key);
		return storeValue != null ? storeValue.getValue() : null;
	}

	@Override
	public V getValueOrDefault(NamedKey key, V valueDefault) {
		Objects.requireNonNull(key, "key");
		NameKeyedObjCollection<V> store = storeTyped();
		StoreValueObj<V> storeValue = store.get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;
	}

	@Override
	public void setValue(NamedKey key, V value) {
		Objects.requireNonNull(key, "key");
		NameKeyedObjCollection<V> store = storeTyped();
		StoreValueObj<V> storeValue = store.get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassKey(), key.toString());
		storeValue.setValue(value);		
	}

}
