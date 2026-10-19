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
package srojak.mantle.vstore;

import java.util.Map;
import java.util.Objects;

import srojak.core.excepts.InitializationRuntimeException;
import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.StoreValueListCommon;
import srojak.numerics.vstore.collections.NamedValueStoreAdapter;

/**
 * @author Stephen
 *
 */
public class NamedListValueStoreAdapter 
		extends NamedValueStoreAdapter<StoreValueListCommon>
		implements NameKeyedListCollection {

	/**
	 * @param classStore
	 * @param classElements
	 * @param storeBase
	 */
	public NamedListValueStoreAdapter(Class<?> classStore, Class<?> classElements,
			Map<NamedKey, StoreValueListCommon> storeBase) {
		super(classStore, classElements, storeBase);
	}

	@Override
	public void define(StoreValueListCommon list) {
		Objects.requireNonNull(list, "list");
		NamedKey key = list.getKey();
		if (putIfAbsent(key, list) != null) {
			throw new InitializationRuntimeException(getStoreKey().getReferencedClass(),
					key.getName() + " is already defined");
		}
	}

}
