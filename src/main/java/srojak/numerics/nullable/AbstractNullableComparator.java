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

import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToIntBiFunction;

import srojak.core.excepts.NullValueException;
import srojak.numerics.NullableComparable;

/**
 * @author Stephen
 *
 */
public abstract class AbstractNullableComparator<T extends NullableComparable<T>>
		implements Comparator<T> {
	private ToIntBiFunction<T, T> _fnNullOperands;
	
	public int refuseNullOperands(T o1, T o2) {
		throw new NullValueException("at least one operand in comparison is null");
	}
	
	protected AbstractNullableComparator() {
		_fnNullOperands = (o1, o2) -> refuseNullOperands(o1, o2);
	}
	
	public void setNullOperandBehavior(ToIntBiFunction<T, T> function) {
		Objects.requireNonNull(function, "function");
		_fnNullOperands = function;
	}
	
	protected final int compareWhenNull(T operand1, T operand2) {
		return _fnNullOperands.applyAsInt(operand1, operand2);
	}
	
	protected abstract int compareNonNull(T operand1, T operand2);

	@Override
	public int compare(T o1, T o2) {
		Objects.requireNonNull(o1, "o1");
		Objects.requireNonNull(o2, "o2");
		if (o1.isNull() || o2.isNull()) {
			return compareWhenNull(o1, o2);
		} else {
			return compareNonNull(o1, o2);
		}
	}
}
