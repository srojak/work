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
package srojak.core.text;

import java.util.Objects;

import srojak.core.NameToken;
import srojak.core.TextAnnotated;

/**
 * @author Stephen
 *
 */
public class TextAnnotatedWrapper<T> 
		extends TextAnnotatedBase implements TextAnnotated<T> {
	private final T _object;
	
	public static final NameToken ClassToken;

	static {
		Class<?> classThis = TextAnnotatedObjectCarrier.class;
		ClassToken = NameToken.classNameFactory(classThis);
	}
	
	/**
	 * 
	 */
	public TextAnnotatedWrapper(T obj) {
		Objects.requireNonNull(obj, "obj");
		_object = obj;
	}

	@Override
	public Object getObject() {
		return _object;
	}

	@Override
	public Object getWrappedObject() {
		return _object;
	}

	@Override
	public Class<?> getValueClass() {
		return _object.getClass();
	}

	@Override
	public T getWrapped() {
		return _object;
	}

	@Override
	protected NameToken getClassToken() {
		return ClassToken;
	}

}
