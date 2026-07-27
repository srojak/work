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

import srojak.numerics.DoubleMethods;
import srojak.numerics.OrderedComparison;

/**
 * @author Stephen
 *
 */
public class PSqGridLinesControl {
	private boolean _bEnabled;
	private double _dMinScale;
	
	public PSqGridLinesControl() {
		_bEnabled = false;
		_dMinScale = 1.0d;
	}
	
	public boolean isEnabled() {
		return _bEnabled;
	}
	
	public void setEnabled(boolean bState) {
		_bEnabled = bState;
	}
	
	public double getMinimumScale() {
		return _dMinScale;
	}
	
	public void setMinimumScale(double dScale) {
		if (DoubleMethods.compare(OrderedComparison.LE, dScale, 0.0d)) {
			throw new IllegalArgumentException("dScale must be positive");
		}
		_dMinScale = dScale;
	}
	
	public boolean canDrawAtScale(double dScale) {
		return _bEnabled && DoubleMethods.compare(OrderedComparison.GE, dScale, _dMinScale);
	}
}
