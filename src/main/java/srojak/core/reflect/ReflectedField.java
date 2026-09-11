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
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.result.XResult;
import srojak.core.result.XResultBoolean;
import srojak.core.result.XResultBooleanCarrier;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultChar;
import srojak.core.result.XResultCharCarrier;
import srojak.core.result.XResultDouble;
import srojak.core.result.XResultDoubleCarrier;
import srojak.core.result.XResultFloat;
import srojak.core.result.XResultFloatCarrier;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.core.result.XResultLong;
import srojak.core.result.XResultLongCarrier;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;
import srojak.core.tools.BitMethods;

/**
 * @author Stephen
 *
 */
public class ReflectedField
		extends ReflectedMemberBase
		implements ReflectedItem, ReflectedNonConstructor {
	private final Field _field;
	
	private static final ObservedActivity _activityGet = new SingleActivity("get access");
	private static final ObservedActivity _activitySet = new SingleActivity("set access");
	
	public static ReflectedField enclose(Field field) {
		return new ReflectedField(field);
	}
	
	public ReflectedField(Field field) {
		Objects.requireNonNull(field, "field");
		_field = field;
	}
	
	@Override
	protected Member getMember() {
		return _field;
	}

	@Override
	public boolean isSynthetic() {
		return _field.isSynthetic();
	}

	@Override
	public boolean isStatic() {
		return BitMethods.test(_field.getModifiers(), Modifier.STATIC);
	}

	@Override
	public void setAccessible(boolean bState) {
		_field.setAccessible(bState);
	}

	@Override
	public XResult trySetAccessible(boolean bState) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySetAccess);
		try {
			_field.setAccessible(bState);
			result.setValid();
		} catch (InaccessibleObjectException exc) {
			result.caughtException(exc);
		}
		return result;
	}

	@Override
	public boolean canAccess(Object obj) {
		return _field.canAccess(obj);
	}
	
	@Override
	public boolean isFinal() {
		return BitMethods.test(_field.getModifiers(), Modifier.FINAL);
	}

	public Class<?> getDeclaredType() {
		return _field.getType();
	}
	
	public boolean isEnumConstant() {
		return _field.isEnumConstant();
	}

	@Override
	public boolean hasAnnotations() {
		return _field.getAnnotations().length > 0;
	}

	@Override
	public Stream<Annotation> getAnnotationsAsStream() {
		return Arrays.stream(_field.getAnnotations());
	}
	
	public XResultOf<Object> get(Object objInstance) {
		XResultCarrierOf<Object> result = new XResultCarrierOf<Object>(_activityGet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			Object value = _field.get(objInstance);
			result.setResult(value);
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResultBoolean getBoolean(Object objInstance) {
		XResultBooleanCarrier result = new XResultBooleanCarrier(_activityGet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			boolean value = _field.getBoolean(objInstance);
			result.setResult(value);
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResultChar getChar(Object objInstance) {
		XResultCharCarrier result = new XResultCharCarrier(_activityGet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			char value = _field.getChar(objInstance);
			result.setResult(value);
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResultInt getInt(Object objInstance) {
		XResultIntCarrier result = new XResultIntCarrier(_activityGet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			int value = _field.getInt(objInstance);
			result.setResult(value);
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResultLong getLong(Object objInstance) {
		XResultLongCarrier result = new XResultLongCarrier(_activityGet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			long value = _field.getLong(objInstance);
			result.setResult(value);
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResultFloat getFloat(Object objInstance) {
		XResultFloatCarrier result = new XResultFloatCarrier(_activityGet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			float value = _field.getFloat(objInstance);
			result.setResult(value);
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResultDouble getDouble(Object objInstance) {
		XResultDoubleCarrier result = new XResultDoubleCarrier(_activityGet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			double value = _field.getDouble(objInstance);
			result.setResult(value);
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult set(Object objInstance, Object value) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			_field.set(objInstance, value);
			result.setValid();
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult setBoolean(Object objInstance, boolean value) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			_field.setBoolean(objInstance, value);
			result.setValid();
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult setByte(Object objInstance, byte value) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			_field.setByte(objInstance, value);
			result.setValid();
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult setChar(Object objInstance, char value) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			_field.setChar(objInstance, value);
			result.setValid();
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult setShort(Object objInstance, short value) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			_field.setShort(objInstance, value);
			result.setValid();
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult setInt(Object objInstance, int value) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			_field.setInt(objInstance, value);
			result.setValid();
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult setLong(Object objInstance, long value) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			_field.setLong(objInstance, value);
			result.setValid();
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult setFloat(Object objInstance, float value) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			_field.setFloat(objInstance, value);
			result.setValid();
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult setDouble(Object objInstance, double value) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activitySet);
		if (!isStatic()) {
			Objects.requireNonNull(objInstance, "objInstance");
		}
		try {
			_field.setDouble(objInstance, value);
			result.setValid();
		} catch (IllegalArgumentException exc) {
			result.caughtException(exc);
		} catch (IllegalAccessException exc) {
			result.caughtException(exc);
		}
		return result;
	}
}
