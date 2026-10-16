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

import srojak.concurrent.CommonConcurrentCollectionSize;

/**
 * @author Stephen
 *
 */
public interface BlockingRunnableQueueServer
		extends CommonConcurrentCollectionSize {

	Runnable remove();

	Runnable poll();

	Runnable element();

	Runnable peek();

	Runnable take() throws InterruptedException;

	Runnable poll(long timeout, TimeUnit unit) throws InterruptedException;

	boolean remove(Object o);

	int drainTo(Collection<? super Runnable> c);

	int drainTo(Collection<? super Runnable> c, int maxElements);

	void clear();

}