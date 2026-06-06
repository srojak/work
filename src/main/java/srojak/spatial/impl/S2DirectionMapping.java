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
package srojak.spatial.impl;

import java.util.Objects;

import srojak.core.containers.TupleContainer;
import srojak.core.specialized.Tuple;
import srojak.numerics.CircleOctant;
import srojak.spatial.S2CompassDirection;

/**
 * @author Stephen
 *
 */
public class S2DirectionMapping {
	private final S2CompassDirection _direction;
	private final CircleOctant _octant;
	private final Tuple<Integer> _intKey;
	
	public S2DirectionMapping(S2CompassDirection direction, CircleOctant octant, int nKeyX, int nKeyY) {
		Objects.requireNonNull(direction, "direction");
		Objects.requireNonNull(octant, "octant");
		_direction = direction;
		_octant = octant;
		_intKey = new TupleContainer<Integer>(Integer.valueOf(nKeyX), Integer.valueOf(nKeyY));
	}
	
	public S2CompassDirection getDirection() {
		return _direction;
	}
	
	public CircleOctant getOctant() {
		return _octant;
	}
	
	public Tuple<Integer> getLocatorKey() {
		return _intKey;
	}
}
