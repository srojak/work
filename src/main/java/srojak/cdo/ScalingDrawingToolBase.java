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
package srojak.cdo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.Objects;

import srojak.debug.DebugSwitch;

/**
 * @author Stephen
 *
 */
public abstract class ScalingDrawingToolBase {
	protected final Scaler _scaler;
	protected final DebugSwitch _swDebug;
	
	protected ScalingDrawingToolBase(DebugSwitch swDebug, Scaler scaler) {
		Objects.requireNonNull(swDebug, "swDebug");
		Objects.requireNonNull(scaler, "scaler");
		_scaler = scaler;
		_swDebug = swDebug;
	}
	
	public void drawSquareBorder(Graphics2D g, Rectangle rectSquare, Stroke strokeBorder, Color color) {
		//g.setColor(PlaneSquareGraphics.colorSquareBorder);
		g.setColor(color);
		Stroke stokeSave = g.getStroke();
		g.setStroke(strokeBorder);
		g.drawRect(rectSquare.x, rectSquare.y, rectSquare.width, rectSquare.height);
		g.setStroke(stokeSave);
	}
}
