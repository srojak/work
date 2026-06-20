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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ResponsiveRect 
		extends JPanel {

	/**
	 * 
	 */
	public ResponsiveRect() {
		this(true);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public ResponsiveRect(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		setOpaque(true);
	}
	
	public void setSelected(boolean bState) {
		if (bState) {
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		} else {
			setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		}
	}
}
