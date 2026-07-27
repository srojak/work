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

import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import srojak.cdo.Scaler;
import srojak.cdo.swing.ScalableComponent;
import srojak.core.NameToken;
import srojak.core.field.SetOnce;
import srojak.core.tools.BitMethods;
import srojak.debug.DebugNexus;
import srojak.debug.DebugSwitch;
import srojak.debug.DebugSwitchTool;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScalableDrawingPanePanel
		extends NameTokenTagPanel {
	protected final SetOnce<Scaler> _scaler;

	public static final NameToken ClassToken;
	@SuppressWarnings("unused")
	private static final DebugSwitch _swDebugClass;
	
	static {
		DebugNexus debug = new DebugNexus();
		Class<?> classThis = ScalableDrawingPanePanel.class;
		ClassToken = NameToken.classNameFactory(classThis);
		_swDebugClass = debug.getSwitch(DebugSwitchTool.makeClassKey(classThis));
	}
	
	/**
	 * @param tokenName
	 */
	public ScalableDrawingPanePanel(NameToken tokenName) {
		super(tokenName);
		_scaler = new SetOnce<Scaler>(NameToken.factory(tokenName, "Scaler"), SetOnce.DEFAULT);
		addHierarchyListener(new ScalableParentListener());
	}

	/**
	 * @param tokenName
	 * @param layout
	 */
	public ScalableDrawingPanePanel(NameToken tokenName, LayoutManager layout) {
		super(tokenName, layout);
		_scaler = new SetOnce<Scaler>(NameToken.factory(tokenName, "Scaler"), SetOnce.DEFAULT);
		addHierarchyListener(new ScalableParentListener());
	}

	/**
	 * @param tokenName
	 * @param isDoubleBuffered
	 */
	public ScalableDrawingPanePanel(NameToken tokenName, boolean isDoubleBuffered) {
		super(tokenName, isDoubleBuffered);
		_scaler = new SetOnce<Scaler>(NameToken.factory(tokenName, "Scaler"), SetOnce.DEFAULT);
	}

	/**
	 * @param tokenName
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public ScalableDrawingPanePanel(NameToken tokenName, LayoutManager layout, boolean isDoubleBuffered) {
		super(tokenName, layout, isDoubleBuffered);
		_scaler = new SetOnce<Scaler>(NameToken.factory(tokenName, "Scaler"), SetOnce.DEFAULT);
		addHierarchyListener(new ScalableParentListener());
	}
	
	protected void attachToScalableComponent(ScalableComponent component) {
		_scaler.set(component.getScaler());
	}

	@SuppressWarnings("unused")
	private void setScaler(Scaler scaler) {
		_scaler.set(scaler);
	}
	
	private class ScalableParentListener
			implements HierarchyListener {

		@Override
		public void hierarchyChanged(HierarchyEvent e) {
			if (BitMethods.test(e.getChangeFlags(), HierarchyEvent.PARENT_CHANGED)) {
				Container parent = getParent();
				while (parent != null) {
					if (parent instanceof ScalableComponent scalable) {
						attachToScalableComponent(scalable);
						break;
					}
					parent = parent.getParent();
				}
			}
		}
		
	}
}
