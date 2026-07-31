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
package srojak.cdo.swing.panels;

import java.awt.Color;

import srojak.cdo.ColorMethods;
import srojak.cdo.swing.components.ResponsiveColorRect;
import srojak.cdo.swing.models.DefaultColorBoxSelectModel;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ColorBoxSelectPanel
		extends ColorBoxSelectPanelBase<Color, ResponsiveColorRect> {
	
	/**
	 * 
	 */
	public ColorBoxSelectPanel(NameToken tokenName) {
		super(tokenName, Color.class);
		setModel(new DefaultColorBoxSelectModel());
	}

	/**
	 * @param isDoubleBuffered
	 */
	public ColorBoxSelectPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered, Color.class);
		setModel(new DefaultColorBoxSelectModel());
	}

	@Override
	protected boolean isRectFor(ResponsiveColorRect rect, Color color) {
		if (rect.hasData()) {
			Color colorRect = (Color) rect.getData();
			return ColorMethods.areColorsEqual(color, colorRect, 0, false);
		}
		return false;
	}

	@Override
	protected ResponsiveColorRect createRectFor(boolean isDoubleBuffered, Color color) {
		ResponsiveColorRect rect = new ResponsiveColorRect(isDoubleBuffered, color);
		return rect;
	}
}
