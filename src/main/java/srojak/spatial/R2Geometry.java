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

import srojak.numerics.IRandomSource;
import srojak.numerics.RadiansMethods;

/**
 * @author Stephen
 *
 */
public class R2Geometry {
	
	/**
	 * 
	 * @param random
	 * @param dRadius
	 * @param dOffset
	 * @return
	 * 
	 * must take square root of random double used on radius to avoid bias where points
	 * 		cluster near the center.
	 * @see https://stackoverflow.com/questions/5837572/generate-a-random-point-within-a-circle-uniformly
	 */
	public static PolarCoords generateRandomPointInCircle(IRandomSource random, 
			double dRadius, double dOffset) {
		double dFirst = random.genDouble();
		double dSecond = random.genDouble();
		return new PolarCoords(dRadius * Math.sqrt(dFirst) + dOffset,
				RadiansMethods.TWOPI * dSecond);
	}
}
