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
package srojak.core.impl;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;

import srojak.core.field.SetOnce;
import srojak.core.logic.OnceFlag;
import srojak.core.main.CoreOutputs;
import srojak.core.props.ConfigProperties;
import srojak.core.props.DebugProperties;
import srojak.core.props.DevEnvProperties;
import srojak.core.reflect.ClassReflector;

/**
 * @author Stephen
 *
 */
public class CoreCentralStore {
	private final Path _pathAppDir;
	private final SetOnce<ClassReflector> _reflectorApp;
	private final Runtime _runtime;
	private final LinkedList<Runnable> _queueShutdown;
	private final CoreOutputs _outputs;
	
	private final DevEnvProperties _propsDevEnv;
	private final ConfigProperties _propsConfig;
	private final DebugProperties _propsDebug;

	public static final CoreCentralStore _instance;
	
	private static final OnceFlag _fInit;
	
	static {
		_fInit = new OnceFlag();
		_instance = new CoreCentralStore();
	}
	
	private CoreCentralStore() {
		_pathAppDir = Path.of(System.getProperty("user.dir"));
		_reflectorApp = new SetOnce<ClassReflector>(SetOnce.DEFAULT);
		_runtime = Runtime.getRuntime();
		_queueShutdown = new LinkedList<Runnable>();
		_runtime.addShutdownHook(new Thread(CoreCentralStore::onShutdown));
		_propsDevEnv = new DevEnvProperties();
		_propsConfig = new ConfigProperties();
		_propsDebug = new DebugProperties();
		
		_propsDevEnv.loadFromParent();
		
		// initially build the log writer over _writerErr
		_outputs = new CoreOutputs();

	}
	
	private static void onShutdown() {	
		while (!_instance._queueShutdown.isEmpty()) {
			Runnable action = _instance._queueShutdown.pop();
			action.run();
		}
		// the last thing to do is to revert the log writer
		_instance._outputs.resetLogWriter();
	}
	
	public void initialize(ClassReflector reflectApp) {
		if (_reflectorApp.isEmpty()) {
			_reflectorApp.set(reflectApp);
		}

		if (!_fInit.isSet()) {		
			
			_fInit.set();
		}
		
		if (!_propsConfig.wasLoaded()) {
			_propsConfig.loadFrom(_pathAppDir, ConfigProperties.PROPERTIES_FILE_NAME);
		}
		if (!_propsDebug.wasLoaded()) {
			_propsDebug.loadFrom(_pathAppDir, DebugProperties.PROPERTIES_FILE_NAME);
		}
		
		long memFree = _runtime.freeMemory();
		long memUsed = _runtime.totalMemory() - memFree;
	
		System.out.println(String.format("Memory: %,d used, %,d free", memUsed, memFree));
	}
	
	public CoreOutputs getOutputs() {
		return _outputs;
	}
	
	public Path getAppDirPath() {
		return _pathAppDir;
	}
	
	public ClassReflector getAppClass() {
		if (_reflectorApp.isEmpty()) {
			throw new IllegalStateException("not initialized");
		}
		return _reflectorApp.get();
	}
	
	public DevEnvProperties getDevEnvProperties() {
		return _propsDevEnv;
	}
	
	public ConfigProperties getConfigProperties() {
		return _propsConfig;
	}
	
	public DebugProperties getDebugProperties() {
		return _propsDebug;
	}
	
	public void addShutdownAction(Runnable action) {
		Objects.requireNonNull(action, "action");
		_queueShutdown.add(action);
	}
}
