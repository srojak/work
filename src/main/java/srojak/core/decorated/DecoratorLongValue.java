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
package srojak.core.decorated;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public class DecoratorLongValue
		extends DecoratorBase
		implements LongDecorator {
	private long _value;

	/**
	 * @param token
	 */
	public DecoratorLongValue(NameToken token, long value) {
		super(token);
		_value = value;
	}

	@Override
	public long getValue() {
		return _value;
	}

	@Override
	public void setValue(long value) {
		testLock();
		_value = value;
	}

	@Override
	protected Object getValueAsObject() {
		return Long.valueOf(_value);
	}

	@Override
	protected String getStringValue() {
		return String.valueOf(_value);
	}

}
