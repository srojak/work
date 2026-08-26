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
package srojak.core.data;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class TupleContainer<V>
		implements Tuple<V> {
	private final V _first;
	private final V _second;
	
	public TupleContainer(V valueFirst, V valueSecond) {
		Objects.requireNonNull(valueFirst, "valueFirst");
		Objects.requireNonNull(valueSecond, "valueSecond");
		_first = valueFirst;
		_second = valueSecond;
	}

	@Override
	public V getFirstValue() {
		return _first;
	}

	@Override
	public V getSecondValue() {
		return _second;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_first, _second);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TupleContainer))
			return false;
		@SuppressWarnings("rawtypes")
		TupleContainer other = (TupleContainer) obj;
		return Objects.equals(_first, other._first) && Objects.equals(_second, other._second);
	}

	@Override
	public String toString() {
		return "Tuple [" + _first + ", " + _second + "]";
	}
}
