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
	public static final double PiOver2;
	
	public static final double PiOver16;
	public static final double PiOver8;
	public static final double PiOver4;
	
	static {
		TWOPI = Math.PI * 2.0d;
		PiOver2 = Math.PI * 0.5d;
		
		PiOver16 = Math.PI / 16.0d;
		PiOver8 = Math.PI / 8.0d;
		PiOver4 = Math.PI / 4.0d;
	}
	
	/**
	 * Normalize an angle to be in the interval (-PI, PI].
	 * @param dRadians the angle in radians.
	 * @return The normalized angle.
	 */
	public static double normalizeAngle(double dRadians) {
		return dRadians - TWOPI * Math.floor((dRadians + Math.PI) / TWOPI);
	}

	/**
	 * Convert from degrees to radians.
	 * @param dDegrees the angle from the positive X axis in degrees.
	 * @return The normalized angle in radians.
	 */
	public static double fromDegrees(double dDegrees) {
		double dRadians = dDegrees * Math.PI / 180.0;
		return normalizeAngle(dRadians);
	}
}
