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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import srojak.cdo.AWTGeometry;
import srojak.cdo.swing.panels.ScalableDrawingPanePanel;
import srojak.core.NameToken;
import srojak.core.containers.SingletonContainer;
import srojak.core.field.SetOnce;
import srojak.core.specialized.ResponsibilityKey;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.psq.PlaneSquare;
import srojak.psq.swing.PlaneSquareDrawingControl;
import srojak.psq.swing.PlaneSquareGraphics;
import srojak.psq.swing.PlaneSquareScalerTool;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public abstract class PlaneSquareLayerBasePanel
		extends ScalableDrawingPanePanel {
	protected final SetOnce<PlaneSquareDrawingControl> _ctlDrawing;
	private final Set<ResponsibilityKey> _setRespos;
	private final SingletonContainer<PlaneSquare> _containerMarkedSquare;
	
	@SuppressWarnings("unused")
	private static final DebugSwitch _swDebugPanel;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PlaneSquareLayerBasePanel.class;
		_swDebugPanel = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	/**
	 * @param tokenName
	 */
	public PlaneSquareLayerBasePanel(NameToken tokenName) {
		super(tokenName);
		_ctlDrawing 
			= new SetOnce<PlaneSquareDrawingControl>(NameToken.factory(tokenName, "DrawingControl"),
					SetOnce.DEFAULT);
		_setRespos = new HashSet<ResponsibilityKey>();
		_containerMarkedSquare = new SingletonContainer<PlaneSquare>();
	}

	/**
	 * @param tokenName
	 * @param layout
	 */
	public PlaneSquareLayerBasePanel(NameToken tokenName, LayoutManager layout) {
		super(tokenName, layout);
		_ctlDrawing 
		= new SetOnce<PlaneSquareDrawingControl>(NameToken.factory(tokenName, "DrawingControl"),
				SetOnce.DEFAULT);
		_setRespos = new HashSet<ResponsibilityKey>();
		_containerMarkedSquare = new SingletonContainer<PlaneSquare>();
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public PlaneSquareLayerBasePanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_ctlDrawing 
			= new SetOnce<PlaneSquareDrawingControl>(NameToken.factory(tokenName, "DrawingControl"),
					SetOnce.DEFAULT);
		_setRespos = new HashSet<ResponsibilityKey>();
		_containerMarkedSquare = new SingletonContainer<PlaneSquare>();
	}

	/**
	 * @param tokenName
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public PlaneSquareLayerBasePanel(NameToken tokenName, 
			LayoutManager layout, boolean isDoubleBuffered) {
		super(tokenName, layout, isDoubleBuffered);
		_ctlDrawing 
			= new SetOnce<PlaneSquareDrawingControl>(NameToken.factory(tokenName, "DrawingControl"),
					SetOnce.DEFAULT);
		_setRespos = new HashSet<ResponsibilityKey>();
		_containerMarkedSquare = new SingletonContainer<PlaneSquare>();
	}
	
	public boolean hasResponsibility(ResponsibilityKey rkey) {
		return _setRespos.contains(rkey);
	}
	
	public List<ResponsibilityKey> getAllResponsibilities() {
		return _setRespos.stream().toList();
	}
	
	protected void addResponsibility(ResponsibilityKey rkey) {
		Objects.requireNonNull(rkey, "rkey");
		_setRespos.add(rkey);
	}
	
	public void setDrawingControl(PlaneSquareDrawingControl controlDrawing) {
		_ctlDrawing.set(controlDrawing);
	}

	public void setMarkedSquare(PlaneSquare square) {
		Objects.requireNonNull(square, "square");
		PlaneSquareScalerTool toolScaler = new PlaneSquareScalerTool(_scaler.get(), _ctlDrawing.get());
		PlaneSquare squarePrior = null;
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
		PlaneSquareScalerTool toolScaler = new PlaneSquareScalerTool(_scaler.get(), _ctlDrawing.get());
		PlaneSquare squarePrior = null;
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
	
	protected void drawSquareBorder(Graphics2D g, Rectangle rectSquare) {
		g.setColor(PlaneSquareGraphics.colorSquareBorder);
		Stroke stokeSave = g.getStroke();
		g.setStroke(new BasicStroke(3.0f));
		g.drawRect(rectSquare.x, rectSquare.y, rectSquare.width, rectSquare.height);
		g.setStroke(stokeSave);
	}
	
	protected abstract void paintPanelContent(Graphics2D g, PlaneSquareScalerTool toolScaler);

	@Override
	protected final void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		PlaneSquareScalerTool toolScaler = new PlaneSquareScalerTool(_scaler.get(), _ctlDrawing.get());
		
		paintPanelContent(g2, toolScaler);
					
		if (!_containerMarkedSquare.isEmpty()) {
			Rectangle rect = AWTGeometry.reduce(
					toolScaler.getSquareRectangle(_containerMarkedSquare.get().getCoords()));
			drawSquareBorder(g2, rect);
		}
	}
}
