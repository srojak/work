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

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import srojak.core.Stoppable;
import srojak.core.StrobeFrequency;
import srojak.core.events.CommonEventListenerList;
import srojak.core.events.CommonEventListenerStore;
import srojak.core.specialized.StoppableBase;

/**
 * @author Stephen
 *
 */
public class StrobeUnit
		extends StoppableBase
		implements Strobe, Runnable, Stoppable {
	private final Clock _clock;
	private final CommonEventListenerStore _listeners;
	private LocalTime _timeRecent;
	private LocalDate _dateRecent;
	
	private static final Clock SYSTEM_CLOCK = Clock.systemDefaultZone();
	
	public StrobeUnit(Clock clock) {
		super(true);
		Objects.requireNonNull(clock, "clock");
		_clock = clock;
		_listeners = new CommonEventListenerList();
		_timeRecent = LocalTime.now(_clock);
		_dateRecent = LocalDate.now(_clock);
		LocalTime time = LocalTime.now(_clock);
	}
	
	public StrobeUnit() {
		this(SYSTEM_CLOCK);
	}

	@Override
	public void addClockListener(StrobeFrequency frequency, ClockEventListener listener) {
		Objects.requireNonNull(frequency, "frequency");
		if (listener == null)
			return;
		FrequencyEventListener freqListener = new FrequencyEventListener(frequency, listener);
		_listeners.add(FrequencyEventListener.class, freqListener);
	}

	@Override
	public void removeClockListener(ClockEventListener listener) {
		if (listener == null)
			return;
		List<FrequencyEventListener> list = _listeners.getListeners(FrequencyEventListener.class);
		Iterator<FrequencyEventListener> iterator = list.iterator();
		while (iterator.hasNext()) {
			FrequencyEventListener freqListener = iterator.next();
			if (freqListener.listenerEquals(listener)) {
				_listeners.remove(FrequencyEventListener.class, freqListener);
			}
		}
	}

	@Override
	public void run() {
		if (isRunning()) {
			LocalDateTime dtNow = LocalDateTime.now(_clock);
			EnumSet<StrobeFrequency> frequencies = EnumSet.of(StrobeFrequency.EVERY_SECOND);
			boolean bNewDate = false;
			if (dtNow.getSecond() < _timeRecent.getSecond()) {
				frequencies.add(StrobeFrequency.EVERY_MINUTE);
				if (dtNow.getMinute() < _timeRecent.getMinute()) {
					frequencies.add(StrobeFrequency.EVERY_HOUR);
					if (dtNow.getHour() < _timeRecent.getHour()) {
						bNewDate = true;
						_dateRecent = dtNow.toLocalDate();
					}
				}
			}
			
			_timeRecent = dtNow.toLocalTime();
			
			sendClockEvents(frequencies);
			if (bNewDate) {
				sendCalendarEvents();
			}
		}

	}
	
	private void sendClockEvents(EnumSet<StrobeFrequency> frequencies) {
		ClockEvent event = null;
		List<FrequencyEventListener> list = _listeners.getListeners(FrequencyEventListener.class);
		Iterator<FrequencyEventListener> iterator = list.iterator();
		while (iterator.hasNext()) {
			FrequencyEventListener listener = iterator.next();
			if (frequencies.contains(listener.frequency())) {
				if (event == null) {
					event = new ClockEvent(this, _timeRecent);
				}
				listener.when(event);
			}
		}
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

	@Override
	protected void halt() {
		_listeners.clear();
	}

	private class FrequencyEventListener
			implements ClockEventListener {
		private final StrobeFrequency _freq;
		private final ClockEventListener _listener;
		
		public FrequencyEventListener(StrobeFrequency frequency, ClockEventListener listener) {
			_freq = frequency;
			_listener = listener;
		}
		
		public StrobeFrequency frequency() {
			return _freq;
		}
		
		public boolean listenerEquals(ClockEventListener listener) {
			return _listener == listener;
		}

		@Override
		public void when(ClockEvent event) {
			_listener.when(event);
		}
		
	}

	@Override
	public LocalTime mostRecentTime() {
		return _timeRecent;
	}

	@Override
	public LocalDate mostRecentDate() {
		return _dateRecent;
	}

	@Override
	public void addCalendarListener(CalendarEventListener listener) {
		
	}

	@Override
	public void removeCalendarListener(CalendarEventListener listener) {
		// TODO Auto-generated method stub
		
	}
}
