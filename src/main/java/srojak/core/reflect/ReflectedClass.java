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
import java.lang.reflect.Type;
import java.util.stream.Stream;

import srojak.core.ClassReferencing;

/**
 * @author Stephen
 *
 */
public interface ReflectedClass 
		extends Type, Member, ClassReferencing {
	
	Package getPackage();
	
	Module getModule();
	
	String getSimpleName();
	
	ClassLoader getClassLoader();
	
	boolean hasAnnotations();
	
	<A extends Annotation> A getAnnotation(Class<A> annotationClass);
	
	Stream<Annotation> getAnnotationsAsStream();
	
	String metadataToString();

}
