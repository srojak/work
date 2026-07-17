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

import java.util.Comparator;

/**
 * @author Stephen
 *
 */
public class S2CoordsComparerRowWise
		implements Comparator<S2Coords> {

	@Override
	public int compare(S2Coords o1, S2Coords o2) {
		int nCompar = Integer.compare(o1._y, o2._y);
		if (nCompar == 0) {
			nCompar = Integer.compare(o1._x, o2._x);
		}
		return nCompar;
	}

}
