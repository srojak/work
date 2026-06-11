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

import srojak.core.Named;

/**
 * @author Stephen
 *
 */
public class InstanceKey
		implements Named {
	private final Class<?> _classOwner;
	private final int _instance;
	private final String _name;

	/**
	 * 
	 */
	public InstanceKey(Object objOwner, String strName) {
		Objects.requireNonNull(objOwner);
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		_classOwner = objOwner.getClass();
		_instance = System.identityHashCode(objOwner);
		_name = strName;
	}

	@Override
	public String getName() {
		return _name;
	}
	
	public Class<?> getOwnerClass() {
		return _classOwner;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("key");
		sb.append('[');
		sb.append(_classOwner.getSimpleName());
		sb.append('#');
		sb.append(Integer.toHexString(_instance));
		sb.append(", ");
		sb.append(_name);
		sb.append(']');
		return sb.toString();
	}
}
