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
package srojak.core;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class SetOnce<T>
		extends SetOnceBase {
	private final boolean _bAllowsNull;	
	private T _value;
	
	/**
	 * @param token
	 */
	public SetOnce(NameToken token, boolean bAllowsNull) {
		super(token);
		_value = null;
		_bAllowsNull = bAllowsNull;
	}
	
	public SetOnce(String strName, boolean bAllowsNull) {
		super(strName);
		_value = null;
		_bAllowsNull = bAllowsNull;
	}

	@Override
	public boolean allowsNonNullValue() {
		return _bAllowsNull;
	}

	public T get() {
		gettingValue();
		return _value;
	}
	
	public void set(T value) {
		if (!_bAllowsNull) {
			Objects.requireNonNull(value, "value");
		}
		settingValue();
		_value = value;
	}
}
