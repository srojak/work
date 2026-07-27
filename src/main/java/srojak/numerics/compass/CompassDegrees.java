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
package srojak.numerics.compass;

import java.text.DecimalFormat;
import java.util.Objects;

import srojak.core.FloatComparable;
import srojak.numerics.SinglePrecisionComparer;

/**
 * @author Stephen
 *
 */
public class CompassDegrees
		implements Comparable<CompassDegrees>, FloatComparable {
	private float _value;
	
	public static final float LIMIT = 360.0f;	
	public static final String DEGREES = "\u00B0";

	private static final DecimalFormat _formatLength = new DecimalFormat("###0.0##");
	
	public CompassDegrees(float valueInitial) {
		_value = valueInitial;
	}
	
	public boolean isNormalized() {
		return _value >= 0 && _value < LIMIT;
	}
	
	public void normalize() {
		if (_value >= LIMIT || _value < 0) {
			_value = (_value % LIMIT + LIMIT) % LIMIT;
		}
	}
	
	public float getValue() {
		return _value;
	}
	
	public CompassDegrees add(float operand) {
		CompassDegrees cdResult = new CompassDegrees(0);
		cdResult._value = _value + operand;
		return cdResult;
	}
	
	public CompassDegrees addAndNormalize(float operand) {
		CompassDegrees cdResult = add(operand);
		cdResult.normalize();
		return cdResult;
	}
	
	public CompassDegrees subtract(float operand) {
		CompassDegrees cdResult = new CompassDegrees(0);
		cdResult._value = _value - operand;
		return cdResult;
	}
	
	public CompassDegrees subtractAndNormalize(float operand) {
		CompassDegrees cdResult = subtract(operand);
		cdResult.normalize();
		return cdResult;
	}
	
	public double convertToRadians() {
		return _value * Math.PI / 180.0;
	}
	
	public static CompassDegrees convertFromRadians(double dRadians) {
		CompassDegrees cdResult = new CompassDegrees((float) (180.0 * dRadians / Math.PI));
		return cdResult;
	}
	
	public CompassDegrees toNearest4Point() {
		normalize();
		float q = _value / 90.0f;
		return new CompassDegrees((int) Math.round(q) * 90.0f);
	}
	
	public CompassDegrees toNearest8Point() {
		normalize();
		float q = _value / 45.0f;
		return new CompassDegrees((int) Math.round(q) * 45.0f);
	}

	@Override
	public int hashCode() {
		return Float.hashCode(_value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof CompassDegrees other) {
				return SinglePrecisionComparer.DEFAULT_COMPARER.areEqual(_value, other._value);
			} else if (obj instanceof Number objNumber) {
				return SinglePrecisionComparer.DEFAULT_COMPARER.areEqual(_value, objNumber.floatValue());
			}
		}
		return false;
	}

	@Override
	public int compareTo(CompassDegrees o) {
		Objects.requireNonNull(o, "o");
		return SinglePrecisionComparer.DEFAULT_COMPARER.compare(_value, o._value);
	}

	@Override
	public int compareTo(float other) {
		return SinglePrecisionComparer.DEFAULT_COMPARER.compare(_value, other);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(_formatLength.format(_value));
		sb.append(DEGREES);
		if (!isNormalized()) {
			sb.append("\u2020");
		}
		return sb.toString();
	}
}
