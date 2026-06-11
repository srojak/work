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
package srojak.core.collections;

import java.util.ArrayList;
import java.util.Collection;

import srojak.core.CommonCollectionSize;
import srojak.core.EmptyCollectionException;
/**
 * @author Stephen
 *
 */
public class TDeck<T>
		implements CommonCollectionSize {
	private ArrayList<T> _list;
	
	public TDeck() {
		_list = new ArrayList<T>();
	}
	
	public boolean isEmpty() {
		return _list.isEmpty();
	}
	
	public int size() {
		return _list.size();
	}
	
	public void clear() {
		_list.clear();
	}
	
	protected void add(T item) {
		_list.add(item);
	}
	
	public void fillFrom(Collection<? extends T> coll) {
		_list.addAll(coll);
	}
	
	public T peek(int index) {
		if (_list.isEmpty()) {
			throw new EmptyCollectionException();
		}
		return _list.get(index);
	}
	
	public T draw(int index) {
		if (_list.isEmpty()) {
			throw new EmptyCollectionException();
		}
		return _list.remove(index);
	}
}
