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

import java.util.Objects;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterLevelFilterPrintStream;

/**
 * @author Stephen
 *
 */
public class XmlStreamErrorHandler 
		implements ErrorHandler {
	private ObservationWriter _writer;
	
	public XmlStreamErrorHandler() {
		_writer = new ObservationWriterLevelFilterPrintStream(System.err);
	}
	
	public ObservationWriter getWriter() {
		return _writer;
	}
	
	public void setWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writer = writer;
	}
	
	private static String createMessage(SAXParseException exc) {
		return String.format("at line %d, column %d: %s",
				exc.getLineNumber(), exc.getColumnNumber(), exc.getMessage());
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		_writer.write(ObsLevel.WARN, createMessage(exception));
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		_writer.write(ObsLevel.ERROR, createMessage(exception));
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		_writer.write(ObsLevel.ALERT, createMessage(exception));
	}

}
