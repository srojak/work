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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class GenericTypeInstance 
		implements ParameterizedType {
	private final Class<?> _classBase;
	private final Class<?>[] _classParam;
	
	public GenericTypeInstance(Class<?> classBase, Class<?> classParamFirst, Class<?> ... classParams) {
		Objects.requireNonNull(classBase, "classBase");
		Objects.requireNonNull(classParamFirst, "classParamFirst");
		_classBase = classBase;
		_classParam = new Class<?>[1 + classParams.length];
		_classParam[0] = classParamFirst;
		for (int i = 0; i < classParams.length; i++) {
			_classParam[i + 1] = classParams[i];
		}
	}

	@Override
	public Type[] getActualTypeArguments() {
		return _classParam;
	}

	@Override
	public Type getRawType() {
		return _classBase;
	}

	@Override
	public Type getOwnerType() {
		return null;
	}

}
