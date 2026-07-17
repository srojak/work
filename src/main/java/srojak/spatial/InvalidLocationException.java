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
 */
public class InvalidLocationException
		extends SpatialCalcException
		implements S2CoordsBearing {
	private final S2Coords _coords;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6870859587198215663L;

	/**
	 * @param coords
	 */
	public InvalidLocationException(S2Coords coords) {
		super();
		_coords = coords;
	}

	/**
	 * @param coords
	 * @param message
	 */
	public InvalidLocationException(S2Coords coords, String message) {
		super(message);
		_coords = coords;
	}

	/**
	 * @param coords
	 * @param cause
	 */
	public InvalidLocationException(S2Coords coords, Throwable cause) {
		super(cause);
		_coords = coords;
	}

	/**
	 * @param coords
	 * @param message
	 * @param cause
	 */
	public InvalidLocationException(S2Coords coords, String message, Throwable cause) {
		super(message, cause);
		_coords = coords;
	}

	/**
	 * @param coords
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidLocationException(S2Coords coords, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		_coords = coords;
	}

	@Override
	public S2Coords getCoords() {
		return _coords;
	}

}
