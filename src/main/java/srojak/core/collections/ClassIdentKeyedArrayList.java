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
import java.util.function.Function;

import srojak.core.keys.ClassKey;

/**
 * @author Stephen
 *
 */
public class ClassIdentKeyedArrayList<K, V> 
		extends KeyedArrayList<K, V> 
		implements ClassIdentKeyedList<K, V> {
	private final ClassKey _ckElements;

	/**
	 * 
	 */
	public ClassIdentKeyedArrayList(Class<?> classElements) {
		super();
		Objects.requireNonNull(classElements, "classElements");
		_ckElements = new ClassKey(classElements);
	}

	/**
	 * @param initialCapacity
	 */
	public ClassIdentKeyedArrayList(Class<?> classElements, int initialCapacity) {
		super(initialCapacity);
		Objects.requireNonNull(classElements, "classElements");
		_ckElements = new ClassKey(classElements);
	}

	/**
	 * @param values
	 * @param fnKeyExtract
	 */
	public ClassIdentKeyedArrayList(Class<?> classElements, Collection<? extends V> values, Function<V, K> fnKeyExtract) {
		super(values, fnKeyExtract);
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
