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

import srojak.core.IndirectReference;

/**
 * @author Stephen
 *
 */
public class IndirectStrongReference<T> 
		implements IndirectReference<T> {
	private final T _ref;
	
	public IndirectStrongReference(T referent) {
		Objects.requireNonNull(referent, "referent");
		_ref = referent;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public T get() {
		return _ref;
	}

}
