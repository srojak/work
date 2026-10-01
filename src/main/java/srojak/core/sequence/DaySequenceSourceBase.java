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

import java.util.Objects;

import srojak.core.events.StateChangeCodes;
import srojak.core.events.StateChangeEvent;
import srojak.core.events.StateChangeListener;

/**
 * @author Stephen
 *
 */
public abstract class DaySequenceSourceBase
		implements DaySequencer {
	protected final long _series;
	protected final String _strTag;
	private final SequenceDaySource _sourceDay;
	private YearAndDay _yday;
	private long _seqCurrent;
	
	protected DaySequenceSourceBase(long series, String strTag, SequenceDaySource source) {
		Objects.requireNonNull(strTag, "strTag");
		Objects.requireNonNull(source, "source");
		_series = series;
		_strTag = strTag;
		_sourceDay = source;
		_yday = _sourceDay.getYearAndDay();
		_seqCurrent = 0L;
		_sourceDay.addStateChangeListener(new MidnightListener());
	}

	@Override
	public long series() {
		return _series;
	}

	@Override
	public String getSeriesTag() {
		return _strTag;
	}
	
	private synchronized void newDay() {
		_yday = _sourceDay.getYearAndDay();
		_seqCurrent = 0L;
	}
	
	protected abstract DaySequential makeValue(YearAndDay yday, long sequence);

	@Override
	public synchronized DaySequential next() {
		return makeValue(_yday, ++_seqCurrent);
	}

	private class MidnightListener
			implements StateChangeListener, StateChangeCodes {

		@Override
		public void stateChanged(StateChangeEvent event) {
			if (event.getID() == SC_MIDNIGHT) {
				newDay();
			}
		}
		
	}
}
