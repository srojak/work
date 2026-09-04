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
package srojak.numerics;

import java.io.Serializable;
import java.util.Objects;

import srojak.core.mutable.MutableBase;

/**
 * @author Stephen
 *
 */
public class DoubleMutable
		extends MutableBase 
		implements Serializable, Comparable<DoubleMutable> {
	private double _value;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4231344370053292165L;
	
	public DoubleMutable(double valueInitial) {
		super();
		_value = valueInitial;
	}
	
	public double getValue() {
		return _value;
	}
	
	public boolean setValue(double valueNew) {
		if (!DoublePrecisionComparer.DEFAULT_COMPARER.areEqual(_value, valueNew)) {
			_value = valueNew;
			whenValueChanged();
			return true;
		} else {
			return false;
		}
	}

    /**
     * Returns {@code true} if this {@code DoubleMutable} value is
     * a Not-a-Number (NaN), {@code false} otherwise.
     *
     * @return  {@code true} if the value represented by this object is
     *          NaN; {@code false} otherwise.
     */
    public boolean isNaN() {
        return Double.isNaN(_value);
    }

    /**
     * Returns {@code true} if this {@code DoubleMutable} value is
     * infinitely large in magnitude, {@code false} otherwise.
     *
     * @return  {@code true} if the value represented by this object is
     *          positive infinity or negative infinity;
     *          {@code false} otherwise.
     */
    public boolean isInfinite() {
        return Double.isInfinite(_value);
    }

	@Override
	public int compareTo(DoubleMutable o) {
		Objects.requireNonNull(o, "o");
		return DoublePrecisionComparer.DEFAULT_COMPARER.compare(_value, o._value);
	}

	@Override
	public int hashCode() {
		return Double.hashCode(_value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof DoubleMutable other) {
			return DoublePrecisionComparer.DEFAULT_COMPARER.areEqual(_value, other._value);
		} else if (obj instanceof Double other) {
			return DoublePrecisionComparer.DEFAULT_COMPARER.areEqual(_value, other.doubleValue());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return Double.toString(_value);
	}

	public static DoubleMutable parse(String str)
			throws NumberFormatException {
		Objects.requireNonNull(str, "str");
		return new DoubleMutable(Double.parseDouble(str));
	}
}
