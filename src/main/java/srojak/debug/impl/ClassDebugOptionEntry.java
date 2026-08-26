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
package srojak.debug.impl;

import java.util.Objects;

import srojak.core.NameComparable;
import srojak.debug.DebugOptionNameValue;

/**
 * @author Stephen
 *
 */
public class ClassDebugOptionEntry
		implements NameComparable, DebugOptionNameValue {
	
	private final String _name;
	private int _value;
	
	public ClassDebugOptionEntry(String strName, int nValue) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_name = strName;
		_value = nValue;
	}
	
	public ClassDebugOptionEntry(String strName) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_name = strName;
		_value = 0;
	}

	@Override
	public String getName() {
		return _name;
	}
	
	@Override
	public int getValue() {
		return _value;
	}
	
	public void setValue(int nValue) {
		_value = nValue;
	}

	@Override
	public int compareToString(String other) {
		return _name.compareTo(other);
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof ClassDebugOptionEntry other) {
			return _name.equals(other._name);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "(name=\"" + _name + "\", value=" + _value + ")";
	}
}
