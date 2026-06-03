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


import java.util.*;
import java.util.function.Consumer;

/**
 * @author Stephen
 *
 */
public class TStack<T> 
		implements TStackReadOnly<T> {
	private LinkedList<T> _stack;
	
	public TStack() {
		_stack = new LinkedList<T>();
	}
	
	@Override
	public boolean isEmpty() {
		return _stack.isEmpty();
	}

	@Override
	public int size() {
		return _stack.size();
	}

	@Override
	public T peek() {
		assert !_stack.isEmpty() : "stack is empty";
		return _stack.get(0);
	}

	@Override
	public T peekSafely() {
		if (!_stack.isEmpty()) {
			return _stack.get(0);
		} else {
			return null;
		}
	}

	@Override
	public T getItemAtDepth(int nDepth) {
		return _stack.get(nDepth);
	}

	@Override
	public void forEach(Consumer<T> consumer) {
		_stack.forEach(consumer);
	}

	public void clear() {
		_stack.clear();
	}
	
	public void push(T item) {
		_stack.addFirst(item);
	}
	
	public T pop() {
		assert !_stack.isEmpty() : "stack is empty";
		return _stack.removeFirst();
	}
}
