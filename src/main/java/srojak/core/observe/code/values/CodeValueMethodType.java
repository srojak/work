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
package srojak.core.observe.code.values;

import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.List;

import srojak.core.observe.impl.NullObject;

/**
 * @author Stephen
 *
 */
public class CodeValueMethodType 
		extends CodeValueCommonBase {
	private final MethodType _typeMethod;

	/**
	 * @param strName
	 * @param classValue
	 */
	public CodeValueMethodType(String strName, MethodType typeMethod) {
		super(strName, MethodType.class);
		if (typeMethod == null) {
			_typeMethod = MethodType.methodType(NullObject.class);
		} else {
			_typeMethod = typeMethod;
		}
	}

	@Override
	public Object value() {
		return _typeMethod;
	}

	@Override
	protected String formatValue() {
		StringBuilder sb = new StringBuilder("methodType(");
		sb.append(_typeMethod.returnType().getSimpleName());
		List<Class<?>> listParams = _typeMethod.parameterList();
		if (!listParams.isEmpty()) {
			sb.append(", params[");
			Iterator<Class<?>> iterator = listParams.iterator();
			Class<?> classParam = iterator.next();
			sb.append(classParam.getSimpleName());
			while (iterator.hasNext()) {
				classParam = iterator.next();
				sb.append(", ");
				sb.append(classParam.getSimpleName());
			}
			sb.append(']');
		}
		sb.append(')');
		return sb.toString();
	}
}
