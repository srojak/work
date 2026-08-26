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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

import srojak.core.observe.ObsLevel;
import srojak.core.observe.ObservationWriter;
import srojak.core.observe.ObservationWriterPrintStream;
import srojak.xml.XmlParseTextFilter;
import srojak.xml.stream.impl.XmlStreamElementAttribute;
import srojak.xml.stream.impl.XmlStreamParserStateContainer;

/**
 * @author Stephen
 *
 */
public class TrialStreamReaderDelegate
		extends StreamReaderDelegate
		implements XMLStreamConstants {
	private final XmlStreamParserStateContainer _state;
	private final XmlParserOptions _options;
	private final HashSet<QName> _setNamesWithText;
	private final List<StreamElementAttribute> _listAttribs;
	private ObservationWriter _writer;
	private int _nEventLast;
	private int _nEventPending;
	private boolean _bDoneOnce;
	
	protected static final XmlStreamEventsDictionary DICT_EVENTS;
	
	static {
		DICT_EVENTS = new XmlStreamEventsDictionary();
	}

	/**
	 * 
	 */
	public TrialStreamReaderDelegate() {
		super();
		_state = new XmlStreamParserStateContainer();
		_options = new XmlParserOptions();
		_setNamesWithText = new HashSet<QName>();
		_listAttribs = new LinkedList<StreamElementAttribute>();
		_writer = new ObservationWriterPrintStream(System.out);
		_nEventLast = -1;
		_nEventPending = -1;
		_bDoneOnce = false;
	}

	/**
	 * @param reader
	 */
	public TrialStreamReaderDelegate(XMLStreamReader reader) {
		super(reader);
		_state = new XmlStreamParserStateContainer();
		_options = new XmlParserOptions();
		_setNamesWithText = new HashSet<QName>();
		_listAttribs = new LinkedList<StreamElementAttribute>();
		_writer = new ObservationWriterPrintStream(System.out);
		_nEventLast = -1;
		_nEventPending = -1;
		_bDoneOnce = false;
	}
	
	public void addNameWithText(QName name) {
		Objects.requireNonNull(name, "name");
		_setNamesWithText.add(name);
	}
	
	private void readAttributes() {
		int nCount = getAttributeCount();
		for (int index = 0; index < nCount; index++ ) {
			_listAttribs.add(new XmlStreamElementAttribute(this, index));
		}
	}
	
	private void getCharacters(StringBuilder sb) {
		char[] charText = getTextCharacters();
		sb.append(charText);
	}

	@Override
	public int getEventType() {
		if (_nEventLast >= 1) {
			_writer.write(ObsLevel.NOTICE, "sending event type " + DICT_EVENTS.getNameForCode(_nEventLast));
			return _nEventLast;
		}
		return super.getEventType();
	}

	@Override
	public String getElementText() throws XMLStreamException {
		// throws the reader out of sync
		throw new UnsupportedOperationException("not available");
	}

	@Override
	public int next() 
			throws XMLStreamException {
		if (_nEventPending >= 1) {
			int nEvent = _nEventPending;
			_nEventPending = -1;
			_nEventLast = -1;
			_writer.write(ObsLevel.NOTICE, "pending event " + DICT_EVENTS.getNameForCode(nEvent));
			return nEvent;
		}
		int n = super.next();
		String strEvent = DICT_EVENTS.getNameForCode(n);
		Location loc = getLocation();
		StringBuilder sbNode = new StringBuilder();
		sbNode.append("read event ");
		sbNode.append(strEvent);
		sbNode.append(" at location ");
		sbNode.append( XmlStreamMethods.format(loc));
		QName nameCurrent;
		XmlParseTextFilter filter = _options.getTextFilter();
		String strSecondMessage = null;
		_listAttribs.clear();
		
		switch (n) {
		case START_DOCUMENT:
			break;
			
		case END_DOCUMENT:
			_state.clearAtElementStart();
			break;
			
		case PROCESSING_INSTRUCTION:
			break;
			
		case START_ELEMENT:
			nameCurrent = getName();
			readAttributes();
			sbNode.append(" name=");
			sbNode.append(nameCurrent);
			sbNode.append(", ");
			sbNode.append(_listAttribs.size());
			sbNode.append(" attrs");
			_state.startElement(nameCurrent);
			if (_setNamesWithText.contains(nameCurrent)) {
				//_nEventLast = super.getEventType();
				String strText = super.getElementText();
				strSecondMessage = "element text: \"" + strText + "\"";
				//_nEventPending = END_ELEMENT;
			}
			if (!_bDoneOnce) {
				Class<?> classLoc = loc.getClass();
				_writer.write(ObsLevel.INFO, "locator class is " + classLoc.getName());
			}
			break;
			
		case END_ELEMENT:
			nameCurrent = getName();
			sbNode.append(" name=");
			sbNode.append(nameCurrent);
			{
				StringBuilder sbText = new StringBuilder();
				_state.endElement(nameCurrent, _options, sbText);
				/*
				if (sbText.length() > 0) {
					strSecondMessage = "gathered text: \"" + sbText.toString() + "\"";
				}
				*/
			}
			break;
			
		case COMMENT:
			strSecondMessage = "comment: " + getText();
			break;
			
		case CHARACTERS:
			{
				StringBuilder sbChars = new StringBuilder();
				getCharacters(sbChars);
				String strText = filter.filterCharacters(_state, 
						_options.ignoreExtraWhiteSpace(), sbChars.toString());
				_state.saveCharacters(strText);
			}
			break;
			
		case CDATA:
			filter = _options.getTextFilter();
			{
				StringBuilder sbChars = new StringBuilder();
				getCharacters(sbChars);
				String strText = filter.filterCharacters(_state, 
						_options.ignoreExtraWhiteSpace(), sbChars.toString());
				_state.saveCharacters(strText);
			}
			break;
		}
		_writer.write(ObsLevel.INFO, sbNode.toString());
		if (!_listAttribs.isEmpty()) {
			_writer.buildAndWrite(ObsLevel.DETAIL, sb -> {
				sb.append("attributes:");
				for (StreamElementAttribute attr : _listAttribs) {
					sb.append("\n  ");
					sb.append(attr.getName());
				}
			});
		}
		if (strSecondMessage != null) {
			_writer.write(ObsLevel.DETAIL, strSecondMessage);
		}
		return n;
	}

}
