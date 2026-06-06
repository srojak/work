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
package srojak.core.field;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public class SetOnceInt
		extends SetOnceBase {
	private int _value;

	/**
	 * @param token
	 */
	public SetOnceInt(NameToken token) {
		super(token);
		_value = 0;
	}

	/**
	 * @param strName
	 */
	public SetOnceInt(String strName) {
		super(strName);
		_value = 0;
	}

	@Override
	public boolean allowsNonNullValue() {
		return false;
	}

	public int get() {
		gettingValue();
		return _value;
	}
	
	public void set(int value) {
		settingValue();
		_value = value;
	}
}
