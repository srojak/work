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
package srojak.core.logic;

import java.util.Objects;

import srojak.core.InvalidOperationException;
import srojak.core.Lockable;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public class LockGate
		implements Lockable {
	private boolean _bLocked;
	
	public LockGate() {
		_bLocked = false;
	}

	@Override
	public final boolean isLocked() {
		return _bLocked;
	}

	@Override
	public final void lock() {
		_bLocked = true;
	}
	
	protected void throwLockException(String strName, String strMessage) {
		throw new InvalidOperationException(strName, "object is locked");
	}
	
	public final void testLock(String strName) {
		Objects.requireNonNull(strName, "strName");
		if (_bLocked) {
			throw new InvalidOperationException(strName, "object is locked");
		}
	}

	public final void testLock(NameToken tokenName) {
		Objects.requireNonNull(tokenName, "tokenName");
		if (_bLocked) {
			throw new InvalidOperationException(tokenName.getName(), "object is locked");
		}
	}
}
