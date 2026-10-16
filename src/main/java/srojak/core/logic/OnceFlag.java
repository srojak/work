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

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 * Although this is concurrency-ready, it has to be here because other classes in this module require it.
 */
public sealed class OnceFlag 
		implements OnceFlagState
		permits OnceFlagNamed {
	private final AtomicBoolean _bState;
	
	public OnceFlag() {
		_bState = new AtomicBoolean(false);
	}

	@Override
	public boolean isSet() {
		return _bState.get();
	}
	
	@Override
	public boolean isNamed() {
		return false;
	}
	
	@Override
	public NameToken getNameToken() {
		return null;
	}

	protected void throwStateException() {
		throw new IllegalStateException("flag already set");
	}
	
	public void set() {
		boolean bValue = _bState.compareAndSet(false, true);
		if (!bValue) {
			throwStateException();
		}
	}
}
