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

import srojak.core.ClassMismatchException;
import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 */
public abstract class GlobalStoreListFacadeBase 
		extends GlobalStoreFacadeBase 
		implements GlobalStoreListValueFacade {

	/**
	 * @param classInstance
	 */
	public GlobalStoreListFacadeBase(Class<?> classInstance) {
		super(classInstance);
	}

	@Override
	public StoreValueListCommon getList(NamedKey key) {
		StoreValueListCommon storeValue = this.<GlobalStoreListCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		return storeValue;
	}

	@Override
	public <V> StoreValueList<V> getListAs(NamedKey key, Class<V> classElement) {
		StoreValueListCommon storeValue = this.<GlobalStoreListCollection>getStoreAs().get(key);
		if (storeValue == null)
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		if (!classElement.isAssignableFrom(storeValue.getElementClass())) {
			throw new ClassMismatchException(storeValue.getElementClass(),
					"incompatible class for list under key " + key.toString());
		}
		@SuppressWarnings("unchecked")
		StoreValueList<V> listTyped = (StoreValueList<V>) storeValue;
		return listTyped;
	}

	@Override
	public <V> StoreValueList<V> addList(NamedKey key, StoreValueList<V> list) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(list, "list");
		GlobalStoreListCollection store = this.getStoreAs();
		store.define(list);
		return list;
	}

}
