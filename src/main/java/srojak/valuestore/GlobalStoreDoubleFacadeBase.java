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
public abstract class GlobalStoreDoubleFacadeBase
		extends GlobalStoreFacadeBase
		implements GlobalStoreDoubleValueFacade {

	/**
	 * @param classInstance
	 */
	public GlobalStoreDoubleFacadeBase(Class<?> classInstance) {
		super(classInstance);
	}

	@Override
	public double getValue(NamedKey key) {
		StoreValueDouble storeValue = this.<GlobalStoreDoubleCollection>getStoreAs().get(key);
		return storeValue != null ? storeValue.getValue() : Double.NaN;
	}

	@Override
	public double getValueOrDefault(NamedKey key, double valueDefault) {
		StoreValueDouble storeValue = this.<GlobalStoreDoubleCollection>getStoreAs().get(key);
		return storeValue != null ? storeValue.getValue() : valueDefault;
	}

	@Override
	public void setValue(NamedKey key, double value) {
		Objects.requireNonNull(key, "key");
		StoreValueDouble storeValue = this.<GlobalStoreDoubleCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		storeValue.setValue(value);		
	}

}
