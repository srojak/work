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

import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 * the interface that a global store of {@code Object} values must provide.
 * @param <V> the type of object to be stored in the global store.
 */
public interface GlobalStoreObjCollection<V>
		extends StoreValueKeyed {
	
	/**
	 * Get the value store for a key.
	 * @param key The key for the value.
	 * @return the store for the value, or {@code null} if not defined.
	 */
	StoreValueObj<V> get(NamedKey key);
	
	/**
	 * Define a value in the store.
	 * @param value The store for the value.
	 */
	void define(StoreValueObj<V> value);
}
