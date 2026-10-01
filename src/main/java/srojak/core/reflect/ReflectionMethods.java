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

import java.lang.invoke.MethodHandle;
import java.util.Objects;

import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public class ReflectionMethods 
		implements ReflectionActivityNames {

	public static XResultOf<Object> callDefaultConstructor(MethodHandle hConstructor) {
		Objects.requireNonNull(hConstructor, "hConstructor");
		XResultCarrierOf<Object> result = new XResultCarrierOf<Object>(ACTIVITY_CREATE_INST);
		try {
			Object objInstance = hConstructor.invoke();
			result.setResult(objInstance);
		} catch (Throwable t) {
			result.caughtThrowable(t);
		}
		return result;
	}
}
