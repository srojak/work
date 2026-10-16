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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;

import srojak.core.observe.HasReplaceableObservationCollector;

/**
 * @author Stephen
 *
 */
public interface Reflectory
		extends HasReplaceableObservationCollector {

	Class<?> getClassFrom(PackageClassLocator locator)
			throws ClassNotFoundException;
	Class<?> getClassFrom(PackageClassLocator locator, boolean bInitialize,
						ClassLoader loader)
			throws ClassNotFoundException;
	Class<?> getClassFrom(Module module, PackageClassLocator locator)
			throws ClassNotFoundException;
	void setAccessible(String strIdentifier, AccessibleObject obj, boolean bState) 
			throws SecurityException;	
	Constructor<?> getDefaultConstructor(Class<?> classImpl)
			throws NoSuchMethodException, SecurityException;
	Constructor<?> getConstructor(Class<?> classImpl, ParameterTypeSet setParams)
			throws NoSuchMethodException, SecurityException;
	Object makeInstance(Constructor<?> constructor, Object ... args)
			throws Throwable;
}
