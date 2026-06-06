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

import srojak.core.tools.ArrayMethods;

/**
 * @author Stephen
 *
 */
public enum CircleOctant {
	RIGHT(0, 0.0),
	UPPER_RIGHT(1, 0.25),
	UP(2, 0.5),
	UPPER_LEFT(3, 0.75),
	LEFT(4, 1.0),
	LOWER_LEFT(5, -0.75),
	DOWN(6, -0.5),
	LOWER_RIGHT(7, -0.25);
	
	private static final double A4 = Math.PI / 4.0;
	
	private final int _index;
	private final double _factor;
	
	private CircleOctant(int index, double factor) {
		_index = index;
		_factor = factor * Math.PI;
	}
	
	public boolean isIndexEqual(int nValue) {
		return _index == nValue;
	}
	
	public double getAngleTo() {
		return _factor;
	}
	
	public static CircleOctant getOctantFor(double dRadians) {
		double dd = Math.round(RadiansMethods.normalizeAngle(dRadians) / A4);
		int n = ((int) dd + 8) % 8;
		return ArrayMethods.findFirst(CircleOctant.values(), i -> i.isIndexEqual(n));
	}
}
