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

import srojak.cdo.ComponentPaint;
import srojak.cdo.events.MouseEventOriginator;
import srojak.cdo.swing.ParentScrollable;
import srojak.cdo.swing.ScalableComponent;
import srojak.psq.PlaneSquare;
import srojak.psq.PlaneSquarePath;
import srojak.spatial.InvalidLocationException;
import srojak.spatial.NoValidMoveException;
import srojak.spatial.S2Coords;

/**
 * @author Stephen
 *
 */
public interface PlaneSquareDisplay<S extends PlaneSquare> 
		extends ParentScrollable, ScalableComponent, ComponentPaint, MouseEventOriginator {

	PlaneSquareDrawingControl getDrawingControl();
	void setMarkedSquare(S square);
	void clearMarkedSquare();
	PlaneSquarePath getSelectionPath();
	void clearSelection();
	void extendSelectionTo(S2Coords coords) 
			throws InvalidLocationException, NoValidMoveException;
}
