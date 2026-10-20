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

/**
 * @author Stephen
 *
 */
public class KeyValueArrayList<K, V>
		extends AbstractValueList<V>
		implements KeyMutableValueList<K, V>, ArrayListBased {
	private final K _key;
	private final ArrayList<V> _list;
	
	public KeyValueArrayList(K key) {
		super();
		Objects.requireNonNull(key, "key");
		_key = key;
		_list = new ArrayList<V>();
	}
	
	public KeyValueArrayList(K key, int initialCapacity) {
		super();
		Objects.requireNonNull(key, "key");
		_key = key;
		_list = new ArrayList<V>(initialCapacity);
	}
	
	public KeyValueArrayList(K key, Collection<? extends V> values) {
		super();
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(values, "values");
		_key = key;
		_list = new ArrayList<V>(values);		
	}

	@Override
	protected List<V> list() {
		return _list;
	}

	@Override
	public K key() {
		return _key;
	}

	@Override
	public boolean isKeyEqual(K key) {
		return _key.equals(key);
	}

	@Override
	public void ensureCapacity(int minCapacity) {
		_list.ensureCapacity(minCapacity);
		
	}
	@Override
	public void trimToSize() {
		_list.trimToSize();
	}
}
