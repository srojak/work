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
package srojak.mantle;

import java.util.Objects;

import srojak.core.Named;

/**
 * @author Stephen
 *
 */
public class OptionalName 
		implements Named {
	private final String _name;
	
	public static final OptionalName ANON = new OptionalName();
	
	private OptionalName() {
		_name = null;
	}

	public OptionalName(String strName) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_name = strName;
	}
	
	public boolean isNamed() {
		return _name != null;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		return _name == null ? 0 : _name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof OptionalName other) {
			if (_name == null) {
				return other._name == null;
			} else {
				return _name.equals(other._name);
			}
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		if (_name == null) {
			return "*anon";
		} else {
			return _name;
		}
	}
	
	public static OptionalName from(String strText) {
		if (strText == null || strText.isBlank()) {
			return ANON;
		} else {
			return new OptionalName(strText);
		}
	}
}
