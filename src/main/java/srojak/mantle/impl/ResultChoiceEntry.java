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
package srojak.mantle.impl;

import java.util.Objects;

import srojak.mantle.restbl.ResultChoice;

/**
 * @author Stephen
 *
 */
public class ResultChoiceEntry<T> 
		implements ResultChoice<T> {
	private final int _width;
	private final T _value;
	private boolean _bEnabled;
	
	public ResultChoiceEntry(int nWidth, T value) {
		Objects.requireNonNull(value, "value");
		if (nWidth < 1) {
			throw new IllegalArgumentException("nWidth must be positive");
		}
		_width = nWidth;
		_value = value;
		_bEnabled = true;
	}

	@Override
	public boolean isEnabled() {
		return _bEnabled;
	}
	
	void setEnabled(boolean bState) {
		_bEnabled = bState;
	}

	@Override
	public int getWidth() {
		return _bEnabled ? _width : 0;
	}

	@Override
	public Object getObject() {
		return _value;
	}

	@Override
	public T getValue() {
		return _value;
	}

	boolean isValueEqual(Object obj) {
		return _value.equals(obj);
	}
}
