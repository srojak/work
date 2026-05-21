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
package srojak.core.reflect;

/**
 * @author Stephen
 *
 * A type bearing value that supports direct comparison.
 * @param <V> the base type of value that the object can store.
 */
public class TypeBearingComparableValue<V extends Comparable<V>>
		extends TypeBearingValue<V> 
		implements Comparable<V> {

	/**
	 * Constructor.
	 * @param value The value to store.
	 */
	public TypeBearingComparableValue(V value) {
		super(value);
	}

	/**
	 * Compares the value in this object with the value passed as a parameter for order.
	 * @param o The value to which to compare.
     * @return  a negative integer, zero, or a positive integer as the value of this object
     *          is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to the value of this object.
	 */
	@Override
	public int compareTo(V o) {
		return _value.compareTo(o);
	}

}
