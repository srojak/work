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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Stephen
 *
 */
public class InterfaceMethods {
	
	private static void innerExpand(Set<Class<?>> setIntfs, Class<?> classIntf) {
		if (setIntfs.add(classIntf))
		{
			for (Class<?> i : classIntf.getInterfaces()) {
				innerExpand(setIntfs, i);
			}
		}
	}

	public static void expand(Set<Class<?>> setIntfs, Class<?> classIntf) {
		Objects.requireNonNull(setIntfs, "setIntfs");
		if (!classIntf.isInterface()) {
			throw new IllegalArgumentException(classIntf.getSimpleName() + " is not an interface");
		}
		innerExpand(setIntfs, classIntf);
	}
	
	public static Set<Class<?>> expand(Class<?> classIntf) {
		if (!classIntf.isInterface()) {
			throw new IllegalArgumentException(classIntf.getSimpleName() + " is not an interface");
		}
		HashSet<Class<?>> interfaces = new HashSet<Class<?>>();		
		innerExpand(interfaces, classIntf);
		return interfaces;
	}
	
	public static boolean isFunctionalInterface(Class<?> classIntf) {
		if (!classIntf.isInterface()) {
			throw new IllegalArgumentException(classIntf.getSimpleName() + " is not an interface");
		}
		HashSet<Class<?>> interfaces = new HashSet<Class<?>>();		
		innerExpand(interfaces, classIntf);
		for (Class<?> i : interfaces) {
			FunctionalInterface a = i.getAnnotation(FunctionalInterface.class);
			if (a != null) {
				return true;
			}
		}
		return false;
	}
}
