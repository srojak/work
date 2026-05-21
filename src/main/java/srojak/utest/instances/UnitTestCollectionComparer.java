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
package srojak.utest.instances;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * @author Stephen
 *
 * The interface to a test instance that compares a collection to a set of expected values.
 * @param <E> The type of the elements of the collection.
 */
public interface UnitTestCollectionComparer<E> {
	
	/**
	 * The type of the elements in the collection.
	 * @return The type of the elements in the collection.
	 */
	Type getElementType();
	
	/**
	 * Compare an array of expected values to a collection of actual values.
	 * The {@code equals( )} method of the object will be used to compare elements.
	 * @param expected The array of expected values.
	 * @param actual The collection of actual values.
	 */
	void compare(E[] expected, Collection<E> actual);
	
	/**
	 * Compare an array of expected values to a collection of actual values.
	 * @param expected The array of expected values.
	 * @param actual The collection of actual values.
	 * @param comparerEq The callback to compare objects of type {@code <E>}
	 *   for equality.
	 */
	void compare(E[] expected, Collection<E> actual, BiPredicate<E, E> comparerEq);
	
	/**
	 * Compare a list of expected values to a collection of actual values.
	 * The {@code equals( )} method of the object will be used to compare elements.
	 * @param expected The list of expected values.
	 * @param actual The collection of actual values.
	 */
	void compare(List<E> expected, Collection<E> actual);
	
	/**
	 * Compare a list of expected values to a collection of actual values.
	 * @param expected The list of expected values.
	 * @param actual The collection of actual values.
	 * @param comparerEq The callback to compare objects of type {@code <E>}
	 *   for equality.
	 */
	void compare(List<E> expected, Collection<E> actual, BiPredicate<E, E> comparerEq);
}
