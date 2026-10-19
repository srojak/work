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
package srojak.mantle.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import srojak.core.keys.ClassKey;

/**
 * @author Stephen
 *
 */
public class TypedArrayList<E>
		extends ArrayList<E>
		implements TypedList<E> {
	private final ClassKey _ckElements;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6953615176077039039L;
	
	public TypedArrayList(Class<E> classElement) {
		super();
		Objects.requireNonNull(classElement, "classElement");
		_ckElements = new ClassKey(classElement);
	}
	
	public TypedArrayList(Class<E> classElement, Collection<? extends E> c) {
		super(c);
		Objects.requireNonNull(classElement, "classElement");
		_ckElements = new ClassKey(classElement);
	}

	@Override
	public Class<?> getElementClass() {
		return _ckElements.getReferencedClass();
	}

	@Override
	public ClassKey getElementClassKey() {
		return _ckElements;
	}
}
