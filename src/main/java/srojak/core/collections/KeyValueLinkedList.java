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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Stephen
 *
 */
public class KeyValueLinkedList<K, V> 
		extends AbstractValueList<V> 
		implements KeyMutableValueList<K, V> {
	private final K _key;
	private final LinkedList<V> _list;

	/**
	 * 
	 */
	public KeyValueLinkedList(K key) {
		super();
		Objects.requireNonNull(key, "key");
		_key = key;
		_list = new LinkedList<V>();
	}
	
	public KeyValueLinkedList(K key, Collection<? extends V> values) {
		super();
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(values, "values");
		_key = key;
		_list = new LinkedList<V>(values);
	}

	@Override
	protected List<V> list() {
		return _list;
	}

	@Override
	public K key() {
		return _key;
	}

	@Override
	public boolean isKeyEqual(K key) {
		return _key.equals(key);
	}

	public V getFirst() {
		return _list.getFirst();
	}

	public V getLast() {
		return _list.getLast();
	}

	public V removeFirst() {
		return _list.removeFirst();
	}

	public V removeLast() {
		return _list.removeLast();
	}

	public void addFirst(V e) {
		_list.addFirst(e);
	}

	public void addLast(V e) {
		_list.addLast(e);
	}

	public V peek() {
		return _list.peek();
	}

	public V element() {
		return _list.element();
	}

	public V poll() {
		return _list.poll();
	}

	public boolean offer(V e) {
		return _list.offer(e);
	}

	public boolean offerFirst(V e) {
		return _list.offerFirst(e);
	}

	public boolean offerLast(V e) {
		return _list.offerLast(e);
	}

	public V peekFirst() {
		return _list.peekFirst();
	}

	public V peekLast() {
		return _list.peekLast();
	}

	public V pollFirst() {
		return _list.pollFirst();
	}

	public V pollLast() {
		return _list.pollLast();
	}

	public void push(V e) {
		_list.push(e);
	}

	public V pop() {
		return _list.pop();
	}

	public boolean removeFirstOccurrence(Object o) {
		return _list.removeFirstOccurrence(o);
	}

	public boolean removeLastOccurrence(Object o) {
		return _list.removeLastOccurrence(o);
	}

}
