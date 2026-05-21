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
package srojak.numerics;

/**
 * @author Stephen
 *
 */
public class RadiansMethods {
		
	public static final double TWOPI;
	public static final double HALFPI;
	
	static {
		TWOPI = Math.PI * 2.0d;
		HALFPI = Math.PI * 0.5d;
	}
	
	/**
	 * Normalize an angle to be in the interval (-PI, PI].
	 * @param dRadians the angle in radians
	 * @return The normalized angle.
	 */
	public static double normalizeAngle(double dRadians) {
		return dRadians - TWOPI * Math.floor((dRadians + Math.PI) / TWOPI);
	}

}
