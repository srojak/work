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

import srojak.core.Keyed;

/**
 * @author Stephen
 *
 */
public abstract class KeyedContainerBase<K>
		implements Keyed<K> {
	protected final K _key;
	
	protected KeyedContainerBase(K key) {
		Objects.requireNonNull(key, "key");
		_key = key;
	}

	@Override
	public K getKey() {
		return _key;
	}
	
	protected boolean isNonNullKeyEqual(Object obj)
	{
		return _key.equals(obj);
	}

	@Override
	public boolean isKeyEqual(K key) {
		return key == null ? false : isNonNullKeyEqual(key);
	}

	@Override
	public int hashCode() {
		return _key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			return isNonNullKeyEqual(obj);
		}
	}
}
