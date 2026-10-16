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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import srojak.core.time.CalendarEvent;
import srojak.core.time.CalendarEventListener;
import srojak.kernel.impl.KernelEndpointHome;
import srojak.kernel.impl.ServiceEndpointBase;

/**
 * @author Stephen
 *
 */
public class CalendarEndpointObj 
		extends ServiceEndpointBase 
		implements CalendarEndpoint, WakeAtMidnight {
	private LocalDate _dateLast;

	/**
	 * @param home
	 */
	public CalendarEndpointObj(@SuppressWarnings("exports") KernelEndpointHome home, LocalDate dateStart) {
		super(home);
		Objects.requireNonNull(dateStart, "dateStart");
		_dateLast = dateStart;
	}

	@Override
	public LocalDate mostRecentDate() {
		return _dateLast;
	}

	@Override
	public void newDay(LocalDate dateNow) {
		Objects.requireNonNull(dateNow, "dateNow");
		_dateLast = dateNow;
		sendCalendarEvents();
	}
	
	private void sendCalendarEvents() {
		CalendarEvent event = null;
		List<CalendarEventListener> list = _listeners.getListeners(CalendarEventListener.class);
		Iterator<CalendarEventListener> iterator = list.iterator();
		while (iterator.hasNext()) {
			CalendarEventListener listener = iterator.next();
			if (event == null) {
				event = new CalendarEvent(this, _dateLast);
			}
			listener.setDate(event);
		}
	}

	@Override
	public void addCalendarListener(CalendarEventListener listener) {
		_listeners.add(CalendarEventListener.class, listener);

	}

	@Override
	public void removeCalendarListener(CalendarEventListener listener) {
		_listeners.remove(CalendarEventListener.class, listener);
	}

	@Override
	protected void closeSelf() {

	}

}
