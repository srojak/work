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
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import srojak.core.functional.IOSupplier;
import srojak.core.io.FileExistence;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservedActivity;
import srojak.core.observe.SourceLocation;
import srojak.core.observe.TraceLevel;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.xml.stream.errors.XmlStreamErrorHandler;
import srojak.xml.stream.factories.XmlStreamInputFactory;
import srojak.xml.stream.factories.XmlStreamParserFactory;
import srojak.xml.stream.parse.XmlStreamParser;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamContent2PassReaderBase<P extends XmlStreamParser> 
		extends XmlStreamContentAndSchemaReaderBase<P> {

	/**
	 * @param factory
	 */
	public XmlStreamContent2PassReaderBase(XmlStreamParserFactory<P> factory) {
		super(factory);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected XmlStreamAdapter createAdapter(XmlStreamInputFactory factoryInput, XResultIntCarrier resultCreate) {
		// check for schema
		if (_schema.isEmpty()) {
			XResultInt resultSchema = tryLoadSchema();
			resultCreate.copyFrom(resultSchema);
			if (!resultSchema.isValid()) {
				_collectObs.write(ObsLevel.ERROR, "failed to load schema");
				return null;
			}
		} else {
			resultCreate.setResult(NO_ACTION_TAKEN);
		}
		// TODO: determine how 2-pass read will work
		// should there be a 2-pass reader?
		return new XmlStreamReadAdapter(factoryInput, _parser);
	}
	
	private XResultInt validate(IOSupplier<InputStream> supplierStream) {
		XResultIntCarrier result = new XResultIntCarrier(ObservedActivity.VALIDATE);
		Validator validator = _schema.get().newValidator();
		validator.setErrorHandler(new XmlStreamErrorHandler());
		// TODO: route errors: see XmlStreamValidatingReadAdapter
		try (InputStream streamIn = supplierStream.get()) {
			validator.validate(new StreamSource(streamIn));
			result.setResult(COMPLETED);
		} catch (SAXException exc) {
			result.caughtException(exc);
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
	public XResultInt parseFromFile(XmlStreamInputFactory factoryInput, ObservedActivity activityRead, Path pathFile,
			FileExistence exists) {
		Objects.requireNonNull(factoryInput, "factoryInput");
		Objects.requireNonNull(activityRead, "activityRead");
		Objects.requireNonNull(pathFile, "pathFile");
		Objects.requireNonNull(exists, "exists");
		_collectObs.writeTraceEnter(TraceLevel.HIGH, this, "path = " + pathFile);
		XResultIntCarrier resultPrepare = new XResultIntCarrier(SourceLocation.here(), ACTIVITY_PREPARE_ADAPTER);
		XmlStreamAdapter adapter = createAdapter(factoryInput, resultPrepare);
		if (!resultPrepare.isValid()) {
			return resultPrepare;
		}
		XResultInt resultValidate = validate(() -> Files.newInputStream(pathFile, StandardOpenOption.READ));
		// TODO: act on above
		XResultInt result = adapter.openAndRead(activityRead, exists != FileExistence.MustExist, 
				() -> Files.newInputStream(pathFile, StandardOpenOption.READ));
		_collectObs.writeTraceReturnValue(TraceLevel.HIGH, this, result);
		return result;
	}

	@Override
	public XResultInt parseFromFile(XmlStreamInputFactory factoryInput, ObservedActivity activityRead, String strPath,
			FileExistence exists) {
		// TODO Auto-generated method stub
		return super.parseFromFile(factoryInput, activityRead, strPath, exists);
	}
}
