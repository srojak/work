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
package srojak.spatial;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class S2FacingCarrier<T>
		implements S2Facing<T> {
	private final S2Direction _direction;
	private final T _value;
	
	public S2FacingCarrier(S2Direction direction, T value) {
		Objects.requireNonNull(direction, "direction");
		Objects.requireNonNull(value, "value");
		_direction = direction;
		_value = value;
	}

	@Override
	public S2Direction getDirection() {
		return _direction;
	}

	@Override
	public T getValue() {
		return _value;
	}

}
