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
import srojak.core.reflect.PackageClassLocator;

/**
 * @author Stephen
 *
 * The interface that all global stores of any data type must provide
 */
public interface GlobalStoreCommon {
	
	/**
	 * Gets the key for the global store.
	 * @return the {@code PackageClassLocator} of the originating facade.
	 */
	PackageClassLocator getClassLocator();
	
	/**
	 * Gets the name of the {@code PackageClassLocator} key.
	 * @return the {@code String} name of the key.
	 */
	String getLocatorName();
	
	/**
	 * Is the store empty?
	 * @return {@code true} if empty.
	 */
	boolean isEmpty();
	
	/**
	 * Get the number of items in the store.
	 * @return the number of items in the store.
	 */
	int size();
	
	/**
	 * Test for the existence of the key in the store.
	 * @param key The key for which to test.
	 * @return {@code true} if the key exists in the store.
	 */
	boolean containsKey(NamedKey key);
	
	/**
	 * Can the value associated with this key be changed directly?
	 * @param key The key for which to test.
	 * @return {@code true} if the key can be changed.
	 */
	boolean canSetValue(NamedKey key);
}
