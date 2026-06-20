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
package srojak.core.containers;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class NamedAndLabeledObj<T>
		extends NamedAndLabeledBase {
	private final T _value;

	/**
	 * @param strName
	 * @param strLabel
	 */
	public NamedAndLabeledObj(String strName, String strLabel, T value) {
		super(strName, strLabel);
		Objects.requireNonNull(value, "value");
		_value = value;
	}

	/**
	 * @param strName
	 */
	public NamedAndLabeledObj(String strName, T value) {
		super(strName);
		Objects.requireNonNull(value, "value");
		_value = value;
	}

	public T getValue() {
		return _value;
	}
}
