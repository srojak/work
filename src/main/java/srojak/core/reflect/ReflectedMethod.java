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
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.result.XResult;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;
import srojak.core.tools.BitMethods;

/**
 * @author Stephen
 *
 */
public class ReflectedMethod
		extends ReflectedMemberBase
		implements ReflectedCallableItem, ReflectedNonConstructor {
	private final Method _method;

	private static final ObservedActivity _activityInvoke = new SingleActivity("invoke");
	
	public static ReflectedMethod enclose(Method method) {
		return new ReflectedMethod(method);
	}
	
	public ReflectedMethod(Method method) {
		Objects.requireNonNull(method, "method");
		_method = method;
	}
	
	@Override
	protected Member getMember() {
		return _method;
	}

	@Override
	public boolean isSynthetic() {
		return _method.isSynthetic();
	}

	@Override
	public boolean isStatic() {
		return BitMethods.test(_method.getModifiers(), Modifier.STATIC);
	}

	@Override
	public void setAccessible(boolean bState) {
		_method.setAccessible(bState);
	}

	@Override
	public XResult trySetAccessible(boolean bState) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySetAccess);
		try {
			_method.setAccessible(bState);
			result.setValid();
		} catch (InaccessibleObjectException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	@Override
	public boolean canAccess(Object obj) {
		return _method.canAccess(obj);
	}
	
	@Override
	public boolean isFinal() {
		return BitMethods.test(_method.getModifiers(), Modifier.FINAL);
	}

	@Override
	public boolean isVarArgs() {
		return _method.isVarArgs();
	}

	public Class<?> getReturnType() {
		return _method.getReturnType();
	}
	
	public boolean returnsVoid() {
		return _method.getReturnType() == Void.TYPE;
	}
	
	@Override
	public Stream<Class<?>> getParameterTypes() {
		return Arrays.stream(_method.getParameterTypes());
	}
	
	public List<Parameter> getParameters() {
		return Arrays.stream(_method.getParameters()).toList();
	}
	
	@Override
	public Stream<Class<?>> getExceptionTypes() {
		return Arrays.stream(_method.getExceptionTypes());
	}
	
	@Override
	public boolean hasAnnotations() {
		return _method.getAnnotations().length > 0;
	}

	@Override
	public Stream<Annotation> getAnnotationsAsStream() {
		return Arrays.stream(_method.getAnnotations());
	}

	public XResultOf<Object> invoke(Object obj, Object... args) {
		XResultCarrierOf<Object> result = new XResultCarrierOf<Object>(_activityInvoke);
		try {
			Object objReturned = _method.invoke(obj, args);
			result.setResult(objReturned);
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
