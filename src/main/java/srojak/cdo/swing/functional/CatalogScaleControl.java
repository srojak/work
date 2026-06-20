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
package srojak.cdo.swing.functional;

import java.awt.Dimension;

import srojak.cdo.ScaleControl;
import srojak.cdo.swing.GuiDoubleStore;

/**
 * @author Stephen
 *
 */
public class CatalogScaleControl 
		extends ScaleControl {

	/**
	 * 
	 */
	public CatalogScaleControl(double dOriginalScale, Dimension dmSurface) {
		super(dOriginalScale, dmSurface);
		
		GuiDoubleStore storeDbls = new GuiDoubleStore();
		double dScaleLimitHigh = storeDbls.getValue(GuiDoubleStore.SCALE_HIGH_LIMIT);
		double dScaleLimitLow = storeDbls.getValue(GuiDoubleStore.SCALE_LOW_LIMIT);
		setLimits(dScaleLimitLow, dScaleLimitHigh, false);
	}
}
