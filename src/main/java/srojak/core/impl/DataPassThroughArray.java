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
package srojak.core.impl;

import java.util.Arrays;
import java.util.Objects;

import srojak.core.data.DataPassThrough;

/**
 * @author Stephen
 *
 */
public class DataPassThroughArray 
		implements DataPassThrough {
	private final Object[] _array;

	/**
	 * Constructor.
	 * @param strings
	 */
	public DataPassThroughArray(Object[] objects) {
		_array = Arrays.copyOf(objects, objects.length);
	}

	@Override
	public boolean isEmpty() {
		return _array.length == 0;
	}

	@Override
	public int size() {
		return _array.length;
	}

	@Override
	public Object get(int index) {
		Objects.checkIndex(index, _array.length);
		return _array[index];
	}

	@Override
	public String getAsString(int index) {
		Objects.checkIndex(index, _array.length);
		Object obj = _array[index];
		if (obj instanceof String str) {
			return str;
		} else {
			return obj.toString();
		}
	}

	@Override
	public <T> T getAs(int index) {
		Objects.checkIndex(index, _array.length);
		@SuppressWarnings("unchecked")
		T value = (T) _array[index];
		return value;
	}
}
