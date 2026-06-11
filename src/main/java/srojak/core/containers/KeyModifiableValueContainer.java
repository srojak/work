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

import srojak.core.KeyModifiableValue;

/**
 * @author Stephen
 *
 */
public class KeyModifiableValueContainer<K, V>
		extends KeyedContainerBase<K>
		implements KeyModifiableValue<K, V> {
	private V _value;
	
	public KeyModifiableValueContainer(K key, V value) {
		super(key);
		// value can be null
		_value = value;
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
	public void setValue(V value) {
		// value can be null
		_value = value;
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
