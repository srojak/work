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
import srojak.numerics.vstore.NamedLongValueStore;
import srojak.numerics.vstore.StoreKeyNotFoundException;
import srojak.numerics.vstore.StoreValueLong;
import srojak.numerics.vstore.collections.NameKeyedLongCollection;

/**
 * @author Stephen
 *
 */
public abstract class NamedLongValueStoreFacade 
		extends TypedNamedStoreFacadeBase
		implements NamedLongValueStore {

	/**
	 * 
	 */
	public NamedLongValueStoreFacade() {
		super();
		registerSelf();
	}
	
	protected abstract NameKeyedLongCollection storeTyped();

	@Override
	public long getValue(NamedKey key) {
		Objects.requireNonNull(key, "key");
		NameKeyedLongCollection store = storeTyped();
		StoreValueLong storeValue = store.get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassKey(), key.toString());
		return storeValue.getValue();
	}

	@Override
	public long getValueOrDefault(NamedKey key, long valueDefault) {
		Objects.requireNonNull(key, "key");
		NameKeyedLongCollection store = storeTyped();
		StoreValueLong storeValue = store.get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;		
	}

	@Override
	public void setValue(NamedKey key, long value) {
		Objects.requireNonNull(key, "key");
		NameKeyedLongCollection store = storeTyped();
		StoreValueLong storeValue = store.get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassKey(), key.toString());
		storeValue.setValue(value);		
	}

}
