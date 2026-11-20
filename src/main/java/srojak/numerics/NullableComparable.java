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

import java.util.Comparator;

import srojak.core.Equatable;
import srojak.numerics.nullable.NullableBoolean;

/**
 * @author Stephen
 *
 * Sometimes called the Curiously Recurring Template Pattern
 * 
 * The basic rules for comparisons are:
 * <ol type="1">
 * <li>Comparisons returning a {@code NullableBoolean}, for which {@code null} moves through the comparison.</li>
 * <li>Comparisons returning a {@code boolean} value return {@code false} if a {@code null} value is an operand.</li>
 * <li>Comparisons returning an {@code int} value cannot express the result if a {@code null} value is an operand.
 * Therefore, they must throw a {@code NullValueException}.</li>
 * </ol>
 * 
 */
public interface NullableComparable<T extends NullableComparable<T>>
		extends NullableValue, Equatable<T>, Comparable<T> {

	
	/**
	 * Compare the value of the object to another value.
	 * @param comparison The comparison to apply.
	 * @param other The other operand to which to compare.
	 * @return A {@code NullableBoolean} object whose value is {@code true} if the value of the object is not {@code null},
	 * 	@{code other} is not {@code null} and the comparison is true.
	 */
	NullableBoolean compareTo(OrderedComparison comparison, T other);
	
	Comparator<T> getComparator();
}
