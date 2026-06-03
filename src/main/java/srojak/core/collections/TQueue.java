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

import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * @author Stephen
 *
 */
public class TQueue<T>
		implements TQueueReadOnly<T> {
	private LinkedList<T> _queue;
	
	public TQueue() {
		_queue = new LinkedList<T>();
	}
	
	@Override
	public boolean isEmpty() {
		return _queue.isEmpty();
	}

	@Override
	public int size() {
		return _queue.size();
	}
	
	@Override
	public T getItemAt(int nPos) {
		return _queue.get(nPos);
	}

	public void clear() {
		_queue.clear();
	}
	
	public void enqueue(T item) {
		_queue.addLast(item);
	}
	
	public T dequeue() {
		return _queue.removeFirst();
	}
	
	public T dequeueSafely() {
		if (_queue.isEmpty()) {
			return null;
		} else {
			return _queue.removeFirst();
		}
	}

	@Override
	public void forEach(Consumer<T> consumer) {
		_queue.forEach(consumer);
	}
}
