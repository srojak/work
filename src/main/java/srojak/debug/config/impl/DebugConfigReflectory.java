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
package srojak.debug.config.impl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import srojak.core.observe.ObservationCollectorCore;
import srojak.core.reflect.ReflectoryCommonBase;

/**
 * @author Stephen
 *
 */
public class DebugConfigReflectory 
		extends ReflectoryCommonBase {

	/**
	 * 
	 */
	public DebugConfigReflectory() {
		super();
	}

	@Override
	protected Class<?> getClassFrom(ObservationCollectorCore collObs, String strFullClassName)
			throws ClassNotFoundException {
		return Class.forName(strFullClassName);
	}

	@Override
	protected Class<?> getClassFrom(ObservationCollectorCore collObs, String strFullClassName, boolean bInitialize,
			ClassLoader loader) throws ClassNotFoundException {
		return Class.forName(strFullClassName, bInitialize, loader);
	}

	@Override
	protected Class<?> getClassFrom(ObservationCollectorCore collObs, Module module, String strFullClassName)
			throws ClassNotFoundException {
		return Class.forName(module, strFullClassName);
	}
	
	@Override
	protected void setAccessible(ObservationCollectorCore collObs, String strIdentifier, AccessibleObject obj, boolean bState) 
			throws SecurityException {
		obj.setAccessible(bState);
	}

	@Override
	protected Constructor<?> getDefaultConstructor(ObservationCollectorCore collObs, Class<?> classImpl)
			throws NoSuchMethodException, SecurityException {
		return classImpl.getConstructor();
	}

	@Override
	protected Constructor<?> getConstructor(ObservationCollectorCore collObs, Class<?> classImpl, Class<?>[] parameterTypes)
			throws NoSuchMethodException, SecurityException {
		return classImpl.getConstructor(parameterTypes);
	}

	@Override
	protected Object makeInstance(ObservationCollectorCore collObs, Constructor<?> constructor, Object[] args)
			throws Throwable {
		return constructor.newInstance(args);
	}

	@Override
	protected Method getMethod(ObservationCollectorCore collObs, Class<?> classOwner, String strMethodName,
			Class<?>[] parameterTypes) throws NoSuchMethodException, SecurityException {
		return classOwner.getMethod(strMethodName, parameterTypes);
	}

	@Override
	protected Method getDeclaredMethod(ObservationCollectorCore collObs, Class<?> classOwner, String strMethodName,
			Class<?>[] parameterTypes) throws NoSuchMethodException, SecurityException {
		return classOwner.getDeclaredMethod(strMethodName, parameterTypes);
	}
}
