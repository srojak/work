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

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author Stephen
 *
 */
public interface ReadOnlyCollection<E>
		extends CommonCollectionReadOnly {
	
	/**
     * Returns {@code true} if and only if this collection
     * contains at least one element {@code e} such that
     * {@code Objects.equals(o, e)}.
	 * @param obj The object to be found in the collection.
	 * @return {@code true} if this collection contains the specified object.
     * @throws NullPointerException if the specified element is null.
	 */
	boolean contains(Object obj);
    Iterator<E> iterator();
    Object[] toArray();
    <T> T[] toArray(T[] a);
    void forEach(Consumer<E> consumer);
}
