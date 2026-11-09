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

import srojak.core.Equatable;
import srojak.numerics.NullableValue;

/**
 * @author Stephen
 *
 */
public sealed interface NullableBoolean 
		extends NullableValue, Equatable<NullableBoolean>, Comparable<NullableBoolean>
		permits NullableBooleanBox {

	/**
	 * Is the value of the object {@code true}?
	 * @return {@code true} if the object is not {@code null} and its value is  {@code true}.
	 */
	boolean isTrue();
	
	/**
	 * Is the value of the object {@code false}?
	 * @return {@code true} if the object is not {@code null} and its value is  {@code false}.
	 */
	boolean isFalse();
	
	/**
	 * Perform the logical negation of the object, provided its value is not {@code null}.
	 * @return The logical negation of the obect's value, 
	 * 	or {@code null} if the object is {@code null}.
	 */
	NullableBoolean not();
	
	/**
	 * Perform a logical <i>and</i> operation on the object and the operands.
	 * @param first The first operand to apply.
	 * @param rest The rest of the operands to apply.
	 * @return A {@code null} object if any of the objects are null;
	 * 	otherwise, a {@code true} object if the object and all operands are {@code true}.
	 */
	NullableBoolean and(NullableBoolean first, NullableBoolean ... rest);
	
	/**
	 * Perform a logical <i>or</i> operation on the object and the operands.
	 * @param first The first operand to apply.
	 * @param rest The rest of the operands to apply.
	 * @return A {@code null} object if any of the objects are {@code null};
	 * 	otherwise, a {@code true} object if the object or any of the operands are {@code true}.
	 */
	NullableBoolean or(NullableBoolean first, NullableBoolean ... rest);
	
	/**
	 * Perform a logical <i>exclusive or</i> operation on the object and the operand.
	 * @param operand The operand to apply.
	 * @return A {@code null} object if either operand is {@code null};
	 * 	otherwise, a {@code true} object if the value of the object is not the same
	 * 	as the value of the operand.
	 */
	NullableBoolean xor(NullableBoolean operand);
	
	/**
	 * Return a nullable object containing a value.
	 * @param value The value to use.
	 * @return The {@code NullableBoolean} object representing the value.
	 */
	public static NullableBoolean from(boolean bValue) {
		return new NullableBooleanBox(bValue);
	}
	
	/**
	 * Convert an {@code Boolean} value to a nullable object.
	 * @param value The value to use, which can be {@code null}.
	 * @return The {@code NullableBoolean} object representing the value.
	 */
	public static NullableBoolean from(Boolean bValue) {
		if (bValue == null) {
			return new NullableBooleanBox();
		} else {
			return new NullableBooleanBox(bValue.booleanValue());
		}
	}
	
	/**
	 * Create a nullable object representing {@code null}.
	 * @return The {@code NullableBoolean} object representing {@code null}.
	 */
	public static NullableBoolean fromNull() {
		return new NullableBooleanBox();
	}
	
	/**
	 * Parse a {@code String} containing a representation of a {@code NullableBoolean}
	 *  in the given radix.
	 * @param strInput The string representation of the value.
	 * 	If the string is empty or "null", it is recognized as null.
	 * @return A {@code NullableBoolean} containing the parsed value.
	 * @throws NullPointerException if {@code strInput} is itself {@code null}.
	 * @see Boolean#parseBoolean(String)
	 */
	public static NullableBoolean parse(String strInput) {
		return NullableBooleanBox.parse(strInput);
	}
}
