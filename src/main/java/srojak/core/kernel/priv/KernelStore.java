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
package srojak.core.kernel.priv;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import srojak.core.OnceFlag;
import srojak.core.observe.ObservationWriter;
import srojak.core.props.ConfigProperties;
import srojak.core.props.DebugProperties;
import srojak.core.props.DevEnvProperties;
import srojak.core.reflect.ClassReflector;

/**
 * @author Stephen
 *
 */
public class KernelStore {

	private static final OnceFlag _fInit;
	private static final Runtime _runtime;
	private static final LinkedList<Runnable> _queueShutdown;
	private static final ModuleLayer _layerBoot;
	private static final Map<String, ModuleLayer> _mapLayers;
	
	public static final DevEnvProperties _propsDevEnv;
	public static final ConfigProperties _propsConfig;
	public static final DebugProperties _propsDebug;
	
	private static Path _pathAppDir;
	private static ClassReflector _reflectorApp;
	
	public static final KernelOutput OUTPUT;
	
	static {
		_fInit = new OnceFlag();
		_runtime = Runtime.getRuntime();
		_queueShutdown = new LinkedList<Runnable>();
		_runtime.addShutdownHook(new Thread(KernelStore::onShutdown));
		_layerBoot = ModuleLayer.boot();
		_mapLayers = new HashMap<String, ModuleLayer>();
		_propsDevEnv = new DevEnvProperties();
		_propsConfig = new ConfigProperties();
		_propsDebug = new DebugProperties();
		
		OUTPUT = new KernelOutput();
	}
	
	public static void initialize(ClassReflector reflectApp) {
		Objects.requireNonNull(reflectApp, "reflectApp");
		if (!_fInit.getState()) {
			long memFree = _runtime.freeMemory();
			long memUsed = _runtime.totalMemory() - memFree;
			System.out.println(String.format("Memory: %,d used, %,d free", memUsed, memFree));
			_pathAppDir = Path.of(System.getProperty("user.dir"));
			_reflectorApp = reflectApp;
			
			_propsDevEnv.loadFromParent();
			
			_queueShutdown.add(KernelStore::onShutdownCloseLogWriter);
			
			_fInit.set();
		}
		if (!_propsConfig.wasLoaded()) {
			_propsConfig.loadFrom(_pathAppDir, ConfigProperties.PROPERTIES_FILE_NAME);
		}
		if (!_propsDebug.wasLoaded()) {
			_propsDebug.loadFrom(_pathAppDir, DebugProperties.PROPERTIES_FILE_NAME);
		}
	}
	
	private static void onShutdown() {	
		while (!_queueShutdown.isEmpty()) {
			Runnable action = _queueShutdown.pop();
			action.run();
		}
	}
	
	public static void addShutdownAction(Runnable action) {
		Objects.requireNonNull(action, "action");
		_queueShutdown.add(action);
	}
	
	public static ModuleLayer getBootModuleLayer() {
		return _layerBoot;
	}
	
	public static ModuleLayer getModuleLayer(String strKey) {
		return _mapLayers.get(strKey);
	}
	
	public static void addModuleLayer(String strKey, ModuleLayer layer) {
		Objects.requireNonNull(strKey, "strKey");
		Objects.requireNonNull(layer, "layer");
		_mapLayers.put(strKey, layer);
	}
	
	public static Path getAppDirPath() {
		if (_pathAppDir == null) {
			throw new IllegalStateException("not initialized");
		}
		return _pathAppDir;
	}
	
	public static ClassReflector getAppClass() {
		if (_reflectorApp == null) {
			throw new IllegalStateException("not initialized");
		}
		return _reflectorApp;
	}
	
	public static void writerInvalidated(ObservationWriter writer) {
		String strClassName = writer.getClass().getSimpleName();
		System.err.println(strClassName + " invalidated");
	}
	
	private static void onShutdownCloseLogWriter() {
		OUTPUT.onShutdown(_propsDebug);
	}
}
