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
import java.util.Objects;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.writers.ObservationWriterUnicodeStream;
import srojak.core.result.XResultCarrierOf;
import srojak.core.result.XResultOf;

/**
 * @author Stephen
 *
 */
public class ObservationWriterLogFileSource
		extends ObservationWriterNamedFileSourceBase {
	private final Path _pathFile;
	private boolean _bAppend;

	/**
	 * @param pathFile
	 * @param optionOpen
	 */
	public ObservationWriterLogFileSource(Path pathFile) {
		super();
		Objects.requireNonNull(pathFile, "pathFile");
		_pathFile = pathFile;
		_bAppend = false;
	}
	
	public void setAppend() {
		_bAppend = true;
	}

	@Override
	public XResultOf<ObservationWriter> createFor(Object objApp) {
		Objects.requireNonNull(objApp, "objApp");
		XResultCarrierOf<ObservationWriter> result
				= new XResultCarrierOf<ObservationWriter>(ACTIVITY_OPEN_WRITE);
		_listOptions.add(StandardOpenOption.CREATE);
		if (_bAppend) {
			_listOptions.add(StandardOpenOption.APPEND);
		}
		try {
			OutputStream stream = open(_pathFile);
			ObservationWriterUnicodeStream writer = new ObservationWriterUnicodeStream();
			writer.assignOutputStream(stream);
			writer.write(ObsLevel.NOTICE, SourceLocation.start(), "log created for " + objApp.getClass().getName());
			result.setResult(writer);
		} catch (IOException exc) {
			result.caughtException(exc);
		}
		return result;
	}
}
