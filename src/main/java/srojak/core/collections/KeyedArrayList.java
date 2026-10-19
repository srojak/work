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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import srojak.core.KeyMutableValue;
import srojak.core.KeyedList;

/**
 * @author Stephen
 *
 */
public class KeyedArrayList<K, V>
		extends AbstractKeyValueList<K, V>
		implements KeyedList<K, V>, ArrayListBased {
	private final ArrayList<KeyMutableValue<K, V>> _list;

	/**
	 * 
	 */
	public KeyedArrayList() {
		_list = new ArrayList<KeyMutableValue<K, V>>();
	}
	
	public KeyedArrayList(int initialCapacity) {
		_list = new ArrayList<KeyMutableValue<K, V>>(initialCapacity);
	}
	
	public KeyedArrayList(Collection<? extends V> values, Function<V, K> fnKeyExtract) {
		Objects.requireNonNull(values, "value");
		Objects.requireNonNull(fnKeyExtract, "fnKeyExtract");
		_list = new ArrayList<KeyMutableValue<K, V>>(
				values.stream().map(v -> bindPair(fnKeyExtract.apply(v), v)).toList());
	}

	@Override
	protected List<KeyMutableValue<K, V>> list() {
		return _list;
	}

	@Override
	public final void ensureCapacity(int minCapacity) {
		_list.ensureCapacity(minCapacity);
	}

	@Override
	public final void trimToSize() {
		_list.trimToSize();
	}
}
