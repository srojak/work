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
package srojak.core.keys;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class NameEquatableKeyBase 
		implements NameEquatableKey {
	protected final String _name;
	
	public NameEquatableKeyBase(String strName) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_name = strName;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int compareToString(String other) {
		Objects.requireNonNull(other, "other");
		return _name.compareTo(other);
	}

	@Override
	public boolean isNameEqual(String strName) {
		return _name.equals(strName);
	}
	
	protected abstract long getRootId();
	
	protected abstract String getRootName();
	
	@Override
	public int hashCode() {
		return Objects.hash(getRootId(), _name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj instanceof NameEquatableKeyBase other) {
			return getRootId() == other.getRootId()
					&& _name.equals(other._name);
		} else
			return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getRootName());
		sb.append('[');
		sb.append(_name);
		sb.append(']');
		return sb.toString();
	}
}
