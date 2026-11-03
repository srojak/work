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
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import srojak.core.collections.QueueSender;

/**
 * @author Stephen
 *
 */
public class FrontGateConcurrentQueue<E> 
		extends ConcurrentLinkedQueue<E>
		implements QueueSender<E>, FrontGateConcurrentReceiver<E> {
	private final Semaphore _semFront;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1071679730901524310L;
	/**
	 * 
	 */
	public FrontGateConcurrentQueue(Semaphore semFront) {
		super();
		Objects.requireNonNull(semFront, "semFront");
		_semFront = semFront;
	}

	/**
	 * @param c
	 */
	public FrontGateConcurrentQueue(Semaphore semFront, Collection<? extends E> c) {
		super(c);
		Objects.requireNonNull(semFront, "semFront");
		_semFront = semFront;
	}
	
	public Semaphore frontGate() {
		return _semFront;
	}

	@Override
	public boolean add(E e) {
		// ConcurrentLinkedQueue.add calls offer
		boolean bResult = super.add(e);
		return bResult;
	}

	@Override
	public boolean offer(E e) {
		boolean bResult = super.offer(e);
		if (bResult) {
			_semFront.release();
		}
		return bResult;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

}
