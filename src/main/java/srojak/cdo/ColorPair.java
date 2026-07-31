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
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class ColorPair {
	private final Color _colorBack;
	private final Color _colorFore;
	
	public ColorPair(Color colorBackground, Color colorForeground) {
		Objects.requireNonNull(colorBackground, "colorBackground");
		Objects.requireNonNull(colorForeground, "colorForeground");
		_colorBack = colorBackground;
		_colorFore = colorForeground;
	}
	
	public Color getBackgroundColor() {
		return _colorBack;
	}
	
	public Color getForegroundColor() {
		return _colorFore;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_colorBack, _colorFore);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ColorPair other) {
			return ColorMethods.areColorsEqual(_colorBack, other._colorBack, 0, true)
					&& ColorMethods.areColorsEqual(_colorFore, other._colorFore, 0, true);
		}
		return false;
	}

	@Override
	public String toString() {
		return "background=color[" + AWTFormatters.formatColor(_colorBack)
			+ "], foreground=color[" + AWTFormatters.formatColor(_colorFore) + "]";
	}
}
