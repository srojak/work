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
package srojak.core;

import java.util.Objects;

import srojak.core.excepts.ClassMismatchException;

/**
 * @author Stephen
 *
 */
public interface ClassIdentifiedElementsStrict 
		extends ClassIdentifiedElements {
	
	default boolean isObjectAssignable(Object objValue) {
		Objects.requireNonNull(objValue, "objValue");
		Class<?> classExpected = getElementClass();
		return classExpected.isAssignableFrom(objValue.getClass());
	}

	default void validateClassOf(Object objValue) {
		Objects.requireNonNull(objValue, "objValue");
		Class<?> classExpected = getElementClass();
		Class<?> classArg = objValue.getClass();
		if (!classExpected.isAssignableFrom(classArg)) {
			throw new ClassMismatchException(classArg, "not of expected class");
		}
	}
}
