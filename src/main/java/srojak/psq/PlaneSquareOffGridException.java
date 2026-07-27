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
package srojak.psq;

import srojak.spatial.S2Coords;

/**
 * @author Stephen
 *
 */
public class PlaneSquareOffGridException
		extends PSqLocOperationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8875280419406912106L;

	/**
	 * @param coords
	 */
	public PlaneSquareOffGridException(S2Coords coords) {
		super(coords);
	}

	/**
	 * @param coords
	 * @param message
	 */
	public PlaneSquareOffGridException(S2Coords coords, String message) {
		super(coords, message);
	}

	/**
	 * @param coords
	 * @param cause
	 */
	public PlaneSquareOffGridException(S2Coords coords, Throwable cause) {
		super(coords, cause);
	}

	/**
	 * @param coords
	 * @param message
	 * @param cause
	 */
	public PlaneSquareOffGridException(S2Coords coords, String message, Throwable cause) {
		super(coords, message, cause);
	}

	/**
	 * @param coords
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PlaneSquareOffGridException(S2Coords coords, String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(coords, message, cause, enableSuppression, writableStackTrace);
	}

}
