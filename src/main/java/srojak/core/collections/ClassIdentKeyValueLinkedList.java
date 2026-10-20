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
import java.util.Objects;

import srojak.core.keys.ClassKey;

/**
 * @author Stephen
 *
 */
public class ClassIdentKeyValueLinkedList<K, V> 
		extends KeyValueLinkedList<K, V>
		implements ClassIdentKeyMutableValueList<K, V> {
	private final ClassKey _ckElements;

	/**
	 * @param key
	 */
	public ClassIdentKeyValueLinkedList(Class<?> classElements, K key) {
		super(key);
		Objects.requireNonNull(classElements, "classElements");
		_ckElements = new ClassKey(classElements);
	}

	/**
	 * @param key
	 * @param values
	 */
	public ClassIdentKeyValueLinkedList(Class<?> classElements, K key, Collection<? extends V> values) {
		super(key, values);
		Objects.requireNonNull(classElements, "classElements");
		_ckElements = new ClassKey(classElements);
	}

	@Override
	public Class<?> getElementClass() {
		return _ckElements.getReferencedClass();
	}

	@Override
	public ClassKey getElementClassKey() {
		return _ckElements;
	}
}
