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
package srojak.cdo;

import java.awt.Dimension;

import srojak.numerics.DoubleMethods;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public class LimitedScaleControl
		extends ScaleControl {

	/**
	 * @param dOriginalScale
	 * @param dmSurface
	 */
	public LimitedScaleControl(double dOriginalScale, double dScaleMin,
			double dScaleMax, Dimension dmSurface) {
		super(dOriginalScale, dmSurface);
		if (DoubleMethods.compare(OrderedComparison.LE, dScaleMin, 0.0d)) {
			throw new IllegalArgumentException("dScaleMin must be positive");
		}
		if (DoubleMethods.compare(OrderedComparison.LE, dScaleMax, 0.0d)) {
			throw new IllegalArgumentException("dScaleLimitHigh must be positive");
		}
		setLimits(dScaleMin, dScaleMax, false);
	}

}
