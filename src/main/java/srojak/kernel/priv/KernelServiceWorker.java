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
package srojak.kernel.priv;

import srojak.concurrent.queues.BlockingRunnableQueueServer;
import srojak.core.logic.OnceFlagState;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;

/**
 * @author Stephen
 *
 */
public class KernelServiceWorker
		implements Runnable, CoreObservers {
	private final BlockingRunnableQueueServer _queue;
	
	private static final ObservedActivity ACTIVITY_SERVICE = new SingleActivity("serving request");
	
	KernelServiceWorker(BlockingRunnableQueueServer queueServer) {
		_queue = queueServer;
	}

	@Override
	public void run() {
		OnceFlagState fShutdown = KernelStore.isShutdown();
		while (!fShutdown.isSet()) {
			try {
				Runnable runnable = _queue.take();
				runnable.run();
			} catch (InterruptedException e) {
				// go around again
			} catch (Exception exc) {
				OBS_LOG.recordThrowable(ObsLevel.ALERT, ACTIVITY_SERVICE, exc, true);
			}
		}
		if (!fShutdown.isSet()) {
			OBS_LOG.record(ObsLevel.WARN, "service worker exiting");
		}
	}

}
