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
package srojak.core.collections;

import java.util.Map.Entry;
import java.util.Objects;

import srojak.core.KeyMutableValue;

/**
 * @author Stephen
 *
 */
class MapEntry<K, V> 
		implements Entry<K, V> {
	private final KeyMutableValue<K, V> _pair;
	
	public MapEntry(KeyMutableValue<K, V> pair) {
		Objects.requireNonNull(pair, "pair");
		_pair = pair;
	}

	@Override
	public K getKey() {
		return _pair.key();
	}

	@Override
	public V getValue() {
		return _pair.value();
	}

	@Override
	public V setValue(V value) {
		return _pair.exchangeValue(value);
	}
}
