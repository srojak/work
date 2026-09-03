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
import java.util.Objects;

import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public class ClassReflector {
	private final Class<?> _classObj;
	
	public ClassReflector(Class<?> classObj) {
		Objects.requireNonNull(classObj, "classObj");
		_classObj = classObj;
	}
	
	public ClassReflector(Object obj) {
		Objects.requireNonNull(obj, "obj");
		_classObj = obj.getClass();
	}
	
	public Class<?> getReflectedClass() {
		return _classObj;
	}
	
	public XResultOf<Method> getMethod(String strName, Class<?>... parameterTypes) {
		XResultCarrierOf<Method> result = new XResultCarrierOf<Method>();
		try {
			Method method = _classObj.getMethod(strName, parameterTypes);
			result.setResult(method);
		} catch (NoSuchMethodException exc) {
			result.caughtException(exc);
		} catch (SecurityException exc) {
			result.caughtException(exc);
		}
		return result;
	}
}
