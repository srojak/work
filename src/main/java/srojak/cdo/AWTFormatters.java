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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class AWTFormatters {

	
	public static String formatColor(Color color) {
		Objects.requireNonNull(color);
		return "r=" + color.getRed() + ",g=" + color.getGreen() + ",b=" + color.getBlue();
	}
	
	public static String formatPoint(Point pt) {
		Objects.requireNonNull(pt);
		return "x=" + pt.x + ",y=" + pt.y;
	}
	
	public static String formatDimension(Dimension dm) {
		Objects.requireNonNull(dm);
		return "width=" + dm.width + ",height=" + dm.height;
	}
	
	public static String formatRectangle(Rectangle r) {
		Objects.requireNonNull(r);
		return "x=" + r.x + ",y=" + r.y + ",width=" + r.width + ",height=" + r.height;
	}
}
