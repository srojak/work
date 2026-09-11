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
import java.lang.reflect.Constructor;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.result.XResult;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public class ReflectedConstructor<T>
		extends ReflectedMemberBase
		implements ReflectedCallableItem {
	private final Constructor<T> _cons;

	private static final ObservedActivity _activityNew = new SingleActivity("create instance");
	
	public ReflectedConstructor(Constructor<T> constructor) {
		Objects.requireNonNull(constructor, "constructor");
		_cons = constructor;
	}
	
	@Override
	protected Member getMember() {
		return _cons;
	}

	@Override
	public void setAccessible(boolean bState) {
		_cons.setAccessible(bState);
	}

	@Override
	public XResult trySetAccessible(boolean bState) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySetAccess);
		try {
			_cons.setAccessible(bState);
			result.setValid();
		} catch (InaccessibleObjectException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	@Override
	public boolean canAccess(Object obj) {
		return _cons.canAccess(obj);
	}

	@Override
	public boolean isSynthetic() {
		return _cons.isSynthetic();
	}

	@Override
	public boolean isVarArgs() {
		return _cons.isVarArgs();
	}

	@Override
	public Stream<Class<?>> getParameterTypes() {
		return Arrays.stream(_cons.getParameterTypes());
	}

	@Override
	public Stream<Class<?>> getExceptionTypes() {
		return Arrays.stream(_cons.getExceptionTypes());
	}
	
	@Override
	public boolean hasAnnotations() {
		return _cons.getAnnotations().length > 0;
	}

	@Override
	public Stream<Annotation> getAnnotationsAsStream() {
		return Arrays.stream(_cons.getAnnotations());
	}

	public XResultOf<T> newInstance(Object ... initargs) {
		XResultCarrierOf<T> result = new XResultCarrierOf<T>(_activityNew);
		try {
			T objInstance = _cons.newInstance(initargs);
			result.setResult(objInstance);
		} catch (InstantiationException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (InvocationTargetException exc) {
			result.caughtException(exc);
		}
		return result;
	}
}
