package srojak.spatial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import srojak.core.Ordered;
import srojak.core.containers.OrderedWrapper;

public class S2Geometry {

	public static List<S2Segment> pointsToSegments(Collection<S2Coords> points, boolean bLockSegments) {
		Objects.requireNonNull(points, "points");
		if (points.size() <= 1) {
			return new ArrayList<S2Segment>();
		}
		ArrayList<S2Segment> list = new ArrayList<S2Segment>(points.size() - 1);
		Iterator<S2Coords> iterator = points.iterator();
		S2Coords coordsPrior = iterator.next();
		while (iterator.hasNext()) {
			S2Coords coords = iterator.next();
			S2Segment segment = new S2Segment(coordsPrior, coords, bLockSegments);
			list.add(segment);
			coordsPrior = coords;
		}
		return list;	
	}
	
	public static List<S2CoordsMove> pointsToMoves(Collection<S2Coords> points) {
		Objects.requireNonNull(points, "points");
		ArrayList<S2CoordsMove> list = new ArrayList<S2CoordsMove>(points.size());
		Iterator<S2Coords> iterator = points.iterator();
		if (iterator.hasNext()) {
			S2CoordsMove cmv = new S2CoordsMove(SpatialMove.Start, iterator.next());
			list.add(cmv);
		}
		while (iterator.hasNext()) {
			S2CoordsMove cmv = new S2CoordsMove(SpatialMove.Move, iterator.next());
			list.add(cmv);
		}
		return list;
	}
	
	public static Ordered<S2Segment> findSegmentContainingPoint(List<S2Segment> listSegments,
			S2Coords coordsPoint) {
		Objects.requireNonNull(listSegments, "listSegments");
		Objects.requireNonNull(coordsPoint, "coordsPoint");
		Iterator<S2Segment> iterator = listSegments.iterator();
		int index = -1;
		while (iterator.hasNext()) {
			index++;
			S2Segment segment = iterator.next();
			S2Rect rectBounding = segment.getBoundingRect();
			if (!rectBounding.contains(coordsPoint)) {
				continue;
			}
			if (segment.isPointOnSegment(coordsPoint)) {
				return new OrderedWrapper<S2Segment>(segment, index);
			}
		}
		return null;
	}
	
	public static S2Offset polarToOffset(PolarCoords polar) {
		Objects.requireNonNull(polar, "polar");
		return new S2Offset((int) Math.round(polar.getX()), (int) Math.round(polar.getY()));
	}
	
	private static int computeDistanceNumerator(S2Coords coordsFirst, S2Coords coordsSecond,
			S2Offset offsetSecond, S2Coords coordsPoint) {
		return Math.abs(offsetSecond.dy * coordsPoint._x - offsetSecond.dx * coordsPoint._y
				+ coordsSecond._x * coordsFirst._y - coordsSecond._y * coordsFirst._x);
	}
	
	/**
	 * Get the numerator of the distance from a point to a line.
	 * @param coordsFirst The first coordinate on the line or segment.
	 * @param coordsSecond The second coordinate on the line or segment.
	 * @param offsetSecond The offset from the first coordinate to the second.
	 * @param coordsPoint The coordinates of the point for which to find the distance.
	 * @return the numerator of the distance formula.
	 * 
	 * @see https://stackoverflow.com/questions/30559799/function-for-finding-the-distance-between-a-point-and-an-edge-in-java
	 * @see https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
	 * 
	 * The denominator, if needed, would be provided by offsetSecond.getDistance().
	 * When comparing two points off the same line, the denominator cancels.
	 */
	public static int getDistanceNumerator(S2Coords coordsFirst, S2Coords coordsSecond,
			S2Offset offsetSecond, S2Coords coordsPoint) {
		Objects.requireNonNull(coordsFirst, "coordsFirst");
		Objects.requireNonNull(coordsSecond, "coordsSecond");
		Objects.requireNonNull(offsetSecond, "offsetSecond");
		Objects.requireNonNull(coordsPoint, "coordsPoint");
		return computeDistanceNumerator(coordsFirst, coordsSecond, offsetSecond, coordsPoint);
	}
	
	/**
	 * Get the numerator of the distance from a point to a line.
	 * @param coordsFirst The first coordinate on the line or segment.
	 * @param coordsSecond The second coordinate on the line or segment.
	 * @param coordsPoint The coordinates of the point for which to find the distance.
	 * @return the numerator of the distance formula.
	 */
	public static int getDistanceNumerator(S2Coords coordsFirst, S2Coords coordsSecond, 
			S2Coords coordsPoint) {
		Objects.requireNonNull(coordsFirst, "coordsFirst");
		Objects.requireNonNull(coordsSecond, "coordsSecond");
		Objects.requireNonNull(coordsPoint, "coordsPoint");
		S2Offset offset = coordsFirst.getOffsetTo(coordsSecond);
		return computeDistanceNumerator(coordsFirst, coordsSecond, offset, coordsPoint);
	}
}
