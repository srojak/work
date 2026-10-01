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
import java.time.LocalDateTime;
import java.util.Objects;

import srojak.core.EnvironmentCharacteristicException;
import srojak.core.kernel.priv.KernelOutput;
import srojak.core.kernel.priv.KernelStore;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterSource;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.props.DebugPropertyKeys;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResult;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;
import srojak.core.tools.EnvTool;

/**
 * @author Stephen
 *
 */
public class KernelLog
		implements DebugPropertyKeys {
	
	private static final ObservedActivity _activityFindLogDir = new SingleActivity("get log directory");
	private static final ObservedActivity _activityCreateLog = new SingleActivity("create log file");
	
	public static ObservationWriter getLogWriter() {
		return KernelStore.OUTPUT.getLogWriter();
	}
	
	public static XResultOf<String> getLogDir() {
		XResultCarrierOf<String> result = new XResultCarrierOf<String>(_activityFindLogDir);
		String strPath = KernelStore._propsDebug.getProperty(LOG_DIR);
		if (strPath == null) {
			result.caughtException(
					new EnvironmentCharacteristicException("property " 
							+ LOG_DIR + " is not defined"));
		} else {
			result.setResult(strPath);
		}
		return result;
	}
	
	public static void startLogFile(ObservationWriter writer, LocalDateTime dtNow) {
		ClassReflector _reflectorApp = KernelStore.getAppClass();
		writer.write(ObsLevel.NOTICE, SourceLocation.start(), 
				"log created for " + _reflectorApp.getName() + " on "
						+ KernelOutput.FORMAT_TIME_STAMP.format(dtNow));
		writer.write(ObsLevel.NOTICE, SourceLocation.start(), 
					"Java version " + EnvTool.getJavaVersion());
	}

	public static XResult tryCreateLogFile(ObservationWriterSource source) {
		Objects.requireNonNull(source, "source");
		XResultStatusCarrier result = new XResultStatusCarrier(_activityCreateLog);
		ClassReflector _reflectorApp = KernelStore.getAppClass();
		XResultOf<ObservationWriter> resultWriter = source.createFor(_reflectorApp.getClass());
		result.copyFrom(resultWriter);
		if (resultWriter.isValid()) {
			LocalDateTime dtNow = LocalDateTime.now();
			ObservationWriter writer = resultWriter.getResult();
			KernelOutput outputKernel = KernelStore.OUTPUT;
			outputKernel.setLogWriter(writer);
			startLogFile(writer, dtNow);
		}
		return result;
	}
	
}
