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
package srojak.concurrent.locking;

import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import srojak.concurrent.ThreadMethods;
import srojak.core.observe.HasSingleObservationCollector;
import srojak.core.observe.ObservationCollectorCore;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.TraceLevel;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.observe.code.CodeValueMethods;

/**
 * @author Stephen
 *
 */
public class VisibleSemaphore 
		extends Semaphore 
		implements HasSingleObservationCollector {
	private ObservationCollectorCore _collectObs;
	// TODO finish putting tracing in all the methods
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4708428061089944319L;
	private static final ObservedActivity ACTIVITY_WAIT = new SingleActivity("wait on semaphore");

	/**
	 * @param permits
	 */
	public VisibleSemaphore(ObservationCollectorCore collector, int permits) {
		super(permits);
		Objects.requireNonNull(collector, "collector");
		_collectObs = collector;
		_collectObs.traceCodeEnter(TraceLevel.HIGH, cb -> {
			cb.appendContent("thread ");
			cb.appendContent(ThreadMethods.identifyCurrentThread());
			cb.addNamedValue(CodeValueMethods.capture("permits", permits));
		});
	}

	/**
	 * @param permits
	 * @param fair
	 */
	public VisibleSemaphore(ObservationCollectorCore collector, int permits, boolean fair) {
		super(permits, fair);
		Objects.requireNonNull(collector, "collector");
		_collectObs = collector;
		_collectObs.traceCodeEnter(TraceLevel.HIGH, cb -> {
			cb.appendContent("thread ");
			cb.appendContent(ThreadMethods.identifyCurrentThread());
			cb.addNamedValue(CodeValueMethods.capture("permits", permits));
			cb.addNamedValue(CodeValueMethods.capture("fair", fair));
		});
	}

	@Override
	public ObservationCollectorCore getObservationCollector() {
		return _collectObs;
	}

	@Override
	public void acquire() 
			throws InterruptedException {
		_collectObs.traceCodeEnter(TraceLevel.HIGH, cb -> {
			cb.appendContent("thread ");
			cb.appendContent(ThreadMethods.identifyCurrentThread());
			cb.addNamedValue(CodeValueMethods.capture("permits", super.availablePermits()));
		});
		try {
			super.acquire();
			_collectObs.traceReturn(TraceLevel.HIGH);
		} catch (InterruptedException exc) {
			_collectObs.traceThrowing(TraceLevel.HIGH, ACTIVITY_WAIT, exc);
			throw exc;
		}
	}

	@Override
	public void acquireUninterruptibly() {
		// TODO Auto-generated method stub
		super.acquireUninterruptibly();
	}

	@Override
	public boolean tryAcquire() {
		// TODO Auto-generated method stub
		return super.tryAcquire();
	}

	@Override
	public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return super.tryAcquire(timeout, unit);
	}

	@Override
	public void release() {
		_collectObs.traceCodeEnter(TraceLevel.HIGH, cb -> {
			cb.appendContent("thread ");
			cb.appendContent(ThreadMethods.identifyCurrentThread());
			cb.addNamedValue(CodeValueMethods.capture("permits", super.availablePermits()));
		});
		super.release();
	}

	@Override
	public void acquire(int permits) throws InterruptedException {
		// TODO Auto-generated method stub
		super.acquire(permits);
	}

	@Override
	public void acquireUninterruptibly(int permits) {
		// TODO Auto-generated method stub
		super.acquireUninterruptibly(permits);
	}

	@Override
	public boolean tryAcquire(int permits) {
		// TODO Auto-generated method stub
		return super.tryAcquire(permits);
	}

	@Override
	public boolean tryAcquire(int permits, long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return super.tryAcquire(permits, timeout, unit);
	}

	@Override
	public void release(int permits) {
		// TODO Auto-generated method stub
		super.release(permits);
	}

}
