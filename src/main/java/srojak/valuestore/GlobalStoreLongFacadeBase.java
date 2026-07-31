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
package srojak.valuestore;

import java.util.Objects;

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public abstract class GlobalStoreLongFacadeBase extends GlobalStoreFacadeBase
		implements GlobalStoreLongValueFacade {

	/**
	 * @param classInstance
	 */
	public GlobalStoreLongFacadeBase(Class<?> classInstance) {
		super(classInstance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getValue(NamedKey key) {
		Objects.requireNonNull(key, "key");
		StoreValueLong storeValue = this.<GlobalStoreLongCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		return storeValue.getValue();
	}

	@Override
	public long getValueOrDefault(NamedKey key, long valueDefault) {
		StoreValueLong storeValue = this.<GlobalStoreLongCollection>getStoreAs().get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;		
	}

	@Override
	public void setValue(NamedKey key, long value) {
		Objects.requireNonNull(key, "key");
		StoreValueLong storeValue = this.<GlobalStoreLongCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		storeValue.setValue(value);		
	}
}
