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

import srojak.core.observe.ObsPassThroughList;

/**
 * @author Stephen
 *
 */
public class ObservablePassThroughArray 
		implements ObsPassThroughList {
	private final String[] _array;

	/**
	 * Constructor.
	 * @param strings
	 */
	public ObservablePassThroughArray(String[] strings) {
		_array = Arrays.copyOf(strings, strings.length);
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
	public String get(int index) {
		return _array[index];
	}
}
