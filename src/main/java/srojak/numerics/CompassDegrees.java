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

import java.util.Objects;

import srojak.core.IntComparable;

/**
 * @author Stephen
 *
 */
public class CompassDegrees
		implements Comparable<CompassDegrees>, IntComparable {
	private int _value;
	
	public static final int LIMIT = 360;	
	public static final String DEGREES = "\u00B0";
	
	public CompassDegrees(int valueInitial) {
		_value = valueInitial;
	}
	
	public boolean isNormalized() {
		return _value >= 0 && _value < LIMIT;
	}
	
	public void normalize() {
		if (_value >= LIMIT) {
			_value %= LIMIT;
		} else if (_value < 0) {
			_value = Math.floorMod(_value, LIMIT) + LIMIT;
		}
	}
	
	public int getValue() {
		return _value;
	}
	
	public CompassDegrees add(int operand) {
		CompassDegrees cdResult = new CompassDegrees(0);
		cdResult._value = _value + operand;
		return cdResult;
	}
	
	public CompassDegrees addAndNormalize(int operand) {
		CompassDegrees cdResult = add(operand);
		cdResult.normalize();
		return cdResult;
	}
	
	public CompassDegrees subtract(int operand) {
		CompassDegrees cdResult = new CompassDegrees(0);
		cdResult._value = _value - operand;
		return cdResult;
	}
	
	public CompassDegrees subtractAndNormalize(int operand) {
		CompassDegrees cdResult = subtract(operand);
		cdResult.normalize();
		return cdResult;
	}
	
	public static CompassDegrees convertFromRadians(double dRadians) {
		CompassDegrees cdResult = new CompassDegrees((int) (180.0 * dRadians / Math.PI));
		return cdResult;
	}
	
	public CompassDegrees toNearest4Point() {
		normalize();
		float q = _value / 90.0f;
		return new CompassDegrees((int) Math.round(q) * 90);
	}
	
	public CompassDegrees toNearest8Point() {
		normalize();
		float q = _value / 45.0f;
		return new CompassDegrees((int) Math.round(q) * 45);
	}

	@Override
	public int hashCode() {
		return _value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof CompassDegrees other) {
				return _value == other._value;
			} else if (obj instanceof Number objNumber) {
				return _value == objNumber.intValue();
			}
		}
		return false;
	}

	@Override
	public int compareTo(CompassDegrees o) {
		Objects.requireNonNull(o, "o");
		return Integer.compare(_value,  o._value);
	}

	@Override
	public int compareTo(int other) {
		return Integer.compare(_value,  other);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(_value);
		sb.append(DEGREES);
		if (!isNormalized()) {
			sb.append("\u2020");
		}
		return sb.toString();
	}
}
