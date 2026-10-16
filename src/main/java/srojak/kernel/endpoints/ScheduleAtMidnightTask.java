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
package srojak.kernel.endpoints;

import java.time.LocalDate;
import java.util.Objects;

import srojak.core.ActiveAndRunnable;
import srojak.kernel.services.KernelScheduleService;

/**
 * @author Stephen
 *
 */
public class ScheduleAtMidnightTask 
		implements ActiveAndRunnable {
	private final KernelScheduleService _scheduler;
	private final WakeAtMidnight _owner;
	
	public ScheduleAtMidnightTask(KernelScheduleService scheduler, WakeAtMidnight owner) {
		Objects.requireNonNull(scheduler, "scheduler");
		Objects.requireNonNull(owner, "owner");
		_scheduler = scheduler;
		_owner = owner;
	}

	@Override
	public boolean isActive() {
		return _owner.isActive();
	}

	@Override
	public void run() {
		LocalDate dateNow = _scheduler.getMostRecentDate();
		_owner.newDay(dateNow);
	}

}
