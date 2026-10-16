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
package srojak.kernel.services.lib;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import srojak.concurrent.FutureWork;
import srojak.concurrent.InvalidWorkHandle;
import srojak.concurrent.ScheduledFutureWork;
import srojak.concurrent.WorkHandle;
import srojak.core.collections.ActiveAndRunnableList;
import srojak.core.containers.SingletonContainer;
import srojak.core.field.SetOnce;
import srojak.kernel.ExecutorServiceHandle;
import srojak.kernel.KernelCentralStore;
import srojak.kernel.KernelServiceException;
import srojak.kernel.ServiceNames;
import srojak.kernel.endpoints.CalendarEndpoint;
import srojak.kernel.endpoints.CalendarEndpointObj;
import srojak.kernel.endpoints.ScheduleAtMidnightTask;
import srojak.kernel.endpoints.ScheduleEverySecondTask;
import srojak.kernel.endpoints.StrobeEndpoint;
import srojak.kernel.endpoints.StrobeEndpointObj;
import srojak.kernel.impl.KernelEndpointHome;
import srojak.kernel.impl.KeyedExecutor;
import srojak.kernel.impl.KeyedExecutorService;
import srojak.kernel.impl.KeyedScheduledExecutorService;
import srojak.kernel.priv.KernelStore;
import srojak.kernel.services.AppKernelService;
import srojak.kernel.services.DependsOnService;
import srojak.kernel.services.KernelScheduleService;
import srojak.kernel.services.KernelServiceProviderBase;

/**
 * @author Stephen
 *
 */
