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

import srojak.numerics.IRandomSource;

/**
 * @author Stephen
 *
 */
public class S2RandomMover {
	private final IRandomSource _rand;
	private final S2Orientation _orient;
	private final S2FieldSize _szField;
	
	public S2RandomMover(IRandomSource sourceRandom, S2Surface surface) {
		Objects.requireNonNull(sourceRandom, "sourceRandom");
		Objects.requireNonNull(surface, "surface");
		_rand = sourceRandom;
		_orient = surface.getOrientation();
		_szField = surface.getFieldSize();
	}
	
	public S2CompassDirection getRandomDirection() {
		int nRoll = _rand.genIntInRange(8);
		return S2CompassDirection.AllDirs.get(nRoll);
	}
	
	public S2CompassDirection getRandomCardinalDirection() {
		int nRoll = _rand.genIntInRange(4);
		return S2CompassDirection.CardinalDirs.get(nRoll);
	}
	
	public S2Offset moveRandom(int nDistance) {
		S2CompassDirection dir = getRandomDirection();
		return _orient.offset(dir, nDistance);
	}
}
