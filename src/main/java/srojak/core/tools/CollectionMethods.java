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
package srojak.core.tools;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Stephen
 *
 */
public class CollectionMethods {
	
	public static <T> int countWhere(Collection<T> collection, Predicate<T> predicate) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(predicate, "predicate");
		int nCount = 0;
		for (T item : collection) {
			if (predicate.test(item)) {
				nCount++;
			}
		}
		return nCount;
	}
	
	public static <T> T findFirstIn(Collection<T> collection, Predicate<T> predicate) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(predicate, "predicate");
		for (T item : collection) {
			if (predicate.test(item)) {
				return item;
			}
		}
		return null;
	}
	
	public static <T> boolean isTrueForAny(Collection<T> collection, Predicate<T> predicate) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(predicate, "predicate");
		for (T item : collection) {
			if (predicate.test(item)) {
				return true;
			}
		}
		return false;		
	}
	
	public static <T> boolean isTrueForAll(Collection<T> collection, Predicate<T> predicate) {
		Objects.requireNonNull(collection, "collection");
		Objects.requireNonNull(predicate, "predicate");
		for (T item : collection) {
			if (!predicate.test(item)) {
				return false;
			}
		}
		return true;		
	}

}
