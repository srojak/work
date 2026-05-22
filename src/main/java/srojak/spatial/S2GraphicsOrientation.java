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

import java.util.ArrayList;
import java.util.List;

import srojak.numerics.CircleOctant;
import srojak.spatial.impl.S2DirectionMapping;

/**
 * @author Stephen
 *
 * The graphics orientation, in which coordinates increase upward and rightward.
 * This conforms to graphics layouts, in which the origin is in the upper left corner.
 */
public class S2GraphicsOrientation
		extends S2OrientationBase {
	private static final List<S2DirectionMapping> _listDirections;
	
	static {
		_listDirections = new ArrayList<S2DirectionMapping>(8);
		_listDirections.add(new S2DirectionMapping(
				S2CompassDirection.North, CircleOctant.DOWN, 0, -1));
		_listDirections.add(new S2DirectionMapping(
				S2CompassDirection.NorthEast, CircleOctant.LOWER_RIGHT, 1, -1));
		_listDirections.add(new S2DirectionMapping(
				S2CompassDirection.East, CircleOctant.RIGHT, 1, 0));
		_listDirections.add(new S2DirectionMapping(
				S2CompassDirection.SouthEast, CircleOctant.UPPER_RIGHT, 1, 1));
		_listDirections.add(new S2DirectionMapping(
				S2CompassDirection.South, CircleOctant.UP, 0, 1));
		_listDirections.add(new S2DirectionMapping(
				S2CompassDirection.SouthWest, CircleOctant.UPPER_LEFT, -1, 1));
		_listDirections.add(new S2DirectionMapping(
				S2CompassDirection.West, CircleOctant.LEFT, -1, 0));
		_listDirections.add(new S2DirectionMapping(
				S2CompassDirection.NorthWest, CircleOctant.LOWER_LEFT, -1, -1));
	}

	/**
	 * Constructor.
	 */
	public S2GraphicsOrientation() {
		super(_listDirections);
	}

	/**
	 * Gets the direction for the orientation where both x and y values increase.
	 * @return The increasing direction for the orientation.
	 */
	@Override
	public S2CompassDirection getIncreasingDirection() {
		return S2CompassDirection.SouthEast;
	}

}
