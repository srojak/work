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

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class PushDownArrowButton
		extends JButton {
	private static final Icon _icon;
	
	static {
		URL urlImage = PushDownArrowButton.class.getResource("/PushDownArrow16.png");
		_icon = new ImageIcon(urlImage);
	}

	/**
	 * 
	 */
	public PushDownArrowButton() {
		super(_icon);
	}
}
