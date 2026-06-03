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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class TypedArrayList<E>
		extends ArrayList<E>
		implements TypedList<E> {
	private final Class<E> _classElement;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6953615176077039039L;
	
	public TypedArrayList(Class<E> classElement) {
		super();
		Objects.requireNonNull(classElement, "classElement");
		_classElement = classElement;
	}
	
	public TypedArrayList(Class<E> classElement, Collection<? extends E> c) {
		super(c);
		Objects.requireNonNull(classElement, "classElement");
		_classElement = classElement;
	}

	@Override
	public Class<E> getElementClass() {
		return _classElement;
	}

	@Override
	public boolean isElementAssignableFrom(Class<?> classOther) {
		Objects.requireNonNull(classOther, "classOther");
		return _classElement.isAssignableFrom(classOther);
	}
}
