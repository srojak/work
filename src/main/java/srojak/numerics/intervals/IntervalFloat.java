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
import java.util.function.DoubleConsumer;

import srojak.numerics.IntervalType;
import srojak.numerics.SinglePrecisionComparer;

/**
 * @author Stephen
 *
 */
public class IntervalFloat 
		extends IntervalBase {
	private final SinglePrecisionComparer _comparer;
	private final float _valueMinimum;
	private final float _valueMaximum;

	public IntervalFloat(IntervalType type, float valueMin, float valueMax,
			SinglePrecisionComparer comparer) {
		super(type, false);
		if (valueMin > valueMax) {
			throw new IllegalArgumentException("valueMin > valueMax");
		}
		Objects.requireNonNull(comparer, "comparer");
		_comparer = comparer;
		_valueMinimum = valueMin;
		_valueMaximum = valueMax;
	}
	
	public IntervalFloat(IntervalType type, float valueMin, float valueMax) {
		this(type, valueMin, valueMax, SinglePrecisionComparer.DEFAULT_COMPARER);
	}

	@Override
	public boolean isDegenerate() {
		return _comparer.areEqual(_valueMinimum, _valueMaximum);
	}
	
	public float getMinimum() {
		return _valueMinimum;
	}
	
	public float getMaximum() {
		return _valueMaximum;
	}
	
	public boolean isInInterval(float fValue) {
		return _type.evalLeftComparison(_comparer.compare(_valueMinimum, fValue))
				&& _type.evalRightComparison(_comparer.compare(_valueMaximum, fValue));
	}
	
	public boolean isInInterval(float fValue, SinglePrecisionComparer comparer) {
		Objects.requireNonNull(comparer, "comparer");
		return _type.evalLeftComparison(comparer.compare(_valueMinimum, fValue))
				&& _type.evalRightComparison(comparer.compare(_valueMaximum, fValue));
	}
	
	public int compareToInterval(float fValue, SinglePrecisionComparer comparer) {
		Objects.requireNonNull(comparer, "comparer");
		if (_type.evalLeftComparison(comparer.compare(_valueMinimum, fValue))) {
			if (_type.evalRightComparison(comparer.compare(_valueMaximum, fValue))) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return -1;
		}
	}
	
	public int compareToInterval(float fValue) {
		return compareToInterval(fValue, _comparer);
	}
	
	public float getFullRange() {
		return _valueMaximum - _valueMinimum;
	}
	
	public void overInterval(DoubleConsumer consumer, float fIncrement) {
		Objects.requireNonNull(consumer, "consumer");
		if (fIncrement <= 0.0d) {
			throw new IllegalArgumentException("dIncrement must be positive");
		}
		float fValue = _valueMinimum;
		if (!_type.isLeftClosed()) {
			fValue += fIncrement;
		}
		while (_type.evalRightComparison(_comparer.compare(_valueMaximum, fValue))) {
			consumer.accept(fValue);
			fValue += fIncrement;
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
