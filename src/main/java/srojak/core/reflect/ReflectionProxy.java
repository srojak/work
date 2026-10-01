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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class ReflectionProxy {
	private final MethodHandles.Lookup _luCaller;
	
	
	public ReflectionProxy(MethodHandles.Lookup lookup) {
		Objects.requireNonNull(lookup, "lookup");
		_luCaller = lookup;
	}
	
	public Class<?> findClass(String strFQName) 
			throws ClassNotFoundException, IllegalAccessException {
		Objects.requireNonNull(strFQName, "strFQName");
		return _luCaller.findClass(strFQName);
	}
	
	public Class<?> findClass(PackageClassLocator locator) 
			throws ClassNotFoundException, IllegalAccessException {
		Objects.requireNonNull( locator, " locator");
		return _luCaller.findClass(locator.getFullName());
	}
	
	public MethodHandle getDefaultConstructor(Class<?> classObj) 
			throws NoSuchMethodException, IllegalAccessException {
		Objects.requireNonNull(classObj, "classObj");
		MethodType typeMethod = MethodType.methodType(void.class);
		return _luCaller.findConstructor(classObj, typeMethod);
	}
	
	public MethodHandle getConstructor(Class<?> classObj, ParameterTypeSet params) 
			throws NoSuchMethodException, IllegalAccessException {
		Objects.requireNonNull(classObj, "classObj");
		Objects.requireNonNull(params, "params");
		MethodType typeMethod = MethodType.methodType(void.class, params.parameterTypes());
		return _luCaller.findConstructor(classObj, typeMethod);
	}
}
