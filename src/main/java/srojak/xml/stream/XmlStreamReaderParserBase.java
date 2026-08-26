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

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import srojak.core.containers.SingletonContainer;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterNull;
import srojak.core.result.XResult;
import srojak.core.result.XResultStatusCarrier;
import srojak.xml.stream.impl.StreamParserStateBasicCtnr;
import srojak.xml.stream.impl.XmlParseMethods;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamReaderParserBase 
		implements XMLStreamConstants {
	private final StreamParserStateBasicCtnr _state;
	private final XmlParserOptions _options;
	private final SingletonContainer<XMLStreamReader> _reader;
	private ObservationWriter _writerObs;
	
	protected static final XmlStreamEventsDictionary DICT_EVENTS;
	
	static {
		DICT_EVENTS = new XmlStreamEventsDictionary();
	}

	/**
	 * 
	 */
	protected XmlStreamReaderParserBase() {
		_state = new StreamParserStateBasicCtnr();
		_options = new XmlParserOptions();
		_reader = new SingletonContainer<XMLStreamReader>();
		_writerObs = new ObservationWriterNull();
	}
	
	protected final XmlStreamParserState getParserState() {
		return _state;
	}
	
	public final XmlParserOptions getOptions() {
		return _options;
	}
	
	public final ObservationWriter getObservationWriter() {
		return _writerObs;
	}
	
	public final void setObservationWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writerObs = writer;
	}
		
	protected StreamElementAttribute findAttributeByName(StreamElementAttribute[] attributes,
			QName nameAttribute) {
		for (int index = 0; index < attributes.length; index++) {
			if (attributes[index].getName().equals(nameAttribute)) {
				return attributes[index];
			}
		}
		return null;
	}
	
	protected abstract void parseInit();
	
	protected void parseEndDocument() {
		
	}
	
	protected abstract void parseStartElement(QName nameElement, StreamElementAttribute[] attributes) 
			throws XMLStreamException ;
	
	protected abstract void parseEndElement(QName nameElement);
	
	protected void parseComment(String strText) {
		
	}
	
	protected void parseOther(int nEventType) {
		
	}
	
	protected String getElementText() 
			throws XMLStreamException {
		return _reader.get().getElementText();
	}
	
	public final void parse(XMLStreamReader reader) 
			throws XMLStreamException {
		Objects.requireNonNull(reader, "reader");
		_reader.set(reader);
		_state.start();
		_writerObs.write(ObsLevel.TRACE, "entering parse");
		parseInit();
			while (reader.hasNext()) {
				int nEvent = reader.next();
				_state.setCurrentLocation(reader.getLocation());
				ObservationCollector collector = _writerObs.createCollector(ObsLevel.DEBUG2);
				if (collector.isActive()) {
					collector.append("location ");
					collector.append(XmlStreamMethods.format(_state.getCurentLocation()));
					collector.append(" event ");
					collector.append(nEvent);
					collector.append(' ');
					collector.append(DICT_EVENTS.getNameForCode(nEvent));
					collector.commit();
				}
				QName nameCurrent;
				switch (nEvent) {
				case START_DOCUMENT:
					_writerObs.write(ObsLevel.DEBUG, "start document");
					break;
					
				case END_DOCUMENT:
					_state.clearAtElementStart();
					_writerObs.write(ObsLevel.DEBUG, "end document");
					parseEndDocument();
					break;
					
				case PROCESSING_INSTRUCTION:
					break;
					
				case START_ELEMENT:
					nameCurrent = reader.getName();
					{
						_state.startElement(nameCurrent);
						StreamElementAttribute[] attribs = XmlParseMethods.getAttributes(reader);
						_writerObs.buildAndWrite(ObsLevel.DEBUG, sb -> {
							sb.append("start element ");
							sb.append(nameCurrent);
							sb.append(", ");
							sb.append(attribs.length);
							sb.append(" attributes");
						});
						parseStartElement(nameCurrent, attribs);
					}
					break;
					
				case END_ELEMENT:
					nameCurrent = reader.getName();
					_state.endElement(nameCurrent);
					_writerObs.buildAndWrite(ObsLevel.DEBUG, sb -> {
						sb.append("end element ");
						sb.append(nameCurrent);
					});
					parseEndElement(nameCurrent);			
					break;
					
				case COMMENT:
					parseComment(reader.getText());
					break;
					
				case CHARACTERS:
				case CDATA:
				case SPACE:
					// don't do anything with these
					break;
					
				default:
					_state.clearAtElementStart();
					parseOther(nEvent);
					break;
				}
				_state.setPriorEventType(nEvent);
			}
		
		_reader.clear();
		_state.reset();
		_writerObs.write(ObsLevel.TRACE, "returning from parse");
	}
	
	public final XResult tryParse(XMLStreamReader reader) {
		XResultStatusCarrier result = new XResultStatusCarrier();
		try {
			parse(reader);
			result.setValid();
		} catch (XMLStreamException exc) {
			result.caughtException(exc);
		}
		return result;
		
	}
}
