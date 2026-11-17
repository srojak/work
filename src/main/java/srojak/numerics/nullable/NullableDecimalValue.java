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
package srojak.numerics.nullable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

import srojak.core.field.Lazy;
import srojak.core.field.LazyInt;
import srojak.numerics.OrderedComparison;
import srojak.numerics.extmath.MathContextDyadicRule;

/**
 * @author Stephen
 *
 */
public class NullableDecimalValue 
		extends NullableValueBase 
		implements NullableDecimal {
	private final BigDecimal _value;
	private final MathContext _ctxMath;
	private final LazyInt _hash;
	private final Lazy<String> _strValue;
	
	NullableDecimalValue(BigDecimal value, MathContext context) {
		super(false);
		Objects.requireNonNull(value, "value");
		Objects.requireNonNull(context, "context");
		_value = value;
		_ctxMath = context;
		_hash = new LazyInt(() -> _value.hashCode());
		_strValue = new Lazy<String>(() -> _value.toEngineeringString());
	}
	
	NullableDecimalValue() {
		super(true);
		_value = BigDecimal.ZERO;
		_ctxMath = MathContext.UNLIMITED;
		_hash = new LazyInt(() -> 0);
		_strValue = new Lazy<String>(() -> STR_NULL);
	}

	@Override
	public MathContext getMathContext() {
		return _ctxMath;
	}

	@Override
	public boolean isEqual(NullableDecimal obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int compareTo(NullableDecimal o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal value() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal valueOr(BigDecimal valueIfNull) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEqualTo(BigDecimal value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableBoolean compareTo(OrderedComparison comparison, NullableDecimal other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal plus(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal plus(NullableDecimal operand, MathContextDyadicRule ruleDyad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal minus(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal minus(NullableDecimal operand, MathContextDyadicRule ruleDyad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal subtractFrom(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal times(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal times(NullableDecimal operand, MathContextDyadicRule ruleDyad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal dividedBy(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal dividedBy(NullableDecimal operand, MathContextDyadicRule ruleDyad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal dividedInto(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal remainder(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal remainder(NullableDecimal operand, MathContextDyadicRule ruleDyad) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal[] divideAndRemainder(NullableDecimal operand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal abs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal negate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal pow(int power) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal pow(NullableInt power) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal round() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal sqrt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal movePointLeft(int places) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal movePointRight(int places) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullableDecimal max(NullableDecimal operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableDecimalValue();
		} else {
			// avoid getting into conformable math contexts
			if (_value.compareTo(_value) >= 0) {
				return this;
			} else {
				return operand;
			}
		}
	}

	@Override
	public NullableDecimal min(NullableDecimal operand) {
		Objects.requireNonNull(operand, "operand");
		if (_isNull || operand.isNull()) {
			return new NullableDecimalValue();
		} else {
			// avoid getting into conformable math contexts
			if (_value.compareTo(_value) <= 0) {
				return this;
			} else {
				return operand;
			}
		}
	}

	/**
	 * Return a hash code for the object.
	 * @return A hash code for the instance.
	 * @see Long#hashCode(int)
	 */
	@Override
	public int hashCode() {
		return _hash.get();
	}

	/**
	 * Test another object for equality to the current object.
	 * @return {@code true} if the other object should be considered equal.
	 *  
	 * This will not return {@code true} if {@code obj} is the same as {@code this}
	 *  and the object is {@code null}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (_isNull || obj == null) {
			return false;
		} else if (obj instanceof NullableDecimal other) {
			return isEqual(other);
		} else if (obj instanceof BigDecimal other) {
			return _value.equals(other);
		} else {
			return false;
		}
	}

	@Override
	protected Object clone() throws 
			CloneNotSupportedException {
		if (_isNull) {
			return new NullableDecimalValue();
		} else {
			return new NullableDecimalValue(_value, _ctxMath);
		}
	}

	@Override
	public String toString() {
		return _strValue.get();
	}
}
