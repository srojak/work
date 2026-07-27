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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.Objects;

import srojak.cdo.DoubleDimension;
import srojak.cdo.Scaler;
import srojak.cdo.events.ScaleChangeEvent;
import srojak.cdo.events.ScaleChangeListener;
import srojak.core.containers.SingletonContainer;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.mantle.MutableDouble;
import srojak.spatial.S2Coords;
import srojak.spatial.S2FieldSize;
import srojak.spatial.S2Surface;

/**
 * @author Stephen
 *
 */
public class PlaneSquareScalerTool {
	private final SingletonContainer<Scaler> _scaler;
	private final S2Surface _surface;
	private final ScaleChangeListener _listenerScaler;
	private final DoubleDimension _ddScaledSquare;
	private final DoubleDimension _ddScaledPanel;
	private final MutableDouble _dScaledHalfLength;
	
	private static final DebugSwitch _swDebugClass;
	private static final DecimalFormat _formatScale;
	
	static {
		DebugNexus debug = new DebugNexus();
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(PlaneSquareScalerTool.class));
		_formatScale = new DecimalFormat("#,##0.0##");
	}
	
	public PlaneSquareScalerTool(Scaler scaler, S2Surface surface) {
		Objects.requireNonNull(scaler, "scaler");
		Objects.requireNonNull(surface, "surface");
		_scaler = new SingletonContainer<Scaler>();
		_scaler.set(scaler);
		_surface = surface;
		_ddScaledSquare = new DoubleDimension();
		_ddScaledPanel = new DoubleDimension();
		_dScaledHalfLength = new MutableDouble();
		computeScaledSizes(scaler.getScale());
		_listenerScaler = new ToolScalerChangeListener();
		scaler.addScaleChangeListener(_listenerScaler);
	}
	
	private void computeScaledSizes(double dScale) {
		double dLength = dScale * PlaneSquareDisplaySizer._nPixelsPerSide;
		_ddScaledSquare.setSize(dLength, dLength);
		_dScaledHalfLength.set(dLength * 0.5d);
		S2FieldSize szGrid = _surface.getFieldSize();
		_ddScaledPanel.setSize(_ddScaledSquare.getWidth() * szGrid.width,
				_ddScaledSquare.getHeight() * szGrid.height); 
	}
	
	public DoubleDimension getScaledSquareSize() {
		return new DoubleDimension(_ddScaledSquare);
	}
	
	public Dimension getUnscaledMapSize() {
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM);
		return PlaneSquareDisplaySizer.getUnscaledFieldSize(_surface.getFieldSize());
	}
	
	public DoubleDimension getScaledPanelSize() {
		_swDebugClass.writeTraceEnter(TraceLevel.MEDIUM);
		return new DoubleDimension(_ddScaledPanel);
	}
	
	public Point2D getPointFromSquareOrigin(int x, int y) {
		double dScale = _scaler.get().getScale();
		Point2D.Double pt = new Point2D.Double();
		pt.setLocation(dScale * x * PlaneSquareDisplaySizer._nPixelsPerSide, 
				dScale * y * PlaneSquareDisplaySizer._nPixelsPerSide);
		return pt;
	}
	
	public Point2D getPointFromSquareOrigin(S2Coords coords) {
		return getPointFromSquareOrigin(coords.getX(), coords.getY());
	}
	
	public Point2D getMidPointFromSquareOrigin(int x, int y) {
		double dScale = _scaler.get().getScale();
		Point ptUnscaled = PlaneSquareDisplaySizer.getMidPointFromSquareOrigin(x, y);
		Point pt = new Point();
		pt.setLocation(dScale * ptUnscaled.getX(), dScale * ptUnscaled.getY());
		return pt;
	}
	
	public Point2D getMidPointFromSquareOrigin(S2Coords coords) {
		return getMidPointFromSquareOrigin(coords.getX(), coords.getY());
	}

	public Rectangle2D getSquareRectangle(int x, int y) {
		Point2D ptOrigin = getPointFromSquareOrigin(x, y);
		return new Rectangle2D.Double(ptOrigin.getX(), ptOrigin.getY(),
				_ddScaledSquare.getWidth(), _ddScaledSquare.getHeight());
	}
	
	public Rectangle2D getSquareRectangle(S2Coords coords) {
		Point2D ptOrigin = getPointFromSquareOrigin(coords);
		return new Rectangle2D.Double(ptOrigin.getX(), ptOrigin.getY(),
				_ddScaledSquare.getWidth(), _ddScaledSquare.getHeight());
	}
	
	public Point getUnscaledPoint(int x, int y) {
		double dScale = _scaler.get().getScale();
		Point ptRaw = new Point();
		ptRaw.setLocation((double) x / dScale, (double) y / dScale);
		return ptRaw;		
	}
	
	public Point getUnscaledPoint(Point pt) {
		double dScale = _scaler.get().getScale();
		Point ptRaw = new Point();
		ptRaw.setLocation(pt.getX() / dScale, pt.getY() / dScale);
		return ptRaw;
	}
	
	public S2Coords getCoordsFromUnscaledPoint(Point ptRaw) {
		int x = ptRaw.x / PlaneSquareDisplaySizer._nPixelsPerSide;
		int y = ptRaw.y / PlaneSquareDisplaySizer._nPixelsPerSide;
		return new S2Coords(x, y);
	}
	
	public S2Coords getCoordsFrom(int x, int y) {
		Point ptRaw = getUnscaledPoint(x, y);
		return getCoordsFromUnscaledPoint(ptRaw);
	}
	
	public S2Coords getCoordsFromPoint(Point pt) {
		Point ptRaw = getUnscaledPoint(pt);
		return getCoordsFromUnscaledPoint(ptRaw);
	}
	
	public boolean isPointInBounds(Point ptSurface) {
		Rectangle2D.Double rectScaledPanel = new Rectangle2D.Double(0.0d, 0.0d,
				_ddScaledPanel.getWidth(), _ddScaledPanel.getHeight());
		return rectScaledPanel.contains(ptSurface);
	}
	
	public double computeScaleToFitMap(S2FieldSize szMap, Dimension dmViewport)
	{
		Dimension dmMap = getUnscaledMapSize();
		double dHoriz = dmViewport.getWidth() / dmMap.getWidth();
		double dVert = dmViewport.getHeight() / dmMap.getHeight();
		return Math.min(dHoriz, dVert);
	}
	
	private class ToolScalerChangeListener
			implements ScaleChangeListener {

		@Override
		public void scaleChanged(ScaleChangeEvent event) {
			double dScale = event.getScale();
			_swDebugClass.write(ObsLevel.INFO, () -> "scale changed to " + _formatScale.format(dScale));
			computeScaledSizes(dScale);
		}
	}
}
