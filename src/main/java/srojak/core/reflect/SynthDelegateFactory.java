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
import java.util.Objects;

import srojak.core.tools.BitMethods;

/**
 * @author Stephen
 *
 */
public class SynthDelegateFactory {
	
	private static boolean isMethodStatic(Method method) {
		return BitMethods.test(method.getModifiers(), Modifier.STATIC);
	}

	public static SynthDelegate createOpenDelegate(Reflectory reflectory, Class<?> classOwner, 
			String strMethodName, ParameterTypeSet typeParams) 
			throws NoSuchMethodException, SecurityException {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(classOwner, "classOwner");
		Objects.requireNonNull(strMethodName, "strMethodName");
		Objects.requireNonNull(typeParams, "typeParams");
		Method method = reflectory.getDeclaredMethod(classOwner, strMethodName, typeParams);
		reflectory.setAccessible(strMethodName, method, true);
		if (!isMethodStatic(method)) {
			throw new IllegalArgumentException(strMethodName + " is not static");
		}
		return new SynthOpenDelegate(method);
	}
	
	public static SynthDelegate createOpenPublicDelegate(Reflectory reflectory, Class<?> classOwner, 
			String strMethodName, ParameterTypeSet typeParams) 
			throws NoSuchMethodException, SecurityException {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(classOwner, "classOwner");
		Objects.requireNonNull(strMethodName, "strMethodName");
		Objects.requireNonNull(typeParams, "typeParams");
		Method method = reflectory.getMethod(classOwner, strMethodName, typeParams);
		if (!isMethodStatic(method)) {
			throw new IllegalArgumentException(strMethodName + " is not static");
		}
		return new SynthOpenDelegate(method);
	}
	
	public static SynthDelegate createClosedDelegate(Reflectory reflectory, Object objInstance, 
			String strMethodName, ParameterTypeSet typeParams) 
			throws NoSuchMethodException, SecurityException {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(objInstance, "objInstance");
		Objects.requireNonNull(strMethodName, "strMethodName");
		Objects.requireNonNull(typeParams, "typeParams");
		Class<?> classOwner = objInstance.getClass();
		Method method = reflectory.getDeclaredMethod(classOwner, strMethodName, typeParams);
		reflectory.setAccessible(strMethodName, method, true);
		if (isMethodStatic(method)) {
			throw new IllegalArgumentException(strMethodName + " is static");
		}
		return new SynthClosedDelegate(objInstance, method);
	}
	
	public static SynthDelegate createClosedPublicDelegate(Reflectory reflectory, Object objInstance, 
			String strMethodName, ParameterTypeSet typeParams) 
			throws NoSuchMethodException, SecurityException {
		Objects.requireNonNull(reflectory, "reflectory");
		Objects.requireNonNull(objInstance, "objInstance");
		Objects.requireNonNull(strMethodName, "strMethodName");
		Objects.requireNonNull(typeParams, "typeParams");
		Class<?> classOwner = objInstance.getClass();
		Method method = reflectory.getDeclaredMethod(classOwner, strMethodName, typeParams);
		if (isMethodStatic(method)) {
			throw new IllegalArgumentException(strMethodName + " is static");
		}
		return new SynthClosedDelegate(objInstance, method);
	}
}
