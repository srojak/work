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

import java.awt.GridBagLayout;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class LayoutMethods {

	public static GridBagLayout applyColumnWidths(GridBagLayout layout, int ... widths) {
		Objects.requireNonNull(layout, "layout");
		if (widths.length < 1) {
			throw new IllegalArgumentException("no widths specified");
		}
		layout.columnWidths = widths;
		return layout;
	}
	
	public static GridBagLayout applyRowHeights(GridBagLayout layout, int ... heights) {
		Objects.requireNonNull(layout, "layout");
		if (heights.length < 1) {
			throw new IllegalArgumentException("no heights specified");
		}
		layout.rowHeights = heights;
		return layout;
	}
	
	public static GridBagLayout assignColumnWeights(GridBagLayout layout, double ... weights) {
		Objects.requireNonNull(layout, "layout");
		if (weights.length < 1) {
			throw new IllegalArgumentException("no weights specified");
		}
		layout.columnWeights = weights;
		return layout;
	}
	
	public static GridBagLayout assignRowWeights(GridBagLayout layout, double ... weights) {
		Objects.requireNonNull(layout, "layout");
		if (weights.length < 1) {
			throw new IllegalArgumentException("no weights specified");
		}
		layout.rowWeights = weights;
		return layout;
	}
}
