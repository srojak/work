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
package srojak.core.collections;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Predicate;

/**
 * @author Stephen
 *
 */
public interface MutableValueList<V> 
		extends ValueList<V> {

	void clear();
	boolean add(V e);
	void add(int index, V element);
	V remove(int index);
	boolean remove(Object o);
	boolean addAll(Collection<? extends V> c);
	boolean addAll(int index, Collection<? extends V> c);
	boolean removeAll(Collection<?> c);
	V set(int index, V element);
	ListIterator<V> listIterator(int index);
	ListIterator<V> listIterator();
	Iterator<V> mutableIterator();
	boolean removeIf(Predicate<? super V> filter);
	void sort(Comparator<? super V> c);
}
