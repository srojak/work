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
package srojak.core.text;

import java.util.Objects;

import srojak.core.LabeledObject;

/**
 * @author Stephen
 *
 */
public class LabeledEnvelope<T>
		extends LabeledEnvelopeBase<T> {
	private final T _item;
	
	public LabeledEnvelope(T item, String strLabel) {
		super(strLabel);
		Objects.requireNonNull(item, "item");
		_item = item;
	}
	
	@Override
	public boolean hasObject() {
		return true;
	}

	@Override
	public T getValue() {
		return _item;
	}
	
	@Override
	public boolean isValueEqual(T value) {
		if (value == null) {
			return false;
		} else {
			return _item.equals(value);
		}
	}

	@Override
	public Object getObject() {
		return _item;
	}

	@Override
	public int hashCode() {
		return _item.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof LabeledObject other) {
			return _item.equals(other.getObject());
		} else {
			return _item.equals(obj);
		}
	}
}
