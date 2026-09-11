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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public class ClassReflector
		implements ReflectedClass {
	private final Class<?> _classObj;
	
	protected static final ObservedActivity ACTIVITY_GET_MEMBER = new SingleActivity("get member");
	
	public ClassReflector(Class<?> classObj) {
		Objects.requireNonNull(classObj, "classObj");
		if (classObj.isInterface()) {
			throw new IllegalArgumentException(classObj.getSimpleName() + " is an interface");
		}
		_classObj = classObj;
	}
	
	public ClassReflector(Object obj) {
		Objects.requireNonNull(obj, "obj");
		_classObj = obj.getClass();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> Class<T> getReflectedClassAs() {
		return (Class<T>) _classObj;
	}
	
	@Override
	public Class<?> getReflectedClass() {
		return _classObj;
	}
	
	@Override
	public Class<?> getDeclaringClass() {
		return _classObj.getDeclaringClass();
	}

	@Override
	public String getName() {
		return _classObj.getName();
	}

	@Override
	public int getModifiers() {
		return _classObj.getModifiers();
	}

	@Override
	public boolean isSynthetic() {
		return _classObj.isSynthetic();
	}

	@Override
	public Package getPackage() {
		return _classObj.getPackage();
	}

	@Override
	public Module getModule() {
		return _classObj.getModule();
	}

	@Override
	public String getSimpleName() {
		return _classObj.getSimpleName();
	}

	@Override
	public boolean hasAnnotations() {
		return _classObj.getAnnotations().length > 0;
	}

	@Override
	public Stream<Annotation> getAnnotationsAsStream() {
		return Arrays.stream(_classObj.getAnnotations());	}

	@Override
	public String metadataToString() {
		return _classObj.toString();
	}

	public Stream<Field> getStreamOfFields() {
		return Arrays.stream(_classObj.getFields());
	}
	
	public Stream<Field> getStreamOfDeclaredFields() {
		return Arrays.stream(_classObj.getDeclaredFields());
	}
	
	public Stream<Method> getStreamOfMethods() {
		return Arrays.stream(_classObj.getMethods());
	}
	
	public Stream<Method> getStreamOfDeclaredMethods() {
		return Arrays.stream(_classObj.getDeclaredMethods());
	}
	
	public Stream<Class<?>> getStreamOfSuperclasses() {
		return Stream.iterate(_classObj.getSuperclass(), c -> c != null, Class::getSuperclass);
	}
	
	private void expandInterfaces(Set<Class<?>> setIntfs, Class<?> classIntf) {
		if (!classIntf.isInterface()) {
			throw new IllegalArgumentException(classIntf.getSimpleName() + " is not an interface");
		}
		if (setIntfs.add(classIntf))
		{
			for (Class<?> i : classIntf.getInterfaces()) {
				expandInterfaces(setIntfs, i);
			}
		}
	}
	
	public Set<Class<?>> getAllImplementedInterfaces() {
		HashSet<Class<?>> interfaces = new HashSet<Class<?>>();
		for (Class<?> classIntf : _classObj.getInterfaces()) {
			expandInterfaces(interfaces, classIntf);
		}
		getStreamOfSuperclasses().forEach(sc -> {
			for (Class<?> classIntf : sc.getInterfaces()) {
				expandInterfaces(interfaces, classIntf);
			}
		});
		return interfaces;
	}
	
	public XResultOf<Field> getField(String strName) {
		XResultCarrierOf<Field> result = new XResultCarrierOf<Field>(ACTIVITY_GET_MEMBER);
		try {
			Field field = _classObj.getField(strName);
			result.setResult(field);
		} catch (NoSuchFieldException exc) {
			result.caughtException(exc);
		} catch (SecurityException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResultOf<Field> getDeclaredField(String strName) {
		XResultCarrierOf<Field> result = new XResultCarrierOf<Field>(ACTIVITY_GET_MEMBER);
		try {
			Field field = _classObj.getDeclaredField(strName);
			result.setResult(field);
		} catch (NoSuchFieldException exc) {
			result.caughtException(exc);
		} catch (SecurityException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResultOf<Method> getMethod(String strName, Class<?>... parameterTypes) {
		XResultCarrierOf<Method> result = new XResultCarrierOf<Method>(ACTIVITY_GET_MEMBER);
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
