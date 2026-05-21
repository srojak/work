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
package srojak.utest.helpers;

/**
 * @author Stephen
 *
 * The base class for element formatters for unit tests.
 * @param <E> The type of an element to be formatted.
 */
public abstract class UnitTestElementFormatter<E> {
	
	protected abstract String formatItem(E item);
	
	/**
	 * Obtain a string representation of the item.
	 * @param item The item to be represented.
	 * @return A string representation of the item,
	 *   or {@code "(null)"} if the item is {@code null}.
	 */
	public final String format(E item) {
		if (item == null) {
			return "(null)";
		} else {
			return formatItem(item);
		}
	}
}
