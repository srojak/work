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
package srojak.numerics.vstore;

import java.util.stream.Stream;

import srojak.core.ClassIdentCommonCollection;
import srojak.core.keys.ClassKey;
import srojak.core.keys.NamedKey;

/**
 * @author Stephen
 *
 * The interface that all stores of any type must provide to be accepted into the global store.
 */
public interface StoreValueKeyed
		extends ClassIdentCommonCollection {
	
	/**
	 * Gets the key for the global store.
	 * @return the {@code ClassKey} of the originating facade.
	 */
	ClassKey getStoreKey();
	
	/**
	 * Test for the existence of the key in the store.
	 * @param key The key for which to test.
	 * @return {@code true} if the key exists in the store.
	 */
	boolean containsKey(NamedKey key);
	
	/**
	 * Get all the keys in the store
	 * @return an array of all the keys currently in the store.
	 */
	Stream<NamedKey> allKeys();
	
	/**
	 * Can the value associated with this key be changed directly?
	 * @param key The key for which to test.
	 * @return {@code true} if the key can be changed.
	 */
	boolean canSetValue(NamedKey key);
}
