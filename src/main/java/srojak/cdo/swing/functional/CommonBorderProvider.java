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

import java.awt.Color;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import srojak.cdo.ColorHSB;
import srojak.cdo.swing.SelectableBorderProvider;

/**
 * @author Stephen
 *
 */
public class CommonBorderProvider
		implements SelectableBorderProvider {
	private Border _borderNormal;
	private Border _borderSelected;
	
	public CommonBorderProvider(Color colorBase) {
		Objects.requireNonNull(colorBase, "colorBase");
		ColorHSB hsb = ColorHSB.fromColor(colorBase);
		if (hsb.getBrightness() < 0.33f) {
			_borderNormal = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2);
		} else {
			_borderNormal = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
		}
		_borderSelected = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
	}

	@Override
	public Border getNormalBorder() {
		return _borderNormal;
	}

	@Override
	public Border getSelectedBorder() {
		return _borderSelected;
	}

}
