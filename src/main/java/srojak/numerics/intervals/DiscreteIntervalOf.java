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

import srojak.numerics.IntervalType;

/**
 * @author Stephen
 *
 */
public class DiscreteIntervalOf<T extends Comparable<T>>
		extends IntervalBase {
	private T _valueMinimum;
	private T _valueMaximum;
	
	public DiscreteIntervalOf(IntervalType type, T valueMin, T valueMax) {
		super(type, true);
		Objects.requireNonNull(valueMin, "valueMin");
		Objects.requireNonNull(valueMax, "valueMax");
		if (valueMin.compareTo(valueMax) > 0) {
			throw new IllegalArgumentException("valueMin > valueMax");
		}
		_valueMinimum = valueMin;
		_valueMaximum = valueMax;
	}
	
	public T getMinimum() {
		return _valueMinimum;
	}
	
	public T getMaximum() {
		return _valueMaximum;
	}
	
	@Override
	public boolean isDegenerate() {
		return _valueMinimum.compareTo(_valueMaximum) == 0;
	}

	public boolean isInRange(T value) {
		Objects.requireNonNull(value, "value");
		return _valueMinimum.compareTo(value) <= 0
				&& _valueMaximum.compareTo(value) >= 0;
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
