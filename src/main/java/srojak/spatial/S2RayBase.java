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

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class S2RayBase
		implements S2CoordsOrigin {
	protected final S2Coords _coordsStart;

	protected static final DecimalFormat _formatLength = new DecimalFormat("0.0##");
	
	public S2RayBase(S2Coords coordsStart) {
		Objects.requireNonNull(coordsStart, "coordsStart");
		_coordsStart = coordsStart;
	}

	@Override
	public S2Coords getOrigin() {
		return _coordsStart;
	}

}
