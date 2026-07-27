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

/**
 * @author Stephen
 *
 */
public sealed class OnceFlag 
		permits OnceFlagNamed {
	private boolean _bState;
	
	public OnceFlag() {
		_bState = false;
	}

	public boolean getState() {
		return _bState;
	}
	
	public boolean isNamed() {
		return false;
	}
	
	protected void throwStateException() {
		throw new IllegalStateException("flag already set");
	}
	
	public void set() {
		if (_bState) {
			throwStateException();
		}
		_bState = true;
	}
	
	public void faultIfSet() {
	}
}
