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
package srojak.psq.swing.panels;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Objects;

import srojak.cdo.AWTGeometry;
import srojak.cdo.swing.panels.ScalableDrawingPanel;
import srojak.core.NameToken;
import srojak.core.containers.SingletonContainer;
import srojak.core.events.OperationCodes;
import srojak.core.events.OperationStateChangeEvent;
import srojak.core.events.OperationStateChangeListener;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.psq.PlaneSquare;
import srojak.psq.PlaneSquareGrid;
import srojak.psq.PlaneSquarePath;
import srojak.psq.swing.PlaneSquareDisplay;
import srojak.psq.swing.PlaneSquareDisplaySizer;
import srojak.psq.swing.PlaneSquareDrawingControl;
import srojak.psq.swing.PlaneSquareDrawingTool;
import srojak.psq.swing.PlaneSquareGraphics;
import srojak.psq.swing.PlaneSquareScalerTool;
import srojak.spatial.InvalidLocationException;
import srojak.spatial.NoValidMoveException;
import srojak.spatial.S2Coords;
import srojak.spatial.S2FieldSize;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class PlaneSquarePanel<S extends PlaneSquare> 
		extends ScalableDrawingPanel 
		implements PlaneSquareDisplay<S> {
	protected final PlaneSquareGrid<S> _grid;
	protected final PlaneSquareDrawingControl _ctlDrawing;
	private final PlaneSquareDrawingTool _toolDrawing;
	private final SingletonContainer<S> _containerMarkedSquare;
	
	public static final NameToken ClassToken;
	private static final DebugSwitch _swDebugPanel;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PlaneSquarePanel.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugPanel = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	private static Dimension getDrawingSize(PlaneSquareGrid<?> grid) {
		Objects.requireNonNull(grid, "grid");
		return PlaneSquareDisplaySizer.getUnscaledFieldSize(grid.getFieldSize());
	}
	
	/**
	 * 
	 * @param tokenName
	 * @param grid
	 */
	public PlaneSquarePanel(NameToken tokenName, PlaneSquareGrid<S> grid) {
		super(tokenName, getDrawingSize(grid));
		_grid = grid;
		_ctlDrawing = new PlaneSquareDrawingControl(this, grid);
		_swDebugPanel.writeTraceEnter(TraceLevel.HIGH);
		_toolDrawing = new PlaneSquareDrawingTool(_scaler, _scroll, _ctlDrawing);
		_containerMarkedSquare = new SingletonContainer<S>();
		postConstruct();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 * @param grid
	 */
	public PlaneSquarePanel(NameToken tokenName, boolean isDoubleBuffered, PlaneSquareGrid<S> grid) {
		super(tokenName, isDoubleBuffered, getDrawingSize(grid));
		_grid = grid;
		_ctlDrawing = new PlaneSquareDrawingControl(this, grid);
		_swDebugPanel.writeTraceEnter(TraceLevel.HIGH);
		_toolDrawing = new PlaneSquareDrawingTool(_scaler, _scroll, _ctlDrawing);
		_containerMarkedSquare = new SingletonContainer<S>();
		postConstruct();
	}
	
	private void postConstruct() {
		_scroll.addOperationStateChangeListener(new ScrollerOperationStateChangeListener());
	}
	
	@Override
	public PlaneSquareDrawingControl getDrawingControl() {
		return _ctlDrawing;
	}
	
	protected PlaneSquareGrid<S> getGrid() {
		return _grid;
	}

	protected S2FieldSize getFieldSize() {
		return _ctlDrawing.getFieldSize();
	}
	
	public void setMarkedSquare(S square) {
		Objects.requireNonNull(square, "square");
		PlaneSquareScalerTool toolScaler = _ctlDrawing.getScalerTool();
		S squarePrior = null;
		if (!_containerMarkedSquare.isEmpty()) {
			squarePrior = _containerMarkedSquare.get();
		}
		_containerMarkedSquare.set(square);
		Rectangle rect;
		if (squarePrior != null) {
			rect = AWTGeometry.reduce(toolScaler.getSquareRectangle(squarePrior.getCoords()));
			rect.grow(1, 1);
			this.repaint(rect);
		}
		rect = AWTGeometry.reduce(toolScaler.getSquareRectangle(square.getCoords()));
		rect.grow(1, 1);
		this.repaint(rect);
	}
	
	public void clearMarkedSquare() {
		PlaneSquareScalerTool toolScaler = _ctlDrawing.getScalerTool();
		S squarePrior = null;
		if (!_containerMarkedSquare.isEmpty()) {
			squarePrior = _containerMarkedSquare.get();
		}
		_containerMarkedSquare.clear();
		Rectangle rect;
		if (squarePrior != null) {
			rect = AWTGeometry.reduce(toolScaler.getSquareRectangle(squarePrior.getCoords()));
			rect.grow(1, 1);
			this.repaint(rect);
		}
	}
	
	@Override
	public PlaneSquarePath getSelectionPath() {
		return _ctlDrawing.getSelectionPath();
	}
	
	@Override
	public void clearSelection() {
		_ctlDrawing.clearSelection();
	}
	
	@Override
	public void extendSelectionTo(S2Coords coords) 
			throws InvalidLocationException, NoValidMoveException {
		_ctlDrawing.extendSelectionTo(coords);
	}
	
	protected void drawGridLines(Graphics2D g, PlaneSquareScalerTool scaler) {
		_toolDrawing.drawGridLines(g, scaler);
	}	
	
	protected void drawSelectionPath(Graphics2D g, PlaneSquareScalerTool scaler) {
		_toolDrawing.drawSelectionPath(g, scaler);
	}
	
	protected void paintEarlyPanelContent(Graphics2D g, PlaneSquareScalerTool scaler) {
		
	}
	
	protected void paintPanelContent(Graphics2D g, PlaneSquareScalerTool scaler) {
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		PlaneSquareScalerTool toolScaler = _ctlDrawing.getScalerTool();
		
		paintEarlyPanelContent(g2, toolScaler);
				
		drawGridLines(g2, toolScaler);
		
		paintPanelContent(g2, toolScaler);
		
		drawSelectionPath(g2, toolScaler);
			
		if (!_containerMarkedSquare.isEmpty()) {
			Rectangle rect = AWTGeometry.reduce(
					toolScaler.getSquareRectangle(_containerMarkedSquare.get().getCoords()));
			_toolDrawing.drawSquareBorder(g2, rect, new BasicStroke(3.0f), 
					PlaneSquareGraphics.colorSquareBorder);
		}
	}
	
	private class ScrollerOperationStateChangeListener
			implements OperationStateChangeListener, OperationCodes {

		@Override
		public void operationStateChanged(OperationStateChangeEvent event) {
			if (event.getOpCode() == SCROLL && event.getState()) {
				repaint();
			}
		}
		
	}
}
