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

import srojak.core.observe.ObservationCommon;

/**
 * @author Stephen
 *
 */
public class CodeValueString
		extends CodeValueCommonBase {
	private final String _value;

	/**
	 * @param strName
	 */
	public CodeValueString(String strName, String strValue) {
		super(strName, String.class);
		_value = strValue;
	}

	@Override
	public Object value() {
		return _value;
	}

	@Override
	protected String formatValue() {
		if (_value == null) {
			return ObservationCommon.STR_NULL;
		} else {
			return "\"" + _value + "\"";
		}
	}

}
