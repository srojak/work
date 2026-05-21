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
package srojak.numerics.intervals;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class IntervalIntValue {
	private final IntervalInt _interval;
	private int _value;
	
	public IntervalIntValue(IntervalInt interval, int nValueOrig) {
		Objects.requireNonNull(interval);
		if (interval.isDegenerate()) {
			throw new IllegalArgumentException("interval is degenerate");
		}
		if (!interval.isInInterval(nValueOrig)) {
			throw new IllegalArgumentException("nValueOrig is not in interval");
		}
		_interval = interval;
		_value = nValueOrig;
	}
	
	public IntervalInt getInterval() {
		return _interval;
	}
	
	public int getValue() {
		return _value;
	}
	
	public void setValue(int nValue) {
		if (!_interval.isInInterval(nValue)) {
			throw new IllegalArgumentException("nValue is not in interval");
		}
		_value = nValue;
		
	}
}
