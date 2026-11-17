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

import java.util.UUID;

import srojak.core.Equatable;
import srojak.core.excepts.NullValueException;
import srojak.numerics.NullableValue;
import srojak.numerics.OrderedComparison;
import srojak.numerics.GuidMethods;

/**
 * @author Stephen
 *
 */
public interface NullableUUID 
		extends NullableValue, Equatable<NullableUUID>, Comparable<NullableUUID> {

	/**
	 * Get the value of the object, if it is not {@code null}.
	 * @return the value of the object.
	 * @throws NullValueException if the object is {@code null}.
	 */
	UUID value();
	
	UUID valueOrZero();
	
	/**
	 * Is the value of the object equal to another value?
	 * @param value The value to test for equality.
	 * @return {@code true} if the value of the object is not null and equal to the parameter {@code value}.
	 * @throws NullPointerException if {@code value} is reference null.
	 */
	boolean isEqualTo(UUID value);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param value The value to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} 
	 * 	if the value of the object is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, UUID value);
	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, NullableUUID other);
	
	byte[] toGuidBytes();
	
	static NullableUUID from(UUID value) {
		return new NullableUUIDValue(value);
	}
	
	static NullableUUID fromNull() {
		return new NullableUUIDValue();
	}
	
	static NullableUUID fromGuidBytes(byte[] bytes) {
		UUID value = GuidMethods.fromGuidBytes(bytes);
		return new NullableUUIDValue(value);
	}
	
	static NullableUUID fromString(String strInput) {
		return NullableUUIDValue.fromString(strInput);
	}
	
	static final UUID ZERO = new UUID(0L, 0L);
}
