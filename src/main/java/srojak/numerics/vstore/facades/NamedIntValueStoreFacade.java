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

import srojak.core.keys.ClassKey;
import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.NamedIntValueStore;
import srojak.numerics.vstore.StoreKeyNotFoundException;
import srojak.numerics.vstore.StoreValueInt;
import srojak.numerics.vstore.collections.NameKeyedIntCollection;

/**
 * @author Stephen
 *
 */
public abstract class NamedIntValueStoreFacade 
		extends TypedNamedStoreFacadeBase 
		implements NamedIntValueStore {

	/**
	 * @param classInstance
	 */
	public NamedIntValueStoreFacade() {
		super();
		registerSelf();
	}
	
	protected abstract NameKeyedIntCollection storeTyped();

	@Override
	public int getValue(NamedKey key) {
		Objects.requireNonNull(key, "key");
		NameKeyedIntCollection store = storeTyped();
		StoreValueInt storeValue = store.get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassKey(), key.toString());
		return storeValue.getValue();
	}

	@Override
	public int getValueOrDefault(NamedKey key, int valueDefault) {
		Objects.requireNonNull(key, "key");
		NameKeyedIntCollection store = storeTyped();
		StoreValueInt storeValue = store.get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;		
	}

	@Override
	public void setValue(NamedKey key, int value) {
		Objects.requireNonNull(key, "key");
		NameKeyedIntCollection store = storeTyped();
		StoreValueInt storeValue = store.get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassKey(), key.toString());
		storeValue.setValue(value);		
	}
}
