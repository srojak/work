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
package srojak.core.tools;

import java.util.Collection;

import srojak.core.KeyValue;
import srojak.core.containers.KeyValueContainer;

/**
 * @author Stephen
 *
 */
public class KeyValueMethods {

	public static <K, V> KeyValue<K, V> pair(K key, V value) {
		return new KeyValueContainer<K, V>(key, value);
	}

	public static <K, V> KeyValue<K, V> findFirstIn(K key, Collection<KeyValue<K, V>> collection) {
		for (KeyValue<K, V> item : collection) {
			if (item.isKeyEqual(key))
				return item;
		}
		return null;
	}
}
