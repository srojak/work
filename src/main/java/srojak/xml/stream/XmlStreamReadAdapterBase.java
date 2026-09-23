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

import srojak.core.functional.IOSupplier;
import srojak.core.io.FileExistence;
import srojak.core.io.IOResultQualifiers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.xml.stream.factories.XmlStreamInputFactory;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamReadAdapterBase 
		implements IOResultQualifiers, XmlStreamAdapter {
	private final XmlStreamInputFactory _factoryInput;

	protected static final ObservedActivity ACTIVITY_READ_STREAM = new SingleActivity("read stream");
	
	public XmlStreamReadAdapterBase(XmlStreamInputFactory factoryInput) {
		Objects.requireNonNull(factoryInput, "factoryInput");		
		_factoryInput = factoryInput;
	}
	
	protected abstract ObservationCollector getObservationCollector();
	
	protected XMLStreamReader createStreamReader(ObservedActivity activity, InputStream streamIn) 
			throws XMLStreamException {
		return _factoryInput.createStreamReader(activity, streamIn);
	}
	
	protected abstract void readCore(InputStream streamIn, XResultIntCarrier result);
	
	@Deprecated
	public XResultInt readStream(ObservedActivity activity, InputStream streamIn) {
		Objects.requireNonNull(activity, "activity");
		XResultIntCarrier result = new XResultIntCarrier(activity);
		readCore(streamIn, result);
		return result;
	}
	
	@Override
	public XResultInt openAndRead(ObservedActivity activity, boolean bPermissive, IOSupplier<InputStream> supplierStream) {
		Objects.requireNonNull(activity, "activity");
		Objects.requireNonNull(supplierStream, "supplierStream");
		XResultIntCarrier result = new XResultIntCarrier(activity);
		try (InputStream streamIn = supplierStream.get()) {
			readCore(streamIn, result);
		} catch (NoSuchFileException exc) {
			if (bPermissive) {
				// for example, a file that may not exist
				result.setResult(NO_FILE_TO_READ);
				return result;
			} else {
				result.caughtException(exc);
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

	protected XResultInt openAndReadCore(Path pathFile, FileExistence exists, XResultIntCarrier result) {
		try (InputStream streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ)) {
			readCore(streamIn, result);
		} catch (NoSuchFileException exc) {
			if (exists.equals(FileExistence.MustExist)) {
				ObservationCollector writer = getObservationCollector();
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
	

	@Deprecated
	public XResultInt readFrom(ObservedActivity activity, Path pathFile, FileExistence exists) {
		Objects.requireNonNull(activity, "activity");
		Objects.requireNonNull(pathFile, "pathFile");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier(activity);
		openAndReadCore(pathFile, exists, result);
		return result;
	}

	@Deprecated
	public XResultInt readFrom(ObservedActivity activity, String strPath, FileExistence exists) {
		Objects.requireNonNull(activity, "activity");
		Objects.requireNonNull(strPath, "strPath");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier(activity);
		Path pathFile = Path.of(strPath);
		openAndReadCore(pathFile, exists, result);
		return result;
	}

}
