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
package srojak.xml.stream.parse;

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
import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.SingleObservationCollector;
import srojak.core.text.PartialTextBuffer;
import srojak.core.text.TextBufferSegment;
import srojak.core.tools.StringMethods;
import srojak.xml.stream.XmlPendingTextCollector;
import srojak.xml.stream.XmlStreamEventsDictionary;
import srojak.xml.stream.XmlStreamMethods;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;
import srojak.xml.stream.errors.XmlStreamParseErrorEntry;
import srojak.xml.stream.impl.StreamParserStateBasicCtnr;
import srojak.xml.stream.work.XmlStreamWorkItemMap;

/**
 * @author Stephen
 *
 * @see https://docs.oracle.com/javase/8/docs/api/javax/xml/stream/XMLStreamReader.html
 */
public abstract class XmlStreamActionParserBase 
		implements XMLStreamConstants, XmlStreamParser {
	private final StreamParserStateBasicCtnr _state;
	private final XmlStreamWorkItemMap _mapWork;	
	private final SingletonContainer<XMLStreamReader> _reader;
	private final XmlPendingTextCollector _collectText;
	private final LinkedList<XmlStreamParseErrorDescr> _listErrors;
	private ObservationCollector _collectObs;
	private boolean _bRecordComments;
	
	protected static final XmlStreamEventsDictionary DICT_EVENTS;
	protected static final boolean DEFAULT_TO_NULL_WRITER = false;
	
	static {
		DICT_EVENTS = new XmlStreamEventsDictionary();
	}

	protected XmlStreamActionParserBase() {
		_state = new StreamParserStateBasicCtnr();
		_mapWork = new XmlStreamWorkItemMap();
		_reader = new SingletonContainer<XMLStreamReader>();
		_collectText = new XmlPendingTextCollector();
		_listErrors = new LinkedList<XmlStreamParseErrorDescr>();
		_collectObs = ObservationCollector.makeInstance();
		_bRecordComments = false;
	}
	
	protected final XmlStreamParserState getParserState() {
		return _state;
	}
	
	protected final XmlStreamWorkItemMap getWorkItemMap() {
		return _mapWork;
	}
	
	public final boolean hasParseErrors() {
		return !_listErrors.isEmpty();
	}
	
	public final List<XmlStreamParseErrorDescr> getParseErrors() {
		return List.copyOf(_listErrors);
	}
	
	public final void writeError(XmlStreamParseErrorDescr error) {
		Objects.requireNonNull(error, "error");
		_listErrors.add(error);
	}
	
	@Override
	public final ObservationCollector getObservationCollector() {
		return _collectObs;
	}
	
	/*
	@Override
	public final void setObservationCollector(ObservationCollector collector) {
		Objects.requireNonNull(collector, "collector");
		_collectObs = collector;
	}
	*/
	
	@Override
	public boolean recordComments() {
		return _bRecordComments;
	}

	@Override
	public void setRecordComments(boolean bState) {
		_bRecordComments = bState;
	}

	@Override
	public boolean removeLeadingWhiteSpace() {
		return _collectText.ignoreInitialWhiteSpace();
	}

	@Override
	public void setRemoveLeadingWhiteSpace(boolean bState) {
		_collectText.setIgnoreInitialWhiteSpace(bState);
	}
	
	protected TextBufferSegment getCharacterSegment() {
		XMLStreamReader reader = _reader.get();
		return new PartialTextBuffer(reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
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

	protected abstract void parseStartElement(QName nameElement, XmlStreamWorkItemMap mapWork, StreamElementAttributeSet attribs) 
			throws XMLStreamException ;

	protected abstract void parseEndElement(QName nameElement, XmlStreamWorkItemMap mapWork, String strElementText);

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
		_mapWork.clear();
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
		TextBufferSegment segment = null;
		{
			String strEvent = DICT_EVENTS.getNameForCode(nEvent);
			SingleObservationCollector collectNode = _collectObs.createCollector(ObsLevel.DEBUG2);
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
			parseStartElement(nameCurrent, _mapWork, attribs);
			break;
			
		case END_ELEMENT:
			if (!_collectText.isEmpty()) {
				strElementText = gatherElementText(nameCurrent);
				_collectObs.write(ObsLevel.DEBUG, "pending text " + strElementText.length() + " chars");
			}
			_state.endElement(nameCurrent);
			parseEndElement(nameCurrent, _mapWork, strElementText);			
			break;
			
		case COMMENT:
			segment = getCharacterSegment();
			if (_bRecordComments) {
				_collectObs.write(ObsLevel.INFO, "at " + XmlStreamMethods.format(loc) 
						+ " comment: " + StringMethods.encloseInQuotes(segment.copySegment()));
			}
			parseComment(segment.copySegment());
			break;
			
		case CHARACTERS:
			if (_state.isAtElementStart()) {
				segment = getCharacterSegment();
				_collectText.acceptChars(segment);
			}
			break;
			
		case CDATA:
			if (_state.isAtElementStart()) {
				segment = getCharacterSegment();
				_collectText.acceptCData(segment);
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
		_collectObs.write(ObsLevel.TRACE, "parse completed");
	}
}
