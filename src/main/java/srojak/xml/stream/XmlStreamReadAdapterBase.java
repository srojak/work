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
package srojak.xml.stream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import srojak.core.io.FileExistence;
import srojak.core.io.IOResultQualifiers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamReadAdapterBase 
		implements IOResultQualifiers, XmlStreamAdapter {
	private final XmlStreamInputBuilder _builderStream;
	
	public XmlStreamReadAdapterBase() {
		_builderStream = new XmlStreamInputBuilder();
	}
	
	protected abstract ObservationWriter getObservationWriter();
	
	protected XMLStreamReader createStreamReader(InputStream streamIn) 
			throws XMLStreamException {
		return _builderStream.createStreamReader(streamIn);
	}
	
	protected abstract void readCore(InputStream streamIn, XResultIntCarrier result);
	
	@Override
	public XResultInt readStream(InputStream streamIn) {
		XResultIntCarrier result = new XResultIntCarrier();
		readCore(streamIn, result);
		return result;
	}

	protected XResultInt openAndReadCore(Path pathFile, FileExistence exists, XResultIntCarrier result) {
		try (InputStream streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ)) {
			readCore(streamIn, result);
		} catch (NoSuchFileException exc) {
			if (exists.equals(FileExistence.MustExist)) {
				ObservationWriter writer = getObservationWriter();
				writer.write(ObsLevel.ERROR, pathFile.getFileName() + " does not exist");
				result.caughtException(exc);
			} else {
				result.setResult(NO_FILE_TO_READ);
				return result;
			}
		} catch (IOException exc) {
			if (result.isValid()) {
				// the exception must have been thrown on close
				result.setResult(EXCEPT_ON_CLOSE);
			} else {
				result.caughtException(exc);
			}
		}
		return result;
	}
	

	@Override
	public XResultInt readFrom(Path pathFile, FileExistence exists) {
		Objects.requireNonNull(pathFile, "pathFile");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier();
		openAndReadCore(pathFile, exists, result);
		return result;
	}

	@Override
	public XResultInt readFrom(String strPath, FileExistence exists) {
		Objects.requireNonNull(strPath, "strPath");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier();
		Path pathFile = Path.of(strPath);
		openAndReadCore(pathFile, exists, result);
		return result;
	}

}
