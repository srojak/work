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
package srojak.cdo.swing.models;

import java.util.Objects;

import srojak.core.collections.SortedList;
import srojak.core.collections.SortedListComparable;

/**
 * @author Stephen
 *
 */
public class ListModelComparableFactory<V extends Comparable<V>> {
	private final Class<V> _classItem;
	
	public ListModelComparableFactory(Class<V> classItem) {
		Objects.requireNonNull(classItem, "classItem");
		_classItem = classItem;
	}
	
	public ModifiableListModel<V> create(boolean bIsSorted) {
		if (bIsSorted) {
			SortedList<V> listBase = new SortedListComparable<V>(_classItem);
			return new SortedListModel<V>(listBase);		
		} else {
			return new SequencedListModel<V>();
		}
	}
}
