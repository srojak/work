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
 */
public interface GlobalStoreLongValueFacade
		extends GlobalStoreCommon {
	
	/**
	 * Get the value for the key.
	 * @param key The key identifying the value.
	 * @return the value associated with the key.
	 * @throws StoreKeyNotFoundException if the key is not defined.
	 */
	long getValue(NamedKey key);
	
	/**
	 * Get the value for the key, or a default value if the key is not defined.
	 * @param key The key identifying the value.
	 * @param valueDefault The value to return if the key is not defined.
	 * @return The value associated with the key, if any, or the default value otherwise.
	 */
	long getValueOrDefault(NamedKey key, long valueDefault);
	
	/**
	 * Sets the value for the key.
	 * @param key The key identifying the value.
	 * @param value The value to set.
	 * @throws StoreKeyNotFoundException if the key is not defined.
	 * @throws IllegalArgumentException if the value for the key has validations
	 *   and these validation requirements were not met.
	 */
	void setValue(NamedKey key, long value);
}

