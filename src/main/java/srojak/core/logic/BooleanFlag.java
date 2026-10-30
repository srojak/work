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

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Stephen
 *
 */
public class BooleanFlag 
		implements FlagReadOnly {
	private final AtomicBoolean _flag;
	
	public BooleanFlag(boolean stateInitial) {
		_flag = new AtomicBoolean(stateInitial);
	}
	
	protected void stateChangedTo(boolean bState) {
		// base class method does nothing
	}
	
	public BooleanFlag() {
		this(false);
	}

	@Override
	public boolean getState() {
		return _flag.get();
	}
	
	public void set() {
		boolean bPrior = _flag.getAndSet(true);
		if (!bPrior) {
			stateChangedTo(true);
		}
	}

	public void clear() {
		boolean bPrior = _flag.getAndSet(false);
		if (bPrior) {
			stateChangedTo(false);
		}
	}
	
	/**
	 * Sets the state of the flag.
	 * @param bState The new state of the flag.
	 * @return {@code true} if the state of the flag <b>changed</b>.
	 */
	public boolean setState(boolean bState) {
		boolean bPrior = _flag.getAndSet(bState);
		if (bPrior != bState) {
			stateChangedTo(bState);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setConditional(boolean expectedValue, boolean newValue) {
		if (_flag.compareAndSet(expectedValue, newValue)) {
			stateChangedTo(newValue);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean testAndSet(boolean newValue) {
		boolean bPrior = _flag.getAndSet(newValue);
		if (bPrior != newValue) {
			stateChangedTo(newValue);
		}
		return bPrior;
	}
}
