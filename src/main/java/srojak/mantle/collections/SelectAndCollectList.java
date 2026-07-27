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
package srojak.mantle.collections;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import srojak.mantle.SelectAndCollect;

/**
 * @author Stephen
 *
 */
public class SelectAndCollectList<T>
		implements SelectAndCollect<T> {
	private final LinkedList<T> _list;
	private final Predicate<T> _predicate;

	/**
	 * 
	 */
	public SelectAndCollectList(Predicate<T> predicate) {
		Objects.requireNonNull(predicate, "predicate");
		_list = new LinkedList<T>();
		_predicate = predicate;
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public int size() {
		return _list.size();
	}

	@Override
	public boolean consider(T item) {
		Objects.requireNonNull(item, "item");
		if (_predicate.test(item)) {
			_list.addLast(item);
			return true;
		} else 
			return false;
	}

	@Override
	public List<T> getCollection() {
		return _list;
	}

}
