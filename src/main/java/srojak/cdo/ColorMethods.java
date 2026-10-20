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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import srojak.core.Labeled;
import srojak.core.text.LabeledEnvelope;
import srojak.core.tools.ListMethods;

/**
 * @author Stephen
 *
 */
public class ColorMethods 
	implements ExtColors {
	
	private static final LinkedList<Labeled<Color>> _listCommonColors;
	
	static {
		_listCommonColors = new LinkedList<Labeled<Color>>(List.of(
				new LabeledEnvelope<Color>(Color.WHITE, "WHITE"),
				new LabeledEnvelope<Color>(Color.LIGHT_GRAY, "LIGHT_GRAY"),
				new LabeledEnvelope<Color>(Color.GRAY , "GRAY"),
				new LabeledEnvelope<Color>(Color.DARK_GRAY, "DARK_GRAY"),
				new LabeledEnvelope<Color>(Color.BLACK, "BLACK"),
				new LabeledEnvelope<Color>(Color.RED, "RED"),
				new LabeledEnvelope<Color>(Color.PINK, "PINK"),
				new LabeledEnvelope<Color>(Color.ORANGE, "ORANGE"),
				new LabeledEnvelope<Color>(Color.YELLOW, "YELLOW"),
				new LabeledEnvelope<Color>(Color.GREEN, "GREEN"),
				new LabeledEnvelope<Color>(Color.MAGENTA, "MAGENTA"),
				new LabeledEnvelope<Color>(Color.CYAN, "CYAN"),
				new LabeledEnvelope<Color>(Color.BLUE, "BLUE"),
				VIVID_GREEN,
				DARK_GREEN,
				EMERALD,
				SAGE,
				FOREST_GREEN,
				TURQUOISE,
				DARK_CYAN,
				CARAMEL,
				BROWN));
	}
	
	public static List<Labeled<Color>> getAllKnownColors() {
		return List.copyOf(_listCommonColors);
	}
	
	public static void addKnownColor(Color color, String strLabel) {
		Objects.requireNonNull(color);
		Objects.requireNonNull(strLabel);
		if (strLabel.isBlank()) {
			throw new IllegalArgumentException("strLabel is blank");
		}
		_listCommonColors.add(new LabeledEnvelope<Color>(color, strLabel));
	}
	
	public static String formatColor(Color color) {
		Objects.requireNonNull(color);
		return "r=" + color.getRed() + ",g=" + color.getGreen() + ",b=" + color.getBlue();
	}
	
	public static String formatColorMatchKnown(Color color) {
		Objects.requireNonNull(color);
		Labeled<Color> colorKnown = ListMethods.findInList(_listCommonColors, e -> e.getWrapped().equals(color));
		if (colorKnown == null) {
			return formatColor(color);
		} else {
			return colorKnown.toString();
		}
	}

	public static Color changeAlpha(Color color, int alpha) {
		Objects.requireNonNull(color, "color");
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}
	
	public static boolean areColorsEqual(Color color1, Color color2, int fuzzColor, 
			boolean bConsiderAlpha) {
		if (fuzzColor < 0) {
			fuzzColor = 0;
		}
		if (color1 == null || color2 == null) {
			return false;
		} else if (color1 == color2) {
			return true;
		}
		if (Math.abs(color1.getRed() - color2.getRed()) > fuzzColor) {
			return false;
		}
		if (Math.abs(color1.getGreen() - color2.getGreen()) > fuzzColor) {
			return false;
		}
		if (Math.abs(color1.getBlue() - color2.getBlue()) > fuzzColor) {
			return false;
		}
		if (bConsiderAlpha) {
			if (Math.abs(color1.getAlpha() - color2.getAlpha()) > fuzzColor) {
				return false;
			}
		}
		return true;
	}
}
