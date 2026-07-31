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
package srojak.cdo.swing.components;

import java.util.Objects;

import srojak.cdo.swing.SelectableBorderProvider;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ResponsiveBorderRect 
		extends ResponsiveRect {
	private SelectableBorderProvider _provBorder;

	/**
	 * 
	 */
	public ResponsiveBorderRect(SelectableBorderProvider providerBorder) {
		super();
		Objects.requireNonNull(providerBorder, "providerBorder");
		_provBorder = providerBorder;
		setBorder(_provBorder.getNormalBorder());
	}

	/**
	 * @param isDoubleBuffered
	 */
	public ResponsiveBorderRect(boolean isDoubleBuffered, SelectableBorderProvider providerBorder) {
		super(isDoubleBuffered);
		Objects.requireNonNull(providerBorder, "providerBorder");
		_provBorder = providerBorder;
		setBorder(_provBorder.getNormalBorder());
	}
	
	@Override
	public void setSelected(boolean bState) {
		if (bState) {
			setBorder(_provBorder.getSelectedBorder());
		} else {
			setBorder(_provBorder.getNormalBorder());
		}
	}
}
