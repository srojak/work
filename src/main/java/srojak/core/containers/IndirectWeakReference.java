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

import java.lang.ref.WeakReference;
import java.util.Objects;

import srojak.core.IndirectReference;

/**
 * @author Stephen
 *
 */
public class IndirectWeakReference<T> 
		implements IndirectReference<T> {
	private final WeakReference<T> _ref;
	private boolean _isValid;
	
	public IndirectWeakReference(T referent) {
		Objects.requireNonNull(referent, "referent");
		_ref = new WeakReference<T>(referent);
		_isValid = true;
	}

	@Override
	public boolean isValid() {
		return _isValid;
	}

	@Override
	public T get() {
		if (_isValid) {
			T referent = _ref.get();
			if (referent == null) {
				_isValid = false;
			}
			return referent;
		} else {
			return null;
		}
	}
}
