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
package srojak.core.main;

import java.nio.file.Path;
import java.util.Objects;

import srojak.core.impl.CoreCentralStore;
import srojak.core.props.ConfigProperties;
import srojak.core.props.DebugProperties;
import srojak.core.props.DevEnvProperties;
import srojak.core.reflect.ClassReflector;

/**
 * @author Stephen
 *
 */
public class CoreCentral {

	private static final CoreCentralStore _store = CoreCentralStore._instance;
	
	public static Path getAppDirPath() {
		return _store.getAppDirPath();
	}
	
	public static ClassReflector getAppClass() {
		return _store.getAppClass();
	}
	
	public static DevEnvProperties getDevEnvProperties() {
		return _store.getDevEnvProperties();
	}
	
	public static ConfigProperties getConfigProperties() {
		return _store.getConfigProperties();
	}
	
	public static DebugProperties getDebugProperties() {
		return _store.getDebugProperties();
	}
	
	public static void addOnShutdownAction(Runnable action) {
		Objects.requireNonNull(action, "action");
		_store.addShutdownAction(action);
	}
}
