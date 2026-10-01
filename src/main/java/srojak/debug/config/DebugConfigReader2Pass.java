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
package srojak.debug.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import srojak.core.functional.IOSupplier;
import srojak.core.io.IONodeIdentifier;
import srojak.core.io.IONodeName;
import srojak.core.io.IONodeType;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.activity.SingleActivity;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;
import srojak.debug.DebugConfigSchema;
import srojak.xml.XmlSchemaTool;
import srojak.xml.stream.errors.XmlStreamErrorHandler;
import srojak.xml.stream.factories.XmlStreamInputFactory;

/**
 * @author Stephen
 *
 */
public class DebugConfigReader2Pass {
	private final XmlStreamInputFactory _factoryStream;
	private XmlStreamErrorHandler _handlerErrors;
	private DebugConfigParser _parser;
	
	private static Schema _schema = null;
	
	public DebugConfigReader2Pass() 
			throws SAXException {
		if (_schema == null) {
			DebugConfigSchema sourceSchema = new DebugConfigSchema();
			InputStream stream = sourceSchema.getResourceStream();
			XmlSchemaTool toolSchema = new XmlSchemaTool();
			_schema = toolSchema.readSchemaDirect(new StreamSource(stream));
		}
		_factoryStream = new XmlStreamInputFactory(true);
		_handlerErrors = new XmlStreamErrorHandler();
		_parser = new DebugConfigParser();
	}
	
	public ObservationCollector getObservationWriter() {
		return _parser.getObservationCollector();
	}
	
	public void setObservationWriter(ObservationCollector writer) {
		Objects.requireNonNull(writer, "writer");
		/*
		_parser.setObservationWriter(writer);
		*/
	}
	
	private void readInput(IONodeIdentifier idSource, IOSupplier<InputStream> supplier)
			throws IOException, XMLStreamException, SAXException {
		_parser.startReading(idSource);
		try (InputStream streamIn = supplier.get())
		{
			XMLStreamReader reader = _factoryStream.createStreamReader(DebugConfigNames.ACTIVITY_READ_DEBUG_CONFIG, streamIn);
			while (reader.hasNext()) {
				int nEvent = reader.next();
				_parser.interpret(nEvent);
			}
		} finally {
			_parser.endReading(idSource);
		}
	}
	
	public void readFrom(String strPath) 
			throws IOException, XMLStreamException, SAXException {
		Path pathConfig = Path.of(strPath);
		readFrom(pathConfig);
	}
	
	public void readFrom(Path pathFile) 
			throws IOException, XMLStreamException, SAXException {
		// TODO put exception handling here
		InputStream streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ);
		Validator validator = _schema.newValidator();
		validator.setErrorHandler(_handlerErrors);
		validator.validate(new StreamSource(streamIn));
		streamIn.close();
		IONodeIdentifier idSource = new IONodeName(IONodeType.FILE, pathFile.toAbsolutePath().toString());
		readInput(idSource, () -> Files.newInputStream(pathFile, StandardOpenOption.READ));
	}
	
	public XResult validateContent(Path pathFile) {
		XResultStatusCarrier result = new XResultStatusCarrier(new SingleActivity("read stream"));
		try (InputStream streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ)) {
			Validator validator = _schema.newValidator();
			validator.setErrorHandler(_handlerErrors);
			validator.validate(new StreamSource(streamIn));
			result.setValid();
		} catch (IOException exc) {
			result.caughtException(exc);
		} catch (SAXException exc) {
			result.caughtException(exc);
		}
		return result;
	}
	
	public XResult validateContent(String strPath) {
		Path pathConfig = Path.of(strPath);
		return validateContent(pathConfig);
	}
}
