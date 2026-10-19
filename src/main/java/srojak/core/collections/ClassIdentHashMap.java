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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import srojak.core.keys.ClassKey;

/**
 * @author Stephen
 *
 */
public class ClassIdentHashMap<K, V> 
		extends HashMap<K, V> implements ClassIdentMap<K, V> {
	private final ClassKey _ckElements;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3605673754751432429L;
	/**
	 * 
	 */
	public ClassIdentHashMap(Class<?> classElements) {
		super();
		Objects.requireNonNull(classElements, "classElements");
		_ckElements = new ClassKey(classElements);
	}

	/**
	 * @param initialCapacity
	 */
	public ClassIdentHashMap(Class<?> classElements, int initialCapacity) {
		super(initialCapacity);
		Objects.requireNonNull(classElements, "classElements");
		_ckElements = new ClassKey(classElements);
	}

	/**
	 * @param m
	 */
	public ClassIdentHashMap(Class<?> classElements, Map<? extends K, ? extends V> m) {
		super(m);
		Objects.requireNonNull(classElements, "classElements");
		_ckElements = new ClassKey(classElements);
	}

	/**
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public ClassIdentHashMap(Class<?> classElements, int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		Objects.requireNonNull(classElements, "classElements");
		_ckElements = new ClassKey(classElements);
	}

	@Override
	public ClassKey getElementClassKey() {
		return _ckElements;
	}

	@Override
	public Class<?> getElementClass() {
		return _ckElements.getReferencedClass();
	}

}
