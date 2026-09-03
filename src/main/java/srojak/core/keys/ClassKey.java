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
package srojak.core.keys;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.specialized.IntegerCounter;

/**
 * @author Stephen
 *
 */
public class ClassKey {
	private final Class<?> _classRef;
	
	public ClassKey(Class<?> classRef) {
		Objects.requireNonNull(classRef, "classRef");
		if (classRef.isInterface()) {
			throw new IllegalArgumentException(classRef.getSimpleName() + " is an interface");
		}
		_classRef = classRef;
	}
	
	public Class<?> getReferencedClass() {
		return _classRef;
	}
	
	public boolean isKeyFor(Class<?> classObj) {
		Objects.requireNonNull(classObj, "classObj");
		return _classRef.isAssignableFrom(classObj);
	}

	@Override
	public int hashCode() {
		return _classRef.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof ClassKey other) {
			return _classRef == other._classRef;
		} else if (obj instanceof Class<?> other) {
			return _classRef == other;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return _classRef.toString();
	}
	
	public static List<ClassKey> findAllQualifyingIn(Collection<ClassKey> keys, Class<?> classTarget) {
		Objects.requireNonNull(keys, "keys");
		Objects.requireNonNull(classTarget, "classTarget");
		LinkedList<ClassKey> list = new LinkedList<ClassKey>();
		Iterator<ClassKey> iterator = keys.iterator();
		while (iterator.hasNext()) {
			ClassKey key = iterator.next();
			if (key.isKeyFor(classTarget)) {
				list.addLast(key);
			}
		}
		return list;
	}
	
	public static List<ClassKey> findAllImplementingIn(Collection<ClassKey> keys, Class<?> classIntf) {
		Objects.requireNonNull(keys, "keys");
		Objects.requireNonNull(classIntf, "classIntf");
		if (!classIntf.isInterface()) {
			throw new IllegalArgumentException(classIntf.getSimpleName() + " is not an interface");
		}
		LinkedList<ClassKey> list = new LinkedList<ClassKey>();
		Iterator<ClassKey> iterator = keys.iterator();
		while (iterator.hasNext()) {
			ClassKey key = iterator.next();
			if (classIntf.isAssignableFrom(key.getReferencedClass())) {
				list.addLast(key);
			}
		}
		return list;
	}
	
	public static ClassKey findMostSpecificFor(Collection<ClassKey> keys, Class<?> classTarget) {
		Objects.requireNonNull(keys, "keys");
		Objects.requireNonNull(classTarget, "classTarget");
		if (classTarget.isInterface()) {
			throw new IllegalArgumentException(classTarget.getSimpleName() + " is an interface");
		}
		List<ClassKey> list = findAllQualifyingIn(keys, classTarget);
		ClassKey keySpecific = null;
		int nDepth = -1;
		Iterator<ClassKey> iterator = list.iterator();
		while (iterator.hasNext()) {
			ClassKey keyTest = iterator.next();
			Class<?> classEval = classTarget;
			IntegerCounter counter = new IntegerCounter();
			while (classEval != null) {
				if (keyTest.equals(classEval)) {
					if (keySpecific == null || nDepth > counter.getValue()) {
						keySpecific = keyTest;
						nDepth = counter.getValue();
						break;
					}
				}
				classEval = classEval.getSuperclass();
				counter.increment();
			}
		}
		return keySpecific;
	}
}
