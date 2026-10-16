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

import java.util.concurrent.LinkedBlockingQueue;

import srojak.concurrent.queues.BlockingRunnableQueue;
import srojak.concurrent.queues.BlockingRunnableQueueClient;
import srojak.core.containers.SingletonContainer;
import srojak.core.impl.CoreCentralStore;
import srojak.core.logic.OnceFlag;
import srojak.core.logic.OnceFlagState;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.safety.CycleIterationStop;
import srojak.kernel.KernelProperties;
import srojak.kernel.KernelServiceException;
import srojak.kernel.services.KernelService;

/**
 * @author Stephen
 *
 */
public class KernelStore
		implements CoreObservers {

	private static final OnceFlag _fInit;
	private static final OnceFlag _fShutdown;
	private static final CoreCentralStore _storeCore;
	private static final ThreadGroup _tgroupSystem;
	private static final ThreadGroup _tgroupKernel;
	private static final SingletonContainer<Thread> _threadKernel;
	
	public static final KernelProperties _propsKernel;
	protected static final KernelServiceProviderTable _services;
	protected static final BlockingRunnableQueue _queueService;
	
	private static final KernelLogManager LOG_MAN;
	
	static {
		_fInit = new OnceFlag();
		_fShutdown = new OnceFlag();
		_services = new KernelServiceProviderTable();
		_propsKernel = new KernelProperties();
		_storeCore = CoreCentralStore._instance;
		_queueService = new BlockingRunnableQueue(new LinkedBlockingQueue<Runnable>());
		_threadKernel = new SingletonContainer<Thread>();
		Thread thread = Thread.currentThread();
		ThreadGroup group = thread.getThreadGroup();
		CycleIterationStop stop = CycleIterationStop.create("thread group seach", 20);
		while (group.getParent() != null) {
			group = group.getParent();
			stop.increment();
		}
		_tgroupSystem = group;
		_tgroupKernel = new ThreadGroup(_tgroupSystem, "kernel");
		
		LOG_MAN = new KernelLogManager();
		LOG_MAN.initialize();
	}
	
	public static void clearOfBarrier() {
		CoreObservers.OBS_LOG.record(ObsLevel.DETAIL, "cleared the init barrier");
	}
	
	public static void initialize() {
		if (!_fInit.isSet()) {
			
			KernelServiceWorker worker = new KernelServiceWorker(_queueService);
			Thread threadWorker = new Thread(_tgroupKernel, worker, "serviceWorker");
			threadWorker.start();
			_threadKernel.set(threadWorker);
				
			_storeCore.addShutdownAction(KernelStore::shutdownKernel);
			
			KernelInitializer init = new KernelInitializer();
			_queueService.add(init);
			
			init.mainActions();
			
			_fInit.set();
		}
		
	}
	
	public static OnceFlagState isShutdown() {
		return _fShutdown;
	}
	
	public static BlockingRunnableQueueClient getRunnableQueue() {
		return _queueService;
	}
	
	public static KernelService getService(String strName) 
			throws KernelServiceException {
		return _services.get(strName);
	}
	
	public static <S extends KernelService> S getServiceOfType(String strName)
			throws KernelServiceException {
		KernelService service = KernelStore.getService(strName);
		@SuppressWarnings("unchecked")
		S styped = (S) service;
		return styped;
	}
	
	public static ThreadGroup getSystemThreadGroup() {
		return _tgroupSystem;
	}
	
	public static ThreadGroup getKernelThreadGroup() {
		return _tgroupKernel;
	}
	
	public static void writerInvalidated(ObservationWriter writer) {
		String strClassName = writer.getClass().getSimpleName();
		System.err.println(strClassName + " invalidated");
	}
	
	private static void shutdownKernel() {
		_fShutdown.set();
		_services.shutdownServices();
		_tgroupKernel.interrupt();
	}
}
