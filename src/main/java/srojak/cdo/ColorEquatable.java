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
import java.util.Objects;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ColorEquatable
	extends Color {

	/**
	 * @param rgb
	 */
	public ColorEquatable(int rgb) {
		super(rgb);
	}

	/**
	 * @param rgba
	 * @param hasalpha
	 */
	public ColorEquatable(int rgba, boolean hasalpha) {
		super(rgba, hasalpha);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 */
	public ColorEquatable(int r, int g, int b) {
		super(r, g, b);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 */
	public ColorEquatable(float r, float g, float b) {
		super(r, g, b);
	}

	/**
	 * @param cspace
	 * @param components
	 * @param alpha
	 */
	public ColorEquatable(ColorSpace cspace, float[] components, float alpha) {
		super(cspace, components, alpha);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public ColorEquatable(int r, int g, int b, int a) {
		super(r, g, b, a);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public ColorEquatable(float r, float g, float b, float a) {
		super(r, g, b, a);
	}
	
	public ColorEquatable(Color colorOrig) {
		super(colorOrig.getRGB());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRGB(), getAlpha());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof ColorEquatable other) {
			return getRGB() == other.getRGB() && getAlpha() == other.getAlpha();
		} else if (obj instanceof Color other) {
			return getRGB() == other.getRGB() && getAlpha() == other.getAlpha();
		} else {
			return false;
		}
	}
}
