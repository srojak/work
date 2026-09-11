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
package srojak.core.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.stream.Stream;

import srojak.core.result.XResult;

/**
 * @author Stephen
 *
 */
public interface ReflectedItem
		extends Member {
	
	boolean isPublic();
	
	void setAccessible(boolean bState);
	
	XResult trySetAccessible(boolean bState);
	
	boolean hasAnnotations();
	
	Stream<Annotation> getAnnotationsAsStream();
	
	/**
     * Test if the caller can access this reflected object. 
     * If this reflected object corresponds to an instance method or field then 
     * this method tests if the caller can access the given {@code obj} 
     * with the reflected object.
     * For instance methods or fields then the {@code obj} argument must be an
     * instance of the {@link Member#getDeclaringClass() declaring class}.
     * For static members and constructors then {@code obj} must be {@code null}.
     *
     * @param obj an instance object of the declaring class of this reflected
     *            object if it is an instance method or field
     *
     * @return {@code true} if the caller can access this reflected object.
     * 
     * @see java.lang.reflect.AccessibleObject#canAccess(Object)
	 */
	boolean canAccess(Object obj);
	
	String metadataToString();
}

