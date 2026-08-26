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
package srojak.xml;

import javax.xml.stream.Location;

/**
 * @author Stephen
 *
 */
public class XmlValueException
		extends Exception {

	/**
	 * The location of the error.
	 */
	protected Location _location;

	/**
	 * 
	 */
	private static final long serialVersionUID = 9017434755834284858L;
	
	@SuppressWarnings("unused")
	private static String prefaceMessageWithLocation(Location location, String message) {
		if (location != null) {
			return "at line=" + location.getLineNumber() + ", col=" + location.getColumnNumber()
				+ " " + message;
		} else {
			return message;
		}
	}

	/**
	 * 
	 */
	public XmlValueException() {
		_location = null;
	}

	/**
	 * @param message
	 */
	public XmlValueException(String message) {
		super(message);
		_location = null;
	}

	/**
	 * @param message
	 */
	public XmlValueException(Location location, String message) {
		super(message);
		_location = location;
	}

	/**
	 * @param cause
	 */
	public XmlValueException(Throwable cause) {
		super(cause);
		_location = null;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public XmlValueException(String message, Throwable cause) {
		super(message, cause);
		_location = null;
	}

	public XmlValueException(Location location, String message, Throwable cause) {
		super(message, cause);
		_location = location;
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public XmlValueException(Location location, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		_location = location;
	}

	/**
	 * Gets the location of the exception
	 *
	 * @return the location of the exception, may be null if none is available
	 */
	public Location getLocation() {
		return _location;
	}
}
