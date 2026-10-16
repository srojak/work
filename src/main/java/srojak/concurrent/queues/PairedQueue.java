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

import java.util.concurrent.LinkedBlockingQueue;

import srojak.concurrent.PairedQueueClient;
import srojak.concurrent.PairedQueueServer;

/**
 * @author Stephen
 *
 */
public class PairedQueue<Q, R> 
		implements PairedQueueClient<Q, R>, PairedQueueServer<Q, R> {
	private final LinkedBlockingQueue<Q> _queueRequest;
	private final LinkedBlockingQueue<R> _queueReply;
	
	public PairedQueue(int boundRequests, int boundReplies) {
		if (boundRequests <= 0) {
			throw new IllegalArgumentException("boundRequests must be positive");
		}
		if (boundReplies <= 0) {
			throw new IllegalArgumentException("boundReplies must be positive");
		}
		_queueRequest = new LinkedBlockingQueue<Q>(boundRequests);
		_queueReply = new LinkedBlockingQueue<R>(boundReplies);
	}

	@Override
	public boolean isRequestQueueEmpty() {
		return _queueRequest.isEmpty();
	}

	@Override
	public boolean isReplyQueueEmpty() {
		return _queueReply.isEmpty();
	}

	@Override
	public int sizeRequestQueue() {
		return _queueRequest.size();
	}

	@Override
	public int sizeReplyQueue() {
		return _queueRequest.size();
	}

	@Override
	public Q pollRequest() {
		return _queueRequest.poll();
	}

	@Override
	public Q awaitRequest() 
			throws InterruptedException {
		return _queueRequest.take();
	}

	@Override
	public void enqueueReply(R reply) {
		_queueReply.add(reply);
	}

	@Override
	public void enqueueRequest(Q request) {
		_queueRequest.add(request);
	}

	@Override
	public R awaitReply() 
			throws InterruptedException {
		return _queueReply.take();
	}

}
