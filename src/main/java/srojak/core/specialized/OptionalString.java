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
package srojak.core.specialized;

import java.util.Objects;

import srojak.core.StringComparable;

/**
 * @author Stephen
 *
 */
public class OptionalString
		implements StringComparable {
	private final String _strValue;
	
	public OptionalString(String strValue) {
		Objects.requireNonNull(strValue, "strValue");
		_strValue = strValue;
	}
	
	public OptionalString() {
		_strValue = null;
	}
	
	public boolean hasValue() {
		return _strValue != null;
	}
	
	public String getValue() {
		return _strValue;
	}

	@Override
	public int compareToString(String other) {
		if (_strValue == null) {
			return -1;
		} else if (other == null) {
			return 1;
		} else if (_strValue == other) {
			return 0;
		} else {
			return _strValue.compareTo(other);
		}
	}

	@Override
	public String toString() {
		if (_strValue == null) {
			return "(null)";
		} else {
			return _strValue;
		}
	}
}
