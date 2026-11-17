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
package srojak.mantle.tprops;

import java.util.Objects;

import srojak.core.Optionality;
import srojak.numerics.intervals.IntervalInt;

/**
 * @author Stephen
 *
 */
public class TypedIntIntervalPropertyValue 
		extends TypedIntPropertyValue {
	private final IntervalInt _interval;

	/**
	 * @param strName
	 * @param opt
	 */
	public TypedIntIntervalPropertyValue(String strName, Optionality opt, IntervalInt interval) {
		super(strName, opt);
		Objects.requireNonNull(interval, "interval");
		_interval = interval;
		if (!interval.isInInterval(0)) {
			setInitialValue(interval.getMinimumValue());
		}
	}

	@Override
	public void setValue(int value) {
		if (!_interval.isInInterval(value)) {
			throw new IllegalArgumentException("value is not in acceptable interval");
		}
		super.setValue(value);
	}

	@Override
	public TypedProperty copy() {
		TypedIntIntervalPropertyValue copy = new TypedIntIntervalPropertyValue(getName(), Optionality.OPTIONAL, _interval);
		copy.setInitialValue(value());
		copy.copyFlagsFrom(this);
		return copy;
	}
}
