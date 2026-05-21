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
package srojak.core.containers;

import java.util.Objects;

import srojak.core.LabeledObject;

/**
 * @author Stephen
 *
 */
public class LabeledEnvelope<T>
		implements LabeledObject {
	private final T _item;
	private final String _strLabel;
	
	public LabeledEnvelope(T item, String strLabel) {
		Objects.requireNonNull(item, "item");
		Objects.requireNonNull(strLabel, "strLabel");
		_item = item;
		_strLabel = strLabel;
	}
	
	public T getValue() {
		return _item;
	}
	
	@Override
	public String toString() {
		return _strLabel;
	}

	@Override
	public Object getObject() {
		return _item;
	}
}
