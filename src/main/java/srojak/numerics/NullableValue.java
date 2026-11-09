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

/**
 * @author Stephen
 *
 * Nullable containers for primitive values.
 * {@code null} values propagate through calculations.
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
public interface NullableValue {
	
	public static final String STR_NULL = "null";

	/**
	 * Is the value of the object {@code null}?
	 * @return {@code true} if the value of the object is {@code null}.
	 */
	boolean isNull();
}
