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
package srojak.core.observe;

/**
 * @author Stephen
 *
 */
public class InvalidObservationLevelException
		extends RuntimeException {

	/**
	 * 
	 */
	public InvalidObservationLevelException() {
		super();
	}

	/**
	 * @param message
	 */
	public InvalidObservationLevelException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidObservationLevelException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidObservationLevelException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 */
    @java.io.Serial
	private static final long serialVersionUID = -7292693245425947632L;
}
