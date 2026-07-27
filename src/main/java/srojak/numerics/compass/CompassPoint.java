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
package srojak.numerics.compass;

import java.util.Objects;
import java.util.function.Predicate;

import srojak.numerics.CircleOctant;
import srojak.numerics.RadiansMethods;

/**
 * @author Stephen
 *
 */
public enum CompassPoint 
		implements CompassOrdinals {
	N(true, OrdNorth, CircleOctant.UP),
	NE(false, OrdNorthEast,CircleOctant.UPPER_RIGHT),
	E(true, OrdEast, CircleOctant.RIGHT),
	SE(false, OrdSouthEast, CircleOctant.LOWER_RIGHT),
	S(true, OrdSouth, CircleOctant.DOWN),
	SW(false, OrdSouthWest, CircleOctant.LOWER_LEFT),
	W(true, OrdWest, CircleOctant.LEFT),
	NW(false, OrdNorthWest, CircleOctant.UPPER_LEFT);
	
	private final boolean _bCardinal;
	private final int _nOrdinal;
	private final CompassDegrees _degrees;
	private final CircleOctant _octant;
	private final double _radiansGraphics;
	
	private CompassPoint(boolean bIsCardinal, int ordinal, CircleOctant octant) {
		_bCardinal = bIsCardinal;
		_nOrdinal = ordinal;
		_degrees = new CompassDegrees(45 * ordinal);
		_octant = octant;
		_radiansGraphics = - _octant.getAngleTo();
	}
	
	public final int getOrdinal() {
		return _nOrdinal;
	}	
	
	public boolean isCardinal() {
		return _bCardinal;
	}

	public CompassDegrees getDegrees() {
		return _degrees;
	}
	
	public CircleOctant getOctant() {
		return _octant;
	}
	
	public double getGraphicsRadians() {
		return _radiansGraphics;
	}
	
	public static CompassPoint find(Predicate<CompassPoint> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		for (CompassPoint cpoint : values()) {
			if (predicate.test(cpoint)) {
				return cpoint;
			}
		}
		return null;
	}
}
