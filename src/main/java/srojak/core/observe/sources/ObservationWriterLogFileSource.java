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
package srojak.core.observe.sources;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Objects;

import srojak.core.io.LogFileLocator;
import srojak.core.kernel.Kernel;
import srojak.core.kernel.KernelLog;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.writers.ObservationWriterUnicodeStream;
import srojak.core.result.XResult;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public class ObservationWriterLogFileSource
		extends ObservationWriterNamedFileSourceBase {
	private final LogFileLocator _locator;
	private boolean _bAppend;

	/**
	 * @param pathFile
	 * @param optionOpen
	 */
	public ObservationWriterLogFileSource(LogFileLocator locator) {
		super();
		Objects.requireNonNull(locator, "locator");
		_locator = locator;
		_bAppend = false;
	}
	
	public void setAppend() {
		_bAppend = true;
	}

	@Override
	public XResultOf<ObservationWriter> createFor(Class<?> classApp) {
		Objects.requireNonNull(classApp, "classApp");
		XResultCarrierOf<ObservationWriter> result
				= new XResultCarrierOf<ObservationWriter>(ACTIVITY_OPEN_WRITE);
		XResult resultValid = _locator.validate();
		if (!resultValid.isValid()) {
			result.copyFrom(resultValid);
			return result;
		}
		_listOptions.add(StandardOpenOption.CREATE);
		if (_bAppend) {
			_listOptions.add(StandardOpenOption.APPEND);
		}
		LocalDateTime dtNow = LocalDateTime.now();
		Path pathFile = _locator.formFile(dtNow);
		try {
			OutputStream stream = open(pathFile);
			ObservationWriterUnicodeStream writer = new ObservationWriterUnicodeStream();
			writer.assignOutputStream(stream);
			writer.setAutoFlush(true);
			KernelLog.startLogFile(writer, dtNow);
			result.setResult(writer);
			Kernel.OBS_KERNEL.writeNoLocation(ObsLevel.INFO, "Created log file " + pathFile);
		} catch (IOException exc) {
			result.caughtException(exc);
		}
		return result;
	}
}
