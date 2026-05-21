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
package srojak.core;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class NamedValueBase 
		implements INamed {
	private final String _strName;
	
	public NamedValueBase(String strName) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isEmpty()) {
			throw new IllegalArgumentException("strName is empty");
		}
		_strName = strName;
	}

	@Override
	public String getName() {
		return _strName;
	}

	@Override
	public int hashCode() {
		return _strName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamedValueBase other = (NamedValueBase) obj;
		return Objects.equals(_strName, other._strName);
	}

}
