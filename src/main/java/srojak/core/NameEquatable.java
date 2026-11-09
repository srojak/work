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
package srojak.core;

/**
 * @author Stephen
 *
 */
public interface NameEquatable 
		extends Named {

	/**
	 * Compare the name to another string for equality.
	 * It is up to the object whether or not the comparison is case-sensitive.
	 * @param strName The string to be compared.
	 * @return {@code true} if the strings are equal.
	 */
	boolean isNameEqual(String strName);
}
