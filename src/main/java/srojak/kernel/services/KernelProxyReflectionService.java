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
package srojak.kernel.services;

import srojak.core.reflect.PackageClassLocator;
import srojak.core.reflect.ParameterTypeSet;
import srojak.core.reflect.ReflectionProxy;
import srojak.core.result.XResultClass;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public interface KernelProxyReflectionService 
		extends KernelService {

	XResultClass findClass(ReflectionProxy proxy, PackageClassLocator locator);
	
	<T> XResultOf<T> createInstanceOf(Class<T> classTarget, PackageClassLocator locator);
	<T> XResultOf<T> createInstanceOf(ReflectionProxy proxy, Class<T> classTarget, PackageClassLocator locator);
	<T> XResultOf<T> createInstanceOf(ReflectionProxy proxy, Class<T> classTarget, PackageClassLocator locator,
			ParameterTypeSet typeParams, Object ... params);
}
