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
	 * Find the direction for the offset in the orientation.
	 * 
	 * A cardinal direction will only be chosen if the appropriate dimensional offset
	 * is zero. For example, East and West will only be chosen if the y offset is zero.
	 * @param offset The offset for which to find the direction.
	 * @return The direction for the offset in the orientation.
	 */
	S2Direction findDirection(S2Offset offset);
	
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
	 * Create a rectangle on the given field size with specified width and height.
	 * @param direction The side of the field to use.
	 * @param szField The full size of the field.
	 * @param nWidth The width of the generated rectangle.
	 * @param nHeight The height of the generated rectangle.
	 * @return The rectangle with the required dimensions and placement.
	 */
	S2Rect getSideRect(S2CompassDirection direction, S2FieldSize szField, int nWidth, int nHeight);
	
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
