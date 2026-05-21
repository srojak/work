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

import java.util.Objects;

/**
 * @author Stephen
 *
 * @param <T> The type of the object being evaluated.
 */
public abstract class UnitTestMethodsBase<T> {
	private final UnitTestElementFormatter<T> _formatter;

	/**
	 * Default constructor.
	 */
	public UnitTestMethodsBase() {
		_formatter = new UTestEFormatterDirect<T>();		
	}
	
	/**
	 * Constructor with formatter.
	 * @param formatter The element formatter to use.
	 */
	public UnitTestMethodsBase(UnitTestElementFormatter<T> formatter) {
		Objects.requireNonNull(formatter, "formatter");
		_formatter = formatter;
	}
	
	/**
	 * Obtain a string representation of the item using the defined formatter.
	 * @param value The object to be represented.
	 * @return A string representation of the item.
	 */
	public String format(T value) {
		return _formatter.format(value);
	}
}
