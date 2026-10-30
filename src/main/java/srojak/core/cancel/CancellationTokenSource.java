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
package srojak.core.cancel;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;

/**
 * @author Stephen
 *
 */
public final class CancellationTokenSource 
		implements CancellationSource {
	private final AtomicBoolean _bCancelled;
	private final List<CancellationActiveBase> _listTokens;
	private final DisposedListener _listenDisposed;
	
	public CancellationTokenSource() {
		_bCancelled = new AtomicBoolean(false);
		_listTokens = new LinkedList<CancellationActiveBase>();
		_listenDisposed = new DisposedListener();
	}

	@Override
	public Cancellation getToken() {
		CancellationToken token = new CancellationToken(_bCancelled);
		_listTokens.add(token);
		token.addLifeCycleListener(_listenDisposed);
		return token;
	}

	@Override
	public CancellationNotifying getNotifyingToken() {
		CancellationNotifyingToken token = new CancellationNotifyingToken(_bCancelled);
		_listTokens.add(token);
		token.addLifeCycleListener(_listenDisposed);
		return token;
	}

	@Override
	public boolean isCancelRequested() {
		return _bCancelled.get();
	}

	@Override
	public void cancel() {
		if (_bCancelled.compareAndSet(false, true)) {
			List<CancellationActiveBase> listNotify = List.copyOf(_listTokens);
			listNotify.forEach(t -> t.cancelNotify());
		}
	}
	
	@Override
	public void reset() {
		_bCancelled.set(false);
	}

	private class DisposedListener
			implements LifeCycleListener {

		@Override
		public void receive(LifeCycleEvent event) {
			if (event.getID() == LifeCycleEvent.ID_DISPOSED) {
				_listTokens.remove(event.getSource());
			}
		}
		
	}
}
