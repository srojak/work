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

import java.util.Objects;

import srojak.cdo.ScaleChangeEventOriginator;
import srojak.cdo.Scaler;
import srojak.cdo.events.ScaleChangeEvent;
import srojak.cdo.events.ScaleChangeListener;
import srojak.cdo.swing.base.GuiLifecycleControllerBase;
import srojak.core.containers.SingletonContainer;
import srojak.core.events.LifeCycleEvent;
import srojak.core.events.LifeCycleListener;
import srojak.core.logic.BooleanLatch;
import srojak.core.observe.TraceLevel;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.psq.PlaneSquare;
import srojak.psq.PlaneSquareGrid;
import srojak.psq.swing.PSqGridLinesControl;
import srojak.psq.swing.PlaneSquareDisplay;
import srojak.psq.swing.PlaneSquareDrawingControl;
import srojak.psq.swing.PlaneSquareRenderingInfo;
import srojak.psq.swing.PlaneSquareScalerTool;

/**
 * @author Stephen
 *
 */
public class PlaneSquareActionController<S extends PlaneSquare, G extends PlaneSquareGrid<S>,
			P extends PlaneSquareDisplay<S>>
		extends GuiLifecycleControllerBase
		implements ScaleChangeEventOriginator {
	protected final G _grid;
	protected final SingletonContainer<P> _containerPanel;
	protected final BooleanLatch _latchSendScaleChange;
	
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PlaneSquareActionController.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	/**
	 * 
	 */
	public PlaneSquareActionController(G grid) {
		super();
		Objects.requireNonNull(grid, "grid");
		_swDebugClass.writeTraceEnter(TraceLevel.HIGH, () -> "grid size = " + grid.getFieldSize());
		_grid = grid;
		_containerPanel = new SingletonContainer<P>();
		_latchSendScaleChange = new BooleanLatch();
	}
	
	protected void newDisplayPanel(P panel) {
		// base class method does nothing
	}
	
	public final void attachToDisplayPanel(P panel) {
		Objects.requireNonNull(panel, "panel");
		_containerPanel.set(panel);
		Scaler scaler = panel.getScaler();
		scaler.addScaleChangeListener(new ControllerScaleChangeListener());
		newDisplayPanel(panel);
		LifeCycleEvent event = new LifeCycleEvent(this, LifeCycleEvent.ID_DISPLAYABLE);
		_listeners.forEach(LifeCycleListener.class, ls -> ls.receive(event));
	}
	
	@Override
	public boolean hasDisplayable() {
		return !_containerPanel.isEmpty();
	}

	public void repaintDisplay() {
		if (!_containerPanel.isEmpty()) {
			_containerPanel.get().repaint();
		}
	}
	
	public PSqGridLinesControl getGridLinesControl() {
		PlaneSquareDrawingControl ctlDrawing = _containerPanel.get().getDrawingControl();
		return ctlDrawing.getGridLinesControl();
	}
	
	public boolean isSendingScaleChange() {
		return _latchSendScaleChange.getState();
	}
	
	public void setSendingScaleChange(boolean bState) {
		_latchSendScaleChange.setState(bState);
	}
	
	protected void relayScaleChange(double dScale) {
		if (_latchSendScaleChange.getState()) {
			ScaleChangeEvent event = new ScaleChangeEvent(this, dScale);
			_listeners.forEach(ScaleChangeListener.class, ls -> ls.scaleChanged(event));
		}
	}
	
	protected PlaneSquareScalerTool getScalerTool() {
		PlaneSquareDrawingControl ctlDrawing = _containerPanel.get().getDrawingControl();
		return ctlDrawing.getScalerTool();
	}
	
	public PlaneSquareRenderingInfo getCurrentRenderingInfo() {
		PlaneSquareDrawingControl ctlDrawing = _containerPanel.get().getDrawingControl();
		return ctlDrawing.getCurrentRendering();
	}

	@Override
	protected void closeSelf() {

	}

	@Override
	public void addScaleChangeListener(ScaleChangeListener listener) {
		_listeners.add(ScaleChangeListener.class, listener);
	}

	@Override
	public void removeScaleChangeListener(ScaleChangeListener listener) {
		_listeners.remove(ScaleChangeListener.class, listener);
	}
	
	private class ControllerScaleChangeListener
			implements ScaleChangeListener {
		
		@Override
		public void scaleChanged(ScaleChangeEvent event) {
			relayScaleChange(event.getScale());
		}
	}

}
