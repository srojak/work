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

/**
 * @author Stephen
 *
 */
public abstract class OnceBase {
	private final FlagsInt _flags;
	
	protected static int ONCE_DONE = 0x1;
	protected static int ONCE_READY = 0x2;
	
	/**
	 * 
	 */
	public OnceBase(boolean bIsReady) {
		_flags = new FlagsInt();
		if (bIsReady) {
			_flags.set(ONCE_READY);
		}
	}
	
	public boolean isReady() {
		return _flags.test(ONCE_READY);
	}
	
	public void markReady() {
		_flags.set(ONCE_READY);
	}
	
	protected void faultIfNotReady() {
		if (!_flags.test(ONCE_READY)) {
			throw new IllegalStateException("not ready to execute");
		}
	}

	public boolean isDone() {
		return _flags.test(ONCE_DONE);
	}
	
	protected void markDone() {
		_flags.set(ONCE_DONE);
	}
}
