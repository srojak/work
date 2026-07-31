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
package srojak.valuestore.values;

import java.util.Objects;

import srojak.core.keys.NamedKey;
import srojak.valuestore.StoreValueObj;

/**
 * @author Stephen
 *
 */
public class StoreValueObjInstance<T>
		extends StoreValueScalarBase
		implements StoreValueObj<T> {
	public final boolean _bAllowsNull;
	public T _value;
	
	/**
	 * @param key
	 */
	public StoreValueObjInstance(NamedKey key, boolean bAllowsNull, T value) {
		super(key);
		if (!bAllowsNull) {
			Objects.requireNonNull(value, "value");
		}
		_bAllowsNull = bAllowsNull;
		_value = value;
	}

	public T getValue() {
		return _value;
	}
	
	public void setValue(T value) {
		if (!_bAllowsNull) {
			Objects.requireNonNull(value, "value");
		}
		_value = value;
	}
}
