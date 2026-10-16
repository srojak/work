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

import java.time.LocalDateTime;
import java.util.Objects;

import srojak.core.EnvironmentCharacteristicException;
import srojak.core.main.CoreCentral;
import srojak.core.main.CoreOutputs;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterSource;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.props.DebugProperties;
import srojak.core.props.DebugPropertyKeys;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResult;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;
import srojak.core.tools.EnvTool;
import srojak.kernel.priv.KernelLogManager;
import srojak.kernel.priv.KernelStore;

/**
 * @author Stephen
 *
 */
@Deprecated(forRemoval = true)
public class KernelLog
		implements DebugPropertyKeys {
	
	public static final ObservationCollector OBS_LOG;
	
	static {
		CoreOutputs outputs = new CoreOutputs();
		OBS_LOG = outputs.log();
	}
	
	private static final ObservedActivity _activityFindLogDir = new SingleActivity("get log directory");
	private static final ObservedActivity _activityCreateLog = new SingleActivity("create log file");
	
	public static ObservationCollector getLogCollector() {
		return OBS_LOG;
	}
	
	public static ObservationWriter getLogWriter() {
		CoreOutputs outputs = new CoreOutputs();
		return outputs.getLogWriter();
	}
	
	public static XResultOf<String> getLogDir() {
		XResultCarrierOf<String> result = new XResultCarrierOf<String>(_activityFindLogDir);
		DebugProperties propsDebug = CoreCentral.getDebugProperties();
		String strPath = propsDebug.getProperty(LOG_DIR);
		if (strPath == null) {
			result.caughtException(
					new EnvironmentCharacteristicException("property " 
							+ LOG_DIR + " is not defined"));
		} else {
			result.setResult(strPath);
		}
		return result;
	}

	public static XResult tryCreateLogFile(ObservationWriterSource source) {
		Objects.requireNonNull(source, "source");
		XResultStatusCarrier result = new XResultStatusCarrier(_activityCreateLog);
		ClassReflector _reflectorApp = CoreCentral.getAppClass();
		XResultOf<ObservationWriter> resultWriter = source.createFor(_reflectorApp.getClass());
		result.copyFrom(resultWriter);
		if (resultWriter.isValid()) {
			LocalDateTime dtNow = LocalDateTime.now();
			ObservationWriter writer = resultWriter.getResult();
			CoreOutputs outputs = new CoreOutputs();
			outputs.startLogFile(writer, dtNow);
			outputs.setLogWriter(writer);
		}
		return result;
	}
	
}
