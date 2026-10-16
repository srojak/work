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
package srojak.core.containers;

import java.util.Objects;

import srojak.core.KeyedIndirectReference;

/**
 * @author Stephen
 *
 */
public class KeyedIndirectWeakReference<K, T> 
		extends IndirectWeakReference<T> 
		implements KeyedIndirectReference<K, T> {
	private final K _key;

	/**
	 * @param referent
	 */
	public KeyedIndirectWeakReference(K key, T referent) {
		super(referent);
		Objects.requireNonNull(key, "key");
		_key = key;
	}

	@Override
	public K key() {
		return _key;
	}

	@Override
	public boolean isKeyEqual(K key) {
		return _key.equals(key);
	}

}
