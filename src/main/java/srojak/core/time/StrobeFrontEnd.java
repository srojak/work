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
package srojak.core.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.StrobeFrequency;
import srojak.core.events.CommonEventListenerStore;

/**
 * @author Stephen
 *
 */
public class StrobeFrontEnd
		implements Strobe {
	private final CommonEventListenerStore _listeners;
	private LocalTime _timeRecent;
	private LocalDate _dateRecent;
	
	public StrobeFrontEnd(CommonEventListenerStore listeners, LocalDateTime dtStart) {
		Objects.requireNonNull(listeners, "listeners");
		Objects.requireNonNull(dtStart, "dtStart");
		_listeners = listeners;
		_timeRecent = dtStart.toLocalTime();
		_dateRecent = dtStart.toLocalDate();
	}

	public void timeNow(LocalTime timeNow) {
		Objects.requireNonNull(timeNow, "timeNow");
		EnumSet<StrobeFrequency> frequencies = EnumSet.of(StrobeFrequency.EVERY_SECOND);
		if (timeNow.getSecond() < _timeRecent.getSecond()) {
			frequencies.add(StrobeFrequency.EVERY_MINUTE);
			if (timeNow.getMinute() < _timeRecent.getMinute()) {
				frequencies.add(StrobeFrequency.EVERY_HOUR);
			}
		}
		
		_timeRecent = timeNow;
		
		sendClockEvents(frequencies);
	}
	
	private void sendClockEvents(EnumSet<StrobeFrequency> frequencies) {
		ClockEvent event = null;
		List<FrequencyClockListener> list = _listeners.getListeners(FrequencyClockListener.class);
		Iterator<FrequencyClockListener> iterator = list.iterator();
		while (iterator.hasNext()) {
			FrequencyClockListener listener = iterator.next();
			if (frequencies.contains(listener.frequency())) {
				if (event == null) {
					event = new ClockEvent(this, _timeRecent);
				}
				listener.when(event);
			}
		}
	}
	
	public void newDay(LocalDate dateNow) {
		Objects.requireNonNull(dateNow, "dateNow");
		_dateRecent = dateNow;
		sendCalendarEvents();
	}
	
	private void sendCalendarEvents() {
		CalendarEvent event = null;
		List<CalendarEventListener> list = _listeners.getListeners(CalendarEventListener.class);
		Iterator<CalendarEventListener> iterator = list.iterator();
		while (iterator.hasNext()) {
			CalendarEventListener listener = iterator.next();
			if (event == null) {
				event = new CalendarEvent(this, _dateRecent);
			}
			listener.setDate(event);
		}
	}
	
	private List<FrequencyClockListener> findListenersFor(ClockEventListener listener) {
		List<FrequencyClockListener> list = _listeners.getListeners(FrequencyClockListener.class);
		List<FrequencyClockListener> listMatch = new LinkedList<FrequencyClockListener>();
		Iterator<FrequencyClockListener> iterator = list.iterator();
		while (iterator.hasNext()) {
			FrequencyClockListener listenFreq = iterator.next();
			if (listenFreq.listenerEquals(listener)) {
				listMatch.add(listenFreq);
			}
		}
		return listMatch;
	}

	@Override
	public LocalTime mostRecentTime() {
		return _timeRecent;
	}

	@Override
	public LocalDate mostRecentDate() {
		return _dateRecent;
	}

	/*
	@Override
	public void addLifeCycleListener(LifeCycleListener listener) {
		_listeners.add(LifeCycleListener.class, listener);
	}

	@Override
	public void removeLifeCycleListener(LifeCycleListener listener) {
		_listeners.remove(LifeCycleListener.class, listener);
	}
	*/

	@Override
	public void addClockListener(StrobeFrequency frequency, ClockEventListener listener) {
		Objects.requireNonNull(frequency, "frequency");
		if (listener == null)
			return;
		FrequencyClockListener freqListener = new FrequencyClockListener(frequency, listener);
		_listeners.add(FrequencyClockListener.class, freqListener);
	}

	@Override
	public void removeClockListener(ClockEventListener listener) {
		List<FrequencyClockListener> list = findListenersFor(listener);
		list.forEach(ls -> _listeners.remove(FrequencyClockListener.class, ls));
	}

	@Override
	public void addCalendarListener(CalendarEventListener listener) {
		_listeners.add(CalendarEventListener.class, listener);
	}

	@Override
	public void removeCalendarListener(CalendarEventListener listener) {
		_listeners.remove(CalendarEventListener.class, listener);
	}

}
