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

import java.util.LinkedList;
import java.util.Objects;

import srojak.core.keys.ClassKey;
import srojak.core.keys.NamedKey;
import srojak.numerics.vstore.StoreKeyNotFoundException;
import srojak.numerics.vstore.StoreValue;
import srojak.numerics.vstore.StoreValueKeyed;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class StoreValueList<V extends StoreValue> 
		extends LinkedList<V>
		implements StoreValueKeyed {
	private final ClassKey _keyStore;
	
	public StoreValueList(ClassKey keyStore) {
		Objects.requireNonNull(keyStore, "keyStore");
		_keyStore = keyStore;
	}

	@Override
	public ClassKey getStoreKey() {
		return _keyStore;
	}
	
	protected V findByKey(NamedKey key) {
		for (V item : this) {
			if (item.getKey().equals(key)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public boolean containsKey(NamedKey key) {
		return findByKey(key) != null;
	}

	@Override
	public NamedKey[] getAllKeys() {
		NamedKey[] array = new NamedKey[size()];
		int index = 0;
		for (V item : this) {
			array[index++] = item.getKey();
		}
		return array;
	}

	@Override
	public boolean canSetValue(NamedKey key) {
		V entry = findByKey(key);
		if (entry == null) {
			throw new StoreKeyNotFoundException(getStoreKey(), key.toString());
		}
		return entry.canSet();
	}

}
