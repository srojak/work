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
import java.util.Objects;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import srojak.core.io.IOResultQualifiers;
import srojak.core.observe.ObservationWriter;
import srojak.core.result.XResultIntCarrier;
import srojak.xml.stream.errors.XmlStreamErrorEvent;
import srojak.xml.stream.errors.XmlStreamErrorHandler;
import srojak.xml.stream.errors.XmlStreamErrorListener;
import srojak.xml.stream.errors.XmlStreamParseErrorEntry;

/**
 * @author Stephen
 *
 */
public class XmlStreamValidatingReadAdapter
		extends XmlStreamReadAdapterBase
		implements XmlStreamAdapter, IOResultQualifiers {
	private final Schema _schema;
	private final XmlStreamActionParserBase _parser;
	private final XmlStreamErrorHandler _handlerErrors;
	
	public XmlStreamValidatingReadAdapter(Schema schema, XmlStreamActionParserBase parser) {
		super();
		Objects.requireNonNull(schema, "schema");
		Objects.requireNonNull(parser, "parser");
		_schema = schema;
		_parser = parser;
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
	
	@Override
	protected ObservationWriter getObservationWriter() {
		return _parser.getObservationWriter();
	}
	
	public XmlStreamErrorHandler getErrorHandler() {
		return _handlerErrors;
	}
	
	@Override
	protected void readCore(InputStream streamIn, XResultIntCarrier result) {
		Validator validator = _schema.newValidator();
		validator.setErrorHandler(_handlerErrors);
		try {
			XMLStreamReader readerBase = createStreamReader(streamIn);
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
}
