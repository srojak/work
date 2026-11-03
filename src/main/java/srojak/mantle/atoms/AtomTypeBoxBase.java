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
import java.util.function.IntBinaryOperator;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public abstract class AtomTypeBoxBase 
		implements AtomType {
	private final String _name;
	
	protected AtomTypeBoxBase(String strName) {
		Objects.requireNonNull(strName, "strName");
		_name = strName;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public abstract long series();
	
	@Override
	public int maxLength() {
		return Integer.MAX_VALUE;
	}

	protected abstract int stringComparer(String s1, String s2);

	@Override
	public int compare(Atom a1, Atom a2) {
		Objects.requireNonNull(a1, "a1");
		Objects.requireNonNull(a2, "a2");
		if (a1.series() != series()) {
			throw new IllegalArgumentException("a1 is not of this series");
		}
		if (a2.series() != series()) {
			throw new  IllegalArgumentException("a2 is not of this series");
		}
		return stringComparer(a1.getName(), a2.getName());
	}

	@Override
	public int compare(Atom atom, String str) {
		Objects.requireNonNull(atom, "atom");
		if (atom.series() != series()) {
			throw new IllegalArgumentException("atom is not of this series");
		}
		return stringComparer(atom.getName(), str);
	}

}
