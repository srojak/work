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
package srojak.psq.swing;

import java.awt.Color;
import java.awt.Font;

import srojak.cdo.ColorMethods;

/**
 * @author Stephen
 *
 */
public class PlaneSquareGraphics {

	public static Color colorGridLines;
	public static Color colorSquareBorder;
	public static Color colorSelection;
	
	public static Font fontGridLocation;
	
	static {
		colorGridLines = new Color(0, 0, 192);
		colorSquareBorder = Color.MAGENTA;
		colorSelection = ColorMethods.changeAlpha(Color.GREEN, 64);
		
		fontGridLocation = new Font("Monospaced", Font.PLAIN, 8);
	}
}
