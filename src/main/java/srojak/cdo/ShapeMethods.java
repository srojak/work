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

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class ShapeMethods {

	public static String formatPath(Shape shape, AffineTransform txform) {
		Objects.requireNonNull(shape, "shape");
		Objects.requireNonNull(txform, "txform");
		StringBuilder sb = new StringBuilder("shape ");
		PathIterator iter = shape.getPathIterator(txform);
		double[] dCoords = new double[6];
		boolean bIsFirst = true;
		while (!iter.isDone()) {
			if (bIsFirst) {
				bIsFirst = false;
			} else {
				sb.append("\n  ");
			}
			int nType = iter.currentSegment(dCoords);
			switch (nType) {
			case PathIterator.SEG_MOVETO:
				sb.append(String.format("move to (%.3f, %.3f)", dCoords[0], dCoords[1]));
				break;
				
			case PathIterator.SEG_LINETO:
				sb.append(String.format("line to (%.3f, %.3f)", dCoords[0], dCoords[1]));
				break;
				
			case PathIterator.SEG_QUADTO:
				sb.append(String.format("quadratic curve to points (%.3f, %.3f), (%.3f, %.3f)",
						dCoords[0], dCoords[1], dCoords[2], dCoords[3]));
				break;
				
			case PathIterator.SEG_CUBICTO:
				sb.append(String.format(
						"cubic curve to points (%.3f, %.3f), (%.3f, %.3f), (%.3f, %.3f)",
						dCoords[0], dCoords[1], dCoords[2], dCoords[3], dCoords[4], dCoords[5]));
				break;
				
			case PathIterator.SEG_CLOSE:
				sb.append("close shape");
				break;
			}
			
			iter.next();
		}
		return sb.toString();
	}
}
