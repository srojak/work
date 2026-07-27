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
package srojak.mantle;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class CharTagWrapper<T>
		implements CharTagged<T> {
	private final char _tag;
	private final T _value;

	/**
	 * 
	 */
	public CharTagWrapper(char charTag, T objValue) {
		Objects.requireNonNull(objValue, "objValue");
		_tag = charTag;
		_value = objValue;
	}

	@Override
	public char getTag() {
		return _tag;
	}
	
	@Override
	public T getValue() {
		return _value;
	}

	@Override
	public boolean isValueEqual(Object obj) {
		return _value.equals(obj);
	}
}
