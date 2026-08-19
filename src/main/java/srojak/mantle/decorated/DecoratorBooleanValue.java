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
package srojak.mantle.decorated;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public class DecoratorBooleanValue
		extends DecoratorBase
		implements BooleanDecorator {
	private boolean _value;

	/**
	 * @param token
	 */
	public DecoratorBooleanValue(NameToken token, boolean value) {
		super(token);
		_value = value;
	}

	@Override
	public boolean getValue() {
		return _value;
	}

	@Override
	public void setValue(boolean value) {
		testLock();
		_value = value;
	}

	@Override
	protected Object getValueAsObject() {
		return Boolean.valueOf(_value);
	}

	@Override
	protected String getStringValue() {
		return String.valueOf(_value);
	}

}
