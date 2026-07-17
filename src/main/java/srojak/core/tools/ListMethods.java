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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Stephen
 *
 */
public class ListMethods {
	
	public static <T> int countWhere(List<T> list, Predicate<T> predicate) {
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(predicate, "predicate");
		int nCount = 0;
		for (T item : list) {
			if (predicate.test(item)) {
				nCount++;
			}
		}
		return nCount;
	}
	
	public static <T> T findInList(List<T> list, Predicate<T> predicate) {
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(predicate, "predicate");
		for (T item : list) {
			if (predicate.test(item)) {
				return item;
			}
		}
		return null;
	}
	
	public static <T> boolean isTrueForAny(List<T> list, Predicate<T> predicate) {
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(predicate, "predicate");
		for (T item : list) {
			if (predicate.test(item)) {
				return true;
			}
		}
		return false;		
	}
	
	public static <T> boolean isTrueForAll(List<T> list, Predicate<T> predicate) {
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(predicate, "predicate");
		for (T item : list) {
			if (!predicate.test(item)) {
				return false;
			}
		}
		return true;		
	}
	
	public static <T> List<T> getOrderedList(List<T> list) {
		Objects.requireNonNull(list, "list");
		return list.stream().sorted().toList();
	}
	
	public static <T> List<T> getOrderedList(List<T> list, Comparator<? super T> comparator) {
		Objects.requireNonNull(list, "list");
		Objects.requireNonNull(comparator, "comparator");
		return list.stream().sorted(comparator).toList();
	}
	
	public static <T> List<T> makeListOf(T[] array) {
		Objects.requireNonNull(array, "array");
		ArrayList<T> list = new ArrayList<T>(array.length);
		list.addAll(Arrays.asList(array));
		return list;
	}
	
	public static <T, R> List<R> transform(List<T> listSource, Function<T, R> fnTransform) {
		Objects.requireNonNull(listSource, "listSource");
		Objects.requireNonNull(fnTransform, "fnTransform");
		return listSource.stream().map(fnTransform).toList();
	}
}
