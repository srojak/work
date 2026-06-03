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

/**
 * @author Stephen
 *
 */
public final class OnceFlag 
		implements NameTokenBearing {
	private final NameToken _tokenName;
	private boolean _bState;
	
	public OnceFlag(NameToken name) {
		Objects.requireNonNull(name, "name");
		_tokenName = name;
		_bState = false;
	}
	
	@Override
	public NameToken getNameToken() {
		return _tokenName;
	}

	@Override
	public boolean isNameTokenEqual(NameToken token) {
		return token == null ? false : _tokenName.equals(token);
	}

	public boolean getState() {
		return _bState;
	}
	
	public void set() {
		if (_bState) {
			throw new IllegalStateException("flag " + _tokenName.getName() + " already set");
		}
		_bState = true;
	}
	
	public void faultIfSet() {
	}
}
