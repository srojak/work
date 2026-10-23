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
package srojak.core.observe.code;

import srojak.core.observe.ObservationCommon;
import srojak.core.observe.impl.NullObject;

/**
 * @author Stephen
 *
 */
public class CodeValueObject 
		extends CodeValueCommonBase 
		implements ObservationCommon {
	private final Object _objValue;

	/**
	 * @param strName
	 */
	public CodeValueObject(String strName, Object objValue) {
		super(strName, objValue != null ? objValue.getClass() : NullObject.class);
		_objValue = objValue;
	}

	public CodeValueObject(String strName, Class<?> classValue) {
		super(strName, classValue);
		_objValue = null;
	}

	@Override
	public Object value() {
		return _objValue;
	}

	@Override
	protected String formatValue() {
		if (_objValue == null) {
			return STR_NULL;
		} else {
			return _objValue.toString();
		}
	}

}
