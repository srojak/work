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
package srojak.sql;

import srojak.core.keys.ClassKey;
import srojak.sql.impl.UnknownMappedObject;

/**
 * @author Stephen
 *
 */
public class MappedObjectException 
		extends Exception {
	private final ClassKey _classObj;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -96125451123791545L;

	/**
	 * 
	 */
	public MappedObjectException(ClassKey classObj) {
		super();
		_classObj = classObj != null ? classObj : new ClassKey(UnknownMappedObject.class);
	}

	/**
	 * @param message
	 */
	public MappedObjectException(ClassKey classObj, String message) {
		super(message);
		_classObj = classObj != null ? classObj : new ClassKey(UnknownMappedObject.class);
	}

	/**
	 * @param cause
	 */
	public MappedObjectException(ClassKey classObj, Throwable cause) {
		super(cause);
		_classObj = classObj != null ? classObj : new ClassKey(UnknownMappedObject.class);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MappedObjectException(ClassKey classObj, String message, Throwable cause) {
		super(message, cause);
		_classObj = classObj != null ? classObj : new ClassKey(UnknownMappedObject.class);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public MappedObjectException(ClassKey classObj,String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		_classObj = classObj != null ? classObj : new ClassKey(UnknownMappedObject.class);
	}
	
	public ClassKey getObjectClass() {
		return _classObj;
	}

}
