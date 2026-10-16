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
package srojak.kernel.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import srojak.concurrent.WorkHandle;
import srojak.kernel.ExecutorServiceHandle;
import srojak.kernel.endpoints.CalendarEndpoint;
import srojak.kernel.endpoints.StrobeEndpoint;

/**
 * @author Stephen
 *
 */
public interface KernelScheduleService 
		extends KernelService {
	
	LocalDate getMostRecentDate();
	LocalTime getMostRecentTime();

	ExecutorServiceHandle createScheduledThreadPool(int szPool);
	
	WorkHandle submit(ExecutorServiceHandle hExec, Runnable command);
	WorkHandle schedule(ExecutorServiceHandle hExec, long delay, TimeUnit unit, Runnable command);
	WorkHandle scheduleAt(ExecutorServiceHandle hExec, LocalTime timeAt, Runnable command);
	WorkHandle scheduleAtFixedRate(ExecutorServiceHandle hExec, long initialDelay, long period, TimeUnit unit, Runnable command);
	WorkHandle scheduleWithFixedDelay(ExecutorServiceHandle hExec, long initialDelay, long delay, TimeUnit unit, Runnable command);
	
	StrobeEndpoint getStrobeEndpoint();
	CalendarEndpoint getCalendarEndpoint();
}
