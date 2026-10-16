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
package srojak.concurrent.queues;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import srojak.concurrent.WorkEnvironment;
import srojak.concurrent.functional.RunnableInWorkEnvironment;

/**
 * @author Stephen
 *
 */
public class BlockingWorkEnvMethodQueue<E extends WorkEnvironment> 
		implements BlockingWorkEnvQueueClient<E>, BlockingWorkEnvQueueServer<E> {
	public BlockingQueue<RunnableInWorkEnvironment<E>> _queue;

	@Override
	public boolean isEmpty() {
		return _queue.isEmpty();
	}

	@Override
	public int size() {
		return _queue.size();
	}

	public void forEach(Consumer<? super RunnableInWorkEnvironment<E>> action) {
		_queue.forEach(action);
	}

	@Override
	public boolean add(RunnableInWorkEnvironment<E> e) {
		return _queue.add(e);
	}

	@Override
	public RunnableInWorkEnvironment<E> remove() {
		return _queue.remove();
	}

	@Override
	public RunnableInWorkEnvironment<E> poll() {
		return _queue.poll();
	}

	@Override
	public RunnableInWorkEnvironment<E> element() {
		return _queue.element();
	}

	@Override
	public boolean offer(RunnableInWorkEnvironment<E> e) {
		return _queue.offer(e);
	}

	@Override
	public RunnableInWorkEnvironment<E> peek() {
		return _queue.peek();
	}

	@Override
	public void put(RunnableInWorkEnvironment<E> e) throws InterruptedException {
		_queue.put(e);
	}

	@Override
	public boolean offer(RunnableInWorkEnvironment<E> e, long timeout, TimeUnit unit) throws InterruptedException {
		return _queue.offer(e, timeout, unit);
	}

	@Override
	public RunnableInWorkEnvironment<E> take() throws InterruptedException {
		return _queue.take();
	}

	@Override
	public RunnableInWorkEnvironment<E> poll(long timeout, TimeUnit unit) throws InterruptedException {
		return _queue.poll(timeout, unit);
	}

	@Override
	public int remainingCapacity() {
		return _queue.remainingCapacity();
	}

	public boolean remove(Object o) {
		return _queue.remove(o);
	}

	public boolean contains(Object o) {
		return _queue.contains(o);
	}

	@Override
	public int drainTo(Collection<? super RunnableInWorkEnvironment<E>> c) {
		return _queue.drainTo(c);
	}

	public Iterator<RunnableInWorkEnvironment<E>> iterator() {
		return _queue.iterator();
	}

	@Override
	public int drainTo(Collection<? super RunnableInWorkEnvironment<E>> c, int maxElements) {
		return _queue.drainTo(c, maxElements);
	}

	public Object[] toArray() {
		return _queue.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return _queue.toArray(a);
	}

	public <T> T[] toArray(IntFunction<T[]> generator) {
		return _queue.toArray(generator);
	}

	public boolean containsAll(Collection<?> c) {
		return _queue.containsAll(c);
	}

	public boolean addAll(Collection<? extends RunnableInWorkEnvironment<E>> c) {
		return _queue.addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return _queue.removeAll(c);
	}

	@Override
	public boolean removeIf(Predicate<? super RunnableInWorkEnvironment<E>> filter) {
		return _queue.removeIf(filter);
	}

	public boolean retainAll(Collection<?> c) {
		return _queue.retainAll(c);
	}

	@Override
	public void clear() {
		_queue.clear();
	}

	public boolean equals(Object o) {
		return _queue.equals(o);
	}

	public int hashCode() {
		return _queue.hashCode();
	}

	public Spliterator<RunnableInWorkEnvironment<E>> spliterator() {
		return _queue.spliterator();
	}

	public Stream<RunnableInWorkEnvironment<E>> stream() {
		return _queue.stream();
	}

	public Stream<RunnableInWorkEnvironment<E>> parallelStream() {
		return _queue.parallelStream();
	}

}
