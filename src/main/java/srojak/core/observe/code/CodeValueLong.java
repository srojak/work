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

import java.text.DecimalFormat;

/**
 * @author Stephen
 *
 */
public class CodeValueLong 
		extends CodeValueDecimalBase {
	private final long _value;

	/**
	 * @param strName
	 * @param formatDecimal
	 */
	public CodeValueLong(String strName, DecimalFormat formatDecimal, long value) {
		super(strName, long.class, formatDecimal);
		_value = value;
	}

	@Override
	public Object value() {
		return Long.valueOf(_value);
	}

	@Override
	protected String formatValue() {
		return _formatter.format(_value);
	}

}
