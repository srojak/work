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

import srojak.core.InvalidOperationException;
import srojak.core.containers.SingletonContainer;
import srojak.core.data.DataErrorSeverity;
import srojak.core.logic.FlagsInt;
import srojak.core.logic.FlagsIntTest;
import srojak.core.observe.HasSingleChangeableObservationCollector;
import srojak.core.observe.ObservationCollector;
import srojak.core.observe.TraceLevel;
import srojak.core.observe.writers.ObservationWriterBase;
import srojak.core.observe.writers.ObservationWriterPrintStream;
import srojak.core.text.PartialTextBuffer;
import srojak.core.text.TextBufferSegment;
import srojak.xml.stream.XmlStreamEventsDictionary;
import srojak.xml.stream.errors.XmlStreamParseErrorDescr;
import srojak.xml.stream.errors.XmlStreamParseErrorEntry;
import srojak.xml.stream.impl.StreamParserStateBasicCtnr;

/**
 * @author Stephen
 *
 */
public abstract class XmlStreamParserCommonBase 
		implements XMLStreamConstants, HasSingleChangeableObservationCollector {
	private final StreamParserStateBasicCtnr _state;
	private final FlagsInt _flags;
	private final SingletonContainer<XMLStreamReader> _reader;
	private final LinkedList<XmlStreamParseErrorDescr> _listErrors;
	private ObservationCollector _collectObs;	
	
	public static final int FLAGS_RECORD_COMMENTS = 0x1;
	public static final int FLAGS_REMOVE_LEADING_WS = 0x2;
	public static final int FLAGS_CAN_GET_ELEMENT_TEXT = 0x4;
	
	protected static final XmlStreamEventsDictionary DICT_EVENTS;

	static {
		DICT_EVENTS = new XmlStreamEventsDictionary();		
	}
	
	protected static String getTextForEvent(int nEvent) {
		return DICT_EVENTS.getNameForCode(nEvent);
	}
	
	protected XmlStreamParserCommonBase(boolean bDefaultToNullWriter) {
		_state = new StreamParserStateBasicCtnr();
		_flags = new FlagsInt();
		_reader = new SingletonContainer<XMLStreamReader>();
		_listErrors = new LinkedList<XmlStreamParseErrorDescr>();	
		_collectObs = ObservationCollector.makeInstance();
		if (!bDefaultToNullWriter) {
			ObservationWriterBase writer = new ObservationWriterPrintStream(System.err);
			_collectObs.addWriterPermanent(writer);
		}
	}

	@Override
	public ObservationCollector getObservationCollector() {
		return _collectObs;
	}
	
	@Override
	public void setObservationCollector(ObservationCollector collector) {
		Objects.requireNonNull(collector, "collector");
		_collectObs = collector;
	}

	public FlagsIntTest getFlags() {
		return _flags;
	}
	
	public boolean recordComments() {
		return _flags.test(FLAGS_RECORD_COMMENTS);
	}
	
	public void setRecordComments(boolean bState) {
		_flags.apply(bState, FLAGS_RECORD_COMMENTS);
	}
	
	public boolean removeLeadingWhiteSpace() {
		return _flags.test(FLAGS_REMOVE_LEADING_WS);
	}
	
	public void setRemoveLeadingWhiteSpace(boolean bState) {
		_flags.apply(bState, FLAGS_REMOVE_LEADING_WS);
	}
	
	public boolean canGetElementText() {
		return _flags.test(FLAGS_CAN_GET_ELEMENT_TEXT);
	}
	
	public void setCanGetElementText(boolean bState) {
		_flags.apply(bState, FLAGS_CAN_GET_ELEMENT_TEXT);
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
	
	protected void recordElementParseError(Location location, QName nameElement, DataErrorSeverity severity, String strText) {
		Objects.requireNonNull(location, "location");
		Objects.requireNonNull(nameElement, "nameElement");
		Objects.requireNonNull(severity, "severity");
		Objects.requireNonNull(strText, "strText");
		XmlStreamParseErrorEntry error = new XmlStreamParseErrorEntry(location,
				nameElement, severity, strText);
		_listErrors.add(error);
	}
	
	public XmlStreamParserState getParserState() {
		return _state;
	}
	
	protected abstract void prepareToRun();

	public final void start(XMLStreamReader reader) {
		Objects.requireNonNull(reader, "reader");
		_collectObs.writeTraceEnter(TraceLevel.HIGH, this);
		_reader.set(reader);
		_state.start();
		_listErrors.clear();
		prepareToRun();
	}
	
	protected TextBufferSegment getCharacterSegment() {
		XMLStreamReader reader = _reader.get();
		return new PartialTextBuffer(reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
	}
	
	protected abstract void interpret(int nEvent, Location loc, StreamParserStateBasicCtnr ctnrState, String strEventType)
			throws XMLStreamException;
	
	public final void interpret(int nEvent)
			throws XMLStreamException {
		if (_reader.isEmpty()) {
			throw new IllegalStateException("no defined reader");
		}
		XMLStreamReader reader = _reader.get();
		Location loc = reader.getLocation();
		_state.setCurrentLocation(loc);
		
		interpret(nEvent, loc, _state, DICT_EVENTS.getNameForCode(nEvent));
		
		_state.setPriorEventType(nEvent);
	}
	
	protected final void onEachRead() {
	}
	
	protected final QName getElementName() {
		return _reader.get().getName();
	}
	
	protected final StreamElementAttributeSet getAttributes() {
		XMLStreamReader reader = _reader.get();
		if (reader.getEventType() != START_ELEMENT) {
			throw new IllegalStateException("not at start of an element");
		}
		return new StreamElementAttributeSet(reader);
	}
	
	protected String getElementText() 
			throws XMLStreamException {
		if (!_flags.test(FLAGS_CAN_GET_ELEMENT_TEXT)) {
			throw new InvalidOperationException(this.getClass().getSimpleName(), "restricted reader operation");
		}
		return _reader.get().getElementText();
	}
	
	public final void completed() {
		_state.reset();
		_reader.clear();
		_collectObs.writeTraceReturn(TraceLevel.HIGH, this, () -> "parse completed");
	}
	
}
