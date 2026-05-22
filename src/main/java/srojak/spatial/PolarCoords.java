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

import srojak.numerics.RadiansMethods;

/**
 * @author Stephen
 *
 */
public class PolarCoords {
	private double _dRadius;
	private double _dTheta;
	
	public static double calcTheta(double dx, double dy) {
		return RadiansMethods.normalizeAngle(Math.atan2(dy, dx));
	}
	
	public static PolarCoords convertFromS2(double dx, double dy) {
		double dh = dx * dx + dy * dy;
		double dRadius = Math.sqrt(dh);
		return new PolarCoords(dRadius, calcTheta(dx, dy));
	}
	
	public static PolarCoords convertFromS2(int x, int y) {
		return convertFromS2((double) x, (double) y);
	}
	
	public static PolarCoords convertFrom(S2Coords coords) {
		Objects.requireNonNull(coords, "coords");
		return convertFromS2((double) coords._x, (double) coords._y);
	}
	
	public PolarCoords(double dRadius, double dTheta) {
		if (dRadius < 0.0d) {
			throw new IllegalArgumentException("negative dRadius");
		}
		_dRadius = dRadius;
		_dTheta = RadiansMethods.normalizeAngle(dTheta);
	}
	
	public double getRadius() {
		return _dRadius;
	}
	
	public double getTheta() {
		return _dTheta;
	}
	
	public double getThetaInDegrees() {
		double dDegrees = 180.0d * _dTheta / Math.PI;
		if (dDegrees < 0.0d) {
			dDegrees += 360.0d;
		}
		return dDegrees;
	}
	
	public double getX() {
		return _dRadius + Math.cos(_dTheta);
	}
	
	public double getY() {
		return _dRadius + Math.sin(_dTheta);
	}
	
	@Override
	public String toString() {
		return String.format("polar(%.3f, %.3f)", _dRadius, _dTheta);
	}
}
