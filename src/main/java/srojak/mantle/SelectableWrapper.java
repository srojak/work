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
package srojak.mantle;

import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class SelectableWrapper<T>
		implements SelectableObject {
	private boolean _bSelected;
	private final T _item;
	
	public SelectableWrapper(T item) {
		Objects.requireNonNull(item, "item");
		_item = item;
		_bSelected = false;
	}

	@Override
	public boolean isSelected() {
		return _bSelected;
	}

	@Override
	public void setSelected(boolean bState) {
		_bSelected = bState;
	}
	
	public T getValue() {
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
		if (obj instanceof @SuppressWarnings("rawtypes") srojak.mantle.SelectableWrapper other) {
			return _item.equals(other.getValue());
		} else {
			return _item.equals(obj);
		}
	}
}
