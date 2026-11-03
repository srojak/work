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

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class CodeValueEnum<T extends Enum<T>> 
		extends CodeValueCommonBase {
	private final T _value;

	/**
	 * @param strName
	 * @param classValue
	 */
	public CodeValueEnum(String strName, Class<T> classValue, T value) {
		super(strName, classValue);
		Objects.requireNonNull(value, "value");
		_value = value;
	}

	@Override
	protected String formatValue() {
		return _value.toString();
	}

	@Override
	public Object value() {
		return _value;
	}

}
