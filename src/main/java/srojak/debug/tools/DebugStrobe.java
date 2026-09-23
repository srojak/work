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
package srojak.debug.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import srojak.core.containers.SingletonContainer;
import srojak.core.observe.ObsLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
public class DebugStrobe
		implements Runnable {
	
	private static final ScheduledExecutorService _poolSched;
	private static final DateTimeFormatter FORMAT_TIME_STAMP;
	private static final DebugSwitch _swDebugClass;
	private static final DebugStrobe _strobe;
	private static final SingletonContainer<ScheduledFuture<?>> _ctnrSched;
	
	static {
		_poolSched = Executors.newScheduledThreadPool(2);
		FORMAT_TIME_STAMP = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");		
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = DebugStrobe.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
		_strobe = new DebugStrobe();
		_ctnrSched = new SingletonContainer<ScheduledFuture<?>>();
	}
	
	public static void startStrobe(int nMinutes) {
		// this is a simplistic form
		if (!_ctnrSched.isEmpty()) {
			_ctnrSched.take().cancel(true);
		}
		ScheduledFuture<?> sched = _poolSched.scheduleAtFixedRate(_strobe, nMinutes, nMinutes, TimeUnit.MINUTES);
		_ctnrSched.set(sched);
	}
	
	public static void shutdown() {
		_poolSched.shutdownNow();
	}

	/**
	 * 
	 */
	private DebugStrobe() {
		
	}

	@Override
	public void run() {
		_swDebugClass.write(ObsLevel.INFO, "time: " + FORMAT_TIME_STAMP.format(LocalDateTime.now()));
	}
}
