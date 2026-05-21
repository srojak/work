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
import java.util.function.BiPredicate;

/**
 * @author Stephen
 *
 * A bundle of possible methods for equality tests, to reduce the number of methods required.
 * @param <T> The type of the object being evaluated.
 */
public final class UnitTestEqualsMethods<T> 
		extends UnitTestMethodsBase<T> {
	private final BiPredicate<T, T> _comparerEquals;
	
	/**
	 * Default constructor.
	 * The {@code equals( )} method of the object will be used.
	 */
	public UnitTestEqualsMethods() {
		super();
		_comparerEquals = null;
	}
	
	/**
	 * Constructor with equality comparison callback.
	 * @param comparerEquals The callback to compare objects of type {@code <T>}
	 *   for equality.
	 */
	public UnitTestEqualsMethods(BiPredicate<T, T> comparerEquals) {
		super();
		Objects.requireNonNull(comparerEquals, "comparerEquals");
		_comparerEquals = comparerEquals;
	}
	
	/**
	 * Constructor with formatter.
	 * The {@code equals( )} method of the object will be used.
	 * @param formatter The element formatter to use.
	 */
	public UnitTestEqualsMethods(UnitTestElementFormatter<T> formatter) {
		super(formatter);
		_comparerEquals = null;
	}
	
	/**
	 * Constructor with equality comparison callback and formatter.
	 * @param comparerEquals The callback to compare objects of type {@code <T>}
	 *   for equality.
	 * @param formatter The element formatter to use.
	 */
	public UnitTestEqualsMethods(BiPredicate<T, T> comparerEquals,
			UnitTestElementFormatter<T> formatter) {
		super(formatter);
		Objects.requireNonNull(comparerEquals, "comparerEquals");
		_comparerEquals = comparerEquals;
	}
	
	/**
	 * Is an explicit comparison defined?
	 * @return {@code true} if a comparison callback was provided in construction.
	 */
	public boolean hasExplicitComparison() {
		return _comparerEquals != null;
	}
	
	/**
	 * Compare two values for equality.
	 * @param value1 The first value to compare.
	 * @param value2 The second value to compare.
	 * @return {@code true} if the values are equal.
	 */
	public boolean areEqual(T value1, T value2) {
		if (value1 == null || value2 == null) {
			return false;
		} else if (_comparerEquals == null) {
			return value1.equals(value2);
		} else {
			return _comparerEquals.test(value1, value2);
		}
	}
}
