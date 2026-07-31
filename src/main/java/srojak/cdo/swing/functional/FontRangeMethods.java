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
package srojak.cdo.swing.functional;

import java.util.Objects;

import javax.swing.SpinnerNumberModel;

import srojak.cdo.FontRange;
import srojak.numerics.intervals.IntervalInt;

/**
 * @author Stephen
 *
 */
public class FontRangeMethods {

	public static SpinnerNumberModel makeSpinnerModel(FontRange rangeFont) {
		Objects.requireNonNull(rangeFont, "rangeFont");
		IntervalInt range = rangeFont.getSizeRange();
		return new SpinnerNumberModel(rangeFont.getSize(),
				range.getMinimumValue(),
				range.getMaximumValue(), 1);
	}
}
