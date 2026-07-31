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
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Objects;

import srojak.cdo.ColorPair;
import srojak.cdo.swing.SelectableBorderProvider;
import srojak.cdo.swing.functional.CommonBorderProvider;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ResponsiveTwoColorRect 
		extends ResponsiveBorderRect {
	private final Dimension _szFull;
	private final Dimension _szInner;
	private int _widthOuter;

	public static final int DEFAULT_OUTER_WIDTH = 8;
	
	private static SelectableBorderProvider getBorderProvider(ColorPair colors) {
		Objects.requireNonNull(colors, "colors");
		return new CommonBorderProvider(colors.getBackgroundColor());
	}
	
	/**
	 * 
	 */
	public ResponsiveTwoColorRect(ColorPair colors) {
		super(getBorderProvider(colors));
		_szFull = new Dimension();
		_szInner = new Dimension();
		_widthOuter = DEFAULT_OUTER_WIDTH;
		setBackground(colors.getBackgroundColor());
		setForeground(colors.getForegroundColor());
		setData(colors);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public ResponsiveTwoColorRect(boolean isDoubleBuffered, ColorPair colors) {
		super(isDoubleBuffered, getBorderProvider(colors));
		_szFull = new Dimension();
		_szInner = new Dimension();
		_widthOuter = DEFAULT_OUTER_WIDTH;
		setBackground(colors.getBackgroundColor());
		setForeground(colors.getForegroundColor());
		setData(colors);
	}
	
	public void calcSizes() {
		_szFull.setSize(getSize());
		_szInner.setSize(_szFull.width - (_widthOuter << 1),
				_szFull.height - (_widthOuter << 1));
	}
	
	public int getOuterWidth() {
		return _widthOuter;
	}
	
	public void setOuterWidth(int width) {
		if (width < 1) {
			throw new IllegalArgumentException("width must be positive");
		}
		_widthOuter = width;
		calcSizes();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		calcSizes();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(getBackground());
		g.fillRect(0, 0, _szFull.width, _szFull.height);
		g.setColor(getForeground());
		g.fillRect(_widthOuter, _widthOuter, _szInner.width, _szInner.height);
	}
}
