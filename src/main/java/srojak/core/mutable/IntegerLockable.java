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
package srojak.core.mutable;

import java.util.Objects;

import srojak.core.Lockable;
import srojak.core.logic.LockGate;

/**
 * @author Stephen
 *
 */
public class IntegerLockable 
		extends IntegerMutable 
		implements Lockable {
	private final LockGate _lock;

	private static final String _className = IntegerLockable.class.getSimpleName();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9076642679871288743L;
	
	/**
	 * @param valueInitial
	 */
	public IntegerLockable(int valueInitial) {
		super(valueInitial);
		_lock = new LockGate();
	}

	@Override
	public boolean isLocked() {
		return _lock.isLocked();
	}

	@Override
	public void lock() {
		_lock.lock();
	}

	@Override
	public boolean setValue(int valueNew) {
		_lock.testLock(_className);
		return super.setValue(valueNew);
	}
	
	public static IntegerLockable parse(String str)
			throws NumberFormatException {
		Objects.requireNonNull(str, "str");
		return new IntegerLockable(Integer.parseInt(str));
	}
}
