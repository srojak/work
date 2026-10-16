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
package srojak.core.observe.code;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class CodeValueCommonBase
		implements CodeValue {
	private final String _name;
	private final Class<?> _classValue;
	
	public CodeValueCommonBase(String strName, Class<?> classValue) {
		Objects.requireNonNull(strName, "strName");
		Objects.requireNonNull(classValue, "classValue");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_name = strName;
		_classValue = classValue;
	}
	
	protected abstract String formatValue();

	@Override
	public final String name() {
		return _name;
	}

	@Override
	public Class<?> valueClass() {
		return _classValue;
	}

	@Override
	public abstract Object value();

	@Override
	public final String format() {
		if (_name.startsWith("*")) {
			return formatValue();
		} else {
			return _name + "=" + formatValue();
		}
	}	
}
