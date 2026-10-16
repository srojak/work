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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import srojak.core.StrobeFrequency;
import srojak.core.time.CalendarEventListener;
import srojak.core.time.ClockEventListener;
import srojak.core.time.StrobeFrontEnd;
import srojak.kernel.impl.KernelEndpointHome;
import srojak.kernel.impl.ServiceEndpointBase;

/**
 * @author Stephen
 *
 */
public class StrobeEndpointObj 
		extends ServiceEndpointBase 
		implements StrobeEndpoint, WakeEverySecond, WakeAtMidnight {
	private final StrobeFrontEnd _fe;
	private LocalDate _dateLast;
	private LocalTime _timeLast;

	/**
	 * @param home
	 */
	public StrobeEndpointObj(@SuppressWarnings("exports") KernelEndpointHome home, LocalDateTime dtStart) {
		super(home);
		_fe = new StrobeFrontEnd(_listeners, dtStart);
		_dateLast = dtStart.toLocalDate();
		_timeLast = dtStart.toLocalTime();
	}

	@Override
	public LocalTime mostRecentTime() {
		return _timeLast;
	}

	@Override
	public LocalDate mostRecentDate() {
		return _dateLast;
	}

	@Override
	public void updateTime(LocalTime timeNow) {
		Objects.requireNonNull(timeNow, "timeNow");
		_timeLast = timeNow;
		if (isActive()) {
			_fe.timeNow(_timeLast);
		}
	}

	@Override
	public void newDay(LocalDate dateNow) {
		Objects.requireNonNull(dateNow, "dateNow");
		_dateLast = dateNow;
		if (isActive()) {
			_fe.newDay(dateNow);
		}
	}

	@Override
	public void addClockListener(StrobeFrequency frequency, ClockEventListener listener) {
		_fe.addClockListener(frequency, listener);
	}

	@Override
	public void removeClockListener(ClockEventListener listener) {
		_fe.removeClockListener(listener);
	}

	@Override
	public void addCalendarListener(CalendarEventListener listener) {
		_fe.addCalendarListener(listener);
	}

	@Override
	public void removeCalendarListener(CalendarEventListener listener) {
		_fe.removeCalendarListener(listener);
	}

	@Override
	protected void closeSelf() {

	}

}
