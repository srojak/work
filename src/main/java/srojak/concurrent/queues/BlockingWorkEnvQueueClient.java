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

import java.util.concurrent.TimeUnit;

import srojak.concurrent.CommonConcurrentCollectionSize;
import srojak.concurrent.WorkEnvironment;
import srojak.concurrent.functional.RunnableInWorkEnvironment;

/**
 * @author Stephen
 *
 */
public interface BlockingWorkEnvQueueClient<E extends WorkEnvironment>
		extends CommonConcurrentCollectionSize {

	boolean add(RunnableInWorkEnvironment<E> e);

	boolean offer(RunnableInWorkEnvironment<E> e);

	void put(RunnableInWorkEnvironment<E> e) throws InterruptedException;

	boolean offer(RunnableInWorkEnvironment<E> e, long timeout, TimeUnit unit) throws InterruptedException;
}