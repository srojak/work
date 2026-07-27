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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import srojak.numerics.CircleOctant;
import srojak.numerics.RadiansMethods;

/**
 * @author Stephen
 *
 */
public class AWTGeometry {
	
	private static final double A8 = RadiansMethods.PiOver8;
	private static final double A4 = RadiansMethods.PiOver4;
	
	public static Point reduce(Point2D point) {
		Objects.requireNonNull(point, "point");
		return new Point((int) Math.round(point.getX()), (int) Math.round(point.getY()));
	}
	
	public static Point2D findMidpoint(Point2D ptStart, Point2D ptEnd) {
		Objects.requireNonNull(ptStart, "ptStart");
		Objects.requireNonNull(ptEnd, "ptEnd");
		return new Point2D.Double((ptStart.getX() + ptEnd.getX()) / 2.0,
				(ptStart.getY() + ptEnd.getY()) / 2.0);
	}
	
	public static Rectangle reduce(Rectangle2D rect) {
		Objects.requireNonNull(rect, "rect");
		return new Rectangle((int) Math.round(rect.getX()), (int) Math.round(rect.getY()),
				(int) Math.round(rect.getWidth()), (int) Math.round(rect.getHeight()));
	}
	
	public static Rectangle2D alter(Rectangle2D rect, double dLeft, double dTop,
			double dRight, double dBottom) {
		Objects.requireNonNull(rect, "rect");
		return new Rectangle2D.Double(rect.getX() + dLeft, rect.getY() + dTop, 
				rect.getWidth() + dLeft + dRight, rect.getHeight() + dTop + dBottom);
	}
	
	public static Dimension scale(Dimension szOrig, double dScaleX, double dScaleY) {
		Dimension szScaled = new Dimension();
		szScaled.setSize(dScaleX * szOrig.width, dScaleY * szOrig.height);
		return szScaled;
	}
	
	public static List<Line2D> linesFromPoints(Collection<? extends Point2D> points) {
		Objects.requireNonNull(points, "points");
		if (points.size() < 2) {
			return List.of();
		}
		List<Line2D> list = new ArrayList<Line2D>(points.size() - 1);
		Iterator<? extends Point2D> iterator = points.iterator();
		Point2D ptLast = iterator.next();
		while (iterator.hasNext()) {
			Point2D ptNext = iterator.next();
			Line2D line = new Line2D.Double(ptLast, ptNext);
			list.add(line);
			ptLast = ptNext;
		}
		return list;
	}
	
	public static CircleOctant findOctant(double deltaX, double deltaY) {
		double dTheta = Math.atan2(deltaY, deltaX);
		// spin for simplicity
		dTheta += A8;
		double dOct = Math.floor(dTheta / A4);
		// this is for graphics orientation
		// values increase rightward and downward
		switch ((int) dOct) {
		case 0:
			return CircleOctant.RIGHT;
			
		case 1:
			return CircleOctant.UPPER_RIGHT;
			
		case 2:
			return CircleOctant.UP;
			
		case 3:
			return CircleOctant.UPPER_LEFT;
			
		case -3:
			return CircleOctant.LOWER_LEFT;
			
		case -2:
			return CircleOctant.DOWN;
			
		case -1:
			return CircleOctant.LOWER_RIGHT;
			
		default:
			return CircleOctant.LEFT;
		}
	}
	
	public static CircleOctant findRelativeOctant(Point2D point, Point2D pointRelative) {
		Objects.requireNonNull(point, "point");
		Objects.requireNonNull(pointRelative, "pointRelative");
		double dx = point.getX() - pointRelative.getX();
		double dy = point.getY() - pointRelative.getY();
		return findOctant(dx, dy);
	}
	
	public static CircleOctant findGraphicsRelativeOctant(Point2D point, Point2D pointRelative) {
		Objects.requireNonNull(point, "point");
		Objects.requireNonNull(pointRelative, "pointRelative");
		double dx = point.getX() - pointRelative.getX();
		double dy = point.getY() - pointRelative.getY();
		return findOctant(dx, -dy);
	}
	
	public static PointOffset findOffset(CircleOctant octant, int nPixels) {
		Objects.requireNonNull(octant, "octant");
		int dx = 0;
		int dy = 0;
		switch (octant) {
		case UP:
			dy = -nPixels;
			break;
			
		case UPPER_RIGHT:
			dx = nPixels;
			dy = -nPixels;
			break;
			
		case RIGHT:
			dx = nPixels;
			break;
			
		case LOWER_RIGHT:
			dx = nPixels;
			dy = nPixels;
			break;
			
		case DOWN:
			dy = nPixels;
			break;
			
		case LOWER_LEFT:
			dx = - nPixels;
			dy = nPixels;
			break;
			
		case LEFT:
			dx = - nPixels;
			break;
			
		case UPPER_LEFT:
			dx = -nPixels;
			dy = -nPixels;
			break;
		}
		return new PointOffset(dx, dy);
	}
}
