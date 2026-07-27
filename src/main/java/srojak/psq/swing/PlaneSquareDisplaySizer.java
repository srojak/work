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
package srojak.psq.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.Objects;

import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.spatial.S2Coords;
import srojak.spatial.S2FieldSize;

/**
 * @author Stephen
 *
 */
public class PlaneSquareDisplaySizer {
	
	/*
	 * number of pixels in a plane square at 1.0 scale
	 */
	static int _nPixelsPerSide;
	static int _nPixelsPerHalfSide;
	static final Dimension _dmSquareBase;
	
	private static final DebugSwitch swDebugClass;
	@SuppressWarnings("unused")
	private static final DecimalFormat _formatScale;
	
	static {
		DebugNexus debug = new DebugNexus();
		swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(PlaneSquareDisplaySizer.class));
		_formatScale = new DecimalFormat("#,##0.0##");
		
		_nPixelsPerSide = 8;
		_nPixelsPerHalfSide = _nPixelsPerSide >> 1;
		_dmSquareBase = new Dimension(_nPixelsPerSide, _nPixelsPerSide);
	}
	
	public static int getPixelsPerSide() {
		return _nPixelsPerSide;
	}
	
	public static void setPixelsPerSide(int nPixels) {
		if (nPixels < 4) {
			throw new IllegalArgumentException("nPixels is too small");
		}
		_nPixelsPerSide = nPixels;
		_nPixelsPerHalfSide = _nPixelsPerSide >> 1;
		_dmSquareBase.setSize(_nPixelsPerSide, _nPixelsPerSide);
	}
	
	public static Dimension getUnscaledSquareSize() {
		return _dmSquareBase;
	}
	
	public static Dimension getUnscaledFieldSize(S2FieldSize szField) {
		Objects.requireNonNull(szField, "szField");
		swDebugClass.writeTraceEnter(TraceLevel.MEDIUM, () -> "szField = " + szField);
		return new Dimension(szField.width * _dmSquareBase.width,
				szField.height * _dmSquareBase.height);
	}
	
	public static Point getPointFromSquareOrigin(int x, int y) {
		return new Point(x * _dmSquareBase.width, y * _dmSquareBase.height);
	}
	
	public static Point getMidPointFromSquareOrigin(int x, int y) {
		return new Point(x * _dmSquareBase.width + _nPixelsPerHalfSide,
					y * _dmSquareBase.height + _nPixelsPerHalfSide);
	}
	
	public static Rectangle getUnscaledSquareRectangle(int x, int y) {
		return new Rectangle(getPointFromSquareOrigin(x, y), _dmSquareBase);
	}
	
	public static Point getUnscaledPoint(double dScale, int x, int y) {
		Point ptRaw = new Point();
		ptRaw.setLocation((double) x / dScale, (double) y / dScale);
		return ptRaw;		
	}
	
	public static Point getUnscaledPoint(double dScale, Point pt) {
		Point ptRaw = new Point();
		ptRaw.setLocation(pt.getX() / dScale, pt.getY() / dScale);
		return ptRaw;
	}
	
	public static S2Coords getCoordsFromUnscaledPoint(Point ptRaw) {
		int x = ptRaw.x / _dmSquareBase.width;
		int y = ptRaw.y / _dmSquareBase.height;
		return new S2Coords(x, y);
	}
}
