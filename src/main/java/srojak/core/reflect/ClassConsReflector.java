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

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Stream;

import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public class ClassConsReflector<T>
		extends ClassReflector {

	/**
	 * @param classObj
	 */
	public ClassConsReflector(Class<T> classObj) {
		super(classObj);
	}

	/**
	 * @param obj
	 */
	public ClassConsReflector(T obj) {
		super(obj);
	}

	@SuppressWarnings("unchecked")
	public Stream<Constructor<T>> getStreamOfConstructors() {
		return Arrays.stream(getReflectedClassAs().getConstructors()).map(c -> (Constructor<T>) c);
	}
	
	public Stream<ReflectedConstructor<T>> getReflectedConstructors() {
		return getStreamOfConstructors().map(c -> new ReflectedConstructor<T>(c));
	}
	
	public XResultOf<Constructor<T>> getConstructor(Class<?>... parameterTypes) {
		XResultCarrierOf<Constructor<T>> result = new XResultCarrierOf<Constructor<T>>(ACTIVITY_GET_MEMBER);
		Class<T> classTyped = getReflectedClassAs();
		try {
			Constructor<T> constr = classTyped.getConstructor(parameterTypes);
			result.setResult(constr);
		} catch (NoSuchMethodException exc) {
			result.caughtException(exc);
		} catch (SecurityException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResultOf<ReflectedConstructor<T>> getReflectedConstructor(Class<?>... parameterTypes) {
		XResultCarrierOf<ReflectedConstructor<T>> result = new XResultCarrierOf<ReflectedConstructor<T>>(ACTIVITY_GET_MEMBER);
		Class<T> classTyped = getReflectedClassAs();
		Constructor<T> constr;
		try {
			constr = classTyped.getConstructor(parameterTypes);
			ReflectedConstructor<T> rcons = new ReflectedConstructor<T>(constr);
			result.setResult(rcons);
		} catch (NoSuchMethodException exc) {
			result.caughtException(exc);
		} catch (SecurityException exc) {
			result.caughtException(exc);
		}
		return result;
	}
}
