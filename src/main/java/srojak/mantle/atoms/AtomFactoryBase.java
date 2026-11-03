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
public abstract class AtomFactoryBase<A extends Atom> {
	private final AtomType _type;
	
	public AtomFactoryBase(AtomType typeAtoms) {
		Objects.requireNonNull(typeAtoms, "typeAtoms");
		_type = typeAtoms;
	}

	public AtomType getAtomType() {
		return _type;
	}
	
	protected abstract A create(AtomType type, String strName);

	public A create(String strName) {
		Objects.requireNonNull(strName, "strName");
		if (strName.isBlank()) {
			throw new IllegalArgumentException("strName is blank");
		}
		if (strName.length() > _type.maxLength()) {
			throw new IllegalArgumentException("strName is too long for " + _type.getName());
		}
		return create(_type, strName);
	}
}
