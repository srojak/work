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

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @author Stephen
 *
 * see https://medium.com/@cloudfullstack/java-blockingqueue-vs-semaphore-complete-producer-consumer-tutorial-c6de375e3ac8
 */
public class SemaphoreManagedQueue<E> 
		implements ManagedQueue<E> {
	private final LinkedList<E> _queue;
	private final int _capacity;
	private final Semaphore _semEmpty;
	private final Semaphore _semFill;
	private final Semaphore _semMutex;
	
	public SemaphoreManagedQueue(int capacity) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("capacity must be positive");
		}
		_queue = new LinkedList<E>();
		_capacity = capacity;
		_semEmpty = new Semaphore(_capacity);
		_semFill = new Semaphore(0);
		_semMutex = new Semaphore(1);
	}
	
	@Override
	public boolean isEmpty() {
		return _semFill.availablePermits() == 0;
	}
	
	@Override
	public int size() {
		return _semFill.availablePermits();
	}
	
	private void enqueueInner(E item) throws InterruptedException {
		_semMutex.acquire();
		try {
			_queue.add(item);
		} finally {
			_semMutex.release();
		}
		_semFill.release();
	}
	
	@Override
	public void enqueue(E item) throws InterruptedException {
		_semEmpty.acquire();
		enqueueInner(item);
	}
	
	@Override
	public boolean tryEnqueue(E item) throws InterruptedException {
		if (_semEmpty.tryAcquire()) {
			enqueueInner(item);
		}
		return false;
	}
	@Override
	public E dequeue() throws InterruptedException {
		_semFill.acquire();
		E item;
		_semMutex.acquire();
		try {
			item = _queue.poll();
		} finally {
			_semMutex.release();
		}
		_semEmpty.release();
		return item;
	}
	

}
