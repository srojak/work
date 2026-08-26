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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import srojak.core.containers.SingletonContainer;
import srojak.core.data.DataErrorSeverity;
import srojak.core.events.ObjectPropertyChangeEvent;
import srojak.core.events.ObjectPropertyChangeListener;
import srojak.core.observe.HasSingleObservationWriter;
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterNull;
import srojak.core.tools.StringMethods;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;
import srojak.xml.stream.errors.XmlStreamParseErrorEntry;
import srojak.xml.stream.impl.StreamParserStateBasicCtnr;
import srojak.xml.stream.impl.XmlParseMethods;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamActionParserBase 
		implements XMLStreamConstants, HasSingleObservationWriter {
	private final StreamParserStateBasicCtnr _state;
	private final XmlParserOptions _options;
	private final SingletonContainer<XMLStreamReader> _reader;
	private final XmlPendingTextCollector _collectText;
	private final LinkedList<XmlStreamParseErrorDescr> _listErrors;
	private ObservationWriter _writerObs;
	
	protected static final XmlStreamEventsDictionary DICT_EVENTS;
	
	static {
		DICT_EVENTS = new XmlStreamEventsDictionary();
	}

	protected XmlStreamActionParserBase() {
		_state = new StreamParserStateBasicCtnr();
		_options = new XmlParserOptions();
		_reader = new SingletonContainer<XMLStreamReader>();
		_collectText = new XmlPendingTextCollector();
		_listErrors = new LinkedList<XmlStreamParseErrorDescr>();
		_writerObs = new ObservationWriterNull();
		_options.addObjectPropertyChangeListener(new ObjectPropertyChangeListener() {

			@Override
			public void propertyChanged(ObjectPropertyChangeEvent event) {
				if (event.isPropertyEqual(XmlParserOptions.PROPERTY_IGNORE_WS)) {
					Boolean b = (Boolean) event.getNewValue();
					_collectText.setIgnoreInitialWhiteSpace(b.booleanValue());
				}
				
			}
			
		});
	}
	
	protected final XmlStreamParserState getParserState() {
		return _state;
	}
	
	public final XmlParserOptions getOptions() {
		return _options;
	}
	
	public final boolean hasParseErrors() {
		return !_listErrors.isEmpty();
	}
	
	public final List<XmlStreamParseErrorDescr> getParseErrors() {
		return List.copyOf(_listErrors);
	}
	
	final void writeError(XmlStreamParseErrorDescr error) {
		Objects.requireNonNull(error, "error");
		_listErrors.add(error);
	}
	
	@Override
	public final ObservationWriter getObservationWriter() {
		return _writerObs;
	}
	
	@Override
	public final void setObservationWriter(ObservationWriter writer) {
		Objects.requireNonNull(writer, "writer");
		_writerObs = writer;
	}
	
	private String gatherElementText(QName nameCurrent) {
		// TODO: filter full text
		return _collectText.getContent();
	}
	
	protected void recordElementParseError(Location location, QName nameElement, DataErrorSeverity severity, String strText) {
		Objects.requireNonNull(location, "location");
		Objects.requireNonNull(nameElement, "nameElement");
		Objects.requireNonNull(severity, "severity");
		Objects.requireNonNull(strText, "strText");
		XmlStreamParseErrorEntry error = new XmlStreamParseErrorEntry(location,
				nameElement, severity, strText);
		_listErrors.add(error);
	}

	protected abstract void parseInit();

	protected void parseEndDocument() {

	}

	protected abstract void parseStartElement(QName nameElement, StreamElementAttributeSet attribs) 
			throws XMLStreamException ;

	protected abstract void parseEndElement(QName nameElement, String strElementText);

	protected void parseComment(String strText) {

	}

	protected void parseOther(int nEventType) {

	}
	
	protected String getElementText() 
			throws XMLStreamException {
		return _reader.get().getElementText();
	}
	
	public final void start(XMLStreamReader reader) {
		Objects.requireNonNull(reader, "reader");
		_reader.set(reader);
		_listErrors.clear();
		_state.start();
		parseInit();
	}
	
	public final void interpret(int nEvent) 
			throws XMLStreamException {
		if (_reader.isEmpty()) {
			throw new IllegalStateException("no defined reader");
		}
		XMLStreamReader reader = _reader.get();
		Location loc = reader.getLocation();
		_state.setCurrentLocation(loc);
		QName nameCurrent = null;
		StreamElementAttributeSet attribs = null;
		String strElementText = null;
		{
			String strEvent = DICT_EVENTS.getNameForCode(nEvent);
			ObservationCollector collectNode = _writerObs.createCollector(ObsLevel.DEBUG);
			collectNode.append("read event ");
			collectNode.append(strEvent);
			collectNode.append(" at location ");
			collectNode.append( XmlStreamMethods.format(loc));
			if (nEvent == START_ELEMENT) {
				nameCurrent = reader.getName();
				attribs = new StreamElementAttributeSet(reader);
				collectNode.append(" start element ");
				collectNode.append(nameCurrent);
				collectNode.append(", ");
				collectNode.append(attribs.size());
				collectNode.append(" attributes");		
			} else if (nEvent == END_ELEMENT) {
				nameCurrent = reader.getName();
				collectNode.append(" end element ");
				collectNode.append(nameCurrent);
			}
			collectNode.commit();
		}
		switch (nEvent) {
		case START_DOCUMENT:
			break;
			
		case END_DOCUMENT:
			_state.clearAtElementStart();
			break;
			
		case PROCESSING_INSTRUCTION:
			break;
		
		case START_ELEMENT:
			_collectText.reset();
			_state.startElement(nameCurrent);
			parseStartElement(nameCurrent, attribs);
			break;
			
		case END_ELEMENT:
			if (!_collectText.isEmpty()) {
				strElementText = gatherElementText(nameCurrent);
				_writerObs.write(ObsLevel.DEBUG, "pending text " + strElementText.length() + " chars");
			}
			_state.endElement(nameCurrent);
			parseEndElement(nameCurrent, strElementText);			
			break;
			
		case COMMENT:
			strElementText = XmlParseMethods.getTextChars(reader).toString();
			if (_options.getFlag(XmlParserOptions.PROPERTY_RECORD_COMMENTS)) {
				_writerObs.write(ObsLevel.INFO, "at " + XmlStreamMethods.format(loc) 
						+ " comment: " + StringMethods.encloseInQuotes(strElementText));
			}
			parseComment(strElementText);
			break;
			
		case CHARACTERS:
			if (_state.isAtElementStart()) {
				_collectText.acceptChars(reader);
			}
			break;
			
		case CDATA:
			if (_state.isAtElementStart()) {
				_collectText.acceptCData(reader);
			}
			break;
			
		case SPACE:
			break;
			
		default:
			_state.clearAtElementStart();
			parseOther(nEvent);
			break;
		}
		
		_state.setPriorEventType(nEvent);
	}
	
	public final void completed() {
		_state.reset();
		_reader.clear();
		_writerObs.write(ObsLevel.TRACE, "parse completed");
	}
}
