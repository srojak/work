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

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import srojak.cdo.AWTGeometry;
import srojak.cdo.DoubleDimension;
import srojak.cdo.Scaler;
import srojak.cdo.ScalingDrawingToolBase;
import srojak.cdo.swing.functional.ScrollableParentControl;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.psq.PlaneSquarePath;
import srojak.spatial.S2Coords;
import srojak.spatial.S2Offset;

/**
 * @author Stephen
 *
 */
public class PlaneSquareDrawingTool
		extends ScalingDrawingToolBase {
	private final PlaneSquareDrawingControl _ctlDrawing;
	private final ScrollableParentControl _scroll;
	
	@SuppressWarnings("unused")
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PlaneSquareDrawingTool.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	public PlaneSquareDrawingTool(Scaler scaler, ScrollableParentControl scroll, 
			PlaneSquareDrawingControl ctlDrawing) {
		super(_swDebugClass, scaler);
		Objects.requireNonNull(scroll, "scroll");
		Objects.requireNonNull(ctlDrawing, "ctlDrawing");
		_scroll = scroll;
		_ctlDrawing = ctlDrawing;
	}
	
	private void writeCoordInUpperRight(Graphics2D g, PlaneSquareScalerTool scaler,
			int x, Rectangle rectSquare, FontMetrics fm) {
		String str = String.valueOf(x);
		int xback = fm.stringWidth(str) + 5;
		g.drawString(str, rectSquare.x + rectSquare.width - xback,
				rectSquare.y + fm.getHeight());
	}
	
	private void writeCoordInLowerLeft(Graphics2D g, PlaneSquareScalerTool scaler,
			int x, Rectangle rectSquare, FontMetrics fm) {
		String str = String.valueOf(x);
		g.drawString(str,  rectSquare.x + 5, rectSquare.y + rectSquare.height - 5);
	}
	
	private void labelGridLines(Graphics2D g, PlaneSquareScalerTool scaler) {
		if (_scroll.isScrolling()) {
			return;
		}
		DoubleDimension ddScaledSquare = scaler.getScaledSquareSize();
		Font fontLoc = PlaneSquareGraphics.fontGridLocation;
		if (ddScaledSquare.getWidth() >= 4.0 * fontLoc.getSize2D()) {
			Rectangle rectViewport = g.getClipBounds();
			FontMetrics fm = g.getFontMetrics(fontLoc);
			S2Coords coordsOrigin = scaler.getCoordsFromPoint(rectViewport.getLocation());
			Point ptOrigin = AWTGeometry.reduce(scaler.getPointFromSquareOrigin(coordsOrigin));
			// move coordsOrigin to make sure it is in the viewport
			int dx = 0;
			int dy = 0;
			if (ptOrigin.x < rectViewport.x) {
				dx = 1;
			}
			if (ptOrigin.y < rectViewport.y) {
				dy = 1;
			}
			if (dx > 0 || dy > 0) {
				coordsOrigin = coordsOrigin.getNewLocationFrom(new S2Offset(dx, dy));
			}
			g.setFont(fontLoc);
			int i = coordsOrigin.getX();
			int j = coordsOrigin.getY();
			Rectangle rectSquare = AWTGeometry.reduce(scaler.getSquareRectangle(i, j));
			writeCoordInUpperRight(g, scaler, i, rectSquare, fm);
			writeCoordInLowerLeft(g, scaler, j, rectSquare, fm);
			while (true) {
				i++;
				rectSquare = AWTGeometry.reduce(scaler.getSquareRectangle(i, j));
				if (!rectViewport.contains(rectSquare.getLocation())) {
					break;
				}
				writeCoordInUpperRight(g, scaler, i, rectSquare, fm);
			}
			i = coordsOrigin.getX();
			while (true) {
				j++;
				rectSquare = AWTGeometry.reduce(scaler.getSquareRectangle(i, j));
				if (!rectViewport.contains(rectSquare.getLocation())) {
					break;
				}
				writeCoordInLowerLeft(g, scaler, j, rectSquare, fm);
			}
		}
	}
	
	public void drawGridLines(Graphics2D g, PlaneSquareScalerTool scaler) {
		if (!_ctlDrawing.getGridLinesControl().canDrawAtScale(_scaler.getScale())) {
			return;
		}
		DoubleDimension ddScaledSquare = scaler.getScaledSquareSize();
		DoubleDimension ddPanel = scaler.getScaledPanelSize();
		Dimension szPanel = ddPanel.reduce();
		g.setColor(PlaneSquareGraphics.colorGridLines);
		for (double x = ddScaledSquare.getWidth(); x < ddPanel.getWidth();
				x += ddScaledSquare.getWidth()) {
			int i = (int) Math.round(x);
			g.drawLine(i, 0,  i, szPanel.height);
		}
		for (double y = ddScaledSquare.getHeight(); y < ddPanel.getHeight(); 
				y += ddScaledSquare.getHeight()) {
			int j = (int) Math.round(y);
			g.drawLine(0, j, szPanel.width, j);
		}
		labelGridLines(g, scaler);
	}
	
	public void drawSelectionPath(Graphics2D g, PlaneSquareScalerTool scaler) {
		PlaneSquarePath pathSelect = _ctlDrawing.getSelectionPath();
		if (pathSelect.isEmpty()) {
			return;
		}
		List<S2Coords> listCoords = pathSelect.getAll();
		Iterator<S2Coords> iterator = listCoords.iterator();
		S2Coords coordStart = iterator.next();
		g.setColor(PlaneSquareGraphics.colorSelection);
		Point ptStart = AWTGeometry.reduce(scaler.getMidPointFromSquareOrigin(coordStart));
		Stroke strokeSave = g.getStroke();
		g.setStroke(new BasicStroke(5.0f));
		g.drawOval(ptStart.x - 4, ptStart.y - 4, 8, 8);
		while (iterator.hasNext()) {
			S2Coords coords = iterator.next();
			Point ptEnd = AWTGeometry.reduce(scaler.getMidPointFromSquareOrigin(coords));
			g.drawLine(ptStart.x, ptStart.y, ptEnd.x, ptEnd.y);
			ptStart = ptEnd;
		}
		g.setStroke(strokeSave);
	}
}
