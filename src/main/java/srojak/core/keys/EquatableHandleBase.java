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
package srojak.core.keys;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public abstract class EquatableHandleBase 
		extends EquatableSeriesKeyBase 
		implements EquatableHandle {
	protected final int _value;

	/**
	 * 
	 */
	public EquatableHandleBase(int value) {
		super();
		_value = value;
	}
	
	public EquatableHandleBase(Object obj) {
		super();
		Objects.requireNonNull(obj, "obj");
		_value = System.identityHashCode(obj);
	}

	@Override
	public int keyValue() {
		return _value;
	}
	
	protected <H extends EquatableHandle> boolean commonEquals(H obj) {
		if (obj != null) {
			return _value == obj.keyValue();
		} else {
			return false;
		}
	}
	
	protected int commonComparer(EquatableHandle other) {
		if (other == null) {
			return 1;
		} else {
			return Integer.compare(_value, other.keyValue());
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getRootId(), _value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		else if (obj instanceof EquatableHandleBase other) {
			return getRootId() == other.getRootId()
					&& _value == other._value;
		} else
			return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getRootName());
		sb.append('[');
		sb.append(Integer.toHexString(_value));
		sb.append(']');
		return sb.toString();
	}


}
