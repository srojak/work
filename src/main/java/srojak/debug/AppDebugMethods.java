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
package srojak.debug;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import srojak.core.io.LogFileLocator;
import srojak.core.kernel.Kernel;
import srojak.core.kernel.KernelLog;
import srojak.core.kernel.priv.KernelOutput;
import srojak.core.kernel.priv.KernelStore;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterSource;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.observe.sources.ObservationWriterLogFileSource;
import srojak.core.observe.writers.ObservationWriterUnicodeStream;
import srojak.core.props.DebugPropertyKeys;
import srojak.core.reflect.ClassReflector;
import srojak.core.result.XResult;
import srojak.core.result.XResultOf;
import srojak.core.result.XResultStatusCarrier;

/**
 * @author Stephen
 *
 */
public class AppDebugMethods
		implements DebugPropertyKeys {
	
	private static final ObservedActivity _activityCreateLog = new SingleActivity("create log file");
	private static final boolean _useSourceObject = true;
	
	public static void setAutoFlush(boolean bState) {
		// TODO: are we keeping this?
	}
	
	private static XResult createLogFile(LogFileLocator locLog) {
		XResultStatusCarrier result = new XResultStatusCarrier(_activityCreateLog);
		XResult resultValid = locLog.validate();
		if (!resultValid.isValid()) {
			result.copyFrom(resultValid);
			return result;
		}
		ClassReflector reflectApp = Kernel.getAppClass();
		KernelOutput outputKernel = KernelStore.OUTPUT;
		LocalDateTime dtNow = LocalDateTime.now();
		if (_useSourceObject) {
			ObservationWriterSource source = new ObservationWriterLogFileSource(locLog);
			XResultOf<ObservationWriter> resultWriter = source.createFor(reflectApp.getReferencedClass());
			if (resultWriter.isValid()) {
				ObservationWriter writer = resultWriter.getResult();
				outputKernel.setLogWriter(writer);
				result.setValid();
			} else {
				result.copyFrom(resultWriter);
			}
		} else {
			Path pathFile = locLog.formFile(dtNow);
			outputKernel.collector().write(ObsLevel.DEBUG, "will write to " + pathFile);
			ObservationWriterUnicodeStream writer = new ObservationWriterUnicodeStream();
			try {
				OutputStream output = Files.newOutputStream(pathFile);
				writer.assignOutputStream(output);
			} catch (IOException exc) {
				result.caughtThrowable(exc);
				try {
					writer.close();
				} catch (IOException e2) {
					
				}
				return result;
			}
			writer.setAutoFlush(true);
			KernelLog.startLogFile(writer, dtNow);
			outputKernel.setLogWriter(writer);
			Kernel.OBS_KERNEL.write(ObsLevel.INFO, "Created log file " + pathFile);
			result.setValid();
		}
		return result;
	}
	
	public static XResult tryCreateLogFile(Class<?> classApp) {
		LogFileLocator locLog = new LogFileLocator();
		return createLogFile(locLog);
	}
	
	public static XResult tryCreateLogFile(Class<?> classApp, String strPrefix) {
		LogFileLocator locLog = new LogFileLocator(strPrefix);
		return createLogFile(locLog);
	}
	
	public static XResult tryCreateLogFileIn(Class<?> classApp, String strPrefix, Path pathDir) {
		LogFileLocator locLog = new LogFileLocator(pathDir, strPrefix);
		return createLogFile(locLog);
	}
	
	public static XResult tryCreateLogFileIn(Class<?> classApp, Path pathDir) {
		LogFileLocator locLog = new LogFileLocator(pathDir);
		return createLogFile(locLog);
	}
}
