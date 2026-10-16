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

import java.nio.file.Path;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import srojak.core.impl.CoreCentralStore;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.kernel.KernelProperties;
import srojak.kernel.KernelServiceException;
import srojak.kernel.services.lib.EndpointServiceProvider;
import srojak.kernel.services.lib.ProxyReflectionServiceProvider;
import srojak.kernel.services.lib.ReflectionServiceProvider;
import srojak.kernel.services.lib.ScheduleServiceProvider;
import srojak.kernel.services.lib.SequenceServiceProvider;

/**
 * @author Stephen
 *
 */
class KernelInitializer
		implements Runnable, CoreObservers {
	private final CyclicBarrier _barrier;
	private final Path _pathAppDir;
	
	KernelInitializer() {
		_barrier = new CyclicBarrier(2, () -> clearOfBarrier());
		_pathAppDir = CoreCentralStore._instance.getAppDirPath();		
	}
	
	private void clearOfBarrier() {
		OBS_LOG.record(ObsLevel.DETAIL, "cleared the init barrier");
	}
	
	private void fellThrough(String strThread, Exception exc) {
		OBS_LOG.record(ObsLevel.WARN, strThread + " fell through barrier");
	}

	void mainActions() {
		try {
			_barrier.await();
		} catch (InterruptedException | BrokenBarrierException exc) {
			fellThrough("main", exc);
		}
	}
	
	@Override
	public void run() {
		// these properties must be loaded first	
		KernelStore._propsKernel.loadFrom(_pathAppDir, KernelProperties.PROPERTIES_FILE_NAME);
		
		KernelStore._services.define(EndpointServiceProvider.class);
		KernelStore._services.define(ScheduleServiceProvider.class);
		KernelStore._services.define(SequenceServiceProvider.class);
		KernelStore._services.define(ReflectionServiceProvider.class);
		KernelStore._services.define(ProxyReflectionServiceProvider.class);
		
		ServiceGatherer gatherer = new ServiceGatherer();
		gatherer.gather(true);
		
		try {
			KernelStore._services.resolvePending();
		} catch (KernelServiceException exc) {
			CoreObservers.OBS_ERR.recordThrowable(ObsLevel.ERROR, ObservedActivity.INIT, exc, false);
			CoreObservers.OBS_ERR.record(ObsLevel.FATAL, "error in service " + exc.getServiceName());
			System.exit(3);
		}
		
		try {
			_barrier.await();
		} catch (InterruptedException | BrokenBarrierException exc) {
			fellThrough("worker", exc);
		}
		
	}
}
