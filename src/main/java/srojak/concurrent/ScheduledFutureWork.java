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
package srojak.concurrent;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Stephen
 *
 */
public class ScheduledFutureWork 
		extends WorkHandleBase {
	private final ScheduledFuture<?> _future;
	
	public ScheduledFutureWork(ScheduledFuture<?> futureWork) {
		super(ERR_NONE);
		Objects.requireNonNull(futureWork, "futureWork");
		_future = futureWork;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public boolean canCancel() {
		return !_future.isDone();
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return _future.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return _future.isCancelled();
	}

	@Override
	public boolean isDone() {
		return _future.isDone();
	}
}