@AppKernelService(name = ServiceNames.SCHEDULER, lazyLoad = false, mustShutDown = true)
@DependsOnService(ServiceNames.ENDPOINT)
public class ScheduleServiceProvider
		extends KernelServiceProviderBase 
		implements KernelScheduleService {
	private final SetOnce<KernelEndpointHome> _svcEndpoint;
	private final LinkedList<KeyedExecutor> _listExecutors;
	
	private final ScheduledExecutorService _poolStrobe;
	private final ScheduledExecutorService _poolCalendar;
	private final SingletonContainer<ScheduledFuture<?>> _ctnrSched;
	private final SingletonContainer<ScheduledFuture<?>> _ctnrCalendar;
	private final ActiveAndRunnableList _listRunEverySecond;
	private final ActiveAndRunnableList _listRunAtMidnight;
	private LocalDateTime _dtMostRecent;
	
	// for general scheduling
	private final ExecutorService _poolExec;
	
	private static final long NANOS_TO_ADD = 1500L * 1000L * 1000L;
	private static final Duration DUR_ONE_DAY = Duration.ofDays(1L);

	/**
	 * 
	 */
	public ScheduleServiceProvider() {
		_svcEndpoint = new SetOnce<KernelEndpointHome>(SetOnce.DEFAULT);
		_listExecutors = new LinkedList<KeyedExecutor>();
		// TODO: get pool sizes from properties
		_poolStrobe = Executors.newScheduledThreadPool(2);
		_ctnrSched = new SingletonContainer<ScheduledFuture<?>>();
		_poolCalendar = Executors.newScheduledThreadPool(2);
		_ctnrCalendar = new SingletonContainer<ScheduledFuture<?>>();
		_listRunEverySecond = new ActiveAndRunnableList();
		_listRunAtMidnight = new ActiveAndRunnableList();
		_poolExec = Executors.newFixedThreadPool(3);
		_dtMostRecent = null;
	}

	@Override
	public void initialize(KernelCentralStore store) 
			throws KernelServiceException {
		_svcEndpoint.set(KernelStore.getServiceOfType(ServiceNames.ENDPOINT));
		_dtMostRecent = LocalDateTime.now();
		ScheduledFuture<?> sched = _poolStrobe.scheduleAtFixedRate(new EverySecond(), 1, 1, TimeUnit.SECONDS);
		_ctnrSched.set(sched);
		
		Duration durSinceMidnight = Duration.between(LocalTime.MIDNIGHT, _dtMostRecent.toLocalTime());
		Duration durToMidnight = DUR_ONE_DAY.minus(durSinceMidnight);
		sched = _poolCalendar.scheduleAtFixedRate(new AtMidnight(), durToMidnight.toSeconds(),
				DUR_ONE_DAY.toSeconds(), TimeUnit.SECONDS);
		_ctnrCalendar.set(sched);
	}

	@Override
	public void shutdown() {
		_poolStrobe.shutdownNow();
		_poolCalendar.shutdownNow();
		_poolExec.shutdownNow();
	}
	
	private void updateClock() {
		LocalDateTime dtNow = LocalDateTime.now();
		_dtMostRecent = dtNow;
	}

	@Override
	public LocalDate getMostRecentDate() {
		return _dtMostRecent.toLocalDate();
	}

	@Override
	public LocalTime getMostRecentTime() {
		return _dtMostRecent.toLocalTime();
	}

	@Override
	public ExecutorServiceHandle createScheduledThreadPool(int szPool) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(szPool);
		KeyedScheduledExecutorService keyedService = new KeyedScheduledExecutorService(service);
		_listExecutors.add(keyedService);
		return keyedService.key();
	}
	
	private KeyedExecutor find(ExecutorServiceHandle hExec) {
		Iterator<KeyedExecutor> iterator = _listExecutors.iterator();
		while (iterator.hasNext()) {
			KeyedExecutor item = iterator.next();
			if (item.isKeyEqual(hExec)) {
				return item;
			}
		}
		return null;
	}
	
	private void doEverySecond() {
		_listRunEverySecond.traverse(t -> _poolExec.submit(t));
	}
	
	private void doAtMidnight() {
		_listRunAtMidnight.traverse(t -> _poolExec.submit(t));
	}

	@Override
	public WorkHandle submit(ExecutorServiceHandle hExec, Runnable command) {
		Objects.requireNonNull(hExec, "hExec");
		Objects.requireNonNull(command, "command");
		KeyedExecutor kex = find(hExec);
		if (kex == null) {
			return new InvalidWorkHandle(WorkHandle.ERR_NO_SERVICE);
		}
		if (kex instanceof KeyedExecutorService xs) {
			ExecutorService svc = xs.getService();
			Future<?> future = svc.submit(command);
			return new FutureWork(future);
		} else if (kex instanceof KeyedScheduledExecutorService xs) {
			ScheduledExecutorService svc = xs.getService();
			Future<?> future = svc.submit(command);
			return new FutureWork(future);
		}
		return new InvalidWorkHandle(WorkHandle.ERR_SERVICE_CANT);
	}

	@Override
	public WorkHandle schedule(ExecutorServiceHandle hExec, long delay, TimeUnit unit, Runnable command) {
		Objects.requireNonNull(hExec, "hExec");
		Objects.requireNonNull(unit, "unit");
		Objects.requireNonNull(command, "command");
		KeyedExecutor kex = find(hExec);
		if (kex == null) {
			return new InvalidWorkHandle(WorkHandle.ERR_NO_SERVICE);
		}
		if (kex instanceof KeyedScheduledExecutorService xs) {
			ScheduledExecutorService svc = xs.getService();
			ScheduledFuture<?> future = svc.schedule(command, delay, unit);
			return new ScheduledFutureWork(future);
		}
		return new InvalidWorkHandle(WorkHandle.ERR_SERVICE_CANT);
	}

	@Override
	public WorkHandle scheduleAt(ExecutorServiceHandle hExec, LocalTime timeAt, Runnable command) {
		Objects.requireNonNull(hExec, "hExec");
		Objects.requireNonNull(timeAt, "timeAt");
		Objects.requireNonNull(command, "command");
		LocalTime tmNow = getMostRecentTime();
		LocalTime tmCompar = tmNow;
		tmCompar = tmNow.plusNanos(NANOS_TO_ADD);
		Duration durTo = Duration.between(tmCompar, timeAt);
		if (durTo.isNegative()) {
			// wraps around to the next day
			durTo = DUR_ONE_DAY.plus(durTo);
		}
		long offsetSecs = durTo.toSeconds();
		KeyedExecutor kex = find(hExec);
		if (kex == null) {
			return new InvalidWorkHandle(WorkHandle.ERR_NO_SERVICE);
		}
		if (kex instanceof KeyedScheduledExecutorService xs) {
			ScheduledExecutorService svc = xs.getService();
			ScheduledFuture<?> future = svc.schedule(command, offsetSecs, TimeUnit.SECONDS);
			return new ScheduledFutureWork(future);
		}
		return new InvalidWorkHandle(WorkHandle.ERR_SERVICE_CANT);
	}

	@Override
	public WorkHandle scheduleAtFixedRate(ExecutorServiceHandle hExec, long initialDelay, long period,
			TimeUnit unit, Runnable command) {
		Objects.requireNonNull(hExec, "hExec");
		Objects.requireNonNull(unit, "unit");
		Objects.requireNonNull(command, "command");
		KeyedExecutor kex = find(hExec);
		if (kex == null) {
			return new InvalidWorkHandle(WorkHandle.ERR_NO_SERVICE);
		}
		if (kex instanceof KeyedScheduledExecutorService xs) {
			ScheduledExecutorService svc = xs.getService();
			ScheduledFuture<?> future = svc.scheduleAtFixedRate(command, initialDelay, period, unit);
			return new ScheduledFutureWork(future);
		}
		return new InvalidWorkHandle(WorkHandle.ERR_SERVICE_CANT);
	}

	@Override
	public WorkHandle scheduleWithFixedDelay(ExecutorServiceHandle hExec, long initialDelay, long delay, 
			TimeUnit unit, Runnable command) {
		Objects.requireNonNull(hExec, "hExec");
		Objects.requireNonNull(unit, "unit");
		Objects.requireNonNull(command, "command");
		KeyedExecutor kex = find(hExec);
		if (kex == null) {
			return new InvalidWorkHandle(WorkHandle.ERR_NO_SERVICE);
		}
		if (kex instanceof KeyedScheduledExecutorService xs) {
			ScheduledExecutorService svc = xs.getService();
			ScheduledFuture<?> future = svc.scheduleWithFixedDelay(command, initialDelay, delay, unit);
			return new ScheduledFutureWork(future);
		}
		return new InvalidWorkHandle(WorkHandle.ERR_SERVICE_CANT);
	}
	
	@Override
	public StrobeEndpoint getStrobeEndpoint() {
		StrobeEndpointObj strobe = new StrobeEndpointObj(_svcEndpoint.get(), _dtMostRecent);
		strobe.activate();
		ScheduleEverySecondTask taskEverySecond = new ScheduleEverySecondTask(this, strobe);
		_listRunEverySecond.add(taskEverySecond);
		ScheduleAtMidnightTask taskMidnight = new ScheduleAtMidnightTask(this, strobe);
		_listRunAtMidnight.add(taskMidnight);
		return strobe;
	}

	@Override
	public CalendarEndpoint getCalendarEndpoint() {
		CalendarEndpointObj calendar = new CalendarEndpointObj(_svcEndpoint.get(), _dtMostRecent.toLocalDate());
		calendar.activate();
		ScheduleAtMidnightTask taskMidnight = new ScheduleAtMidnightTask(this, calendar);
		_listRunAtMidnight.add(taskMidnight);
		return calendar;
	}

	private class EverySecond
			implements Runnable {

		@Override
		public void run() {
			updateClock();
			doEverySecond();
		}
		
	}
	
	private class AtMidnight
			implements Runnable {

		@Override
		public void run() {
			doAtMidnight();
		}
		
	}
}
