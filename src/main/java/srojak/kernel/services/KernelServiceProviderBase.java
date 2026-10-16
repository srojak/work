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
package srojak.kernel.services;

import java.util.Objects;

import srojak.concurrent.queues.BlockingRunnableQueueClient;
import srojak.core.InvalidClassDefinitionException;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.reflect.ClassReflector;
import srojak.kernel.KernelCentralStore;
import srojak.kernel.KernelProperties;
import srojak.kernel.KernelServiceException;
import srojak.kernel.priv.KernelStore;

/**
 * @author Stephen
 *
 */
public abstract class KernelServiceProviderBase 
		implements KernelServiceInternal {
	private final AppKernelService _annotService;
	private final FlagsInt _flags;
	
	public static final int FLAGS_VERBOSE = 0x1;
	
	protected KernelServiceProviderBase() {
		ClassReflector refClass = new ClassReflector(this);	
		_annotService = refClass.getAnnotation(AppKernelService.class);
		if (_annotService == null) {
			throw new InvalidClassDefinitionException(refClass.getReferencedClass(),
					"not a service provider");
		}
		_flags = new FlagsInt();
		KernelProperties propsKernel = KernelStore._propsKernel;
		if (propsKernel.evalBooleanProperty(KernelProperties.formKeyForServiceName(_annotService.name(), "verbose"), false)) {
			_flags.set(FLAGS_VERBOSE);
		}
	}
	
	protected void submitRequest(Runnable request) {
		Objects.requireNonNull(request, "request");
		BlockingRunnableQueueClient client = KernelStore.getRunnableQueue();
		client.add(request);
	}

	@Override
	public final String name() {
			return _annotService.name();
	}

	@Override
	public void initialize(KernelCentralStore store)
			throws KernelServiceException {
		// base class method does nothing
		
	}

	@Override
	public void shutdown() {
		// base class method does nothing
		
	}

	@Override
	public final FlagsIntTest flags() {
		return _flags;
	}

	@Override
	public final boolean isVerbose() {
		return _flags.test(FLAGS_VERBOSE);
	}

	@Override
	public final void setVerbose(boolean bState) {
		_flags.apply(bState, FLAGS_VERBOSE);
	}

}
