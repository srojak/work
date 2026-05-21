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
import java.util.function.LongConsumer;

import srojak.numerics.IntervalType;

/**
 * @author Stephen
 *
 */
public class IntervalLong
		extends IntervalBase {
	private final long _valueMinimum;
	private final long _valueMaximum;

	/**
	 * @param type
	 * @param bIsDiscrete
	 */
	public IntervalLong(IntervalType type, long valueMin, long valueMax) {
		super(type, true);
		if (valueMin > valueMax) {
			throw new IllegalArgumentException("valueMin > valueMax");
		}
		_valueMinimum = valueMin;
		_valueMaximum = valueMax;
	}
	
	public IntervalLong(long valueMin, long valueMax) {
		this(IntervalType.OPEN_RIGHT, valueMin, valueMax);
	}
	
	public long getMinimumValue() {
		return _type.isLeftClosed() ? _valueMinimum : _valueMinimum + 1;
	}
	
	public long getMaximumValue() {
		return _type.isRightClosed() ? _valueMaximum : _valueMaximum - 1;
	}

	@Override
	public boolean isDegenerate() {
		return _valueMinimum == _valueMaximum;
	}
	
	public long getFullRange() {
		return _valueMaximum - _valueMinimum;
	}
	
	public long getRange() {
		return getMaximumValue() - getMinimumValue();
	}
	
	public boolean isInInterval(long value) {
		return _type.evalLeftComparison(Long.compare(_valueMinimum, value))
				&& _type.evalRightComparison(Long.compare(_valueMaximum, value));
	}
	
	public void overEachValue(LongConsumer consumer) {
		Objects.requireNonNull(consumer, "consumer");
		long lnStop = getMaximumValue();
		for (long lnValue = getMinimumValue(); lnValue <= lnStop; lnValue++) {
			consumer.accept(lnValue);
		}
	}

	@Override
	protected void writeMinimumValueTo(StringBuilder sb) {
		sb.append(_valueMinimum);
	}

	@Override
	protected void writeMaximumValueTo(StringBuilder sb) {
		sb.append(_valueMaximum);
	}

}
