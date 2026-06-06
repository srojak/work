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
import java.awt.color.ColorSpace;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ColorSelfSelectProvider
	extends Color
	implements ColorSelectionProvider {

	/**
	 * @param rgb
	 */
	public ColorSelfSelectProvider(int rgb) {
		super(rgb);
	}

	/**
	 * @param rgba
	 * @param hasalpha
	 */
	public ColorSelfSelectProvider(int rgba, boolean hasalpha) {
		super(rgba, hasalpha);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 */
	public ColorSelfSelectProvider(int r, int g, int b) {
		super(r, g, b);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 */
	public ColorSelfSelectProvider(float r, float g, float b) {
		super(r, g, b);
	}

	/**
	 * @param cspace
	 * @param components
	 * @param alpha
	 */
	public ColorSelfSelectProvider(ColorSpace cspace, float[] components, float alpha) {
		super(cspace, components, alpha);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public ColorSelfSelectProvider(int r, int g, int b, int a) {
		super(r, g, b, a);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public ColorSelfSelectProvider(float r, float g, float b, float a) {
		super(r, g, b, a);
	}
	
	public ColorSelfSelectProvider(Color colorOrig) {
		super(colorOrig.getRGB());
	}

	@Override
	public Color getSelectionColor() {
		return this;
	}

	public static List<ColorSelfSelectProvider> fromColors(Color first, Color ... rest) {
		Objects.requireNonNull(first, "first");
		LinkedList<ColorSelfSelectProvider> list = new LinkedList<ColorSelfSelectProvider>();
		list.add(new ColorSelfSelectProvider(first));
		for (Color color : rest) {
			list.add(new ColorSelfSelectProvider(color));
		}
		return list;
	}
}
