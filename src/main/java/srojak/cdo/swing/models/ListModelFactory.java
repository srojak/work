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

import java.util.Comparator;
import java.util.Objects;

import srojak.core.collections.SortedList;

/**
 * @author Stephen
 *
 */
public class ListModelFactory<V> {
	private final Class<V> _classItem;
	private final Comparator<V> _comparer;

	public ListModelFactory(Class<V> classItem, Comparator<V> comparer) {
		Objects.requireNonNull(classItem, "classItem");
		Objects.requireNonNull(comparer, "comparer");
		_classItem = classItem;
		_comparer = comparer;
	}
	
	public ModifiableListModel<V> create(boolean bIsSorted) {
		if (bIsSorted) {
			SortedList<V> listBase = new SortedList<V>(_classItem, _comparer);
			return new SortedListModel<V>(listBase);		
		} else {
			return new SequencedListModel<V>();
		}
	}
}
