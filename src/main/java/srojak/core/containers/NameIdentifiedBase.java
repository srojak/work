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
package srojak.core.containers;

import java.util.Objects;

import srojak.core.NameIdentified;

/**
 * @author Stephen
 *
 */
public abstract class NameIdentifiedBase
		implements NameIdentified {
	private final String _strName;
	
	public NameIdentifiedBase(String strName) {
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
	
	protected abstract boolean canBeComparedTo(NameIdentifiedBase other);

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NameIdentifiedBase other) {
			if (canBeComparedTo(other)) {
				return _strName.equals(other._strName);
			}
		}
		return false;
	}

	@Override
	public int compareToString(String other) {
		Objects.requireNonNull(other, "other");
		return _strName.compareTo(other);
	}
	
	protected String makeTaggedName(String strTag) {
		Objects.requireNonNull(strTag, "strTag");
		return strTag + "[" + _strName + "]";
	}

}
