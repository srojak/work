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

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.util.Objects;

import srojak.cdo.swing.components.ScalableDrawingLayeredPane;
import srojak.core.NameToken;
import srojak.core.collections.HashMapOfLists;
import srojak.core.observe.ObsLevel;
import srojak.core.specialized.ResponsibilityKey;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;
import srojak.psq.PlaneSquare;
import srojak.psq.PlaneSquareGrid;
import srojak.psq.swing.panels.PlaneSquareLayerBasePanel;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class PSqScalableDrawingLayeredPane<S extends PlaneSquare> 
		extends ScalableDrawingLayeredPane {
	private final PlaneSquareDrawingControl _ctlDrawing;
	private final HashMapOfLists<ResponsibilityKey, PlaneSquareLayerBasePanel> _mapRespos;
	
	@SuppressWarnings("unused")
	private static final DebugSwitch _swDebugClass;

	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = PSqScalableDrawingLayeredPane.class;
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}

	private static Dimension getDrawingSize(PlaneSquareGrid<?> grid) {
		Objects.requireNonNull(grid, "grid");
		return PlaneSquareDisplaySizer.getUnscaledFieldSize(grid.getFieldSize());
	}
	
	/**
	 * @param tokenName
	 * @param dmDrawing
	 */
	public PSqScalableDrawingLayeredPane(NameToken tokenName, PlaneSquareGrid<S> grid) {
		super(tokenName, getDrawingSize(grid));
		_ctlDrawing = new PlaneSquareDrawingControl(this, grid);
		_mapRespos = new HashMapOfLists<ResponsibilityKey, PlaneSquareLayerBasePanel>();
	}

	@Override
	public void addInLayer(Component component, int nLayer) {
		super.addInLayer(component, nLayer);
		if (component instanceof PlaneSquareLayerBasePanel panel) {
			NameToken tokenName = panel.getNameTag();
			panel.setDrawingControl(_ctlDrawing);
			List<ResponsibilityKey> listResps = panel.getAllResponsibilities();
			for (ResponsibilityKey rkey : listResps) {
				_mapRespos.add(rkey,  panel);
				_swDebugClass.write(ObsLevel.DETAIL, () -> "panel " + tokenName.getName()
						+ " has responsibility " + rkey.getName());
			}
		}
	}

}
