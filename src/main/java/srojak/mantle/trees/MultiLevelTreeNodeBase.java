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
package srojak.mantle.trees;

import java.util.Objects;

import srojak.core.keys.MultiPartKeyPart;

/**
 * @author Stephen
 *
 */
public abstract class MultiLevelTreeNodeBase<E>
		implements MultiLevelTreeNode<E> {
	private E _value;
	
	protected MultiLevelTreeNodeBase() {
		_value = null;
	}

	@Override
	public abstract boolean containsKey(MultiPartKeyPart keyPart);

	@Override
	public boolean hasValue() {
		return _value != null;
	}

	@Override
	public E getValue() {
		return _value;
	}

	@Override
	public void putValue(E value) {
		Objects.requireNonNull(value, "value");
		_value = value;
	}

	@Override
	public boolean putValueIfAbsent(E value) {
		Objects.requireNonNull(value, "value");
		if (_value == null) {
			_value = value;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeValue() {
		if (_value != null) {
			_value = null;
			return true;
		} else {
			return false;
		}
	}
}
