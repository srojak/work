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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import srojak.core.excepts.InvalidOperationException;

/**
 * @author Stephen
 *
 */
public abstract class AbstractSynthDelegate 
		implements SynthDelegate {
	private final Method _method;
	private boolean _valid;

	/**
	 * 
	 */
	public AbstractSynthDelegate(Method method) {
		_method = method;
		_valid = true;
	}

	@Override
	public boolean isValid() {
		return _valid;
	}
	
	@Override
	public String getMethodName() {
		return _method.getName();
	}

	@Override
	public Class<?> getReturnType() {
		return _method.getReturnType();
	}
	
	protected void invalidate() {
		_valid = false;
	}

	protected abstract Object getInstance();

	@Override
	public Object invoke(Object... args) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object objInstance = getInstance();
		if (!_valid) {
			throw new InvalidOperationException("marked invalid");
		}
		try {
			return _method.invoke(objInstance, args);
		} catch (IllegalAccessException e) {
			_valid = false;
			throw e;
		} catch (IllegalArgumentException e) {
			_valid = false;
			throw e;
		} catch (InvocationTargetException e) {
			_valid = false;
			throw e;
		}
	}

}
