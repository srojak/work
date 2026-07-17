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
package srojak.spatial;

import java.util.Collection;
import java.util.List;

import srojak.numerics.CircleOctant;
import srojak.numerics.CompassDegrees;

/**
 * @author Stephen
 *
 * Defines application of compass directions to the S2 coordinates.
 */
public interface S2Orientation {
	
	/**
	 * Gets the direction for the orientation where x values increase.
	 * @return The increasing horizontal direction for the orientation.
	 */
	S2CompassDirection getIncreasingHorizontalDirection();
	
	/**
	 * Gets the direction for the orientation where y values increase.
	 * @return The increasing vertical direction for the orientation.
	 */
	S2CompassDirection getIncreasingVerticalDirection();
	
	/**
	 * Translate the direction for the octant in the orientation.
	 * @param octant The octant of the circle containing the direction.
	 * @return The corresponding direction for the orientation.
	 */
	S2CompassDirection getDirectionFromOctant(CircleOctant octant);
	
	/**
	 * Find the direction for the offset in the orientation.
	 * 
	 * A cardinal direction will only be chosen if the appropriate dimensional offset
	 * is zero. For example, East and West will only be chosen if the y offset is zero.
	 * @param offset The offset for which to find the direction.
	 * @return The direction for the offset in the orientation.
	 */
	S2Direction findDirection(S2Offset offset);
	
	/**
	 * Find the direction for the offset in the orientation.
	 * 
	 * A cardinal direction will only be chosen if the appropriate dimensional offset
	 * is zero. For example, East and West will only be chosen if the y offset is zero.
	 * @param offset The offset for which to find the direction.
	 * @return The direction for the offset in the orientation.
	 * @throws NoValidMoveException The offset is zero.
	 */
	S2CompassDirection findCompassDirection(S2Offset offset)
			throws NoValidMoveException;
	
	/**
	 * Find the nearest direction for the offset in the orientation.
	 * 
	 * The method divides a circle into 8 arcs with each direction in the center of an arc.
	 * The direction having the offset in its arc is chosen.
	 * @param offset The offset for which to find the direction.
	 * @return The nearest direction for the offset in the orientation.
	 */
	S2Direction findNearestDirection(S2Offset offset);
	
	/**
	 * Compute the offset to move by one square in the given direction.
	 * @param direction The direction in which to move.
	 * @return The offset to move in that direction in the orientation.
	 */
	S2Offset offsetByOne(S2CompassDirection direction);
	
	/**
	 * Compute the offset to move by a given distance in the given direction.
	 * @param direction The direction in which to move.
	 * @param nDistance The distance in squares in which to move.
	 * @return The offset to move in that direction in the orientation.
	 */
	S2Offset offset(S2CompassDirection direction, int nDistance);
	
	/**
	 * Compute the offset to move by a given distance in the given direction.
	 * @param dRadians The angle in radians with respect to this orientation.
	 * @param fDistance The distance in squares in which to move.
	 * @return The offset to move in that direction in the orientation.
	 */
	S2Offset offset(double dRadians, float fDistance);
	
	/**
	 * Create a rectangle on the given field size with specified width and height.
	 * @param direction The side of the field to use.
	 * @param szField The full size of the field.
	 * @param nWidth The width of the generated rectangle.
	 * @param nHeight The height of the generated rectangle.
	 * @return The rectangle with the required dimensions and placement.
	 */
	S2Rect getSideRect(S2CompassDirection direction, S2FieldSize szField, int nWidth, int nHeight);
	
	/**
	 * Find the angle in degrees for an offset.
	 * The angle is with respect to the orientation.
	 * @param offset The offset for which to find the direction.
	 * @return The angle for the offset.
	 */
	CompassDegrees findDegreesFor(S2Offset offset);
	
	/** Find a unit vector that represents movement from one <strong>adjacent</strong>
	 * 		
	 * @param coordsFrom
	 * @param coordsTo
	 * @return
	 * @throws NoValidMoveException
	 */
	S2UnitRay findUnitVector(S2Coords coordsFrom, S2Coords coordsTo)
			throws NoValidMoveException;
	
	/**
	 * Find a vector that represents movement from one coordinate to another.
	 * @param coordsFrom The origin coordinate.
	 * @param coordsTo The coordinate that is the destination.
	 * @return A vector that begins at the origin and moves to the destination.
	 */
	S2RayFixedHeading findVector(S2Coords coordsFrom, S2Coords coordsTo);
	
	/**
	 * From a sequential collection of points, create a list of vectors.
	 * @param coords The collection of points to traverse.
	 * @return A list of vectors. The list will be empty if there are less than two points
	 * 		in the input.
	 */
	List<S2RayFixedHeading> getVectorsFrom(Collection<S2Coords> coords);
	
	/**
	 * Gets the math orientation, in which coordinates increase upward and rightward.
	 * @return An instance of the math orientation.
	 */
	public static S2Orientation math() {
		return new S2MathOrientation();
	}
	
	/**
	 * Gets the graphics orientation, in which coordinates increase upward and rightward.
	 * @return An instance of the graphics orientation.
	 */
	public static S2Orientation graphics() {
		return new S2GraphicsOrientation();
	}
}
