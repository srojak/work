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
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import srojak.core.observe.ObservationWriter;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;
import srojak.debug.DebugConfigSchema;
import srojak.debug.impl.DebugNexusCore;
import srojak.xml.XmlSchemaTool;
import srojak.xml.stream.XmlParserOptions;
import srojak.xml.stream.XmlStreamInputBuilder;
import srojak.xml.stream.errors.XmlStreamErrorHandler;

/**
 * @author Stephen
 *
 */
public class DebugConfigReader2Pass {
	private final XmlStreamInputBuilder _builderStream;
	private XmlStreamErrorHandler _handlerErrors;
	private DebugConfigParserV1 _parser;
	
	private static Schema _schema = null;
	
	public DebugConfigReader2Pass() 
			throws SAXException {
		if (_schema == null) {
			DebugConfigSchema sourceSchema = new DebugConfigSchema();
			InputStream stream = sourceSchema.getResource();
			XmlSchemaTool toolSchema = new XmlSchemaTool();
			_schema = toolSchema.readSchemaDirect(new StreamSource(stream));
		}
		_builderStream = new XmlStreamInputBuilder();
		_handlerErrors = new XmlStreamErrorHandler();
		_parser = new DebugConfigParserV1(_builderStream);
	}
	
	public XmlParserOptions getParserOptions() {
		return _parser.getOptions();
	}
	
	public ObservationWriter getObservationWriter() {
		return _parser.getObservationWriter();
	}
	
	public void setObservationWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_parser.setObservationWriter(writer);
	}
	
	public void readFrom(String strPath) 
			throws IOException, XMLStreamException, SAXException {
		Path pathConfig = Path.of(strPath);
		readFrom(pathConfig);
	}
	
	public void readFrom(Path pathFile) 
			throws IOException, XMLStreamException, SAXException {
		InputStream streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ);
		Validator validator = _schema.newValidator();
		validator.setErrorHandler(_handlerErrors);
		validator.validate(new StreamSource(streamIn));
		streamIn.close();
		DebugNexusCore.startConfigFile(pathFile);
		streamIn = Files.newInputStream(pathFile, StandardOpenOption.READ);
		_parser.parse(streamIn);
		DebugNexusCore.endConfigFile(pathFile);
	}
	
	public XResult validateContent(Path pathFile) {
		XResultStatusCarrier result = new XResultStatusCarrier();
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
