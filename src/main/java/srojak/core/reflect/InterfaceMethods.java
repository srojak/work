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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import srojak.core.tools.BitMethods;

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
	
	private static List<Method> getNonStaticMethods(Class<?> classIntf) {
		Method[] methods = classIntf.getMethods();
		List<Method> listMethods = new ArrayList<Method>(methods.length);
		Arrays.stream(methods).filter(m -> !Modifier.isStatic(m.getModifiers())).forEach(m -> listMethods.add(m));
		return listMethods;
	}
	
	public static Method getFunctionalInterfaceMethod(Class<?> classIntf) {
		if (!classIntf.isInterface()) {
			throw new IllegalArgumentException(classIntf.getSimpleName() + " is not an interface");
		}
		// any interface with a single instance method is a functional interface,
		// whether annotated or not
		List<Method> listMethods = getNonStaticMethods(classIntf);
		if (listMethods.size() == 1) {
			return listMethods.get(0);
		} else {
			return null;
		}
	}
	
	public static boolean isFunctionalInterface(Class<?> classIntf) {
		if (!classIntf.isInterface()) {
			throw new IllegalArgumentException(classIntf.getSimpleName() + " is not an interface");
		}
		// any interface with a single instance method is a functional interface,
		// whether annotated or not
		List<Method> listMethods = getNonStaticMethods(classIntf);
		return listMethods.size() == 1;
	}
}
