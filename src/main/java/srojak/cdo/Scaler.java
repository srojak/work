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

import srojak.cdo.events.ScaleChangeListener;
import srojak.numerics.intervals.IntervalDouble;

/**
 * @author Stephen
 *
 */
public interface Scaler
		extends ScaleChangeEventOriginator {
	
	/**
	 * Get the current scale.
	 * @return The current scale factor.
	 */
	double getScale();
	
	/**
	 * Get the unscaled surface size.
	 * @return A {@code Dimension} object containing the unscaled surface size.
	 */
	Dimension getUnscaledSurfaceSize();
	
	/**
	 * Get the scaled full size.
	 * @return A {@code Dimension} object containing the surface size at the current scale.
	 */
	Dimension getScaledFullSize();
	
	/**
	 * Is the scale range limited?
	 * @return {@code true} if the scale is limited within an interval.
	 */
	boolean isRangeLimited();
	
	/**
	 * Get the interval limiting the scale range, if any.
	 * @return The interval limiting the scale range, or {@code null} if there is no limit.
	 */
	IntervalDouble getLimits();
}
