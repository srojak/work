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
package srojak.core.excepts;

import java.io.IOException;

/**
 * @author Stephen
 *
 */
public class PathResolutionException 
		extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 974937633603666167L;

	/**
	 * 
	 */
	public PathResolutionException() {
		super();
	}

	/**
	 * @param message
	 */
	public PathResolutionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PathResolutionException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PathResolutionException(String message, Throwable cause) {
		super(message, cause);
	}

}
