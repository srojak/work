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
package srojak.core.events;

import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Stephen
 *
 */
public class SingleEventListenerList<T extends EventListener>
		implements SingleEventListenerStore<T> {
	private final LinkedList<T> _list;
	
	public SingleEventListenerList() {
		_list = new LinkedList<T>();
	}

	@Override
	public int getListenerCount() {
		return _list.size();
	}

	@Override
	public void clear() {
		_list.clear();
	}

	@Override
	public List<T> getListeners() {
		return List.copyOf(_list);
	}

	@Override
	public void forEach(Consumer<T> consumer) {
		ListIterator<T> iter = _list.listIterator();
		while (iter.hasNext()) {
			T listener = iter.next();
			consumer.accept(listener);
		}
		
	}

	@Override
	public void forEachReversed(Consumer<T> consumer) {
		ListIterator<T> iter = _list.listIterator(_list.size());
		while (iter.hasPrevious()) {
			T listener = iter.previous();
			consumer.accept(listener);
		}
	}

	@Override
	public synchronized void add(T listener) {
		if (listener == null) {
			return;
		}
		_list.add(listener);
	}

	@Override
	public synchronized void remove(T listener) {
		if (listener == null) {
			return;
		}
		_list.remove(listener);
	}

}
