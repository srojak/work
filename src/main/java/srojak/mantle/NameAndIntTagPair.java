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

import srojak.core.NamedAndIntTagged;
import srojak.core.StringComparable;

/**
 * @author Stephen
 *
 */
public class NameAndIntTagPair
		implements NamedAndIntTagged, StringComparable, Comparable<NameAndIntTagPair> {
	private final int _tag;
	private final String _name;
	
	public NameAndIntTagPair(int nTag, String strName) {
		Objects.requireNonNull(strName, "strName");
		_tag = nTag;
		_name = strName;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public boolean isNameEqual(String strName) {
		return _name.equals(strName);
	}

	@Override
	public int getTagValue() {
		return _tag;
	}

	@Override
	public int compareToString(String other) {
		return _name.compareTo(other);
	}

	@Override
	public int compareTo(NameAndIntTagPair o) {
		if (this == o) {
			return 0;
		} else if (o == null) {
			return 1;
		} else {
			return _name.compareTo(o._name);
		}
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof NameAndIntTagPair other) {
			return _name.equals(other._name);
		}
		return false;
	}

}
