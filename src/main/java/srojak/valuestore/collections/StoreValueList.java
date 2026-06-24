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
package srojak.valuestore.collections;

import java.util.ArrayList;
import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.core.reflect.PackageClassLocator;
import srojak.valuestore.StoreKeyNotFoundException;
import srojak.valuestore.StoreValue;
import srojak.valuestore.StoreValueKeyed;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class StoreValueList<V extends StoreValue> 
		extends ArrayList<V>
		implements StoreValueKeyed {
	private final PackageClassLocator _locator;
	
	public StoreValueList(PackageClassLocator locator) {
		Objects.requireNonNull(locator, "locator");
		_locator = locator;
	}

	@Override
	public PackageClassLocator getClassLocator() {
		return _locator;
	}

	@Override
	public String getLocatorName() {
		return _locator.getFullName();
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
			throw new StoreKeyNotFoundException(getClassLocator(), key.toString());
		}
		return entry.canSet();
	}

}
