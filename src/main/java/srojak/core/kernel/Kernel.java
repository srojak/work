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
package srojak.core.kernel;

import java.nio.file.Path;
import java.util.Objects;

import srojak.core.Announcer;
import srojak.core.kernel.priv.KernelStore;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.props.ConfigProperties;
import srojak.core.props.DebugProperties;
import srojak.core.props.DevEnvProperties;
import srojak.core.reflect.ClassReflector;

/**
 * @author Stephen
 *
 */
public class Kernel {

	public static final ObservationCollector OBS_KERNEL = KernelStore.OUTPUT.collector();
	
	public static void initialize(ClassReflector reflectApp) {
		Objects.requireNonNull(reflectApp, "reflectApp");
		KernelStore.initialize(reflectApp);
	}
	
	public static void initialize(Class<?> classApp) {
		Objects.requireNonNull(classApp, "classApp");
		KernelStore.initialize(new ClassReflector(classApp));
	}
	
	public static Path getAppDirPath() {
		return KernelStore.getAppDirPath();
	}
	
	public static ClassReflector getAppClass() {
		return KernelStore.getAppClass();
	}
	
	public static Announcer getAnnouncer() {
		return KernelStore.OUTPUT.announcer();
	}
	
	public static DevEnvProperties getDevEnvProperties() {
		return KernelStore._propsDevEnv;
	}
	
	public static ConfigProperties getConfigProperties() {
		return KernelStore._propsConfig;
	}
	
	public static DebugProperties getDebugProperties() {
		return KernelStore._propsDebug;
	}
	
	public static ObsLevel getObserverHighObsLevel() {
		return KernelStore.OUTPUT.getHighObsLevel();
	}
	
	public static void setObserverHighObsLevel(ObsLevel level) {
		KernelStore.OUTPUT.setHighObsLevel(level);
	}
	
	public void registerObservationWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		writer.addLifeCycleListener(new KernelWriterListener());
	}
	
	public static ModuleLayer getBootModuleLayer() {
		return KernelStore.getBootModuleLayer();
	}
	
	public static ModuleLayer getModuleLayer(String strKey) {
		return KernelStore.getModuleLayer(strKey);
	}
}
