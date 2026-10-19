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
import java.util.function.Function;

import srojak.core.KeyMutableValue;
import srojak.core.KeyedList;

/**
 * @author Stephen
 *
 */
public class KeyedLinkedList<K, V> 
		extends AbstractKeyValueList<K, V> 
		implements KeyedList<K, V> {
	private final LinkedList<KeyMutableValue<K, V>> _list;
	
	public KeyedLinkedList() {
		_list = new LinkedList<KeyMutableValue<K, V>>();
	}
	
	public KeyedLinkedList(Collection<? extends V> values, Function<V, K> fnKeyExtract) {
		Objects.requireNonNull(values, "value");
		Objects.requireNonNull(fnKeyExtract, "fnKeyExtract");
		_list = new LinkedList<KeyMutableValue<K, V>>(
				values.stream().map(v -> bindPair(fnKeyExtract.apply(v), v)).toList());
	}

	@Override
	protected List<KeyMutableValue<K, V>> list() {
		return _list;
	}

	public KeyMutableValue<K, V> getFirst() {
		return _list.getFirst();
	}

	public KeyMutableValue<K, V> getLast() {
		return _list.getLast();
	}

	public KeyMutableValue<K, V> removeFirst() {
		return _list.removeFirst();
	}

	public KeyMutableValue<K, V> removeLast() {
		return _list.removeLast();
	}

	public KeyMutableValue<K, V> peek() {
		return _list.peek();
	}

	public KeyMutableValue<K, V> poll() {
		return _list.poll();
	}

	public boolean offer(KeyMutableValue<K, V> e) {
		return _list.offer(e);
	}

	public boolean offerFirst(KeyMutableValue<K, V> e) {
		return _list.offerFirst(e);
	}

	public boolean offerLast(KeyMutableValue<K, V> e) {
		return _list.offerLast(e);
	}

	public KeyMutableValue<K, V> peekFirst() {
		return _list.peekFirst();
	}

	public KeyMutableValue<K, V> peekLast() {
		return _list.peekLast();
	}

	public KeyMutableValue<K, V> pollFirst() {
		return _list.pollFirst();
	}

	public KeyMutableValue<K, V> pollLast() {
		return _list.pollLast();
	}

	public KeyMutableValue<K, V> pop() {
		return _list.pop();
	}

	public void push(KeyMutableValue<K, V> e) {
		_list.push(e);
	}

	public boolean removeFirstOccurrence(Object o) {
		return _list.removeFirstOccurrence(o);
	}

	public boolean removeLastOccurrence(Object o) {
		return _list.removeLastOccurrence(o);
	}
}
