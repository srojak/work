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

import srojak.core.impl.NameTokenCommon;
import srojak.core.impl.NameTokenFixed;

/**
 * @author Stephen
 *
 */
public abstract sealed class NameTokenBase
		implements NameToken
		permits NameTokenFixed, NameTokenCommon {
	private final String _strName;
	
	public NameTokenBase(String strName) {
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
	
	protected abstract int getSeries();
	
	protected abstract String getLeaderTag();
	
	@Override
	public int hashCode() {
		return Objects.hash(getSeries(), _strName);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj instanceof NameTokenBase other) {
			return getSeries() == other.getSeries()
					&& _strName.equals(other._strName);
		} else
			return false;
	}

	@Override
	public int compareTo(NameToken o) {
		if (o == null)
			return 1;
		else {
			NameTokenBase other = (NameTokenBase) o;
			int nResult = Integer.compare(getSeries(), other.getSeries());
			if (nResult == 0) {
				nResult = _strName.compareTo(other._strName);
			}
			return nResult;
		}
	}

	@Override
	public int compareToString(String other) {
		Objects.requireNonNull(other, "other");
		return _strName.compareTo(other);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getLeaderTag());
		sb.append('[');
		sb.append(_strName);
		sb.append(']');
		return sb.toString();
	}
}
