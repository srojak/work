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
package srojak.mantle.reflect;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Consumer;

import srojak.core.observe.ObsLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class ReflectTool {
	private final DebugSwitch _swDebug;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = ReflectTool.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * 
	 */
	public ReflectTool() {
		_swDebug = _swDebugClass;
	}

	public ReflectTool(DebugSwitch swDebug) {
		Objects.requireNonNull(swDebug, "swDebug");
		_swDebug = swDebug;
	}
	
	/**
	 * 
	 * The module containing a class that uses this must be open to srojak.mantle.
	 * 
	 * @param classFields
	 * @param objOwner
	 * @param consumer
	 * @return
	 * @see java.lang.reflect.Field.setAccessible
	 * @see java.lang.reflect.Field.get
	 */
	public <T> boolean overDeclaredFieldsOfType(Class<?> classFields, Object objOwner, Consumer<T> consumer) {
		Class<?> classOwner = objOwner.getClass();
		Field[] fields = classOwner.getDeclaredFields();
		for (Field f : fields) {
			final Field fx = f;
			if (fx.isSynthetic()) {
				continue;
			}
			if (classFields.isAssignableFrom(fx.getType())) {
				fx.setAccessible(true);
				Object objField = null;
				try {
					objField = fx.get(objOwner);
				} catch (IllegalArgumentException exc) {
					_swDebug.writeException(ObsLevel.ERROR, exc, true);
					return false;
				} catch (IllegalAccessException exc) {
					_swDebug.write(ObsLevel.ALERT, () -> "illegal access to "
						+ f.getName() + " in class " + classOwner.getName());
					_swDebug.writeException(ObsLevel.ERROR, exc, false);
					return false;
				}
				@SuppressWarnings("unchecked")
				T valueField = (T) classFields.cast(objField);
				consumer.accept(valueField);
			}
		}
		return true;
	}
}
