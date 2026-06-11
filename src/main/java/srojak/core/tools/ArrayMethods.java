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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ArrayMethods {

	public static <T> int countWhere(T[] array, Predicate<T> predicate) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(predicate, "predicate");
		int nCount = 0;
		for (T item : array) {
			if (predicate.test(item)) {
				nCount++;
			}
		}
		return nCount;
	}
	
	public static <T> List<T> where(T[] array, Predicate<T> predicate) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(predicate, "predicate");
		LinkedList<T> list = new LinkedList<T>();
		for (T item : array) {
			if (predicate.test(item)) {
				list.addLast(item);
			}
		}
		return list;
	}
	
	public static <T> T findFirst(T[] array,  Predicate<T> predicate) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(predicate, "predicate");
		for (T item : array) {
			if (predicate.test(item)) {
				return item;
			}
		}
		return null;
	}
	
	public static <T> int findFirstIndex(T[] array,  Predicate<T> predicate) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(predicate, "predicate");
		for (int i = 0; i < array.length; i++) {
			if (predicate.test(array[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static <T> boolean equalsAny(T[] array, T itemCompare) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(itemCompare, "itemCompare");
		for (T item : array) {
			if (itemCompare.equals(item)) {
				return true;
			}
		}
		return false;		
	}
	
	public static <T> boolean isTrueForAny(T[] array, Predicate<T> predicate) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(predicate, "predicate");
		for (T item : array) {
			if (predicate.test(item)) {
				return true;
			}
		}
		return false;		
	}
	
	public static <T> boolean isTrueForAll(T[] array, Predicate<T> predicate) {
		Objects.requireNonNull(array, "array");
		Objects.requireNonNull(predicate, "predicate");
		for (T item : array) {
			if (!predicate.test(item)) {
				return false;
			}
		}
		return true;		
	}
}
