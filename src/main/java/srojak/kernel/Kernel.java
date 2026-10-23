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
package srojak.kernel;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.Objects;

import srojak.core.Announcer;
import srojak.core.impl.CoreCentralStore;
import srojak.core.main.CoreCollectors;
import srojak.core.main.CoreOperations;
import srojak.core.modules.ModuleName;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.props.DebugProperties;
import srojak.core.props.DevEnvProperties;
import srojak.core.reflect.ClassReflector;
import srojak.core.reflect.Reflectory;
import srojak.kernel.priv.KernelReflectory;
import srojak.kernel.priv.KernelStore;
import srojak.kernel.services.KernelService;

/**
 * @author Stephen
 *
 */
public class Kernel {
	
	private static final CoreCentralStore _store = CoreCentralStore._instance;
	private static final ObservationCollector OBS_LOG = CoreObservers.OBS_LOG;
	
	public static final ObservedActivity ACTIVITY_GET_REFL = new SingleActivity("get core reflectory");
	public static final ObservedActivity ACTIVITY_GET_SERVICE = new SingleActivity("get kernel service");
	
	public static void initialize() {
		KernelStore.initialize();
	}
	
	@Deprecated
	public static void initialize(ClassReflector reflectApp) {
		Objects.requireNonNull(reflectApp, "reflectApp");	
		CoreOperations start = new CoreOperations(reflectApp);
		start.initialize();
		KernelStore.initialize();
	}
	
	public static Announcer getAnnouncer() {
		// TODO: should the kernel have its own announcer?
		CoreCollectors outputs = new CoreCollectors();
		return outputs.announcer();
	}
	
	public static void registerObservationWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		writer.addLifeCycleListener(new KernelWriterListener());
	}
	
	public static <S extends KernelService> S getServiceOfType(String strName) 
			throws KernelServiceException {
		KernelService service = KernelStore.getService(strName);
		@SuppressWarnings("unchecked")
		S styped = (S) service;
		return styped;
	}
	
	public static Reflectory getCoreReflectoryFor(MethodHandles.Lookup luOriginator)
			throws IllegalAccessException {
		Objects.requireNonNull(luOriginator, "luOriginator");
		Class<?> classOrigin = luOriginator.lookupClass();
		OBS_LOG.record(ObsLevel.INFO, "class " + classOrigin.getName() + " requested core reflectory");
		// this is where an access decision would be made
		return new KernelReflectory();
	}
	
	public static void shutdown(int exitcode) {
		System.exit(exitcode);
	}
}
