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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class ClassKeyCommon {
	protected final Class<?> _classRef;

	protected ClassKeyCommon(Class<?> classRef) {
		Objects.requireNonNull(classRef, "classRef");
		_classRef = classRef;
	}
	
	public Class<?> getReferencedClass() {
		return _classRef;
	}
	
	public Field[] getDeclaredFields() {
		return _classRef.getDeclaredFields();
	}
	
	public Annotation[] getAnnotations() {
		return _classRef.getAnnotations();
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
	
	public String toShortString() {
		return _classRef.getSimpleName();
	}
}
