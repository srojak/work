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
package srojak.core.sequence;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.time.CalendarEvent;
import srojak.core.time.CalendarEventListener;

/**
 * @author Stephen
 *
 */
public class DaySequencer 
		extends SequencerBase 
		implements CalendarEventListener, LifeCycleListener {
	private long _lnDatePart;
	private volatile int _sequence;
	private final AtomicLong _value;
	private boolean _bActive;
	// TODO: tell it the source has gone away

	/**
	 * @param idSeries
	 */
	public DaySequencer(long idSeries, LocalDate dtStart) {
		super(idSeries);
		setDatePart(dtStart);
		_sequence = 0;
		_value = new AtomicLong(_lnDatePart << 14);
		_bActive = true;
	}
	
	private void setDatePart(LocalDate dtNow) {
		_lnDatePart = (((long) dtNow.getYear() & 0x3fL) << 9)
				| ((long) dtNow.getDayOfYear());
	}
	
	private void setValue(int nSequence) {
		_value.set((_lnDatePart << 14) | (long) nSequence);
	}

	@Override
	public boolean isActive() {
		return _bActive;
	}

	@Override
	public long current() {
		return _value.get();
	}

	@Override
	public long next() {
		setValue(++_sequence);
		return _value.get();
	}

	@Override
	public void setDate(CalendarEvent event) {
		setDatePart(event.getDate());
		_sequence = 0;
		setValue(_sequence);
	}

	@Override
	public void receive(LifeCycleEvent event) {
		if (event.getID() == LifeCycleEvent.ID_CLOSED) {
			_bActive = false;
		}
	}

}
