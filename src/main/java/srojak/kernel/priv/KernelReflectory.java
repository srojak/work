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
package srojak.kernel.priv;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;

import srojak.core.observe.ObservationCollector;
import srojak.core.reflect.ReflectoryCommonBase;

/**
 * @author Stephen
 *
 */
public final class KernelReflectory 
		extends ReflectoryCommonBase {

	/**
	 * 
	 */
	public KernelReflectory() {
		super();
	}

	@Override
	protected Class<?> getClassFrom(ObservationCollector collObs, String strFullClassName)
			throws ClassNotFoundException {
		return Class.forName(strFullClassName);
	}

	@Override
	protected Class<?> getClassFrom(ObservationCollector collObs, String strFullClassName, boolean bInitialize,
			ClassLoader loader) throws ClassNotFoundException {
		return Class.forName(strFullClassName, bInitialize, loader);
	}

	@Override
	protected Class<?> getClassFrom(ObservationCollector collObs, Module module, String strFullClassName)
			throws ClassNotFoundException {
		return Class.forName(module, strFullClassName);
	}

	@Override
	protected Constructor<?> getDefaultConstructor(ObservationCollector collObs, Class<?> classImpl)
			throws NoSuchMethodException, SecurityException {
		return classImpl.getConstructor();
	}

	@Override
	protected Constructor<?> getConstructor(ObservationCollector collObs, Class<?> classImpl,
			Class<?>[] parameterTypes) 
					throws NoSuchMethodException, SecurityException {
		return classImpl.getConstructor(parameterTypes);
	}
	
	@Override
	protected void setAccessible(ObservationCollector collObs, String strIdentifier, AccessibleObject obj, boolean bState) 
			throws SecurityException {
		obj.setAccessible(bState);
	}

	@Override
	protected Object makeInstance(ObservationCollector collObs, Constructor<?> constructor, Object[] args)
			throws Throwable {
		return constructor.newInstance(args);
	}

}
