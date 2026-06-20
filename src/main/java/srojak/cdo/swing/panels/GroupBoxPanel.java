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

import java.awt.LayoutManager;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class GroupBoxPanel
		extends NameTokenTagPanel {
	private Border _border;
	private TitledBorder _borderTitled;
	private String _strTitle;
	
	/**
	 * 
	 */
	public GroupBoxPanel(NameToken tokenName) {
		super(tokenName);
		_border = LineBorder.createBlackLineBorder();
		_strTitle = "group";
		_borderTitled = createTitledBorder(_border);
		_borderTitled.setTitle(_strTitle);
		setBorder(_borderTitled);
	}

	/**
	 * @param layout
	 */
	public GroupBoxPanel(NameToken tokenName, LayoutManager layout) {
		super(tokenName, layout);
		_border = LineBorder.createBlackLineBorder();
		_strTitle = "group";
		_borderTitled = createTitledBorder(_border);
		_borderTitled.setTitle(_strTitle);
		setBorder(_borderTitled);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public GroupBoxPanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_border = LineBorder.createBlackLineBorder();
		_strTitle = "group";
		_borderTitled = createTitledBorder(_border);
		_borderTitled.setTitle(_strTitle);
		setBorder(_borderTitled);
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public GroupBoxPanel(NameToken tokenName, LayoutManager layout, boolean isDoubleBuffered) {
		super(tokenName, layout, isDoubleBuffered);
		_border = LineBorder.createBlackLineBorder();
		_strTitle = "group";
		_borderTitled = createTitledBorder(_border);
		_borderTitled.setTitle(_strTitle);
		setBorder(_borderTitled);
	}

	private static TitledBorder createTitledBorder(Border borderBase) {
		TitledBorder tb = new TitledBorder(borderBase);
		tb.setTitlePosition(TitledBorder.TOP);
		tb.setTitleJustification(TitledBorder.LEFT);
		return tb;
	}
	
	public void setGroupBoxTitle(String strText) {
		_strTitle = strText;
		_borderTitled.setTitle(_strTitle);
	}
	
	public void setBaseBorder(Border border) {
		_border = border;
		_borderTitled = createTitledBorder(_border);
		_borderTitled.setTitle(_strTitle);
		setBorder(_borderTitled);
	}
}
