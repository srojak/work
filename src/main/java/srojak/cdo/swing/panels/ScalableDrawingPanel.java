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
import java.awt.Dimension;

import srojak.cdo.Scaler;
import srojak.cdo.events.ScaleChangeEvent;
import srojak.cdo.events.ScaleChangeListener;
import srojak.cdo.swing.functional.CatalogScaleControl;
import srojak.cdo.swing.functional.ScrollableParentControl;
import srojak.core.NameToken;
import srojak.numerics.DoubleMethods;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
@SuppressWarnings("serial")
public class ScalableDrawingPanel
		extends NameTokenTagPanel {
	private final CatalogScaleControl _scaler;
	private final ScrollableParentControl _scroll;

	/**
	 * 
	 */
	public ScalableDrawingPanel(NameToken tokenName, Dimension dmDrawing) {
		super(tokenName);
		_scaler = new CatalogScaleControl(1.0, dmDrawing);
		setPreferredSize(_scaler.getScaledFullSize());
		_scaler.addScaleChangeListener(new PanelScaleChangeListener());
		_scroll = new ScrollableParentControl(this);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public ScalableDrawingPanel(NameToken tokenName, boolean isDoubleBuffered, Dimension dmDrawing) {
		super(tokenName, isDoubleBuffered);
		_scaler = new CatalogScaleControl(1.0, dmDrawing);
		setPreferredSize(_scaler.getScaledFullSize());
		_scaler.addScaleChangeListener(new PanelScaleChangeListener());
		_scroll = new ScrollableParentControl(this);
	}
	
	public ScrollableParentControl getScrollControl() {
		return _scroll;
	}
	
	public Scaler getScaler() {
		return _scaler;
	}

	public void setScale(double dScale) {
		if (dScale <= 0.0d) {
			throw new IllegalArgumentException("scale is nonpositive");
		}
		_scaler.setScale(dScale);
		Container container = getParent();
		if (container != null) {
			container.revalidate();
			container.repaint();
		}
	}
	
	public void multiplyScaleBy(double dFactor) {
		if (DoubleMethods.compare(OrderedComparison.LE, dFactor, 0.0d)) {
			throw new IllegalArgumentException("dFactor is nonpositive");
		}
		
		double dScale = _scaler.getScale();
		_scaler.setScale(dScale * dFactor);
		Container container = getParent();
		if (container != null) {
			container.revalidate();
			container.repaint();
		}
	}
	
	private class PanelScaleChangeListener
			implements ScaleChangeListener {

		@Override
		public void scaleChanged(ScaleChangeEvent event) {
			setPreferredSize(_scaler.getScaledFullSize());
			revalidate();
		}
		
	}
}
