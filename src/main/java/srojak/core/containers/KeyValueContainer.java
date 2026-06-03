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

import srojak.core.KeyValue;

/**
 * @author Stephen
 *
 * A container for a key and an associated value.
 * Equality for the container is equality for the key.
 * 
 */
public class KeyValueContainer<K, V>
		implements KeyValue<K, V> {
	private final K _key;
	private final V _value;
	
	public KeyValueContainer(K key, V value) {
		Objects.requireNonNull(key, "key");
		// value can be null
		_key = key;
		_value = value;
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
	public boolean isValueNull() {
		return _value == null;
	}

	@Override
	public V getValue() {
		return _value;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeyValue [");
		builder.append("_key=");
		builder.append(_key);
		builder.append(", ");
		builder.append("_value=");
		if (_value != null) {
			builder.append(_value);
		} else {
			builder.append("(null)");
		}
		builder.append("]");
		return builder.toString();
	}

}
