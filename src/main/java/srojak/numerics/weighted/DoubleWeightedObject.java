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
package srojak.numerics.weighted;

import java.util.Objects;

import srojak.numerics.DoublePrecisionComparer;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 * Use of untyped Object allows sets of dissimilar objects to be constructed.
 */
public class DoubleWeightedObject 
		implements DoubleWeighted {
	private final Object _objWrapped;
	private double _factor;
	private double _weight;
	
	private static final DoublePrecisionComparer _comparer;

	static {
		_comparer = DoublePrecisionComparer.DEFAULT_COMPARER;
	}
	
	static DoublePrecisionComparer getComparer() {
		return _comparer;
	}
	
	public DoubleWeightedObject(Object obj, double dWeightFactor) {
		Objects.requireNonNull(obj, "obj");
		if (_comparer.compare(dWeightFactor, OrderedComparison.LE, 0.0d)) {
			throw new IllegalArgumentException("dWeightFactor must be positive");
		}
		_objWrapped = obj;
		_factor = dWeightFactor;
		_weight = 1.0d;
	}

	@Override
	public Object getWrappedObject() {
		return _objWrapped;
	}

	@Override
	public double getWeightFactor() {
		return _factor;
	}

	@Override
	public double getWeight() {
		return _weight;
	}

	@Override
	public void computeWeight(double dTotal) {
		_weight = _factor / dTotal;
	}

}
