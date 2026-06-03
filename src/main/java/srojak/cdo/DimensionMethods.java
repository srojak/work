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
import java.awt.Insets;
import java.util.Objects;

public class DimensionMethods {

	public static Dimension addHorizontal(Dimension ... dims) {
		int width = 0;
		int height = 0;
		for (Dimension dm : dims) {
			width += dm.width;
			height = Math.max(height, dm.height);
		}
		return new Dimension(width, height);
	}
	
	public static Dimension addHorizontal(Insets inset, Dimension ... dims) {
		Objects.requireNonNull(inset, "inset");
		int width = 0;
		int height = 0;
		for (Dimension dm : dims) {
			width += inset.left + inset.right + dm.width;
			height = Math.max(height, inset.top + inset.bottom + dm.height);
		}
		return new Dimension(width, height);
	}
	
	public static Dimension addVertical(Dimension ... dims) {
		int width = 0;
		int height = 0;
		for (Dimension dm : dims) {
			width = Math.max(width, dm.width);
			height += dm.height;
		}
		return new Dimension(width, height);
	}
	
	public static Dimension addVertical(Insets inset, Dimension ... dims) {
		Objects.requireNonNull(inset, "inset");
		int width = 0;
		int height = 0;
		for (Dimension dm : dims) {
			width = Math.max(width, inset.left + inset.bottom + dm.width);
			height += inset.top + inset.bottom + dm.height;
		}
		return new Dimension(width, height);
	}
}
