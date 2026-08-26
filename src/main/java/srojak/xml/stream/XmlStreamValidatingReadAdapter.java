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
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import srojak.core.io.FileExistence;
import srojak.core.io.IOResultQualifiers;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.result.XResult;
import srojak.core.result.XResultInt;
import srojak.core.result.XResultIntCarrier;
import srojak.core.result.XResultStatusCarrier;
import srojak.xml.stream.errors.XmlStreamErrorEvent;
import srojak.xml.stream.errors.XmlStreamErrorHandler;
import srojak.xml.stream.errors.XmlStreamErrorListener;
import srojak.xml.stream.errors.XmlStreamParseErrorEntry;

/**
 * @author Stephen
 *
 */
public class XmlStreamValidatingReadAdapter
		implements IOResultQualifiers {
	private final Schema _schema;
	private final XmlStreamActionParserBase _parser;
	private final XmlStreamInputBuilder _builderStream;
	private final XmlStreamErrorHandler _handlerErrors;
	
	public XmlStreamValidatingReadAdapter(Schema schema, XmlStreamActionParserBase parser) {
		Objects.requireNonNull(schema, "schema");
		Objects.requireNonNull(parser, "parser");
		_schema = schema;
		_parser = parser;
		_builderStream = new XmlStreamInputBuilder();
		_handlerErrors = new XmlStreamErrorHandler();
		_handlerErrors.addStreamErrorListener(new XmlStreamErrorListener() {

			@Override
			public void receive(XmlStreamErrorEvent event) {
				XmlStreamParseErrorEntry entry = new XmlStreamParseErrorEntry(event.getLocation(),
						event.getSeverity(), event.getText());
				_parser.writeError(entry);
			}
			
		});
	}
	
	public XmlStreamErrorHandler getErrorHandler() {
		return _handlerErrors;
	}
	
	private void readCore(InputStream streamIn, XResultIntCarrier result) {
		Validator validator = _schema.newValidator();
		validator.setErrorHandler(_handlerErrors);
		try {
			XMLStreamReader readerBase = _builderStream.createStreamReader(streamIn);
			StreamReaderParsingDelegate reader = new StreamReaderParsingDelegate(_parser, readerBase);
			_parser.start(reader);
			validator.validate(new StAXSource(reader));
			_parser.completed();
			result.setResult(COMPLETED);
		} catch (XMLStreamException exc) {
			result.caughtException(exc);
		} catch (SAXException exc) {
			result.caughtException(exc);
		} catch (IOException exc) {
			result.caughtException(exc);
		}
	}
	
	public XResultInt readFrom(InputStream streamIn) {
		XResultIntCarrier result = new XResultIntCarrier();
		readCore(streamIn, result);
		return result;
	}
	
	public XResultInt openAndReadCore(Path pathFile, FileExistence exists, XResultIntCarrier result) {
		try (InputStream streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ)) {
			readCore(streamIn, result);
		} catch (NoSuchFileException exc) {
			if (exists.equals(FileExistence.MustExist)) {
				ObservationWriter writer = _parser.getObservationWriter();
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
	
	public XResultInt readFrom(Path pathFile, FileExistence exists) {
		Objects.requireNonNull(pathFile, "pathFile");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier();
		openAndReadCore(pathFile, exists, result);
		return result;
	}
	
	public XResultInt readFrom(String strPath, FileExistence exists) {
		Objects.requireNonNull(strPath, "strPath");
		Objects.requireNonNull(exists, "exists");
		XResultIntCarrier result = new XResultIntCarrier();
		Path pathFile = Path.of(strPath);
		openAndReadCore(pathFile, exists, result);
		return result;
	}
}
