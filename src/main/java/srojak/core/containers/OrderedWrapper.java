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

import srojak.core.Ordered;

/**
 * @author Stephen
 *
 */
public class OrderedWrapper<T>
		implements Ordered<T> {
	private final int _order;
	private final T _value;

	/**
	 * 
	 */
	public OrderedWrapper(T value, int order) {
		Objects.requireNonNull(value, "value");
		_value = value;
		_order = order;
	}

	@Override
	public int getOrder() {
		return _order;
	}

	@Override
	public Object getValueObject() {
		return _value;
	}

	@Override
	public T getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return "order=" + _order + ", " + _value + "]";
	}

}
