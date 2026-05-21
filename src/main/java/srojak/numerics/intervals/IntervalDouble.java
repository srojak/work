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

import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.IntervalType;

/**
 * @author Stephen
 *
 */
public class IntervalDouble 
		extends IntervalBase {
	private DoublePrecisionComparer _comparer;
	private final double _valueMinimum;
	private final double _valueMaximum;
	
	/**
	 * Create a double interval.
	 * @param type
	 * @param valueMin
	 * @param valueMax
	 * @param comparer
	 */
	public IntervalDouble(IntervalType type, double valueMin, double valueMax,
			DoublePrecisionComparer comparer) {
		super(type, false);
		if (valueMin > valueMax) {
			throw new IllegalArgumentException("valueMin > valueMax");
		}
		Objects.requireNonNull(comparer, "comparer");
		_comparer = comparer;
		_valueMinimum = valueMin;
		_valueMaximum = valueMax;
	}

	/**
	 * Create a double interval.
	 * @param type
	 * @param valueMin
	 * @param valueMax
	 */
	public IntervalDouble(IntervalType type, double valueMin, double valueMax) {
		this(type, valueMin, valueMax, DoublePrecisionComparer.DEFAULT_COMPARER);
	}

	@Override
	public boolean isDegenerate() {
		return _comparer.areEqual(_valueMinimum, _valueMaximum);
	}
	
	public double getMinimum() {
		return _valueMinimum;
	}
	
	public double getMaximum() {
		return _valueMaximum;
	}
	
	public boolean isInInterval(double dValue) {
		return _type.evalLeftComparison(_comparer.compare(_valueMinimum, dValue))
				&& _type.evalRightComparison(_comparer.compare(_valueMaximum, dValue));
	}
	
	public boolean isInInterval(double dValue, DoublePrecisionComparer comparer) {
		Objects.requireNonNull(comparer, "comparer");
		return _type.evalLeftComparison(comparer.compare(_valueMinimum, dValue))
				&& _type.evalRightComparison(comparer.compare(_valueMaximum, dValue));
	}
	
	public double getFullRange() {
		return _valueMaximum - _valueMinimum;
	}
	
	public void overInterval(DoubleConsumer consumer, double dIncrement) {
		Objects.requireNonNull(consumer, "consumer");
		if (dIncrement <= 0.0d) {
			throw new IllegalArgumentException("dIncrement must be positive");
		}
		double dValue = _valueMinimum;
		if (!_type.isLeftClosed()) {
			dValue += dIncrement;
		}
		while (_type.evalRightComparison(_comparer.compare(_valueMaximum, dValue))) {
			consumer.accept(dValue);
			dValue += dIncrement;
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
