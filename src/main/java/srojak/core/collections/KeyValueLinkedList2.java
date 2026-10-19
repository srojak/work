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
import srojak.core.keys.ClassKey;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class KeyValueLinkedList2<K, V> 
		extends LinkedList<KeyValue<K, V>>
		implements KeyValueList2<K, V> {
	private final ClassKey _ckValues;

	/**
	 * 
	 */
	public KeyValueLinkedList2(Class<?> classValues) {
		super();
		Objects.requireNonNull(classValues, "classValues");
		_ckValues = new ClassKey(classValues);
	}

	/**
	 * @param c
	 */
	public KeyValueLinkedList2(Class<?> classValues, Collection<? extends KeyValue<K, V>> c) {
		super(c);
		Objects.requireNonNull(classValues, "classValues");
		_ckValues = new ClassKey(classValues);
	}

	@Override
	public Class<?> getElementClass() {
		return _ckValues.getReferencedClass();
	}

	@Override
	public ClassKey getElementClassKey() {
		return _ckValues;
	}

	@Override
	public boolean replace(KeyValue<K, V> pairNew) {
		KeyValue<K, V> pair = findByKey(pairNew.key());
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
		KeyValue<K, V> pair = findByKey(pairNew.key());
		if (pair != null) {
			remove(pair);
		}
		add(pairNew);
		return true;
	}
}
