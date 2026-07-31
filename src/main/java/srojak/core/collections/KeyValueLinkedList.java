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

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

import srojak.core.KeyValue;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class KeyValueLinkedList<K, V> 
		extends LinkedList<KeyValue<K, V>>
		implements KeyValueList<K, V> {
	private final Class<?> _classValues;

	/**
	 * 
	 */
	public KeyValueLinkedList(Class<?> classValues) {
		super();
		Objects.requireNonNull(classValues, "classValues");
		_classValues = classValues;
	}

	/**
	 * @param c
	 */
	public KeyValueLinkedList(Class<?> classValues, Collection<? extends KeyValue<K, V>> c) {
		super(c);
		Objects.requireNonNull(classValues, "classValues");
		_classValues = classValues;
	}

	@Override
	public Class<?> getElementClass() {
		return _classValues;
	}

	@Override
	public boolean replace(KeyValue<K, V> pairNew) {
		KeyValue<K, V> pair = findByKey(pairNew.getKey());
		if (pair == null) {
			return false;
		} else {
			remove(pair);
			add(pairNew);
			return true;
		}
	}

	@Override
	public boolean addOrReplace(KeyValue<K, V> pairNew) {
		KeyValue<K, V> pair = findByKey(pairNew.getKey());
		if (pair != null) {
			remove(pair);
		}
		add(pairNew);
		return true;
	}
}
