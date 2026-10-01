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
package srojak.core.specialized;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Objects;

import srojak.core.Stoppable;
import srojak.core.Strobe;
import srojak.core.StrobeFrequency;
import srojak.core.events.ClockEvent;
import srojak.core.events.ClockEventListener;
import srojak.core.events.SingleEventListenerList;
import srojak.core.events.SingleEventListenerStore;

/**
 * @author Stephen
 *
 */
public class StrobeClock
		extends StoppableBase
		implements Strobe, Runnable, Stoppable {
	private final Clock _clock;
	private final SingleEventListenerStore<FrequencyEventListener> _listenersClock;
	private int _lastSecond;
	private int _lastMinute;
	private int _lastHour;
	
	private static final Clock SYSTEM_CLOCK = Clock.systemDefaultZone();
	
	public StrobeClock(Clock clock) {
		super(true);
		Objects.requireNonNull(clock, "clock");
		_clock = clock;
		_listenersClock = new SingleEventListenerList<FrequencyEventListener>();
		LocalTime time = LocalTime.now(_clock);
		_lastSecond = time.getSecond();
		_lastMinute = time.getMinute();
		_lastHour = time.getHour();
	}
	
	public StrobeClock() {
		this(SYSTEM_CLOCK);
	}

	@Override
	public void addClockListener(StrobeFrequency frequency, ClockEventListener listener) {
		Objects.requireNonNull(frequency, "frequency");
		if (listener == null)
			return;
		FrequencyEventListener freqListener = new FrequencyEventListener(frequency, listener);
		_listenersClock.add(freqListener);
	}

	@Override
	public void removeClockListener(ClockEventListener listener) {
		if (listener == null)
			return;
		Iterator<FrequencyEventListener> iterator = _listenersClock.iterator();
		while (iterator.hasNext()) {
			FrequencyEventListener freqListener = iterator.next();
			if (freqListener.listenerEquals(listener)) {
				iterator.remove();
				return;
			}
		}
	}

	@Override
	public void run() {
		if (isRunning()) {
			LocalTime time = LocalTime.now(_clock);
			StrobeFrequency freqNow = StrobeFrequency.EVERY_SECOND;
			if (time.getSecond() < _lastSecond) {
				if (time.getMinute() < _lastMinute) {
					if (time.getHour() < _lastHour) {
						freqNow = StrobeFrequency.AT_MIDNIGHT;
					} else {
						freqNow = StrobeFrequency.EVERY_HOUR;
					}
				} else {
					freqNow = StrobeFrequency.EVERY_MINUTE;
				}
			}
			
			_lastSecond = time.getSecond();
			_lastMinute = time.getMinute();
			_lastHour = time.getHour();
			
			ClockEvent event = null;
			Iterator<FrequencyEventListener> iterator = _listenersClock.iterator();
			while (iterator.hasNext()) {
				FrequencyEventListener freqListener = iterator.next();
				if (freqListener.isFor(freqNow)) {
					if (event == null) {
						event = new ClockEvent(this, time);
					}
					freqListener.when(event);
				}
			}
		}

	}

	@Override
	protected void halt() {
		_listenersClock.clear();
	}

	private class FrequencyEventListener
			implements ClockEventListener {
		private final StrobeFrequency _freq;
		private final ClockEventListener _listener;
		
		public FrequencyEventListener(StrobeFrequency frequency, ClockEventListener listener) {
			_freq = frequency;
			_listener = listener;
		}
		
		public boolean isFor(StrobeFrequency frequency) {
			return _freq.compareTo(frequency) <= 0;
		}
		
		public boolean listenerEquals(ClockEventListener listener) {
			return _listener == listener;
		}

		@Override
		public void when(ClockEvent event) {
			_listener.when(event);
		}
		
	}
}
