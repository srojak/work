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
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import srojak.concurrent.CommonConcurrentCollectionSize;
import srojak.concurrent.WorkEnvironment;
import srojak.concurrent.functional.RunnableInWorkEnvironment;

/**
 * @author Stephen
 *
 */
public interface BlockingWorkEnvQueueServer<E extends WorkEnvironment>
		extends CommonConcurrentCollectionSize {

	RunnableInWorkEnvironment<E> remove();

	RunnableInWorkEnvironment<E> poll();

	RunnableInWorkEnvironment<E> element();

	RunnableInWorkEnvironment<E> peek();

	RunnableInWorkEnvironment<E> take() throws InterruptedException;

	RunnableInWorkEnvironment<E> poll(long timeout, TimeUnit unit) throws InterruptedException;

	int drainTo(Collection<? super RunnableInWorkEnvironment<E>> c);

	int drainTo(Collection<? super RunnableInWorkEnvironment<E>> c, int maxElements);

	boolean removeIf(Predicate<? super RunnableInWorkEnvironment<E>> filter);

	void clear();

}