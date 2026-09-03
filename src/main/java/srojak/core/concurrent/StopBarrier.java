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
package srojak.core.concurrent;

import srojak.core.Disposable;

/**
 * @author Stephen
 *
 */
public class StopBarrier
		implements Disposable {
	private final Object _owner;
	private final StopGate _gate;
	private boolean _bDisposed;

	/**
	 * 
	 */
	StopBarrier(StopGate gate, Object objOwner) {
		_gate = gate;
		_owner = objOwner;
		_bDisposed = false;
	}

	public Object getOwner() {
		return _owner;
	}

	@Override
	public boolean isDisposed() {
		return _bDisposed;
	}

	@Override
	public void dispose() {
		_gate.removeBarrier(this);
		_bDisposed = true;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (!_bDisposed) {
			_gate.finalRemove(this);
		}
	}
}
