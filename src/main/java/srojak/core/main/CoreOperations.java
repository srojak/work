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

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import srojak.core.impl.CoreCentralStore;
import srojak.core.io.LogFileLocator;
import srojak.core.observe.CoreObservers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterSource;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.props.DebugProperties;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResult;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public class CoreOperations
		implements CoreObservers {
	private final ClassReflector _reflectApp;
	
	private static final CoreCentralStore _store = CoreCentralStore._instance;
	
	public static final ObservedActivity ACTIVITY_CREATE_LOG = new SingleActivity("create log file");
	
	public CoreOperations(ClassReflector reflectApp) {
		Objects.requireNonNull(reflectApp, "reflectApp");
		_reflectApp = reflectApp;
	}
	
	public CoreOperations(Class<?> classApp) {
		Objects.requireNonNull(classApp, "classApp");
		_reflectApp = new ClassReflector(classApp);
	}
	
	public CoreOperations(Object app) {
		Objects.requireNonNull(app, "app");
		_reflectApp = new ClassReflector(app);
	}
	
	public void initialize() {
		_store.initialize(_reflectApp);
	}
	
	public LogFileLocator locateImplicitLogFile(String strPrefix) {
		DebugProperties propsDebug = _store.getDebugProperties();
		if (strPrefix == null || strPrefix.isBlank()) {
			return new LogFileLocator(propsDebug);
		} else {
			return new LogFileLocator(propsDebug, strPrefix);
		}
	}

	public XResult openLogFile(LogFileLocator locatorLog) {
		Objects.requireNonNull(locatorLog, "locatorLog");
		XResult resultValid = locatorLog.validate();
		if (!resultValid.isValid()) {
			OBS_ERR.recordThrowable(ObsLevel.FATAL, resultValid.getActivity(), resultValid.getException(), false);
			return resultValid;
		}
		ClassReflector reflectApp = CoreCentralStore._instance.getAppClass();
		ObservationWriterSource source = new ObservationWriterLogFileSource(locatorLog);
		XResultOf<ObservationWriter> resultWriter = source.createFor(reflectApp.getReferencedClass());
		if (resultWriter.isValid()) {
			ObservationWriter writer = resultWriter.getResult();
			writer.setShowLocations(true);
			writer.setShowExceptionStackEnabled(true);
			CoreOutputs outputs = new CoreOutputs();
			outputs.startLogFile(writer, LocalDateTime.now());
			outputs.setLogWriter(writer);
		} else {
			OBS_ERR.recordThrowable(ObsLevel.FATAL, resultWriter.getActivity(), resultWriter.getException(), false);
		}
		return resultWriter;
	}
	
	public boolean tryOpenLogFile(LogFileLocator locatorLog) {
		XResult result = openLogFile(locatorLog);
		return result.isValid();
	}
	
	public boolean pauseFor(TimeUnit unit, long timeout) {
		Objects.requireNonNull(timeout, "timeout");
		try {
			unit.sleep(timeout);
		} catch (InterruptedException exc) {
			return false;
		}
		return true;
	}
}
