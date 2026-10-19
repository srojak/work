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
import srojak.numerics.vstore.NamedDoubleValueStore;
import srojak.numerics.vstore.StoreKeyNotFoundException;
import srojak.numerics.vstore.StoreValueDouble;
import srojak.numerics.vstore.StoreValueKeyed;
import srojak.numerics.vstore.collections.NameKeyedDoubleCollection;

/**
 * @author Stephen
 *
 */
public abstract class NamedDoubleValueStoreFacade 
		extends TypedNamedStoreFacadeBase 
		implements NamedDoubleValueStore {

	/**
	 * @param classInstance
	 */
	public NamedDoubleValueStoreFacade() {
		super();
		registerSelf();
	}
	
	protected abstract NameKeyedDoubleCollection storeTyped();

	@Override
	public double getValue(NamedKey key) {
		Objects.requireNonNull(key, "key");
		NameKeyedDoubleCollection store = storeTyped();
		StoreValueDouble storeValue = store.get(key);
		return storeValue != null ? storeValue.getValue() : Double.NaN;
	}

	@Override
	public double getValueOrDefault(NamedKey key, double valueDefault) {
		Objects.requireNonNull(key, "key");
		NameKeyedDoubleCollection store = storeTyped();
		StoreValueDouble storeValue = store.get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;
	}

	@Override
	public float getValueAsFloat(NamedKey key) {
		Objects.requireNonNull(key, "key");
		NameKeyedDoubleCollection store = storeTyped();
		StoreValueDouble storeValue = store.get(key);
		return storeValue != null ? (float) storeValue.getValue() : Float.NaN;
	}

	@Override
	public float getValueOrDefaultAsFloat(NamedKey key, float valueDefault) {
		Objects.requireNonNull(key, "key");
		NameKeyedDoubleCollection store = storeTyped();
		StoreValueDouble storeValue = store.get(key);
		return storeValue != null ? (float) storeValue.getValue() : valueDefault;
	}

	@Override
	protected StoreValueKeyed store() {
		return storeTyped();
	}

	@Override
	public void setValue(NamedKey key, double value) {
		Objects.requireNonNull(key, "key");
		NameKeyedDoubleCollection store = storeTyped();
		StoreValueDouble storeValue = store.get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassKey(), key.toString());
		storeValue.setValue(value);		
	}

}
