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

import java.util.Objects;

import srojak.core.StrobeFrequency;

/**
 * @author Stephen
 *
 */
class FrequencyClockListener 
		implements ClockEventListener {
	private final StrobeFrequency _freq;
	private final ClockEventListener _listener;
	
	public FrequencyClockListener(StrobeFrequency frequency, ClockEventListener listener) {
		Objects.requireNonNull(frequency, "frequency");
		Objects.requireNonNull(listener, "listener");
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
