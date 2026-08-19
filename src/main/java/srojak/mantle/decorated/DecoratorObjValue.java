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

import java.util.Objects;

import srojak.core.NameToken;

/**
 * @author Stephen
 *
 */
public class DecoratorObjValue<V>
		extends DecoratorBase
		implements ObjDecorator<V> {
	private V _value;

	/**
	 * @param token
	 */
	public DecoratorObjValue(NameToken token, V value) {
		super(token);
		Objects.requireNonNull(value, "value");
		_value = value;
	}

	@Override
	public Class<?> getValueClass() {
		return _value.getClass();
	}

	@Override
	public V getValue() {
		return _value;
	}

	@Override
	public void setValue(V value) {
		Objects.requireNonNull(value, "value");
		testLock();
		_value = value;
	}

	@Override
	protected Object getValueAsObject() {
		return _value;
	}

	@Override
	protected String getStringValue() {
		return _value.toString();
	}

}
