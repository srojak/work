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
 * A container for methods relevant to unit testing using a specific class.
 * @param <E> The class of the elements under test.
 */
public class UnitTestClassElementMethods<E>
		extends UnitTestMethodsBase<E> {
	private final Class<E> _classElement;

	/**
	 * Default constructor.
	 * @param classElement The class of the elements under test.
	 */
	public UnitTestClassElementMethods(Class<E> classElement) {
		super();
		Objects.requireNonNull(classElement, "classElement");
		_classElement = classElement;
	}

	/**
	 * Constructor with formatter.
	 * @param formatter The element formatter to use.
	 * @param classElement The class of the elements under test.
	 */
	public UnitTestClassElementMethods(Class<E> classElement, UnitTestElementFormatter<E> formatter) {
		super(formatter);
		Objects.requireNonNull(classElement, "classElement");
		_classElement = classElement;
	}

	/**
	 * Get the class for the elements under test.
	 * @return The class object for the elements under test.
	 */
	public Class<E> getElementClass() {
		return _classElement;
	}
	
	/**
	 * Get the simple name of the class for the elements under test.
	 * @return The simple name of the element class.
	 */
	public String getElementSimpleName() {
		return _classElement.getSimpleName();
	}
}
