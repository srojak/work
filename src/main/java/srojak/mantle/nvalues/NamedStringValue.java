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
package srojak.mantle.nvalues;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class NamedStringValue 
		extends NamedValueBase 
		implements NameAndString {
	private String _value;

	/**
	 * @param strName
	 */
	public NamedStringValue(String strName, String strValue) {
		super(strName);
		Objects.requireNonNull(strValue, "strValue");
		_value = strValue;
	}

	@Override
	public String value() {
		return _value;
	}
	
	@Override
	public boolean isValueEqual(String other) {
		return _value.equals(other);
	}

	protected void validate(String strValue) {
		// base class method does nothing
	}

	public void setValue(String strValue) {
		Objects.requireNonNull(strValue, "strValue");
		validate(strValue);
		_value = strValue;
	}

	@Override
	protected void formatValue(StringBuilder sb) {
		sb.append('\"');
		sb.append(_value);
		sb.append('\"');
	}
}
