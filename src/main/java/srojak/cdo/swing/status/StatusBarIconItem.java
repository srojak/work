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
package srojak.cdo.swing.status;

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * @author Stephen
 *
 */
public class StatusBarIconItem 
		extends StatusBarItemBase {
	private final JLabel _label;
	
	/**
	 * 
	 */
	public StatusBarIconItem() {
		super();
		_label = new JLabel();
	}

	/**
	 * @param image
	 * @param horizontalAlignment
	 */
	public StatusBarIconItem(Icon image, int horizontalAlignment) {
		_label = new JLabel(image, horizontalAlignment);
		_label.setHorizontalAlignment(LEFT);
	}

	/**
	 * @param image
	 */
	public StatusBarIconItem(Icon image) {
		_label = new JLabel(image, SwingConstants.LEFT);
		_label.setHorizontalAlignment(LEFT);
	}

	@Override
	JComponent getComponent() {
		return _label;
	}
	
	public void setPreferredWidth(int nWidth) {
		_label.setPreferredSize(new Dimension(nWidth, STD_HEIGHT));
	}

	@Override
	public boolean hasImage() {
		return true;
	}

	@Override
	public boolean hasText() {
		return false;
	}

	@Override
	public int getHorizontalAlignment() {
		return _label.getHorizontalAlignment();
	}

	@Override
	public void setHorizontalAlignment(int alignment) {
		_label.setHorizontalAlignment(alignment);
		
	}

	@Override
	public Border getBorder() {
		return _label.getBorder();
	}

	@Override
	public void setBorder(Border border) {
		_label.setBorder(border);		
	}
}
