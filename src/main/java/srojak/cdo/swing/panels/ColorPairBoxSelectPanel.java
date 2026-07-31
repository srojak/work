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

import srojak.cdo.ColorPair;
import srojak.cdo.swing.components.ResponsiveTwoColorRect;
import srojak.cdo.swing.models.DefaultColorPairBoxSelectModel;
import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ColorPairBoxSelectPanel
		extends ColorBoxSelectPanelBase<ColorPair, ResponsiveTwoColorRect> {
	private int _widthBoxOuter;

	/**
	 * @param tokenName
	 * @param classData
	 */
	public ColorPairBoxSelectPanel(NameToken tokenName) {
		super(tokenName, ColorPair.class);
		_widthBoxOuter = ResponsiveTwoColorRect.DEFAULT_OUTER_WIDTH;
		setModel(new DefaultColorPairBoxSelectModel());
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 * @param classData
	 */
	public ColorPairBoxSelectPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered, ColorPair.class);
		_widthBoxOuter = ResponsiveTwoColorRect.DEFAULT_OUTER_WIDTH;
		setModel(new DefaultColorPairBoxSelectModel());
	}

	@Override
	protected boolean isRectFor(ResponsiveTwoColorRect rect, ColorPair color) {
		if (rect.hasData()) {
			return color.equals(rect.getData());
		}
		return false;
	}
	
	public int getBoxOuterZoneWidth() {
		return _widthBoxOuter;
	}
	
	public void setBoxOuterZoneWidth(int width) {
		if (width < 1) {
			throw new IllegalArgumentException("width must be positive");
		}
		_widthBoxOuter = width;
		forEachRect(r -> r.setOuterWidth(width));
	}

	@Override
	protected ResponsiveTwoColorRect createRectFor(boolean isDoubleBuffered, ColorPair color) {
		ResponsiveTwoColorRect rect = new ResponsiveTwoColorRect(isDoubleBuffered, color);
		rect.setOuterWidth(_widthBoxOuter);
		return rect;
	}

}
