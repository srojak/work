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
package srojak.mantle.atoms;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class AtomBase 
		implements Atom {
	private final AtomType _type;
	private final String _name;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1499482764117167929L;
	
	protected AtomBase(AtomType typeAtom, String strName) {
		Objects.requireNonNull(typeAtom, "typeAtom");
		Objects.requireNonNull(strName, "strName");
		_type = typeAtom;
		_name = strName;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int compareToString(String other) {
		return _type.compare(this, other);
	}

	@Override
	public long series() {
		return _type.series();
	}

	@Override
	public int compareTo(Atom o) {
		return _type.compare(this, o);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(_type.series(), _name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj instanceof Atom other) {
			return _type.series() == other.series()
					&& _type.compare(this, other) == 0;
		} else
			return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(_type.getName());
		sb.append('[');
		sb.append(_name);
		sb.append(']');
		return sb.toString();
	}
}
