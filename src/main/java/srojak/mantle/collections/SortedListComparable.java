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
package srojak.mantle.collections;

import java.util.Collection;

/**
 * @author Stephen
 *
 */
public class SortedListComparable<E extends Comparable<E>>
		extends SortedList<E> {

	/**
	 * 
	 */
	public SortedListComparable(Class<E> classElement) {
		super(classElement, (o1, o2) -> o1.compareTo(o2));
	}

	/**
	 * 
	 * @param c
	 */
	public SortedListComparable(Class<E> classElement, Collection<? extends E> c) {
		super(classElement, (o1, o2) -> o1.compareTo(o2), c);
	}

}
