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

import java.util.Objects;

import srojak.cdo.DoubleDimension;

/**
 * @author Stephen
 *
 */
public class PlaneSquareRenderingInfo {
	private final double _dScale;
	private final DoubleDimension _szSquare;
	
	public PlaneSquareRenderingInfo(double dScale, DoubleDimension szSquare) {
		Objects.requireNonNull(szSquare, "szSquare");
		_dScale = dScale;
		_szSquare = szSquare;
	}
	
	public double getScale() {
		return _dScale;
	}
	
	public DoubleDimension getScaledSquareSize() {
		return _szSquare;
	}
}
