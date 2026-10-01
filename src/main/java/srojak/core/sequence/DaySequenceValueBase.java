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

import srojak.core.IncommensurableValuesException;

/**
 * @author Stephen
 *
 * This class does not have a sequential class ID because it is abstract.
 */
@SuppressWarnings("serial")
public abstract class DaySequenceValueBase
		implements DaySequential {
	private final long _series;
	private final String _strTag;
	private final YearAndDay _yday;
	private final long _sequence;
	
	public DaySequenceValueBase(long series, String strTag, YearAndDay yday, long seq) {
		Objects.requireNonNull(strTag, "strTag");
		Objects.requireNonNull(yday, "yday");
		_series = series;
		_strTag = strTag;
		_yday = yday;
		_sequence = seq;
	}
	
	private boolean coreEquals(DaySequential obj) {
		return _series == obj.series() && _yday.isEqual(obj.getDay()) && _sequence == obj.getSequence();
	}

	@Override
	public boolean isEqual(DaySequential obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else {
			return coreEquals(obj);
		}
	}

	@Override
	public int compareTo(DaySequential o) {
		if (o == null) {
			return 1;
		} else if (_series != o.series()) {
			throw new IncommensurableValuesException(getClass(), o.getClass(), "not of same series");
		} else {
			int nc = _yday.compareTo(o.getDay());
			if (nc == 0) {
				nc = Long.compare(_sequence, o.getSequence());
			}
			return nc;
		}
	}

	@Override
	public long series() {
		return _series;
	}

	@Override
	public String getSeriesTag() {
		return _strTag;
	}

	@Override
	public YearAndDay getDay() {
		return _yday;
	}

	@Override
	public long getSequence() {
		return _sequence;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_series, _yday, _sequence);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof DaySequential other) {
			return coreEquals(other);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return _strTag + " " + _yday.toString() + String.format(" %,d", _sequence);
	}
}
