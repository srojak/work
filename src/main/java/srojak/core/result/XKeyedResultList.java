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
package srojak.core.result;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import srojak.core.CommonCollectionSize;
import srojak.core.tools.ListMethods;

/**
 * @author Stephen
 *
 */
public class XKeyedResultList 
		implements CommonCollectionSize {
	private final List<XKeyedResult> _list;
	
	public XKeyedResultList() {
		_list = new LinkedList<XKeyedResult>();
	}

	@Override
	public boolean isEmpty() {
		return _list.isEmpty();
	}

	@Override
	public int size() {
		return _list.size();
	}

	public boolean containsKey(XResultKey key) {
		return ListMethods.isTrueForAny(_list, x -> x.key().equals(key));
	}
	
	public XKeyedResult find(XResultKey key) {
		return ListMethods.findInList(_list, x -> x.key().equals(key));
	}
	
	public Stream<XKeyedResult> getAll() {
		return _list.stream();
	}
	
	public void addResult(XResultKey key, XResult result) {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(result, "result");
		if (containsKey(key)) {
			throw new IllegalArgumentException("key " + key + " is already in list");
		}
		XKeyedResult keyed = new XKeyedResult(key, result);
		_list.add(keyed);
	}
	
	public boolean anyResultsFailed() {
		return ListMethods.isTrueForAny(_list, r -> !r.isValid());
	}
}
