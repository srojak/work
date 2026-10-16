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
package srojak.core.io;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public final class IONodeName 
		implements IONodeIdentifier {
	private final IONodeType _type;
	private final String _name;
	
	/**
	 * 
	 */
	public IONodeName(IONodeType typeSource, String strName) {
		Objects.requireNonNull(typeSource, "typeSource");
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_type = typeSource;
		_name = strName;
	}

	@Override
	public IONodeType nodeType() {
		return _type;
	}

	@Override
	public String name() {
		return _name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_type, _name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof IONodeIdentifier other) {
			return isEqual(other);
		} else {
			return false;
		}
	}

	@Override
	public boolean isEqual(IONodeIdentifier obj) {
		if (obj == null) {
			return false;
		} else {
			return _type.equals(obj.nodeType()) && _name.equals(obj.name());
		}
	}

	@Override
	public String toString() {
		return "[" + _type + ": " + _name + "]";
	}
}
